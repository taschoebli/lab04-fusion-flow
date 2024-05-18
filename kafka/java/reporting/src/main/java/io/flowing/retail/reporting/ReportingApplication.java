package io.flowing.retail.reporting;

import io.flowing.retail.reporting.topology.CombineStreamsAndPrepareForReportingTopology;
import io.flowing.retail.reporting.topology.FilterProcessesToLocationsTopology;
import io.flowing.retail.reporting.topology.ReportingService;
import io.flowing.retail.reporting.topology.WindowTopology;
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
      KafkaStreams[] streams = new KafkaStreams[3];

      Topology topologySessionMonitor = CombineStreamsAndPrepareForReportingTopology.build();
      Topology locationCount = FilterProcessesToLocationsTopology.build();
      Topology windowTopology = WindowTopology.build();

      Properties configSessionMonitor= new Properties();
      configSessionMonitor.put(StreamsConfig.APPLICATION_ID_CONFIG, "sessionMonitor");
      configSessionMonitor.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      Properties configLocationCount = new Properties();
      configLocationCount.put(StreamsConfig.APPLICATION_ID_CONFIG, "locationCount");
      configLocationCount.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");

      Properties windowConfig = new Properties();
      windowConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "windowTopology");
      windowConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");


      streams[0] = new KafkaStreams(locationCount, configLocationCount);
      streams[1] = new KafkaStreams(topologySessionMonitor, configSessionMonitor);
      streams[2] =  new KafkaStreams(windowTopology, windowConfig);

      Runtime.getRuntime().addShutdownHook(new Thread(streams[0]::close));
      Runtime.getRuntime().addShutdownHook(new Thread(streams[1]::close));
      Runtime.getRuntime().addShutdownHook(new Thread(streams[2]::close));

      System.out.println("Starting stream processing");
      streams[0].start();
      streams[1].start();
      streams[2].start();

      // start the REST service
      HostInfo hostInfo = new HostInfo("localhost", 7070);
      ReportingService service = new ReportingService(hostInfo, streams);
      service.start();
  }
}
