package io.flowing.retail.booking.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.booking.messages.MessageSender;
import jakarta.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import io.flowing.retail.booking.domain.Booking;
import org.springframework.web.client.RestTemplate;


@Component
@Configuration
@Named
public class QrInvoiceAdapter implements JavaDelegate {

    @Autowired
    MessageSender messageSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate rest;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);
        String processId = execution.getProcessBusinessKey();
        /*
        messageSender.send(new Message<CreateQrInvoiceCommandPayload>("CreateQrInvoiceCommand", processId,
                new CreateQrInvoiceCommandPayload()
                        .setAmount(125)
                        .setBookingId(booking.getOrderId())));*/
        String base64QrCode = rest
                .getForObject("http://qrinvoice:8092/api/qr/createQrInvoice?totalCost=15", String.class);
        System.out.println("QR Invoice Created: " + base64QrCode);
        execution.setVariable("qrInvoice", base64QrCode);
    }

    @Configuration
    public static class Config {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }
}
