package io.flowing.retail.booking.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.booking.domain.Booking;
import io.flowing.retail.booking.messages.Message;
import io.flowing.retail.booking.messages.MessageSender;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class BookingValidAdapter implements JavaDelegate {

    @Autowired
    MessageSender messageSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);
        String processId = execution.getProcessBusinessKey();

        System.out.println("Booking: " + booking.getOrderId() + " is valid.");
    }
}
