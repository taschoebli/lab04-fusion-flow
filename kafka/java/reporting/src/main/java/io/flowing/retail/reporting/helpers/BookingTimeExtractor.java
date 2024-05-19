package io.flowing.retail.reporting.helpers;

import io.flowing.retail.reporting.Serialization.model.BookingDateTime;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.Instant;

public class BookingTimeExtractor implements TimestampExtractor {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN);
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long partitionTime) {
        BookingDateTime event = (BookingDateTime) consumerRecord.value();
        if (event != null && event.getBookingDateTimeCustom() != null) {
            String timestamp = event.getBookingDateTimeCustom();
            System.out.println("Extracting timestamp: " + timestamp);
            DateTime dateTime = formatter.parseDateTime(timestamp);
            return dateTime.getMillis();
        }
        // fallback to partition time
        System.out.println("Falling back to partition time: " + partitionTime);
        return partitionTime;
    }
}
