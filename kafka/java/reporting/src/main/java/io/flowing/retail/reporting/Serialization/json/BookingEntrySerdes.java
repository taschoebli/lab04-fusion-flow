package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class BookingEntrySerdes implements Serde<BookingEntry> {

    @Override
    public Serializer<BookingEntry> serializer() {
        return new BookingEntrySerializer();
    }

    @Override
    public Deserializer<BookingEntry> deserializer() {
        return new BookingEntryDeserializer();
    }
}
