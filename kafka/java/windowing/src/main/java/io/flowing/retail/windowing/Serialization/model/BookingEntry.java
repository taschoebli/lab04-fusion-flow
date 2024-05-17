package io.flowing.retail.windowing.Serialization.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingEntry implements EventDateTime {

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

    public String getEventDateTimeCustom() {
        return this.eventDateTime;
    }

}

