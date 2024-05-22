package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.json.*;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.Serialization.model.NonAvroAnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.SessionInfo;
import io.flowing.retail.reporting.Serialization.model.aggregations.SessionStats;
import io.flowing.retail.reporting.Serialization.model.joins.BookingEntrySessionInfo;
import io.flowing.retail.reporting.helpers.Constants;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CombineStreamsAndPrepareForReportingTopology {

    public static Topology build(){
        StreamsBuilder builder = new StreamsBuilder();

        KTable<Integer, BookingEntry> bookingsStream =
                builder.table("bookings", Consumed.with(Serdes.Integer(), new BookingEntrySerdes()));

        KStream<Integer, SessionInfo> sessionsSteam =
                builder.stream("sessionInfos", Consumed.with(Serdes.Integer(), new SessionInfoSerdes()));


        //Step one: filter out timestamp of bookings
        KTable<Integer, BookingEntry> bookingEntriesWithoutTimestamp =
                bookingsStream.mapValues(
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
        KTable<Integer, NonAvroAnonymizedBookingEntry> anonymizedBookingEntries =
                bookingEntriesWithoutTimestamp.mapValues(
                        (BookingEntry) -> {
                            NonAvroAnonymizedBookingEntry anonymizedBookingEntry = new NonAvroAnonymizedBookingEntry();
                            anonymizedBookingEntry.setBookingId(BookingEntry.getId());
                            anonymizedBookingEntry.setLocationId(BookingEntry.getLocationId());
                            anonymizedBookingEntry.setBookingKey(BookingEntry.getBookingKey());
                            anonymizedBookingEntry.setProductName(BookingEntry.getProductName());
                            anonymizedBookingEntry.setAnonymizedCustomer(BookingEntry.getCustomerName().hashCode());
                            anonymizedBookingEntry.setBookingDateTime(BookingEntry.getBookingDateTime());
                            anonymizedBookingEntry.setEventDateTime(BookingEntry.getEventDateTime());
                            anonymizedBookingEntry.setAmount(BookingEntry.getAmount());
                            anonymizedBookingEntry.setPaymentStatusIsPaid(BookingEntry.getPaymentStatusIsPaid());

                            return anonymizedBookingEntry;
                        });

        //step 4: join the streams - first create joiner
        ValueJoiner<SessionInfo, NonAvroAnonymizedBookingEntry, BookingEntrySessionInfo> valueJoiner = BookingEntrySessionInfo::new;

        // join params
        Joined<Integer, SessionInfo, NonAvroAnonymizedBookingEntry> joinParams =
                Joined.with(Serdes.Integer(), new SessionInfoSerdes(), new NonAvroAnonymizedBookingEntrySerdes());

        // Perform the join operation
        KStream<Integer, BookingEntrySessionInfo> joinedStream = sessionsSteam.join(
                anonymizedBookingEntries,
                valueJoiner,
                joinParams
        );

        //re-key the stream
        KStream<String, BookingEntrySessionInfo> allKeyedStream = joinedStream.selectKey((key, value) -> {
            if (value.getBookingEntry().getLocationId() == 1 || value.getBookingEntry().getLocationId() == 11) {
                return Constants.LOCATION_ZUERICH;
            } else if (value.getBookingEntry().getLocationId() == 3 || value.getBookingEntry().getLocationId() == 30 || value.getBookingEntry().getLocationId() == 31) {
                return Constants.LOCATION_STGALLEN;
            } else if (value.getBookingEntry().getLocationId() == 2) {
                return Constants.LOCATION_BERN;
            } else {
                return Constants.INVALID_LOCATION;
            }
        });

        KGroupedStream<String, BookingEntrySessionInfo> groupedByLocation = allKeyedStream.groupByKey(Grouped.with(Serdes.String(), new BookingEntrySessionInfoSerdes()));

        Initializer<SessionStats> SessionStatsInitializer = () -> new SessionStats(0,0, 0,0, 0);

        Aggregator<String, BookingEntrySessionInfo, SessionStats> sessionStatsAggregator = (key, bookingSession, sessionStats) -> {
         DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN);
            DateTime actualDateTime = formatter.parseDateTime(bookingSession.getSessionInfo().getActualStartTime());
            DateTime bookedDateTime = formatter.parseDateTime(String.valueOf(bookingSession.getBookingEntry().getEventDateTime()));
            float delay = ((float) (actualDateTime.getMillis() - bookedDateTime.getMillis()) / 1000 / 60);

            float newTotalDelay = (sessionStats.getTotalLostTime() + delay);
            int newTotalSessions = (sessionStats.getNumberOfSessions() + 1);
            int newTotalCustomersTooLate = delay != 0 ? (sessionStats.getNumberOfLateCustomers() + 1) : sessionStats.getNumberOfLateCustomers();
            float newAverageSessionDelay = newTotalCustomersTooLate == 0 ? 0 : (newTotalDelay / newTotalCustomersTooLate);
            float newPercentageCustomersTooLate = newTotalCustomersTooLate == 0 ? 0 :
                    ((float) newTotalCustomersTooLate / newTotalSessions * 100);
            return new SessionStats(newAverageSessionDelay, newPercentageCustomersTooLate, newTotalCustomersTooLate, newTotalDelay, newTotalSessions);
        };

        KTable<String, SessionStats> sessionStatsByLocation = groupedByLocation.aggregate(
                SessionStatsInitializer,
                sessionStatsAggregator,
                Materialized.<String, SessionStats, KeyValueStore<Bytes, byte[]>>as("sessionStats")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new SessionStatsSerdes())
                );


        return builder.build();
    }
}
