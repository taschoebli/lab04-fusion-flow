package io.flowing.retail.reporting.Serialization.json;

import com.google.gson.Gson;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.aggregations.SessionStats;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

class SessionStatsSerializer implements Serializer<SessionStats> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, SessionStats entry) {
        if (entry == null) return null;
        return gson.toJson(entry).getBytes(StandardCharsets.UTF_8);
    }
}
