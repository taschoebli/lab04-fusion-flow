package io.flowing.retail.reporting.Serialization.model.aggregations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionStats {


    @SerializedName("averageSessionDelay")
    float averageSessionDelay;

    @SerializedName("percentageOfCustomersTooLate")
    float percentageOfCustomersTooLate;

    @SerializedName("numberOfLateCustomers")
    float numberOfLateCustomers;

    @SerializedName("totalLostTime")
    long totalLostTime;


    public SessionStats(float averageSessionDelay, float percentageOfCustomersTooLate, float numberOfLateCustomers, long totalLostTime) {
        this.averageSessionDelay = averageSessionDelay;
        this.percentageOfCustomersTooLate = percentageOfCustomersTooLate;
        this.numberOfLateCustomers = numberOfLateCustomers;
        this.totalLostTime = totalLostTime;
    }

    public float getAverageSessionDelay() {
        return averageSessionDelay;
    }

    public void setAverageSessionDelay(float averageSessionDelay) {
        this.averageSessionDelay = averageSessionDelay;
    }

    public float getPercentageOfCustomersTooLate() {
        return percentageOfCustomersTooLate;
    }

    public void setPercentageOfCustomersTooLate(float percentageOfCustomersTooLate) {
        this.percentageOfCustomersTooLate = percentageOfCustomersTooLate;
    }

    public float getNumberOfLateCustomers() {
        return numberOfLateCustomers;
    }

    public void setNumberOfLateCustomers(float numberOfLateCustomers) {
        this.numberOfLateCustomers = numberOfLateCustomers;
    }

    public long getTotalLostTime() {
        return totalLostTime;
    }

    public void setTotalLostTime(long totalLostTime) {
        this.totalLostTime = totalLostTime;
    }

    @Override
    public String toString(){
        return "Session Stats: \n" +
                "    average session delay: " + this.averageSessionDelay + "\n" +
                "    percentage of too late customers: " + this.percentageOfCustomersTooLate + "\n" +
                "    number of too late customers: " + this.numberOfLateCustomers;
    }
}

