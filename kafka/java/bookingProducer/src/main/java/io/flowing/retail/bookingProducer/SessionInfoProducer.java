package io.flowing.retail.bookingProducer;

import com.google.common.io.Resources;
import io.flowing.retail.bookingProducer.helpers.Constants;
import io.flowing.retail.bookingProducer.helpers.Converters;
import io.flowing.retail.bookingProducer.model.BookingEntry;
import io.flowing.retail.bookingProducer.model.SessionInfo;
import io.flowing.retail.bookingProducer.repository.BookingRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

@SpringBootApplication
public class SessionInfoProducer {

    private final static String TOPIC_NAME = "sessionInfos";

    @Autowired
    private BookingRepository bookingRepository;

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(SessionInfoProducer.class, args);
        SessionInfoProducer app = context.getBean(SessionInfoProducer.class);
        app.produceSessionInfos();
    }

    public void produceSessionInfos() throws Exception {
        KafkaProducer<byte[], String> producer;

        List<BookingEntry> bookings = bookingRepository.findAll();

        try (InputStream props = Resources.getResource("application.properties").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            // Create Kafka booking
            producer = new KafkaProducer<byte[], String>(properties);
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(Constants.DATE_TIME_PATTERN);

        for (int id = 1; id <= bookings.size(); id++) {

            DateTime dt = formatter.parseDateTime(bookings.get(id).getEventDateTime());
            DateTime actualTime = determineActualSessionTime(dt);
            SessionInfo sessionInfo = new SessionInfo();
            sessionInfo.setBookingId(id);
            sessionInfo.setActualStartTime(actualTime.toString(formatter));
            JSONObject sessionJson = Converters.toJSON(sessionInfo);
            byte[] key = null;
            String value = sessionJson.toString();
            System.out.println("New actual Session data - " + value);
            ProducerRecord<byte[], String> record = new ProducerRecord<>(TOPIC_NAME, key, value);
            producer.send(record);
            Thread.sleep(30000);

        }
    }

    private DateTime determineActualSessionTime(DateTime dt) {
        Random r = new Random();
        float prob = r.nextFloat();

        //determine if the customer was punctual or late
        if (prob < 0.6){
            //customer was punctual! hurray!
            return dt;
        } else {
            //customer was late! sad! between 10 and 30 minutes!
            long late = r.nextLong(600000, 1800001);
            return (dt.plus(late));
        }
    }
}
