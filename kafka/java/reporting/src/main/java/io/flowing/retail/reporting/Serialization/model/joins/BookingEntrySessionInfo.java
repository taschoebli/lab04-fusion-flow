package io.flowing.retail.reporting.Serialization.model.joins;

import com.google.gson.annotations.SerializedName;
import io.flowing.retail.reporting.Serialization.model.NonAvroAnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.SessionInfo;

public class BookingEntrySessionInfo {
    @SerializedName("SessionInfo")
    private SessionInfo sessionInfo;
    @SerializedName("AnonymizedBookingEntry")
    private NonAvroAnonymizedBookingEntry bookingEntry;

    public BookingEntrySessionInfo(SessionInfo sessionInfo, NonAvroAnonymizedBookingEntry bookingEntry) {
        this.sessionInfo = sessionInfo;
        this.bookingEntry = bookingEntry;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public NonAvroAnonymizedBookingEntry getBookingEntry() {
        return bookingEntry;
    }

    public void setBookingEntry(NonAvroAnonymizedBookingEntry bookingEntry) {
        this.bookingEntry = bookingEntry;
    }
}
