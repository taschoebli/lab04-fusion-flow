package io.flowing.retail.reporting.partitioner;

import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.helpers.Constants;
import org.apache.kafka.streams.processor.StreamPartitioner;

public class LocationPartitioner implements StreamPartitioner<String, AnonymizedBookingEntry> {
    @Override
    public Integer partition(String topic, String key, AnonymizedBookingEntry value, int numPartitions) {
       if (key.equals(Constants.LOCATION_ZUERICH)) {
           return 1;
       } else if (key.equals(Constants.LOCATION_STGALLEN)){
           return 2;
       } else if (key.equals(Constants.LOCATION_BERN)){
           return 3;
       } else {
           return 4;
       }
    }
}
