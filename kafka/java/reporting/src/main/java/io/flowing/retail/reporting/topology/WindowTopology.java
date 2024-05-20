package io.flowing.retail.reporting.topology;

import io.flowing.retail.reporting.Serialization.json.BookingEntrySerdes;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.helpers.Constants;
import io.flowing.retail.reporting.helpers.EventDateTimeExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;

import java.awt.print.Book;
import java.time.Duration;

public class WindowTopology {

    public static Topology build() {

        StreamsBuilder builder = new StreamsBuilder();
        //Window Aggregation
        Consumed<Integer, BookingEntry> bookingEntryConsumerOptions = Consumed.with(Serdes.Integer(), new BookingEntrySerdes())
                .withTimestampExtractor(new EventDateTimeExtractor());

        // Source Processor
        KStream<Integer, BookingEntry> stream =
                builder.stream("bookings", bookingEntryConsumerOptions);

        // Now we will create a tumbling window of 1 day since we are interested in the number of bookings per day per Location
        TimeWindows tumblingWindow = TimeWindows.ofSizeWithNoGrace(Duration.ofDays(1));

        KTable<Windowed<String>, Long> eventDateTimeCounts =
                stream
                        .groupBy((k, v) -> Constants.locationIdToLocationString(v.getLocationId()), Grouped.with(Serdes.String(), new BookingEntrySerdes()))
                        .windowedBy(tumblingWindow)
                        .count(Materialized.<String, Long, WindowStore<Bytes, byte[]>>as("eventDateTimeCounts")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(Serdes.Long()));

        return builder.build();
    }
}
