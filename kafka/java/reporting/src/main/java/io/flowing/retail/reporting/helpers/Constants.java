package io.flowing.retail.reporting.helpers;

public class Constants {
    public static String LOCATION_ZUERICH = "bookings-zuerich";

    public static String LOCATION_BERN = "bookings-bern";

    public static String LOCATION_STGALLEN = "bookings-stgallen";

    public static String INVALID_LOCATION = "invalid_booking_location";

    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String locationIdToLocationString(Integer locationId) {
        return switch (locationId) {
            case 1, 11 -> Constants.LOCATION_ZUERICH;
            case 2 -> Constants.LOCATION_BERN;
            case 3, 30, 31 -> Constants.LOCATION_STGALLEN;
            default -> Constants.INVALID_LOCATION;
        };
    }

}
