package qrInvoice.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import qrInvoice.application.QrInvoiceService;

@Component
public class MessageListener {    
  
  @Autowired
  private MessageSender messageSender;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/qrInvoice", topics = MessageSender.TOPIC_NAME)
  public void bookingCreated(String messageJson, @Header("type") String messageType) throws Exception {

    if ("BookingCreatedEvent".equals(messageType)) {
      JsonNode message = objectMapper.readTree(messageJson);
      ObjectNode payload = (ObjectNode) message.get("data");

      // TODO implement the logic here
      // only continue of the booking is valid

      messageSender.send( //
              new Message<JsonNode>( //
                      "QRInvoiceCreatedEvent", //
                      message.get("traceid").asText(), //
                      payload));

    }
  }
    
}
