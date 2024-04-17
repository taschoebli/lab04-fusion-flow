package io.flowing.retail.accounting.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.accounting.domain.Booking;
import io.flowing.retail.accounting.messages.MessageSender;
import io.flowing.retail.accounting.repository.BlacklistRepository;
import io.flowing.retail.accounting.model.BlacklistEntry;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Autowired
    private BlacklistRepository blacklistRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Approve Customer Adapter");
        Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);

        System.out.println("Booking received! \nCustomer: " + booking.getCustomer().getName());
        System.out.println("try to automatically approve customer now");

        String customerEmail = booking.getCustomerEmail();
        System.out.println("Customer email: " + customerEmail);

        // Null check -> dummy email
        if (customerEmail == null) {
            customerEmail = "luzi.schoeb@student.unisg.ch";
        }
        boolean isCustomerApproved = true;

        if(blacklistRepository != null){
            var entries = blacklistRepository.findAll();
            System.out.println("Black list entry size: " + entries.size());
            for (BlacklistEntry entry : entries) {
                System.out.println("Black list entry: " + entry.getEmail());
            }
            List<BlacklistEntry> blacklistEntries = blacklistRepository.findByEmail(customerEmail);
            isCustomerApproved = blacklistEntries.isEmpty();
        }
        
        execution.setVariable("isCustomerApproved", isCustomerApproved);
        System.out.println("isCustomerApproved: " + isCustomerApproved);

    }
}
