package io.flowing.retail.reporting.application;

import io.flowing.retail.reporting.Serialization.model.aggregations.SessionStats;
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
    private final KafkaStreams[] streams;

    Map<String, Long> fraudulentCount = new HashMap<>();
    Map<String, Long> eventDayCount = new HashMap<>();

    public ReportingService(HostInfo hostInfo, KafkaStreams[] streams) {
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
        app.get("/locationMonitor", this::getLocationCount);
        app.get("/windowMonitor", this::getEventDateTimeCount);
        app.get("/fraudDetection", this::getSuspiciousCustomers);
        app.get("/sessionMonitor", this::getSessionStats);

    }

    void getLocationCount(Context ctx) {
        Map<String, Long> monitor = new HashMap<>();

        ReadOnlyKeyValueStore<String, Long> store = streams[0].store(
                StoreQueryParameters.fromNameAndType(
                        "bookingCount",
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

    void getSuspiciousCustomers(Context ctx) {
        ReadOnlyWindowStore<String, Long> store = streams[3].store(
                StoreQueryParameters.fromNameAndType(
                        "customerCounts",
                        QueryableStoreTypes.windowStore()));

        KeyValueIterator<Windowed<String>, Long> frauds = store.all();
        while (frauds.hasNext()) {
            KeyValue<Windowed<String>, Long> next = frauds.next();
            if (next.value == 1) continue;
            Windowed<String> key = next.key;
            Long value = next.value;
            //save customer as fraudulent
            fraudulentCount.put(key.toString(), value);
        }
        ctx.json(fraudulentCount);
        frauds.close();
    }


    void getSessionStats(Context ctx){
        Map<String, SessionStats> monitor = new HashMap<>();

        ReadOnlyKeyValueStore<String, SessionStats> store = streams[1].store(
                StoreQueryParameters.fromNameAndType(
                        "sessionStats",
                        QueryableStoreTypes.keyValueStore()));

        KeyValueIterator<String, SessionStats> range = store.all();
        while (range.hasNext()) {
            KeyValue<String, SessionStats> next = range.next();
            String location = next.key;
            SessionStats stats = next.value;
            monitor.put(location, stats);
        }
        range.close();
        ctx.json(monitor);
    }

    void getEventDateTimeCount(Context ctx) {

        ReadOnlyWindowStore<String, Long> store = streams[2].store(
                StoreQueryParameters.fromNameAndType(
                        "eventDateTimeCounts",
                        QueryableStoreTypes.windowStore()));

        try (KeyValueIterator<Windowed<String>, Long> range = store.all()) {
            while (range.hasNext()) {
                KeyValue<Windowed<String>, Long> next = range.next();
                if(next.value == 1) continue;
                Windowed<String> key = next.key;
                long value = next.value;
                //Save event day count
                eventDayCount.put(key.toString(), value);
            }
            range.close();
            ctx.json(eventDayCount);
        }
    }
}
