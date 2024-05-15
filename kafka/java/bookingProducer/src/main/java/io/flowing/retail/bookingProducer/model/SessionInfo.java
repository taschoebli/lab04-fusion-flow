package io.flowing.retail.bookingProducer.model;

import lombok.Getter;
import lombok.Setter;

import com.google.gson.annotations.SerializedName;

@Getter
@Setter
public class SessionInfo {

    @SerializedName("id")
    Integer bookingId;

    @SerializedName("actualStartTime")
    String actualStartTime;

}

