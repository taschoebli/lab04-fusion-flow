package io.flowing.retail.accounting;

import io.flowing.retail.accounting.model.BlacklistEntry;
import io.flowing.retail.accounting.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class AccountingApplication {

  @Autowired
  private BlacklistRepository blacklistRepository;

  public static void main(String[] args) throws Exception {
    ConfigurableApplicationContext context = SpringApplication.run(AccountingApplication.class, args);
    AccountingApplication app = context.getBean(AccountingApplication.class);
    app.runApp();
  }

  public void runApp() {
    List<BlacklistEntry> result = blacklistRepository.findAll();
    System.out.println("Number of entries in blacklist found: " + result.size());
  }

}
