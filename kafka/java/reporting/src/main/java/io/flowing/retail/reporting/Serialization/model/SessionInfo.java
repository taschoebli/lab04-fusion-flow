package io.flowing.retail.reporting.Serialization.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionInfo {

    @SerializedName("id")
    Integer bookingId;

    @SerializedName("actualStartTime")
    String actualStartTime;

}

