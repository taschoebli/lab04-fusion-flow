package io.flowing.retail.reporting.Serialization.json;

import com.google.gson.Gson;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.Serialization.model.NonAvroAnonymizedBookingEntry;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

class NonAvroAnonymizedBookingEntrySerializer implements Serializer<NonAvroAnonymizedBookingEntry> {
    private Gson gson = new Gson();

    @Override
    public byte[] serialize(String topic, NonAvroAnonymizedBookingEntry entry) {
        if (entry == null) return null;
        return gson.toJson(entry).getBytes(StandardCharsets.UTF_8);
    }
}
