package io.flowing.retail.reporting.helpers;

import io.flowing.retail.reporting.Serialization.model.EventDateTime;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;

public class EventDateTimeExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
        EventDateTime event = (EventDateTime) consumerRecord.value();
        if (event != null && event.getEventDateTimeCustom() != null) {
            String timestamp = event.getEventDateTimeCustom();
            System.out.println("Extracting timestamp: " + timestamp);
            return Instant.parse(timestamp).toEpochMilli();
        }
        // fallback to partition time
        System.out.println("Falling back to partition time: " + partitionTime);
        return partitionTime;
    }
}
