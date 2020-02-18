package com.directparking.app.util;

public class Constants {

   // public static final String TNC_URL = "http://profile.appsimity.com/directParking/t&c.php";
    public static final String TNC_URL = "http://167.71.169.83/directparking/t&c.php";
    public static final String PRIVACY_URL = "http://167.71.169.83/directparking/privatePolicy.php";

    public static final int SPLASH_TIMEOUT_IN_SECONDS = 2;
    public static final int BACK_PRESS_INTERVAL = 2 * 1000;

    static final int UPLOAD_IMAGE_QUALITY = 75;
    public static final int IMAGE_UPLOAD_MAX_SIZE = 5;  // MB
    public static final int IMAGE_UPLOAD_COMPRESS_LIMIT = 1048576;  // MB

    public static final int PICKUP_ADDRESS_REQUEST = 1;
    public static final int DROP_ADDRESS_REQUEST = 2;
    public static final int LOCATION_SETTINGS_REQUEST = 3;
    public static final long LOCATION_UPDATE_INTERVAL = 5 * 1000;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 3;

    // Events
    public static final int EVENT_CONNECTIVITY_LOST = 101;
    public static final int EVENT_CONNECTIVITY_CONNECTED = 102;

    public static final String TITLE = "title";
    public static final String DESC = "desc";

    public static final String URL = "url";
    public static final String PIC_URLS = "picUrls";
    public static final String PROFILE_JPG = "profile.jpg";

    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String MY_PROFILE_FRAGMENT = "MyProfileFragment";
    public static final String EDIT_PROFILE_FRAGMENT = "EditProfileFragment";
    public static final String NOTIFICATION_FRAGMENT = "NotificationFragment";
    public static final String ACCEPT_REQUEST_FRAGMENT = "AcceptFragment";
    public static final String PARKING_SLOT_FRAGMENT = "ParkingSlotFragment";
    public static final String HISTORY_FRAGMENT = "HistoryFragment";
    public static final String ABOUT_US_FRAGMENT = "AboutUsFragment";
    public static final String CHANGE_PASSWORD_FRAGMENT = "ChangePasswordFragment";
    public static final String SETTINGS_FRAGMENT = "SettingsFragment";


    public static final String LOGOUT_DIALOG = "LogoutDialog";

    public static final String SIMPLE_DETAIL_DIALOG = "SimpleDetailDialog";
    public static final String CUSTOM_DETAIL_DIALOG = "CustomDetailDialog";
    public static final String NETWORK_ERROR_FRAGMENT = "NetworkErrorFragment";
    public static final String LOCATION_ERROR_DIALOG = "LocationErrorDialog";

    public static final int INT_CONST_FOR_HOME_FRAGMENT = 1;
    public static final int INT_CONST_FOR_EDIT_PROFILE_FRAGMENT = 2;
    public static final int INT_CONST_FOR_MY_PROFILE_FRAGMENT = 3;
    public static final int INT_CONST_FOR_NOTIFICATION_FRAGMENT = 4;
    public static final int INT_CONST_FOR_ACCEPT_RIDE_FRAGMENT = 5;
    public static final int INT_CONST_FOR_PARKING_SLOT_FRAGMENT = 6;
    public static final int INT_CONST_FOR_HISTORY_FRAGMENT = 7;
    public static final int INT_CONST_FOR_ABOUT_US_FRAGMENT = 8;
    public static final int INT_CONST_FOR_CHANGE_PASSWORD_FRAGMENT = 9;
    public static final int INT_CONST_FOR_SETTINGS_FRAGMENT = 10;


    public static final String PICKUP_ADDRESS = "pickupAddress";
    public static final String DROP_ADDRESS = "dropAddress";
    public static final String PICKUP_TIME = "pickupTime";
    public static final String PARKING_NAME_NUMBER = "parkingNameNumber";

    public static final String ACCEPT_STATUS = "accept";
    public static final String DECLINE_STATUS = "decline";
    public static final String CANCEL_STATUS = "cancel";

    // NOTIFICATION TYPES
    public static final String NOTIFICATION_CHAT = "CHAT";


    static final int TRACK_ORDER_JOB_INTERVAL = 2 * 1000;
    static final int UPDATE_LOCATION_JOB_INTERVAL = 2 * 1000;

    static final int TRACK_ORDER_JOB_ID = 108;
    static final int UPDATE_LOCATION_JOB_ID = 1008;
    static final String JOB_TRACK_ORDER_TASK_TAG = "TrackOrderTask";
    static final String JOB_UPDATE_LOCATION_TASK_TAG = "UpdateLocationTask";
}