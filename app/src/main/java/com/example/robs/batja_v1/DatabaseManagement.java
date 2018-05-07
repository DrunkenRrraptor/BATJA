package com.example.robs.batja_v1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by Robs on 31.03.18.
 */

public class DatabaseManagement extends SQLiteOpenHelper {


   /* DatabaseHelper dbHelper;

    public DatabaseManagement(Context applicationContext) {
        super();

        dbHelper = new DatabaseHelper( applicationContext );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    static class DatabaseHelper extends SQLiteOpenHelper {*/

    // Database Info
    private static final String DATABASE_NAME = "batjaDatabase_";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users_3";
    private static final String TABLE_GPS = "gps_3";

    // User Table Columns
    private static final String KEY_USERS_ID = "users_id";
    private static final String KEY_USERS_NAME = "users_name";
    private static final String KEY_USERS_PASSWORD = "users_password";

    // GPS Table Columns
    private static final String KEY_GPS_ID = "gps_id";
    private static final String KEY_GPS_SYSDATE = "gps_sys_date";
    private static final String KEY_GPS_LAT = "gps_lat";
    private static final String KEY_GPS_LONG = "gps_long";
    private static final String KEY_GPS_SPEED = "gps_speed";


    private static final String DB_FULL_PATH = "";


    private static DatabaseManagement sInstance;

        /*public DatabaseHelper() {
            super();
        }*/

    public static synchronized DatabaseManagement getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseManagement( context.getApplicationContext() );
        }
        return sInstance;
    }

    public DatabaseManagement(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure( db );
        db.setForeignKeyConstraintsEnabled( true );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("DB", "START");

        db.beginTransaction();

        try {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                    " (" +
                    KEY_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    KEY_USERS_NAME + " TEXT, " +
                    KEY_USERS_PASSWORD + " TEXT" +
                    ");";

            db.execSQL( CREATE_USERS_TABLE );

            String CREATE_GPS_TABLE = "CREATE TABLE " + TABLE_GPS +
                    " (" +
                    KEY_GPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    KEY_GPS_SYSDATE + " TEXT, " +
                    KEY_GPS_LAT + " FLOAT, " +
                    KEY_GPS_LONG + " FLOAT, " +
                    KEY_GPS_SPEED + " FLOAT" +
                    ");";

            db.execSQL( CREATE_GPS_TABLE );
            db.setTransactionSuccessful();

            Log.e( "DB", CREATE_GPS_TABLE );
            Log.e( "DB", CREATE_USERS_TABLE );


        } catch (Exception e) {
            Log.e( "DB", "error in on create tables");
        } finally {
            db.endTransaction();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_GPS );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
            onCreate( db );
        }




    }

    public void onClearBoth(){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_GPS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
        onCreate( db );

    }

    public void addUser(String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        String ADD_USERS = "";

        try {

            ADD_USERS = "INSERT INTO " + TABLE_USERS +
                    " (" +
                    KEY_USERS_NAME + ", " +
                    KEY_USERS_PASSWORD + ") VALUES ('" +
                    userName + "', '" +
                    password + "');";

            db.execSQL( ADD_USERS );
            Log.e( "DB", ADD_USERS);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e( "DB", "error in insert - users" + ADD_USERS);
        } finally {
            db.endTransaction();
        }

    }


    public void addLocation(double lat, double lng, double speed){

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);

        SQLiteDatabase db = getWritableDatabase();

        String ADD_GPS = "";

        db.beginTransaction();

        try {

            ADD_GPS = "INSERT INTO " + TABLE_USERS +
                    " (" +
                    KEY_GPS_SYSDATE + ", " +
                    KEY_GPS_LAT +
                    KEY_GPS_LONG +
                    KEY_GPS_LAT + ") VALUES ('" +
                    dateString + "', '" +
                    lat + "', '" +
                    lng + "', '" +
                    speed + "');";

                db.execSQL( ADD_GPS );

                Log.e( "DB", ADD_GPS );
                db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e( "DB", "error in add gps" + ADD_GPS );
        } finally {
            db.endTransaction();
        }

    }


    public void deleteTable(String db_name) {

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL( "DROP TABLE " + db_name );


    }

    public int checkUser(String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

        int m = 0;                                                                              // m = 0 ... nichts stimmt

        //  String CHECK_USER_COMPLETLY_RIGHT = "SELECT * FROM " + TABLE_USERS +
        //        " WHERE " + KEY_USERS_NAME + " = " + userName +
        //      " AND " + KEY_USERS_PASSWORD + " = " + password + ";";

        String CHECK_USER_COMPLETELY_RIGHT =
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE " + KEY_USERS_NAME + " = ?" +
                        " AND " + KEY_USERS_PASSWORD + " = ?;";


        Cursor c1 = db.rawQuery( CHECK_USER_COMPLETELY_RIGHT, new String[]{userName, password} );

        String CHECK_USER_PARTLY_RIGHT =
                "SELECT * FROM " + TABLE_USERS +
                        " WHERE " + KEY_USERS_NAME + " = ?";

        Cursor c2 = db.rawQuery( CHECK_USER_PARTLY_RIGHT, new String[]{userName} );


        if (c2.moveToNext()) {
            // username stimmt, passwort auch

            m = 1;                                                                              // m = 1 ... name und passwort stimmen

        } else if (c1.moveToNext()) {
            // username stimmt, passwort nicht                                                  // m = 2 ... name stimmt, passwort nicht

            m = 2;

        }

        return m;

    }

        /*public long addOrUpdateUser(String userName, String password) {
            // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
            SQLiteDatabase db = getWritableDatabase();
            long userId = -1;

            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(KEY_USERS_NAME, userName);

                // First try to update the user in case the user already exists in the database
                // This assumes userNames are unique
                int rows = db.update(TABLE_USERS, values, KEY_USERS_NAME + "= ?", new String[]{userName});

                // Check if update succeeded
                if (rows == 1) {
                    // Get the primary key of the user we just updated
                    String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                            KEY_USERS_ID, TABLE_USERS, KEY_USERS_NAME);
                    Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(userName)});
                    try {
                        if (cursor.moveToFirst()) {
                            userId = cursor.getInt(0);
                            db.setTransactionSuccessful();
                        }
                    } finally {
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                    }
                } else {
                    // user with this userName did not already exist, so insert new user
                    userId = db.insertOrThrow(TABLE_USERS, null, values);
                    db.setTransactionSuccessful();
                }
            } catch (Exception e) {
                Log.d(TAG, "Error while trying to add or update user");
            } finally {
                db.endTransaction();
            }
            return userId;
        }*/

       /* private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;
            try {
                checkDB = SQLiteDatabase.openDatabase();
                checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                        SQLiteDatabase.OPEN_READONLY);
                checkDB.close();
            } catch (SQLiteException e) {
                // database doesn't exist yet.
            }
            return checkDB != null;
        }*/

    // }


}
