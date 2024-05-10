package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.json.BookingEntrySerdes;


import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;

public class FilterProcessesToLocationsTopology {

    public static Topology build(){
        StreamsBuilder builder = new StreamsBuilder();

        KStream<byte[], BookingEntry> stream =
                builder.stream("bookings", Consumed.with(Serdes.ByteArray(), new BookingEntrySerdes()));
        stream.print(Printed.<byte[], BookingEntry>toSysOut().withLabel("BookingEntry-stream"));

        //Step one: filter out timestamp
        KStream<byte[], BookingEntry> bookingEntriesWithoutTimestamp =
                stream.mapValues(
                        (BookingEntry) -> {
                            BookingEntry bookingEntryWithoutTimestamp = new BookingEntry();
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

        anonymizedBookingEntries.print(Printed.<byte[], AnonymizedBookingEntry>toSysOut().withLabel("Anonymized booking entry stream"));



        return builder.build();
    }
}
