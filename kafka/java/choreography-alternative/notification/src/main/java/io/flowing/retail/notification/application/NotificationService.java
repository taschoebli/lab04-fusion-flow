package io.flowing.retail.notification.application;

import java.util.UUID;

import org.springframework.stereotype.Component;


@Component
public class NotificationService {

  public String createEmailNotification(String orderId, long amount) {
    System.out.println("Email Confirmation: Payment received for orderId " + orderId);
    System.out.println("The payed amount is " + amount);
    return UUID.randomUUID().toString();
  }

}
