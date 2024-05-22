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
    int numberOfLateCustomers;

    @SerializedName("totalLostTime")
    float totalLostTime;

    @SerializedName("numberOfSessions")
    int numberOfSessions;

    public SessionStats(float averageSessionDelay, float percentageOfCustomersTooLate, int numberOfLateCustomers, float totalLostTime, int numberOfSessions) {
        this.averageSessionDelay = averageSessionDelay;
        this.percentageOfCustomersTooLate = percentageOfCustomersTooLate;
        this.numberOfLateCustomers = numberOfLateCustomers;
        this.totalLostTime = totalLostTime;
        this.numberOfSessions = numberOfSessions;
    }

    public float getAverageSessionDelay() {
        return this.averageSessionDelay;
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

    public int getNumberOfLateCustomers() {
        return numberOfLateCustomers;
    }

    public void setNumberOfLateCustomers(int numberOfLateCustomers) {
        this.numberOfLateCustomers = numberOfLateCustomers;
    }

    public float getTotalLostTime() {
        return this.totalLostTime;
    }

    public void setTotalLostTime(float totalLostTime) {
        this.totalLostTime = totalLostTime;
    }

    public int getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(int numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }
    @Override
    public String toString(){
        return "Session Stats: \n" +
                "    number of total bookings: " + this.numberOfLateCustomers + "\n" +
                "    average session delay: " + (this.averageSessionDelay / 1000 / 60)+ " minutes\n" +
                "    percentage of too late customers: " + this.percentageOfCustomersTooLate + "%\n" +
                "    number of too late customers: " + this.numberOfLateCustomers;
    }
}

