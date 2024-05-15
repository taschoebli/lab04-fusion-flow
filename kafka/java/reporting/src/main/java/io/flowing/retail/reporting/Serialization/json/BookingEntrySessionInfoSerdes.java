package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.Serialization.model.joins.BookingEntrySessionInfo;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class BookingEntrySessionInfoSerdes implements Serde<BookingEntrySessionInfo> {

    @Override
    public Serializer<BookingEntrySessionInfo> serializer() {
        return new BookingEntrySessionInfoSerializer();
    }

    @Override
    public Deserializer<BookingEntrySessionInfo> deserializer() {
        return new BookingEntrySessionInfoDeserializer();
    }
}
