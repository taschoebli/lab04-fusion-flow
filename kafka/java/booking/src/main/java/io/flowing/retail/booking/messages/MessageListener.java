package io.flowing.retail.booking.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/booking", topics = MessageSender.TOPIC_NAME)
  public void paymentHandled(String messageJson, @Header("type") String messageType) throws Exception {
    if ("PaymentHandled".equals(messageType)) {

      System.out.println("PaymentHandledEvent Kafka received");

      //JsonNode message = objectMapper.readTree(messageJson);
      //ObjectNode payload = (ObjectNode) message.get("data");

      runtimeService.createMessageCorrelation("BankTransferRetrievedNew")
              .processInstanceBusinessKey(UUID.randomUUID().toString())
              .correlateWithResult();
    }
  }
    
}
