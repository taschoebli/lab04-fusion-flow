package io.flowing.retail.reporting.Serialization.json;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.aggregations.SessionStats;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class SessionStatsDeserializer implements Deserializer<SessionStats>{
    private Gson gson =
            new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

    @Override
    public SessionStats deserialize(String topic, byte[] bytes) {
        if (bytes == null) return null;
        return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), SessionStats.class);
    }
}
