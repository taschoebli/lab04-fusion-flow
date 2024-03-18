package io.flowing.retail.booking.flow;

public class QRInvoiceCreatedEventPayload {

    private String bookingId;

    private String base64QrInvoice;

    public String getBookingId(){
        return bookingId;
    }

    public QRInvoiceCreatedEventPayload setBookingId(String bookingId){
        this.bookingId = bookingId;
        return this;
    }

    public String getBase64QrInvoice(){
        return base64QrInvoice;
    }

    public QRInvoiceCreatedEventPayload setBase64QrInvoice(String base64QrInvoice){
        this.base64QrInvoice = base64QrInvoice;
        return this;
    }



}
