package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.json.BookingEntrySerdes;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.helpers.BookingTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;

public class FraudDetectionTopology {

    public static Topology build() {

        StreamsBuilder builder = new StreamsBuilder();
        //Window Aggregation
        Consumed<Integer, BookingEntry> bookingEntryConsumerOptions = Consumed.with(Serdes.Integer(), new BookingEntrySerdes())
                .withTimestampExtractor(new BookingTimeExtractor());

        // Source Processor
        KStream<Integer, BookingEntry> stream =
                builder.stream("bookings", bookingEntryConsumerOptions);

        //we are interested in seeing if a customer made bookings suspiciously close to one another - in our case, if a
        //customer tries to place multiple bookings within one minute
        SlidingWindows slidingWindow = SlidingWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(1));

        KTable<Windowed<String>, Long> customerCounts =
                stream
                        .groupBy((k, v) -> v.getCustomerName(), Grouped.with(Serdes.String(), new BookingEntrySerdes()))
                        .windowedBy(slidingWindow)
                        .count(Materialized.<String, Long, WindowStore<Bytes, byte[]>>as("customerCounts")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.Long()));

        return builder.build();
    }
}
