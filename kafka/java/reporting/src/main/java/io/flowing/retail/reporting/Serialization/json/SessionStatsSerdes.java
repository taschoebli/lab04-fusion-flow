package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.aggregations.SessionStats;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class SessionStatsSerdes implements Serde<SessionStats> {

    @Override
    public Serializer<SessionStats> serializer() {
        return new SessionStatsSerializer();
    }

    @Override
    public Deserializer<SessionStats> deserializer() {
        return new SessionStatsDeserializer();
    }
}
