package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class AnonymizedBookingEntrySerdes implements Serde<AnonymizedBookingEntry> {

    @Override
    public Serializer<AnonymizedBookingEntry> serializer() {
        return new AnonymizedBookingEntrySerializer();
    }

    @Override
    public Deserializer<AnonymizedBookingEntry> deserializer() {
        return new AnonymizedBookingEntryDeserializer();
    }
}
