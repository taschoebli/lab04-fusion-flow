package qrInvoice.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
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
  private QrInvoiceService bankService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @KafkaListener(id = "io/flowing/retail/qrInvoice", topics = MessageSender.TOPIC_NAME)
  public void paymentReceived(String messageJson, @Header("type") String messageType) throws Exception {
    //TODO: do stuff here
    if ("Booking".equals(messageType)) {

    }
  }
    
}
