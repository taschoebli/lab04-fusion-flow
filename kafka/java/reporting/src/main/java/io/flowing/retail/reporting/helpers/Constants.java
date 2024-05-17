package io.flowing.retail.reporting.helpers;

import java.time.Duration;

public class Constants {
    public static String LOCATION_ZUERICH = "bookings-zuerich";

    public static String LOCATION_BERN = "bookings-bern";

    public static String LOCATION_STGALLEN = "bookings-stgallen";

    public static String INVALID_LOCATION = "invalid_booking_location";

    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static Duration WINDOW_SIZE = Duration.ofDays(1);
}
