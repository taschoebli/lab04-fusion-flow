package io.flowing.retail.bookingProducer;

import com.google.common.io.Resources;
import io.flowing.retail.bookingProducer.helpers.Converters;
import io.flowing.retail.bookingProducer.model.BookingEntry;
import io.flowing.retail.bookingProducer.repository.BookingRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.print.Book;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


@SpringBootApplication
public class BookingProducer {

    private final static String TOPIC_NAME = "bookings";

    @Autowired
    private BookingRepository bookingRepository;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(BookingProducer.class, args);
        BookingProducer app = context.getBean(BookingProducer.class);
        app.runApp();
    }

    public void runApp() throws IOException, InterruptedException {
        List<BookingEntry> result = bookingRepository.findAll();
        System.out.println("Number of entries in booking db found: " + result.size());
        System.out.println("Hello World");

        //This is the method that produces the bookings for a long time
        produceBooking(result);
    }

    public void produceBooking(List<BookingEntry> entries) throws IOException, InterruptedException {

        KafkaProducer<Integer, String> producer;

        try (InputStream props = Resources.getResource("application.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            // Create Kafka booking
            producer = new KafkaProducer<Integer, String>(properties);
        }

        for (int id = 1; id <= entries.size(); id++) {
            for (BookingEntry bookingEntry : entries) {
                JSONObject bookingJson = Converters.toJSON(bookingEntry);
                byte[] key = null;
                String value = bookingJson.toString();
                System.out.println("New Booking - " + value);
                ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC_NAME, bookingEntry.getBookingId(), value);
                producer.send(record);

                Thread.sleep(1500);
                }
        }
    }
}
