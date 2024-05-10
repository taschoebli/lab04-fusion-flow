package io.flowing.retail.reporting.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingEntry {
    private Integer locationId;
    private String bookingKey;
    private String productName;
    private String customerName;
    private String bookingDateTime;
    private String eventDateTime;
    private Integer amount;
    private Boolean paymentStatusIsPaid;
    private String timestamp;
}

