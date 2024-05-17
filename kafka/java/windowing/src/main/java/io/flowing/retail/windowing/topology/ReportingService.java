package io.flowing.retail.windowing.topology;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.*;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ReportingService {

    private final HostInfo hostInfo;
    private final KafkaStreams streams;

    public ReportingService(HostInfo hostInfo, KafkaStreams streams) {
        this.hostInfo = hostInfo;
        this.streams = streams;
    }

    // Start the Javalin web server and configure routes
    public void start() {

        // Create and start a Javalin server instance with the specified port
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        }).start(hostInfo.port());

        // Define a route for querying in the key-value store
        app.get("/windowMonitor", this::getEventDateTimeCount);
    }
    void getEventDateTimeCount(Context ctx) {
        Map<String, Long> monitor = new HashMap<>();

        ReadOnlyWindowStore<byte[], Long> store = streams.store(
                StoreQueryParameters.fromNameAndType(
                        "eventDateTimeCounts",
                        QueryableStoreTypes.windowStore()));

        try (KeyValueIterator<Windowed<byte[]>, Long> range = store.all()) {
            while (range.hasNext()) {
                KeyValue<Windowed<byte[]>, Long> next = range.next();
                String aoi = new String(next.key.key());
                long count = next.value;
                monitor.put(aoi, count);
            }
            range.close();
            ctx.json(monitor);
        }
    }
}