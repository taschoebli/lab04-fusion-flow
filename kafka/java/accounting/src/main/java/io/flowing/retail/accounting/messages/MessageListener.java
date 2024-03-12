package io.flowing.retail.accounting.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/accounting", topics = MessageSender.TOPIC_NAME)
  public void qrInvoiceReceived(String messageJson, @Header("type") String messageType) throws Exception {
    if ("QRInvoiceCreatedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      // TODO implement the logic here
      // check if the QR invoice is overdue

        messageSender.send( //
                new Message<JsonNode>( //
                        "OverdueNoticeCreatedEvent", //
                        message.get("traceid").asText(), //
                        payload));
    }
    else if ("PaymentReceivedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      // TODO implement the logic here

      messageSender.send( //
              new Message<JsonNode>( //
                      "AccountingEvent", //
                      message.get("traceid").asText(), //
                      payload));
    }
  }
    
}
