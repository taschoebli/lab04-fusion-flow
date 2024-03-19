package io.flowing.retail.booking.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.booking.domain.Booking;
import io.flowing.retail.booking.messages.Message;
import io.flowing.retail.booking.messages.MessageSender;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Named
public class SendInvoiceAdapter implements JavaDelegate {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processId = execution.getProcessInstanceId();
        String base64QrInvoice = execution.getVariable("qrInvoice").toString();
        Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);

        System.out.println("Send Invoice (Kafka message)");

        messageSender.send(new Message<QRInvoiceCreatedEventPayload>("QRInvoiceCreatedEvent", processId,
                new QRInvoiceCreatedEventPayload()
                        .setBase64QrInvoice(base64QrInvoice)
                        .setBookingId(booking.getOrderId())));
    }
}
