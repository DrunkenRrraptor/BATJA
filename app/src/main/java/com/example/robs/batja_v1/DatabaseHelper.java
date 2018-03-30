package com.example.robs.batja_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robs on 30.03.18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "batjaDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_GPS = "gps";

    // User Table Columns
    private static final String KEY_USERS_ID = "users_id";
    private static final String KEY_USERS_NAME = "usersNAme";
    private static final String KEY_USERS_PASSWORD = "usersPassword";

    // GPS Table Columns
    private static final String KEY_GPS_ID = "id";
    private static final String KEY_GPS_USERS_ID_FK = "userId";



    private static DatabaseHelper sInstance;

    // ...

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USERS_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_USERS_NAME + " TEXT," +
                KEY_USERS_PASSWORD + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

}
