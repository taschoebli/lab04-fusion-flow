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
// Note that we now have to read order data from this message!
      // Bad smell 1 (reading some event instead of dedicated command)
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      String orderId = payload.get("orderId").asText();
      if (orderId == null) {
        // We do not yet have an order id - as the responsibility who creates that is unclear
        // Bad smell 2 (order context missing)
        // But actually not that problematic - as a good practice could be to
        // generate it in the checkout anyway to improve idempotency
        orderId = UUID.randomUUID().toString();
        payload.put("orderId", orderId);
      }
      // the totalSum needs to be calculated by the checkout in this case - responsibility unclear
      // as this is not done we have to calculate it here - which means we have to learn to much about orders!
      // Bad smell 3 (order context missing)
      //long amount = payload.get("items").iterator().next().get("amount").asLong();
      long amount = payload.get("totalSum").asLong();

      String notificationId = notificationService.createEmailNotification(orderId, amount);

      // Note that we need to pass along the whole order object
      // Maybe with additional data we have
      // Bad smell 4 (data flow passing through - data might grow big and most data is not needed for payment)
      payload.put("notificationId", notificationId);

      messageSender.send( //
              new Message<JsonNode>( //
                      "NotificationEvent", //
                      message.get("traceid").asText(), //
                      payload));
    }
  }
    
}
