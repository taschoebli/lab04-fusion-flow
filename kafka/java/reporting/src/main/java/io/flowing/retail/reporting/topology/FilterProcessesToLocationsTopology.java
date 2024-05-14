package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.avro.AvroSerdes;
import io.flowing.retail.reporting.Serialization.json.BookingEntrySerdes;


import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.helpers.Constants;
import io.flowing.retail.reporting.partitioner.LocationPartitioner;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;


public class FilterProcessesToLocationsTopology {

    public static Topology build(){
        StreamsBuilder builder = new StreamsBuilder();

        KStream<byte[], BookingEntry> stream =
                builder.stream("bookings", Consumed.with(Serdes.ByteArray(), new BookingEntrySerdes()));

        //Step one: filter out timestamp
        KStream<byte[], BookingEntry> bookingEntriesWithoutTimestamp =
                stream.mapValues(
                        (BookingEntry) -> {
                            BookingEntry bookingEntryWithoutTimestamp = new BookingEntry();
                            bookingEntryWithoutTimestamp.setId(BookingEntry.getId());
                            bookingEntryWithoutTimestamp.setLocationId(BookingEntry.getLocationId());
                            bookingEntryWithoutTimestamp.setBookingKey(BookingEntry.getBookingKey());
                            bookingEntryWithoutTimestamp.setProductName(BookingEntry.getProductName());
                            bookingEntryWithoutTimestamp.setCustomerName(BookingEntry.getCustomerName());
                            bookingEntryWithoutTimestamp.setBookingDateTime(BookingEntry.getBookingDateTime());
                            bookingEntryWithoutTimestamp.setEventDateTime(BookingEntry.getEventDateTime());
                            bookingEntryWithoutTimestamp.setAmount(BookingEntry.getAmount());
                            bookingEntryWithoutTimestamp.setPaymentStatusIsPaid(BookingEntry.getPaymentStatusIsPaid());
                            return bookingEntryWithoutTimestamp;
                        });

        //Step two: anonymize the customer
        KStream<byte[], AnonymizedBookingEntry> anonymizedBookingEntries =
                bookingEntriesWithoutTimestamp.mapValues(
                        (BookingEntry) -> {
                            AnonymizedBookingEntry anonymizedBookingEntry =
                                    AnonymizedBookingEntry.newBuilder()
                                            .setId(BookingEntry.getId())
                                            .setLocationId(BookingEntry.getLocationId())
                                            .setBookingKey(BookingEntry.getBookingKey())
                                            .setProductName(BookingEntry.getProductName())
                                            .setAnonymizedCustomer(BookingEntry.getCustomerName().hashCode())
                                            .setBookingDateTime(BookingEntry.getBookingDateTime())
                                            .setEventDateTime(BookingEntry.getEventDateTime())
                                            .setAmount(BookingEntry.getAmount())
                                            .setPaymentStatusIsPaid(BookingEntry.getPaymentStatusIsPaid())
                                            .build();

                            return anonymizedBookingEntry;
                        });

        //step three: routing
        KStream<byte[], AnonymizedBookingEntry>[] locationBranches = anonymizedBookingEntries.branch(
                (k, anonymizedBookingEntry) -> (anonymizedBookingEntry.getLocationId() == 1 || anonymizedBookingEntry.getLocationId() == 11),
                (k, anonymizedBookingEntry) -> (anonymizedBookingEntry.getLocationId() == 3 || anonymizedBookingEntry.getLocationId() == 30
                                               || anonymizedBookingEntry.getLocationId() == 31),
                (k, anonymizedBookingEntry) -> (anonymizedBookingEntry.getLocationId() == 2 ));

        // Name the branches
        for (int i = 0; i < locationBranches.length; i++) {
            KStream<byte[], AnonymizedBookingEntry> branch = locationBranches[i];

            int i_helper = i;
            KStream<String, AnonymizedBookingEntry> keyedStream = branch.selectKey((key, value) -> {
                if (i_helper==0) {
                    return Constants.LOCATION_ZUERICH;
                } else if (i_helper == 1) {
                    return Constants.LOCATION_STGALLEN;
                } else if (i_helper == 2) {
                    return Constants.LOCATION_BERN;
                } else {
                    return Constants.INVALID_LOCATION;
                }
            });

            // Write to the output topic
            keyedStream.to(
                    "locationPartitionedBookings",
                    Produced.with(
                            Serdes.String(),
                            AvroSerdes.avroAnonymizedBookingEntry("http://localhost:8081", false),
                            new LocationPartitioner()));
        }

        // Stateful processing -> Bookings per Location Aggregation
        // TODO: check: isnt it said in the slides that aggregation based on properties isnt stateful?
        KStream<String, AnonymizedBookingEntry> allKeyedStream = anonymizedBookingEntries.selectKey((key, value) -> {
            if (value.getLocationId() == 1 || value.getLocationId() == 11) {
                return Constants.LOCATION_ZUERICH;
            } else if (value.getLocationId() == 3 || value.getLocationId() == 30 || value.getLocationId() == 31) {
                return Constants.LOCATION_STGALLEN;
            } else if (value.getLocationId() == 2) {
                return Constants.LOCATION_BERN;
            } else {
                return Constants.INVALID_LOCATION;
            }
        });
        KGroupedStream<String, AnonymizedBookingEntry> groupedByLocation = allKeyedStream.groupByKey(Grouped.with(Serdes.String(), AvroSerdes.avroAnonymizedBookingEntry("http://localhost:8081", false)));
        KTable<String, Long> bookingCountByLocation = groupedByLocation.count(Materialized.as("bookingCount"));

        return builder.build();
    }
}
