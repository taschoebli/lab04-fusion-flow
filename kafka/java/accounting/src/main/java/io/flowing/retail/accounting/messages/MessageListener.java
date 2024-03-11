package io.flowing.retail.accounting.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.accounting.application.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;
  
  @Autowired
  private AccountingService accountingService;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/accounting", topics = MessageSender.TOPIC_NAME)
  public void paymentReceived(String messageJson, @Header("type") String messageType) throws Exception {
    //TODO: do stuff here
    if ("Booking".equals(messageType)) {

    }
  }
    
}
