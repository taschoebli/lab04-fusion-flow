package io.flowing.retail.accounting.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PaymentReceivedAdapter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Payment Received Adapter");

        // TODO if needed later
        // Create Kafka event PaymentReceivedEvent
        // eg. messageSender.send(new Message<PaymentReceivedEventPayload>("PaymentReceived", processId, new PaymentReceivedEventPayload().setCustomer(booking.getCustomer()).setBookingId(booking.getOrderId())));
    }
}
