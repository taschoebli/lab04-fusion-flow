package io.flowing.retail.bookingProducer.helpers;

import io.flowing.retail.bookingProducer.model.BookingEntry;
import io.flowing.retail.bookingProducer.model.SessionInfo;
import org.json.JSONObject;

public class Converters {
    public static JSONObject toJSON(BookingEntry booking) {
        JSONObject bookingJson = new JSONObject();
        //Here we could add the Id of the booking as well, but currently not necessary
        bookingJson.put("id", booking.getBookingId());
        bookingJson.put("locationId", booking.getLocationId());
        bookingJson.put("bookingKey", booking.getBookingKey());
        bookingJson.put("productName", booking.getProductName());
        bookingJson.put("customerName", booking.getCustomerName());
        bookingJson.put("bookingDateTime", booking.getBookingDateTime());
        bookingJson.put("eventDateTime", booking.getEventDateTime());
        bookingJson.put("amount", booking.getAmount());
        bookingJson.put("paymentStatusIsPaid", booking.getPaymentStatusIsPaid());
        bookingJson.put("timestamp", booking.getTimestamp());
        return bookingJson;
    }

    public static JSONObject toJSON(SessionInfo sessionInfo) {
        JSONObject sessionInfoJson = new JSONObject();
        //Here we could add the Id of the booking as well, but currently not necessary
        sessionInfoJson.put("id", sessionInfo.getBookingId());
        sessionInfoJson.put("actualStartTime", sessionInfo.getActualStartTime());

        return sessionInfoJson;
    }
}
