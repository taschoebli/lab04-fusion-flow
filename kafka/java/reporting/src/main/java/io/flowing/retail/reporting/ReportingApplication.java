package io.flowing.retail.reporting;

import io.flowing.retail.reporting.topology.FilterProcessesToLocationsTopology;
import io.flowing.retail.reporting.topology.ReportingService;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.HostInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;


@SpringBootApplication
public class ReportingApplication {

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(ReportingApplication.class, args);
    ReportingApplication app = context.getBean(ReportingApplication.class);
    app.runApp();
  }

  public void runApp() {
      Topology topology = FilterProcessesToLocationsTopology.build();

      Properties config = new Properties();
      config.put(StreamsConfig.APPLICATION_ID_CONFIG, "reportingStream");
      config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      KafkaStreams streams = new KafkaStreams(topology, config);

      Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

      System.out.println("Starting stream processing");
      streams.start();

      // start the REST service
      HostInfo hostInfo = new HostInfo("localhost", 7070);
      ReportingService service = new ReportingService(hostInfo, streams);
      service.start();
  }

}
