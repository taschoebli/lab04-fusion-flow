package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.NonAvroAnonymizedBookingEntry;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class NonAvroAnonymizedBookingEntrySerdes implements Serde<NonAvroAnonymizedBookingEntry> {

    @Override
    public Serializer<NonAvroAnonymizedBookingEntry> serializer() {
        return new NonAvroAnonymizedBookingEntrySerializer();
    }

    @Override
    public Deserializer<NonAvroAnonymizedBookingEntry> deserializer() {
        return new NonAvroAnonymizedBookingEntryDeserializer();
    }
}
