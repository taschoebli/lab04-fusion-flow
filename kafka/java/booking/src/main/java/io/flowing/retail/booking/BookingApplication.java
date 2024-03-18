package io.flowing.retail.booking;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class BookingApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookingApplication.class, args);
  }

}
