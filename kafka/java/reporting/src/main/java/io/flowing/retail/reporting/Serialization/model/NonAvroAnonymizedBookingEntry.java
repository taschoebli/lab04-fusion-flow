package io.flowing.retail.reporting.Serialization.model;
import lombok.Getter;
import lombok.Setter;
import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class NonAvroAnonymizedBookingEntry {

        @SerializedName("id")
        private Integer bookingId;

        @SerializedName("locationId")
        private Integer locationId;

        @SerializedName("bookingKey")
        private String bookingKey;

        @SerializedName("productName")
        private String productName;

        // Changed to match anonymized customer id from the schema, assuming a potential typo
        @SerializedName("anonymizedCustomer")
        private Integer anonymizedCustomer;

        @SerializedName("bookingDateTime")
        private String bookingDateTime;

        @SerializedName("eventDateTime")
        private String eventDateTime;

        @SerializedName("amount")
        private Integer amount;

        @SerializedName("paymentStatusIsPaid")
        private Boolean paymentStatusIsPaid;

        @SerializedName("timestamp")
        private String timestamp;
}
