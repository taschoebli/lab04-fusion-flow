package io.flowing.retail.reporting.Serialization.json;

import com.google.gson.Gson;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.Serialization.model.SessionInfo;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

class SessionInfoSerializer implements Serializer<SessionInfo> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, SessionInfo info) {
        if (info == null) return null;
        return gson.toJson(info).getBytes(StandardCharsets.UTF_8);
    }
}
