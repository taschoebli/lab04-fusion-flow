package io.flowing.retail.reporting.Serialization.model;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class BookingEntry {

    @SerializedName("id")
    Integer id;

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

