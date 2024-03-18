package io.flowing.retail.notification.messages;

import java.util.UUID;

import io.flowing.retail.notification.application.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.handler.annotation.Header;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;
  
  @Autowired
  private NotificationService notificationService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "notification", topics = MessageSender.TOPIC_NAME)
  public void paymentReceived(String messageJson, @Header("type") String messageType) throws Exception {
    if ("PaymentReceivedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      String orderId = payload.get("orderId").asText();
      if (orderId == null) {
        orderId = UUID.randomUUID().toString();
        payload.put("orderId", orderId);
      }

      long amount = payload.get("items").iterator().next().get("amount").asLong();

      String notificationId = notificationService.createEmailNotification(orderId, amount);

      payload.put("notificationId", notificationId);

      messageSender.send( //
              new Message<JsonNode>( //
                      "NotificationEvent", //
                      message.get("traceid").asText(), //
                      payload));

    } else if ("QRInvoiceCreatedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      QRInvoiceCreatedEventPayload qrInvoice = objectMapper.readValue(payload.toString(),
              QRInvoiceCreatedEventPayload.class);

      System.out.println("Here is your Invoice for booling: " + qrInvoice.getBookingId());
      System.out.println(qrInvoice.getBase64QrInvoice());
    }

    else if ("BookingCreatedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      // TODO implement the logic here

      messageSender.send( //
              new Message<JsonNode>( //
                      "NotificationEvent", //
                      message.get("traceid").asText(), //
                      payload));
    }

    else if ("OverdueNoticeCreatedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      // TODO implement the logic here

      messageSender.send( //
              new Message<JsonNode>( //
                      "NotificationEvent", //
                      message.get("traceid").asText(), //
                      payload));
    }
  }
    
}
