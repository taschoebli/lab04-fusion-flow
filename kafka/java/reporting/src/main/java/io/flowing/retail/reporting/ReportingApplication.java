package io.flowing.retail.reporting;

import io.flowing.retail.reporting.application.BookingProducer;
import io.flowing.retail.reporting.repository.BookingRepository;
import io.flowing.retail.reporting.topology.FilterProcessesToLocationsTopology;
import org.apache.kafka.streams.Topology;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;


@SpringBootApplication
public class ReportingApplication {

  @Autowired
  private BookingRepository bookingRepository;

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(ReportingApplication.class, args);
    ReportingApplication app = context.getBean(ReportingApplication.class);
    app.runApp();
  }

  public void runApp() {
    /*List<BlacklistEntry> result = blacklistRepository.findAll();
    System.out.println("Number of entries in blacklist found: " + result.size());*/
    System.out.println("Hello World");
    //TODO: implement stream processing
      BookingProducer producer = new BookingProducer();
      new Thread(producer).start();

      Topology topology = FilterProcessesToLocationsTopology.build();

      Properties config = new Properties();
      config.put(StreamsConfig.APPLICATION_ID_CONFIG, "reportingStream");
      config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      KafkaStreams streams = new KafkaStreams(topology, config);

      Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

      System.out.println("Starting stream processing");
      streams.start();
  }

}
