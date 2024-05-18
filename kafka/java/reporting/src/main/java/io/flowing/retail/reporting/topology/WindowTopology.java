package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.json.BookingEntrySerdes;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.helpers.Constants;
import io.flowing.retail.reporting.helpers.EventDateTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;

import java.awt.print.Book;

public class WindowTopology {

    public static Topology build() {

        StreamsBuilder builder = new StreamsBuilder();
        //Window Aggregation
        Consumed<Integer, BookingEntry> bookingEntryConsumerOptions = Consumed.with(Serdes.Integer(), new BookingEntrySerdes())
                .withTimestampExtractor(new EventDateTimeExtractor());

        // Source Processor
        /*KStream<byte[], BookingEntry> stream =
                builder.stream("bookings_simple", bookingEntryConsumerOptions);*/
        KStream<Integer, BookingEntry> stream =
                builder.stream("bookings", Consumed.with(Serdes.Integer(), new BookingEntrySerdes()));

        TimeWindows tumblingWindow = TimeWindows.ofSizeWithNoGrace(Constants.WINDOW_SIZE);

        // Stream processor
        stream.foreach((key, value) -> System.out.println(" Value: " + value));

        /*KTable<Windowed<byte[]>, Long> eventDateTimeCounts = stream
                .groupByKey()
                .windowedBy(tumblingWindow)
                .count(Materialized.as("eventDateTimeCounts"));
        //.suppress(Supp1ressed.untilWindowCloses(Suppressed.BufferConfig.unbounded().shutDownWhenFull()));

        // For debugging only
        eventDateTimeCounts.toStream().print(Printed.<Windowed<byte[]>, Long>toSysOut().withLabel("eventDateTimeCounts"));*/

        return builder.build();
    }
}
