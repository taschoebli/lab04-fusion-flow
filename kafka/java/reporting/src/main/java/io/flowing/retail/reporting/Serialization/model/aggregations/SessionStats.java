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
    long totalLostTime;

    @SerializedName("numberOfBookings")
    int numberOfBookings;

    public SessionStats(float averageSessionDelay, float percentageOfCustomersTooLate, int numberOfLateCustomers, long totalLostTime, int numberOfBookings) {
        this.averageSessionDelay = averageSessionDelay;
        this.percentageOfCustomersTooLate = percentageOfCustomersTooLate;
        this.numberOfLateCustomers = numberOfLateCustomers;
        this.totalLostTime = totalLostTime;
        this.numberOfBookings = numberOfBookings;
    }

    public float getAverageSessionDelay() {
        //convert to minutes
        return (this.averageSessionDelay / 1000 / 60);
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

    public long getTotalLostTime() {
        return totalLostTime;
    }

    public void setTotalLostTime(long totalLostTime) {
        this.totalLostTime = totalLostTime;
    }

    public int getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setNumberOfBookings(int numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
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

