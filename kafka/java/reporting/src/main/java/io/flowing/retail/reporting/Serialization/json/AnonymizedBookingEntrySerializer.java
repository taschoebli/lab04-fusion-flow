package io.flowing.retail.reporting.Serialization.json;

import com.google.gson.Gson;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

class AnonymizedBookingEntrySerializer implements Serializer<AnonymizedBookingEntry> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, AnonymizedBookingEntry entry) {
        if (entry == null) return null;
        return gson.toJson(entry).getBytes(StandardCharsets.UTF_8);
    }
}
