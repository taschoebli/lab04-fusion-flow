package io.flowing.retail.bookingProducer.helpers;

import org.json.JSONObject;

import java.util.Random;
import java.util.UUID;

public class Booking {

    // TODO THIS CAN BE REMOVED BEFORE FINAL SUBMISSION
    public static JSONObject generateRandomBooking() {
        Random rand = new Random();
        int[] numbers = {11, 22, 33}; // 3 arena ids
        int randomIndex = rand.nextInt(numbers.length);
        int randomNumber = numbers[randomIndex];

        long timestamp = System.currentTimeMillis();

        // Generate random booking details
        String id = UUID.randomUUID().toString();
        String locationId = "" + randomNumber;

        // Create JSON object for booking
        JSONObject booking = new JSONObject();

        booking.put("id", id);
        booking.put("locationId", locationId);
        booking.put("bookingKey", String.valueOf(timestamp));
        booking.put("productName", "ProductName");
        booking.put("customerName", "James T. Kirk");
        booking.put("bookingDateTime", "2024-10-31T08:00");
        booking.put("eventDateTime", "2024-10-31T12:00");
        booking.put("amount", 12);
        booking.put("paymentStatusIsPaid", true);
        booking.put("timestamp", timestamp);

        return booking;
    }
}
