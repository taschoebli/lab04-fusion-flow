package io.flowing.retail.reporting.application;

import com.google.common.io.Resources;
import io.flowing.retail.reporting.Serialization.model.BookingEntry;
import io.flowing.retail.reporting.repository.BookingRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class BookingProducer implements Runnable{

    private static BookingRepository bookingRepository = new BookingRepository();
    private final static String TOPIC_NAME = "bookings";

    public void produceBooking() throws IOException, InterruptedException {

        KafkaProducer<byte[], String> producer;

        try (InputStream props = Resources.getResource("reporting.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            // Create Kafka booking
            producer = new  KafkaProducer<byte[], String>(properties);
        }

        // TODO FOR FINAL SUBMISSION WE CAN USE A LARGER NUMBER SUCH AS 1500 or 2000
        for (int id = 1; id <= 1000; id++) { //while (true) {
          /*
            List<BookingEntry> bookingEntries = bookingRepository.findById(id);
            if (bookingEntries != null && !bookingEntries.isEmpty()) {
                for (BookingEntry bookingEntry : bookingEntries) {
                    JSONObject bookingJson =  convertToJSON(bookingEntry);
                    byte[] key = null;
                    String value = bookingJson.toString();
                    System.out.println("New Booking - " + value);
                    ProducerRecord<byte[], String> record = new ProducerRecord<>(TOPIC_NAME, key, value);
                    producer.send(record);
                }
            } else {
                System.out.println("Keine Buchung gefunden für ID " + id);
            }*/
            String booking = generateRandomBooking().toString();
            byte[] bytes = new byte[4];
            // BigEndian
            ByteBuffer.wrap(bytes).putInt(id);

            ProducerRecord<byte[], String> record = new ProducerRecord<>(TOPIC_NAME, bytes, booking);
            producer.send(record);
            Thread.sleep(500);
        }


    }
    public static JSONObject convertToJSON(BookingEntry booking) {
        JSONObject bookingJson = new JSONObject();
        //Here we could add the Id of the booking as well, but currently not necessary
        bookingJson.put("locationId", booking.getLocationId());
        bookingJson.put("bookingKey", booking.getBookingKey());
        bookingJson.put("productName", booking.getProductName());
        bookingJson.put("customerName", booking.getCustomerName());
        bookingJson.put("bookingDateTime", booking.getBookingDateTime());
        bookingJson.put("eventDateTime", booking.getEventDateTime());
        bookingJson.put("amount", booking.getAmount());
        bookingJson.put("paymentStatusIsPaid", booking.getPaymentStatusIsPaid());
        bookingJson.put("timestamp", booking.getTimestamp());
        return bookingJson;
    }

    public static JSONObject generateRandomBooking() {
        // TODO THIS CAN BE REMOVED BEFORE FINAL SUBMISSION
        Random rand = new Random();
        int[] numbers = {11,22,33}; // 3 arena ids
        int randomIndex = rand.nextInt(numbers.length);
        int randomNumber = numbers[randomIndex];

        long timestamp = System.currentTimeMillis();

        // Generate random booking details
        String id = UUID.randomUUID().toString();
        String locationId = "" + randomNumber;

        // Create JSON object for booking
        JSONObject booking = new JSONObject();

        booking.put("id", id);
        booking.put("locationId", locationId);
        booking.put("bookingKey", String.valueOf(timestamp));
        booking.put("productName", "ProductName");
        booking.put("customerName", "James T. Kirk");
        booking.put("bookingDateTime", "2024-10-31T08:00");
        booking.put("eventDateTime", "2024-10-31T12:00");
        booking.put("amount", 12);
        booking.put("paymentStatusIsPaid", true);
        booking.put("timestamp", timestamp);

        return booking;
    }

    @Override
    public void run() {
        try {
            produceBooking();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
