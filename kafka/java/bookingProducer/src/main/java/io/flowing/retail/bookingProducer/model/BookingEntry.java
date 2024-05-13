package io.flowing.retail.bookingProducer.model;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class BookingEntry {
    //Here we could add the Id of the booking as well, but currently not necessary
    @SerializedName("locationId")
    Integer locationId;

    @SerializedName("bookingKey")
    String bookingKey;

    @SerializedName("productName")
    String productName;

    @SerializedName("customerName")
    String customerName;

    @SerializedName("bookingDateTime")
    String bookingDateTime;

    @SerializedName("eventDateTime")
    String eventDateTime;

    @SerializedName("amount")
    Integer amount;

    @SerializedName("paymentStatusIsPaid")
    Boolean paymentStatusIsPaid;

    @SerializedName("timestamp")
    String timestamp;

}

