package io.flowing.retail.bookingProducer.model;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class SessionInfo {
    //Here we could add the Id of the booking as well, but currently not necessary
    @SerializedName("id")
    Integer bookingId;

    @SerializedName("actualStartTime")
    String actualStartTime;

}

