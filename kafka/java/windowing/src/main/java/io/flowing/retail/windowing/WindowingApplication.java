package io.flowing.retail.windowing;

import io.flowing.retail.windowing.helpers.EventDateTimeExtractor;
import io.flowing.retail.windowing.topology.ReportingService;
import io.flowing.retail.windowing.topology.WindowTopology;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.HostInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;


@SpringBootApplication
public class WindowingApplication {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(WindowingApplication.class, args);
    WindowingApplication app = context.getBean(WindowingApplication.class);
    app.runApp();
  }

  public void runApp() {
      Topology topology = WindowTopology.build();

      Properties config = new Properties();
      config.put(StreamsConfig.APPLICATION_ID_CONFIG, "windowingStream");
      config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
      config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Void().getClass());
      config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
      config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, EventDateTimeExtractor.class);

      KafkaStreams streams = new KafkaStreams(topology, config);

      Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

      System.out.println("Starting stream processing");
      streams.start();

      // start the REST service
      HostInfo hostInfo = new HostInfo("localhost", 7071);
      ReportingService service = new ReportingService(hostInfo, streams);
      service.start();
  }

}
