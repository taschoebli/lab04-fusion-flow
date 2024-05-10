package io.flowing.retail.reporting.messages;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageListener {    

  @Transactional
  @KafkaListener(id = "io/flowing/retail/reporting", topics = MessageSender.TOPIC_NAME)
  public void invoiceBookingCreated(String messageJson, @Header("type") String messageType) throws Exception {

  }
    
}
