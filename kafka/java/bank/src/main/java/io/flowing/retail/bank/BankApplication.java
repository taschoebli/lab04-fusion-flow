package io.flowing.retail.bank;

import io.flowing.retail.bank.application.BankService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(BankApplication.class, args);
  }

}
