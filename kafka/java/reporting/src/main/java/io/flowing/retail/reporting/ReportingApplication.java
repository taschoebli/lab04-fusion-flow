package io.flowing.retail.reporting;

import io.flowing.retail.reporting.topology.CombineStreamsAndPrepareForReportingTopology;
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
      Topology topology2 = CombineStreamsAndPrepareForReportingTopology.build();

      Properties config = new Properties();
      config.put(StreamsConfig.APPLICATION_ID_CONFIG, "filterProcessesToLocationsTopology");
      config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      Properties config2 = new Properties();
      config2.put(StreamsConfig.APPLICATION_ID_CONFIG, "CombineStreamsAndPrepareForReportingTopology");
      config2.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      KafkaStreams stream = new KafkaStreams(topology, config);
      KafkaStreams stream2 = new KafkaStreams(topology2, config2);

      Runtime.getRuntime().addShutdownHook(new Thread(stream::close));
      Runtime.getRuntime().addShutdownHook(new Thread(stream2::close));

      System.out.println("Starting stream processing");
      stream.start();
      stream2.start();

      // start the REST service
      HostInfo hostInfo = new HostInfo("localhost", 7070);
      ReportingService service = new ReportingService(hostInfo, stream, stream2);
      service.start();
  }

}
