package io.flowing.retail.accounting.flow;

import io.flowing.retail.accounting.domain.Customer;

import java.util.UUID;

public class PaymentHandledEventPayload {
    private String bookingId;

    private Customer customer;

    public String getBookingId(){
        return bookingId;
    }

    public PaymentHandledEventPayload setBookingId(String bookingId){
        this.bookingId = bookingId;
        return this;
    }

    public Customer getCustomer(){
        return customer;
    }

    public PaymentHandledEventPayload setCustomer(Customer customer){
        this.customer = customer;
        return this;
    }
}
