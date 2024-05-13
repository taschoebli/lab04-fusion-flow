package io.flowing.retail.reporting.topology;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import io.javalin.Javalin;
import io.javalin.http.Context;
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
        Javalin app = Javalin.create(config -> {config.staticFiles.add("/public");}).start(hostInfo.port());

        // Define a route for querying in the key-value store
        app.get("/locationMonitor", this::getLocationCount);

    }

    void getLocationCount(Context ctx){
        Map<String, Long> monitor = new HashMap<>();

        ReadOnlyKeyValueStore<String, Long> store = streams.store(
                StoreQueryParameters.fromNameAndType(
                        "bookings-zuerich",
                        QueryableStoreTypes.keyValueStore()));

        KeyValueIterator<String, Long> range = store.all();
        while (range.hasNext()) {
            KeyValue<String, Long> next = range.next();
            String aoi = next.key;
            long count = next.value;
            monitor.put(aoi, count);
        }
        range.close();
        ctx.json(monitor);
    }
}
