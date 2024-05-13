package io.flowing.retail.bookingProducer.repository;

import io.flowing.retail.bookingProducer.model.BookingEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<BookingEntry> BOOKING_ROW_MAPPER = (rs, rowNum) -> {
        BookingEntry entry = new BookingEntry();
        //Here we could add the Id of the booking as well, but currently not necessary
        entry.setLocationId(rs.getInt("locationId"));
        entry.setBookingKey(rs.getString("bookingKey"));
        entry.setProductName(rs.getString("productName"));
        entry.setCustomerName(rs.getString("customerName"));
        entry.setBookingDateTime(rs.getString("bookingDateTime"));
        entry.setEventDateTime(rs.getString("eventDateTime"));
        entry.setAmount(rs.getInt("amount"));
        entry.setPaymentStatusIsPaid(rs.getBoolean("paymentStatusIsPaid"));
        entry.setBookingKey(rs.getString("bookingKey"));
        return entry;
    };

    public List<BookingEntry> findById(Integer id) {
        //Here we could add the Id of the booking as well, but currently not necessary
        String sql = "SELECT `locationId`, `bookingKey`, `productName`, `customerName`, `bookingDateTime`, `eventDateTime`, `amount`, `paymentStatusIsPaid`, `timestamp` FROM `bookingsPerpetual` WHERE `id` = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, BOOKING_ROW_MAPPER);
    }

    public List<BookingEntry> findAll() {
        //Here we could add the Id of the booking as well, but currently not necessary
        String sql = "SELECT `locationId`, `bookingKey`, `productName`, `customerName`, `bookingDateTime`, `eventDateTime`, `amount`, `paymentStatusIsPaid`, `timestamp` FROM `bookingsPerpetual` WHERE 1";
        return jdbcTemplate.query(sql, BOOKING_ROW_MAPPER);
    }
}
