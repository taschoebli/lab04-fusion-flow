package io.flowing.retail.accounting.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.accounting.domain.Booking;
import io.flowing.retail.accounting.messages.MessageSender;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Configuration
public class ApproveCustomerAdapter implements JavaDelegate{

    @Autowired
    MessageSender messageSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Approve Customer Adapter");
        Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);

        System.out.println("Booking received! \nCustomer: " + booking.getCustomer().getName());
        System.out.println("try to automatically approve customer now");

        boolean isCustomerApproved = true;


        /* TODO */
        // Check if customer is on blacklist

        execution.setVariable("isCustomerApproved", false);

    }
}
