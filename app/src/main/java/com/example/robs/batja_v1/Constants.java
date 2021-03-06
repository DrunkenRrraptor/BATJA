package com.example.robs.batja_v1;

/**
 * Created by Robs on 11.05.18.
 */

public class Constants {

    public static final double CONST_KMH_TO_MS = 3.6;

    public static final double CONST_SPEED_TRESH_1_KMH = 15;
    public static final double CONST_SPEED_THRESH_1_MS = CONST_SPEED_TRESH_1_KMH / CONST_KMH_TO_MS;

    public static final double CONST_SPEED_TRESH_2_KMH = 30;
    public static final double CONST_SPEED_THRESH_2_MS = CONST_SPEED_TRESH_2_KMH / CONST_KMH_TO_MS;

    public static final float HUE_THEME_COLOR = 0.545098f;

    // URL
    public static final String URL_1H_NOW = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_1h.php";
    public static final String URL_TODAY = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_day.php";
    public static final String URL_HIST = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_hist_1h.php";
    public static final String URL_FULL = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_full.php";

    public static final String URL_USERS = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_users.php";

    public static final String URL_POST_LOC = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/post_loc_to_database.php";
    public static final String URL_POST_USER = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/post_user_to_database.php";

    // Database information
    public static final String DATABASE_NAME = "batjaDatabase_";
    public static final int DATABASE_VERSION = 2;

    // Table Names
    public static final String TABLE_USERS = "users_3";
    public static final String TABLE_GPS = "gps_3";
    public static final String TABLE_GPS_STATS = "gps_3_stats";

    // User_Class Table Columns
    public static final String KEY_USERS_ID = "users_id";
    public static final String KEY_USERS_NAME = "users_name";
    public static final String KEY_USERS_PASSWORD = "users_password";

    // GPS Table Columns
    public static final String KEY_GPS_ID = "gps_id";
    public static final String KEY_GPS_SYSDATE = "gps_sys_date";
    public static final String KEY_GPS_LAT = "gps_lat";
    public static final String KEY_GPS_LONG = "gps_long";
    public static final String KEY_GPS_SPEED = "gps_speed";
    public static final String KEY_GPS_ACCEL = "gps_accel";

    public static final double EARTH_RADIUS = 637000.785;

}
