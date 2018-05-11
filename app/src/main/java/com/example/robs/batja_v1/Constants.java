package com.example.robs.batja_v1;

/**
 * Created by Robs on 11.05.18.
 */

public class Constants {

    public static final double CONST_KMH_TO_MS = 3.6;

    public static final double CONST_SPEED_TRESH_1_KMH = 10;
    public static final double CONST_SPEED_THRESH_1_MS = CONST_SPEED_TRESH_1_KMH / CONST_KMH_TO_MS;

    public static final double CONST_SPEED_TRESH_2_KMH = 25;
    public static final double CONST_SPEED_THRESH_2_MS = CONST_SPEED_TRESH_2_KMH / CONST_KMH_TO_MS;

    public static final double CONST_SPEED_TRESH_3_KMH = 40;
    public static final double CONST_SPEED_THRESH_3_MS = CONST_SPEED_TRESH_3_KMH / CONST_KMH_TO_MS;


    // Database Info
    public static final String DATABASE_NAME = "batjaDatabase_";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_USERS = "users_3";
    public static final String TABLE_GPS = "gps_3";

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

}
