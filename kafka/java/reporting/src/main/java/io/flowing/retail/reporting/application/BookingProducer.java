package io.flowing.retail.reporting.application;

import com.google.common.io.Resources;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class BookingProducer {

    private final static String TOPIC_NAME = "bookings";

    public static void produceBooking() throws IOException, InterruptedException {

        KafkaProducer<byte[], String> producer;

        try (InputStream props = Resources.getResource("reporting.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            // Create Kafka booking
            producer = new  KafkaProducer<byte[], String>(properties);
        }

        // generate random booking events
        while (true) {


            JSONObject booking =  generateRandomBooking();
            byte[] key = null;
            String value = booking.toString();
            System.out.println("New Booking - " + value);
            ProducerRecord<byte[], String> record = new ProducerRecord<>(TOPIC_NAME, key, value);
            producer.send(record);

            Thread.sleep(8);
        }


    }

    public static JSONObject generateRandomBooking() {
        Random rand = new Random();
        int[] numbers = {11,22,33}; // 3 arena ids
        int randomIndex = rand.nextInt(numbers.length);
        int randomNumber = numbers[randomIndex];

        long timestamp = System.currentTimeMillis();


        // Generate random booking details
        String id = UUID.randomUUID().toString();
        String locationId = "" + randomNumber;
        // TODO add more fields

        // Create JSON object for booking
        JSONObject booking = new JSONObject();

        booking.put("id", id);
        booking.put("locationId", locationId);

        return booking;
    }

}
