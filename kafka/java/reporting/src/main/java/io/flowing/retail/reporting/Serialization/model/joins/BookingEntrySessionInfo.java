package io.flowing.retail.reporting.Serialization.model.joins;

import com.google.gson.annotations.SerializedName;
import io.flowing.retail.reporting.Serialization.model.AnonymizedBookingEntry;
import io.flowing.retail.reporting.Serialization.model.SessionInfo;

public class BookingEntrySessionInfo {
    @SerializedName("SessionInfo")
    private SessionInfo sessionInfo;
    @SerializedName("AnonymizedBookingEntry")
    private AnonymizedBookingEntry bookingEntry;

    public BookingEntrySessionInfo(SessionInfo sessionInfo, AnonymizedBookingEntry bookingEntry) {
        this.sessionInfo = sessionInfo;
        this.bookingEntry = bookingEntry;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public AnonymizedBookingEntry getBookingEntry() {
        return bookingEntry;
    }

    public void setBookingEntry(AnonymizedBookingEntry bookingEntry) {
        this.bookingEntry = bookingEntry;
    }
}
