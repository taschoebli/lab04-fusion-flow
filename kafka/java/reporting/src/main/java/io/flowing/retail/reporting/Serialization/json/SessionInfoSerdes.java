package io.flowing.retail.reporting.Serialization.json;
import io.flowing.retail.reporting.Serialization.model.SessionInfo;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

public class SessionInfoSerdes implements Serde<SessionInfo> {

    @Override
    public Serializer<SessionInfo> serializer() {
        return new SessionInfoSerializer();
    }

    @Override
    public Deserializer<SessionInfo> deserializer() {
        return new SessionInfoDeserializer();
    }
}
