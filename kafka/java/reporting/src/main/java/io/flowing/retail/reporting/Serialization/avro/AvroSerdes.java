package io.flowing.retail.reporting.Serialization.avro;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import org.apache.kafka.common.serialization.Serde;

import java.util.Collections;
import java.util.Map;

public class AvroSerdes {

    public static Serde<AnonymizedBookingEntry> avroAnonymizedBookingEntry(String url, boolean isKey) {
        Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", url);
        Serde<AnonymizedBookingEntry> serde = new SpecificAvroSerde<>();
        serde.configure(serdeConfig, isKey);
        return serde;
    }
}
