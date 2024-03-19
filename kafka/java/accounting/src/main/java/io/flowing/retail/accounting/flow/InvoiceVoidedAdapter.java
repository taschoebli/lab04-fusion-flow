package io.flowing.retail.accounting.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class InvoiceVoidedAdapter implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Invoice Voided Adapter");

        // TODO if needed later
        // Create Kafka event InvoiceVoidedEvent
        // eg. messageSender.send(new Message<InvoiceVoidedEventPayload>("InvoiceVoided", processId, new InvoiceVoidedEventPayload().setCustomer(booking.getCustomer()).setBookingId(booking.getOrderId())));
    }
}
