package io.flowing.retail.reporting;

import io.flowing.retail.reporting.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class ReportingApplication {

  @Autowired
  private BlacklistRepository blacklistRepository;

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
  }

}
