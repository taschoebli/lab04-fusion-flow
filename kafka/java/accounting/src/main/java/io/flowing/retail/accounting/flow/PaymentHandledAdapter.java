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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Payment Handled Adapter");

        String processId = execution.getProcessBusinessKey();
        //Booking booking = objectMapper.readValue(execution.getVariable("booking").toString(), Booking.class);

        messageSender.send(new Message<>("PaymentHandled", processId,
                ""));

//        messageSender.send(new Message<PaymentHandledEventPayload>("PaymentHandled", processId,
//                new PaymentHandledEventPayload()
//                        .setCustomer(booking.getCustomer())
//                        .setBookingId(booking.getOrderId())));
    }
}
