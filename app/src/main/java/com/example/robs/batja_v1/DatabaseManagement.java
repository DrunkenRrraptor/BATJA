package com.example.robs.batja_v1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.CheckedOutputStream;

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

    /*
    // Database Info
    private static final String DATABASE_NAME = "batjaDatabase_";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users_3";
    private static final String TABLE_GPS = "gps_3";

    // User_Class Table Columns
    private static final String KEY_USERS_ID = "users_id";
    private static final String KEY_USERS_NAME = "users_name";
    private static final String KEY_USERS_PASSWORD = "users_password";

    // GPS Table Columns
    private static final String KEY_GPS_ID = "gps_id";
    private static final String KEY_GPS_SYSDATE = "gps_sys_date";
    private static final String KEY_GPS_LAT = "gps_lat";
    private static final String KEY_GPS_LONG = "gps_long";
    private static final String KEY_GPS_SPEED = "gps_speed";
    */


    private static final String DB_FULL_PATH = "";

    private List<GPS_Class> gps_list = new ArrayList<>(  );

    public List<GPS_Class> getGps_list() {
        return gps_list;
    }

    public void setGps_list(List<GPS_Class> gps_list) {
        this.gps_list = gps_list;
    }

    private List<GPS_Class> gps_listAll = new ArrayList<>(  );

    public List<GPS_Class> getGps_listAll() {
        return gps_listAll;
    }

    public void setGps_listAll(List<GPS_Class> gps_list) {
        this.gps_listAll = gps_list;
    }

    private static DatabaseManagement sInstance;

    private User_Class user_logged = new User_Class(  );

    public User_Class getUser_logged() {
        return user_logged;
    }

    public void setUser_logged(User_Class user_logged) {
        this.user_logged = user_logged;
    }

    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private int count_number_users = 0;
    private int count_loops = 0;
    private int count_records_all = 0;
    private int count_records_user = 0;
    private double min_lat = -90;                   // apply those only for logged in user
    private double min_lat_temp = -90;
    private double max_lat = 90;
    private double max_lat_temp = 90;
    private double min_lng = -90;
    private double min_lng_temp = -90;
    private double max_lng = 90;
    private double max_lng_temp = 90;
    private double avg_speed_user = 0;
    private double avg_speed_all = 0;
    private double avg_accel_user = 0;
    private double avg_accel_all = 0;

    public int getCount_number_users() {
        return count_number_users;
    }

    public void setCount_number_users(int count_number_users) {
        this.count_number_users = count_number_users;
    }

    public int getCount_loops() {
        return count_loops;
    }

    public void setCount_loops(int count_loops) {
        this.count_loops = count_loops;
    }

    public int getCount_records_all() {
        return count_records_all;
    }

    public void setCount_records_all(int count_records_all) {
        this.count_records_all = count_records_all;
    }

    public int getCount_records_user() {
        return count_records_user;
    }

    public void setCount_records_user(int count_records_user) {
        this.count_records_user = count_records_user;
    }

    public double getMin_lat() {
        return min_lat;
    }

    public void setMin_lat(double min_lat) {
        this.min_lat = min_lat;
    }

    public double getMin_lat_temp() {
        return min_lat_temp;
    }

    public void setMin_lat_temp(double min_lat_temp) {
        this.min_lat_temp = min_lat_temp;
    }

    public double getMax_lat() {
        return max_lat;
    }

    public void setMax_lat(double max_lat) {
        this.max_lat = max_lat;
    }

    public double getMax_lat_temp() {
        return max_lat_temp;
    }

    public void setMax_lat_temp(double max_lat_temp) {
        this.max_lat_temp = max_lat_temp;
    }

    public double getMin_lng() {
        return min_lng;
    }

    public void setMin_lng(double min_lng) {
        this.min_lng = min_lng;
    }

    public double getMin_lng_temp() {
        return min_lng_temp;
    }

    public void setMin_lng_temp(double min_lng_temp) {
        this.min_lng_temp = min_lng_temp;
    }

    public double getMax_lng() {
        return max_lng;
    }

    public void setMax_lng(double max_lng) {
        this.max_lng = max_lng;
    }

    public double getMax_lng_temp() {
        return max_lng_temp;
    }

    public void setMax_lng_temp(double max_lng_temp) {
        this.max_lng_temp = max_lng_temp;
    }

    public double getAvg_speed_user() {
        return avg_speed_user;
    }

    public void setAvg_speed_user(double avg_speed_user) {
        this.avg_speed_user = avg_speed_user;
    }

    public double getAvg_speed_all() {
        return avg_speed_all;
    }

    public void setAvg_speed_all(double avg_speed_all) {
        this.avg_speed_all = avg_speed_all;
    }

    public double getAvg_accel_user() {
        return avg_accel_user;
    }

    public void setAvg_accel_user(double avg_accel_user) {
        this.avg_accel_user = avg_accel_user;
    }

    public double getAvg_accel_all() {
        return avg_accel_all;
    }

    public void setAvg_accel_all(double avg_accel_all) {
        this.avg_accel_all = avg_accel_all;
    }

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

    /*
    public DatabaseManagement(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }
     */

    private DatabaseManagement(Context context) {
        super( context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION );
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure( db );
        db.setForeignKeyConstraintsEnabled( true );
    }


    /*
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
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("DB", "START");

        db.beginTransaction();

        try {
            String CREATE_USERS_TABLE = "CREATE TABLE " + Constants.TABLE_USERS +
                    " (" +
                    Constants.KEY_USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    Constants.KEY_USERS_NAME + " TEXT, " +
                    Constants.KEY_USERS_PASSWORD + " TEXT" +
                    ");";

            db.execSQL( CREATE_USERS_TABLE );

//          String CREATE_GPS_TABLE = "CREATE TABLE " + Constants.TABLE_GPS +
            String CREATE_GPS_TABLE = "CREATE TABLE " + Constants.TABLE_GPS +
                    " (" +
                    Constants.KEY_GPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    Constants.KEY_USERS_ID + " INTEGER, " +
                    Constants.KEY_GPS_SYSDATE + " TIMESTAMP, " +
                    Constants.KEY_GPS_LAT + " FLOAT, " +
                    Constants.KEY_GPS_LONG + " FLOAT, " +
                    Constants.KEY_GPS_SPEED + " FLOAT, " +
                    Constants.KEY_GPS_ACCEL + " FLOAT, " +
                    "CONSTRAINT fk_userID FOREIGN KEY (" + Constants.KEY_USERS_ID + ") REFERENCES " + Constants.TABLE_USERS + "(" + Constants.KEY_USERS_ID + ")" +
                    ");";

            String CREATE_GPS_TABLE_STATS = "CREATE TABLE " + Constants.TABLE_GPS_STATS +
                    " (" +
                    Constants.KEY_GPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    Constants.KEY_USERS_ID + " INTEGER, " +
                    Constants.KEY_GPS_SYSDATE + " TIMESTAMP, " +
                    Constants.KEY_GPS_LAT + " FLOAT, " +
                    Constants.KEY_GPS_LONG + " FLOAT, " +
                    Constants.KEY_GPS_SPEED + " FLOAT, " +
                    Constants.KEY_GPS_ACCEL + " FLOAT, " +
                    "CONSTRAINT fk_userID FOREIGN KEY (" + Constants.KEY_USERS_ID + ") REFERENCES " + Constants.TABLE_USERS + "(" + Constants.KEY_USERS_ID + ")" +
                    ");";


            //CONSTRAINT fk_abt FOREIGN KEY (Abt_Nr) REFERENCES Abteilungen(Abt_Nr)

            db.execSQL( CREATE_GPS_TABLE );
            db.execSQL( CREATE_GPS_TABLE_STATS);
            db.setTransactionSuccessful();

            Log.e( "DB", CREATE_GPS_TABLE );
            Log.e( "DB", CREATE_USERS_TABLE );
            Log.e( "DB", CREATE_GPS_TABLE_STATS );


        } catch (Exception e) {
            Log.e( "DB", "error in on create tables");
        } finally {
            db.endTransaction();
        }


    }

    /*
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_GPS );
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
            onCreate( db );
        }

    }
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_GPS );
            db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_USERS );
            db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_GPS_STATS );
            onCreate( db );
        }

    }

    /*
    public void onClearBoth(){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_GPS );
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_USERS );
        onCreate( db );

    }*/

    public void onClearBoth(){

        SQLiteDatabase db = getWritableDatabase();


        db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_GPS );
        db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_GPS_STATS );
        db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_USERS );
        onCreate( db );

    }

    public void onClearUser(){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_USERS );
        onCreate( db );

    }

    public void onClearGPS(){

        SQLiteDatabase db = getWritableDatabase();

        db.execSQL( "DROP TABLE IF EXISTS " + Constants.TABLE_GPS );

        db.beginTransaction();

        try {

            String CREATE_GPS_TABLE = "CREATE TABLE " + Constants.TABLE_GPS +
                    " (" +
                    Constants.KEY_GPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Define a primary key
                    Constants.KEY_USERS_ID + " INTEGER, " +
                    Constants.KEY_GPS_SYSDATE + " TIMESTAMP, " +
                    Constants.KEY_GPS_LAT + " FLOAT, " +
                    Constants.KEY_GPS_LONG + " FLOAT, " +
                    Constants.KEY_GPS_SPEED + " FLOAT, " +
                    Constants.KEY_GPS_ACCEL + " FLOAT, " +
                    "CONSTRAINT fk_userID FOREIGN KEY (" + Constants.KEY_USERS_ID + ") REFERENCES " + Constants.TABLE_USERS + "(" + Constants.KEY_USERS_ID + ")" +
                    ");";



            db.execSQL( CREATE_GPS_TABLE );
            db.setTransactionSuccessful();

            Log.e( "DB", CREATE_GPS_TABLE );

        } catch (Exception e) {
            Log.e( "DB", "error in on create table gps again");
        } finally {
            db.endTransaction();
        }

    }

    /*
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
     */

    public void addUser(String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        String ADD_USERS = "";

        try {

            ADD_USERS = "INSERT INTO " + Constants.TABLE_USERS +
                    " (" +
                    Constants.KEY_USERS_NAME + ", " +
                    Constants.KEY_USERS_PASSWORD + ") VALUES ('" +
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

    public void addUserFromJSON(int userID, String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();



        String ADD_USERS = "";

        try {

            ADD_USERS = "INSERT INTO " + Constants.TABLE_USERS +
                    " (" +
                    Constants.KEY_USERS_ID + ", " +
                    Constants.KEY_USERS_NAME + ", " +
                    Constants.KEY_USERS_PASSWORD + ") VALUES ('" +
                    userID + "', '" +
                    userName + "', '" +
                    password + "');";

            db.execSQL( ADD_USERS );
            Log.e( "DB", ADD_USERS);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e( "DB", "error in insert - usersFromJSON" + ADD_USERS);
        } finally {
            db.endTransaction();
        }

    }

    /*
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
     */


    public void addLocationFromJSON(GPS_Class gps_class, String table){

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);

        SQLiteDatabase db = getWritableDatabase();

        String ADD_GPS = "";

        db.beginTransaction();

        try {

            ADD_GPS = "INSERT INTO " + table +
                    " (" +
                    Constants.KEY_GPS_ID + ", " +
                    Constants.KEY_USERS_ID + ", " +
                    //Constants.KEY_GPS_SYSDATE + ", " +
                    Constants.KEY_GPS_LAT + ", " +
                    Constants.KEY_GPS_LONG + ", " +
                    Constants.KEY_GPS_SPEED + ", " +
                    Constants.KEY_GPS_ACCEL + ") VALUES ('" +
                    gps_class.getLoc_id() + "', '" +
                    gps_class.getUser_id_fk() + "', '" +
                    //gps_class.getLoc_date() + "', '" +
                    gps_class.getLoc_lat() + "', '" +
                    gps_class.getLoc_lng() + "', '" +
                    gps_class.getLoc_speed() + "', '" +
                    gps_class.getAccel() + "');";

            db.execSQL( ADD_GPS );

            Log.e( "DB", ADD_GPS );
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e( "DB", "error in add gps from json" + ADD_GPS );
        } finally {
            db.endTransaction();
        }

    }


    public void addLocation(GPS_Class gps_ins){

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);

        SQLiteDatabase db = getWritableDatabase();

        String ADD_GPS = "";

        db.beginTransaction();

        try {

            ADD_GPS = "INSERT INTO " + Constants.TABLE_GPS +
                    " (" +
                    Constants.KEY_GPS_SYSDATE + ", " +
                    Constants.KEY_GPS_LAT + ", " +
                    Constants.KEY_GPS_LONG + ", " +
                    Constants.KEY_GPS_SPEED + ", " +
                    Constants.KEY_GPS_ACCEL + ") VALUES ('" +
                    dateString + "', '" +
                    gps_ins.getLoc_lat() + "', '" +
                    gps_ins.getLoc_lng() + "', '" +
                    gps_ins.getLoc_speed() + "', '" +
                    gps_ins.getAccel() + "');";

                db.execSQL( ADD_GPS );

                Log.e( "DB", ADD_GPS );
                db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e( "DB", "error in add gps" + ADD_GPS );
        } finally {
            db.endTransaction();
        }

    }


    public void deleteFromTable(String table_name) {

        SQLiteDatabase db = getWritableDatabase();

        String statement = "";



        db.beginTransaction();

        try {


            statement = "DELETE FROM " + table_name;

            db.execSQL( statement );

            Log.e( "DB", "delete from table via statement: " + statement);

        } catch (Exception e) {
            Log.e( "DB", "error in delete from table " + statement );
        } finally {
            db.endTransaction();
        }



    }

    /*
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
                        " WHERE " + KEY_USERS_NAME + " != ?";

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
     */

    public List<GPS_Class> fetch_gps(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date loc_temp_date = null;

        SQLiteDatabase db = getWritableDatabase();


        List<GPS_Class> gps_list = new ArrayList<>(  );

        String FETCH_GPS_DATA =
                "SELECT * FROM " + Constants.TABLE_GPS + ";";

        Log.e( "DB-GPS", "query: " +  FETCH_GPS_DATA);

        Cursor c1 = db.rawQuery( FETCH_GPS_DATA, new String[]{} );
        //Cursor c1 = db.rawQuery( FETCH_GPS_DATA, new String[]{""} );

        //Cursor c1 = db.query( Constants.TABLE_GPS, null, null, null,  null, null, null);

        int count_rows = c1.getCount();

        if(count_rows == 0){
            GPS_Class returnOnZero = new GPS_Class( 48.239078, 16.378282, 5, 1.5 );
            gps_list.add( returnOnZero );
            return gps_list;
        }

        Log.e( "DB-GPS", "count rows: " + count_rows );


        /*while(c1.moveToNext()){

            try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }

            gps_data.setLoc_id( c1.getInt( 1 ) );
            //gps_data.setLoc_date( loc_temp_date );
            gps_data.setLoc_lat( c1.getFloat( 3 ) );
            gps_data.setLoc_lng( c1.getFloat( 4 ) );
            gps_data.setLoc_speed( c1.getFloat( 5 ) );

            gps_list.add( gps_data );

            Log.e( "DB-GPS", "row 1 data: " + gps_data.getLoc_id() + gps_data.getLoc_lat() + gps_data.getLoc_lng() + gps_data.getLoc_speed());


        }*/

        c1.moveToFirst();

        for (int i = 0; i < count_rows; i++){

            GPS_Class gps_data = new GPS_Class(  );

            /*try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }*/

            Log.e( "DB-GPS", "rows: " + c1.getInt( 0 ) + c1.getInt( 1 ) + c1.getFloat( 3 ) + c1.getFloat( 4 ) + c1.getFloat( 5 ) );

            gps_data.setLoc_id( c1.getInt( 0 ) );
            gps_data.setUser_id_fk( c1.getInt( 1 ) );
            //gps_data.setLoc_date( loc_temp_date );
            gps_data.setLoc_lat( c1.getFloat( 3 ) );
            gps_data.setLoc_lng( c1.getFloat( 4 ) );
            gps_data.setLoc_speed( c1.getFloat( 5 ) );
            gps_data.setAccel( c1.getFloat( 6 ) );

            gps_list.add( gps_data );

            Log.e( "DB-GPS", "row " + i + " data: " + gps_data.getLoc_id() + " " + gps_data.getUser_id_fk() + " " + gps_data.getLoc_lat() + " " + gps_data.getLoc_lng() + " " + gps_data.getLoc_speed() + " " + gps_data.getAccel());

            c1.moveToNext();

        }



        return gps_list;

    }



    public List<User_Class> fetch_users(){


        SQLiteDatabase db = getWritableDatabase();


        List<User_Class> user_list = new ArrayList<>(  );

        String FETCH_USR_DATA =
                "SELECT * FROM " + Constants.TABLE_USERS + ";";

        Log.e( "DB-USR", "query: " +  FETCH_USR_DATA);

        Cursor c1 = db.rawQuery( FETCH_USR_DATA, new String[]{} );
        //Cursor c1 = db.rawQuery( FETCH_GPS_DATA, new String[]{""} );

        //Cursor c1 = db.query( Constants.TABLE_GPS, null, null, null,  null, null, null);

        int count_rows = c1.getCount();

        Log.e( "DB-USR", "count rows: " + count_rows );


        /*while(c1.moveToNext()){

            try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }

            gps_data.setLoc_id( c1.getInt( 1 ) );
            //gps_data.setLoc_date( loc_temp_date );
            gps_data.setLoc_lat( c1.getFloat( 3 ) );
            gps_data.setLoc_lng( c1.getFloat( 4 ) );
            gps_data.setLoc_speed( c1.getFloat( 5 ) );

            gps_list.add( gps_data );

            Log.e( "DB-GPS", "row 1 data: " + gps_data.getLoc_id() + gps_data.getLoc_lat() + gps_data.getLoc_lng() + gps_data.getLoc_speed());


        }*/

        c1.moveToFirst();

        for (int i = 0; i < count_rows; i++){

            User_Class user_data = new User_Class(  );

            /*try {
                loc_temp_date = sdf.parse( c1.getString( 2 ) );

                Log.e( "DB-GPS", "TRY: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );


            } catch (ParseException e) {
                e.printStackTrace();

                Log.e( "DB-GPS", "CATCH - ERROR: statement fetching gps: " + FETCH_GPS_DATA );
                Log.e( "DB-GPS", "ERROR in fetching gps data; date was: " + loc_temp_date + c1.getString( 2 ) );

            }*/

            Log.e( "DB-USR", "rows: " + c1.getInt( 0 ) + c1.getString( 1 ) + c1.getString( 2 ));

            user_data.setUsers_id_global( c1.getInt( 0 ) );
            //gps_data.setLoc_date( loc_temp_date );
            user_data.setUsers_name( c1.getString( 1 ) );
            user_data.setUsers_password( c1.getString( 2 ) );

            user_list.add( user_data );

            Log.e( "DB-GPS", "row " + i + " data: " + user_data.getUsers_id_global() + " " + user_data.getUsers_name() + " " + user_data.getUsers_password());


            c1.moveToNext();

        }


        for (int i = 0; i < user_list.size(); i++){

            Log.e( "DB-GPS", "LIST: row " + i + " data: " + user_list.get( i ).getUsers_id_global() + " " + user_list.get( i ).getUsers_name() + " " + user_list.get( i ).getUsers_password());


        }



        return user_list;

    }




    public int checkUser(String userName, String password) {

        SQLiteDatabase db = getWritableDatabase();

        int m = 0;                                                                              // m = 0 ... nichts stimmt

        //  String CHECK_USER_COMPLETLY_RIGHT = "SELECT * FROM " + TABLE_USERS +
        //        " WHERE " + KEY_USERS_NAME + " = " + userName +
        //      " AND " + KEY_USERS_PASSWORD + " = " + password + ";";

        String CHECK_USER_COMPLETELY_RIGHT =
                "SELECT * FROM " + Constants.TABLE_USERS +
                        " WHERE " + Constants.KEY_USERS_NAME + " = ?" +
                        " AND " + Constants.KEY_USERS_PASSWORD + " = ?;";

        Cursor c1 = db.rawQuery( CHECK_USER_COMPLETELY_RIGHT, new String[]{userName, password} );

        String CHECK_USER_PARTLY_RIGHT =
                "SELECT * FROM " + Constants.TABLE_USERS +
                        " WHERE " + Constants.KEY_USERS_NAME + " = ?" +
                        " AND " + Constants.KEY_USERS_PASSWORD + " != ?;";

        Cursor c2 = db.rawQuery( CHECK_USER_PARTLY_RIGHT, new String[]{userName, password} );


        if (c1.moveToNext()) {
            // username stimmt, passwort auch

            m = 1;                                                                              // m = 1 ... name und passwort stimmen
                                                                                                        // ... c1
            setUser_id( c1.getInt( 0 ) );
            setUser_logged( new User_Class(c1.getInt( 0 ), c1.getString( 1 ), c1.getString( 2 )) );


        } else if (c2.moveToNext()) {
            // username stimmt, passwort nicht                                                  // m = 2 ... name stimmt, passwort nicht
                                                                                                        // ... c2

            m = 2;

        }

        return m;

    }


    public void addToGPSList(GPS_Class gps_instance){

        List<GPS_Class> gps_list = new ArrayList<>(  );

        gps_list.add( gps_instance );

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
