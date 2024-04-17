package io.flowing.retail.booking.flow;

import io.flowing.retail.booking.domain.Customer;

public class InvoiceBookingCreatedEventPayload {

    private String bookingId;

    private Customer customer;

    public String getBookingId(){
        return bookingId;
    }

    private String customerEmail;

    public InvoiceBookingCreatedEventPayload setBookingId(String bookingId){
        this.bookingId = bookingId;
        return this;
    }

    public Customer getCustomer(){
        return customer;
    }

    public InvoiceBookingCreatedEventPayload setCustomer(Customer customer){
        this.customer = customer;
        return this;
    }


    public String getCustomerEmail() {
        return customerEmail;
    }

    public InvoiceBookingCreatedEventPayload setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }
}
