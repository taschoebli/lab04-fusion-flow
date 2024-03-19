package io.flowing.retail.accounting.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.flowing.retail.accounting.application.AccountingService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;
  
  @Autowired
  private AccountingService accountingService;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/accounting", topics = MessageSender.TOPIC_NAME)
  public void invoiceBookingCreated(String messageJson, @Header("type") String messageType) throws Exception {
    if ("InvoiceBookingCreated".equals(messageType)) {

      System.out.println("InvoiceBookingCreatedEvent Kafka received");

      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      runtimeService.createMessageCorrelation("InvoiceCreated")
              .processInstanceBusinessKey(UUID.randomUUID().toString())
              .correlateWithResult();
    }
  }
    
}
