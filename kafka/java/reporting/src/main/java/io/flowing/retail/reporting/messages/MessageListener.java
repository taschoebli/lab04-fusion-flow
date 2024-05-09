package io.flowing.retail.reporting.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.reporting.application.ReportingService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;
  
  @Autowired
  private ReportingService accountingService;

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/reporting", topics = MessageSender.TOPIC_NAME)
  public void invoiceBookingCreated(String messageJson, @Header("type") String messageType) throws Exception {
    if ("InvoiceBookingCreated".equals(messageType)) {

      System.out.println("InvoiceBookingCreatedEvent Kafka received");

      JsonNode message = objectMapper.readTree(messageJson);
      String traceId = message.get("traceid").toString().replaceAll("\"", "");
      runtimeService.createMessageCorrelation("InvoiceCreated")
              .processInstanceBusinessKey(traceId)
              .setVariable("booking", message.get("data").toString())
              .correlateWithResult();
    }
  }
    
}
