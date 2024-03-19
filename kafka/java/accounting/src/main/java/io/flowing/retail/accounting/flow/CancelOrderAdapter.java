package io.flowing.retail.accounting.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderAdapter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Cancel Order Adapter");

        // TODO if needed later
        // Create Kafka event OrderCanceledEvent
        // eg. messageSender.send(new Message<OrderCanceledEventPayload>("OrderCanceled", processId, new OrderCanceledEventPayload().setCustomer(booking.getCustomer()).setBookingId(booking.getOrderId())));
    }
}
