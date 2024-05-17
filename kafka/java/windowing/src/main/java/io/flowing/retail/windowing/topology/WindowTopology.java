package io.flowing.retail.windowing.topology;

import io.flowing.retail.windowing.Serialization.json.BookingEntrySerdes;
import io.flowing.retail.windowing.Serialization.model.BookingEntry;
import io.flowing.retail.windowing.helpers.Constants;
import io.flowing.retail.windowing.helpers.EventDateTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

public class WindowTopology {

    public static Topology build() {

        StreamsBuilder builder = new StreamsBuilder();
        //Window Aggregation
        Consumed<byte[], BookingEntry> bookingEntryConsumerOptions = Consumed.with(Serdes.ByteArray(), new BookingEntrySerdes())
                .withTimestampExtractor(new EventDateTimeExtractor());

        // Source Processor
        /*KStream<byte[], BookingEntry> stream =
                builder.stream("bookings_simple", bookingEntryConsumerOptions);*/
        KStream<Void, String> stream =
                builder.stream("bookings_simple");

        TimeWindows tumblingWindow = TimeWindows.ofSizeWithNoGrace(Constants.WINDOW_SIZE);

        // Stream processor
        stream.foreach((key, value) -> System.out.println(" Value: " + value));

        /*KTable<Windowed<byte[]>, Long> eventDateTimeCounts = stream
                .groupByKey()
                .windowedBy(tumblingWindow)
                .count(Materialized.as("eventDateTimeCounts"));
        //.suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded().shutDownWhenFull()));

        // For debugging only
        eventDateTimeCounts.toStream().print(Printed.<Windowed<byte[]>, Long>toSysOut().withLabel("eventDateTimeCounts"));*/

        return builder.build();
    }
}
