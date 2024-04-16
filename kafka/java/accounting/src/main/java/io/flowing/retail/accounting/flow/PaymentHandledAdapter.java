package io.flowing.retail.accounting.flow;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.flowing.retail.accounting.domain.Booking;
import io.flowing.retail.accounting.messages.Message;
import io.flowing.retail.accounting.messages.MessageSender;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentHandledAdapter implements JavaDelegate {
    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Payment Handled Adapter");

        String processId = execution.getProcessBusinessKey();

        messageSender.send(new Message<>("PaymentHandled", processId,
                ""));

    }
}
