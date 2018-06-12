package com.example.robs.batja_v1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    //
    //
    // LOG IN SCREEN
    //
    //

    DatabaseManagement dbm;
    int m;
    private RequestQueue requestQuestJSONIncoming;

    private List<GPS_Class> gps_listAll = new ArrayList<>(  );

    private int arraySize = 0;

    private int count_loops;
    private int count_records_all;
    private int count_records_user;
    private double min_lat;                   // apply those only for logged in user
    private double max_lat;
    private double min_lng;
    private double max_lng;
    private double avg_speed_user;
    private double avg_speed_all;
    private double avg_accel_user;
    private double avg_accel_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        // Database Singleton
        dbm = DatabaseManagement.getInstance( this );

        requestQuestJSONIncoming = Volley.newRequestQueue( this );

        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );

        dbm.onClearBoth();

        retrieveJSONonlineUser();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e( "Main2A", "on resume" );

        // reset Stats-Values if user return to login screen, thus potentially re-logging in
        arraySize = 0;
        count_loops = 0;
        count_records_user = 0;
        count_records_all = 0;
        min_lat = 90;
        max_lat = -90;
        min_lng = 90;
        max_lng = -90;
        avg_speed_user = 0;
        avg_speed_all = 0;
        avg_accel_user = 0;
        avg_accel_all = 0;
        dbm.setCount_records_user( 0 );
        dbm.setCount_records_all( 0 );
        //dbm.setCount_number_users( 0 );
        dbm.setMin_lat( 90 );
        dbm.setMax_lat( -90 );
        dbm.setMin_lng( 90 );
        dbm.setMin_lng( -90 );
        dbm.setAvg_speed_user( 0 );
        dbm.setAvg_speed_all( 0 );
        dbm.setAvg_accel_user( 0 );
        dbm.setAvg_accel_all( 0 );

    }

    @Override
    public void onClick(View v) {

        EditText textUserName = (EditText) findViewById( R.id.textName );
        EditText textUserPassword = (EditText) findViewById( R.id.textPwd );

        // use MD5 hash algorithm immediately for safe usage
        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();
        String userPasswordHashed = hashString( textUserPassword.getText().toString() );

        Log.e( "HASH", "PASSWORD: " + userPasswordHashed );

        // check if user entered text
        if (userName.matches("" ) || userPassword.matches( "" )) {
            if (userName.matches( "" )) {
                Toast.makeText( this, "You did not enter a username", Toast.LENGTH_SHORT ).show();
            }
            if (userPassword.matches( "" )) {
                Toast.makeText( this, "You did not enter a password", Toast.LENGTH_SHORT ).show();
            }
        } else {
            switch (v.getId()){

                // go do corresponding onClickHandler for pressed button
                case R.id.buttonLog:
                    button1LogOnClickHandler(userName, userPasswordHashed);
                    break;
                case R.id.buttonNew:
                    button2NewOnClickHandler(userName, userPasswordHashed);
                    break;

                default: break;

            }
        }

    }

    // log in handler
    public void button1LogOnClickHandler (String userName, String userPassword) {

        // check in database. return:
        // 0 = name and pwd are wrong.
        // 1 = name and pwd are right.
        // 2 = name is right and pwd is wrong.
        m = dbm.checkUser( userName, userPassword );

        switch (m){
            case 0: Toast toast4 = Toast.makeText(this, "Your name and password are both wrong.", Toast.LENGTH_SHORT);
                    toast4.show();
                    break;
            case 1: Toast toast5 = Toast.makeText(this, "Welcome // Loading data ...", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, MainMenu.class);
                    toast5.show();
                    // if user is allowed to log in get all loc data to dbm -> gps_3_stats
                    retrieveJSONonlineLoc();
                    // go to Act -> MainMenu
                    startActivity(intent);
                    break;
            case 2: Toast toast2 = Toast.makeText(this, "Your password is wrong.", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;

            default: break;
        }

    }

    // sign up (registration) handler
    public void button2NewOnClickHandler (String userName, String userPassword) {

        // create object of User Class and send data to post-method.
        // immediately delete from local table and retrieve data again to check if it worked and to have the up-to-date table
        User_Class user = new User_Class( userName, userPassword );

        postUser( user );
        dbm.onClearBoth();
        retrieveJSONonlineUser();

        Toast toast2 = Toast.makeText(this, "Registration successful. Please sign in", Toast.LENGTH_SHORT);
        toast2.show();

    }

    private void retrieveJSONonlineLoc(){

        JsonObjectRequest requestloc = new JsonObjectRequest( Request.Method.GET, Constants.URL_FULL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("loc");

                            // fetch json data with tag "loc" from url

                            arraySize = jsonArray.length();
                            dbm.setCount_records_all( arraySize );

                            Log.e( "JSON", "json array length " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {

                                // run through array and get information from every single object

                                JSONObject loc = jsonArray.getJSONObject(i);

                                int loc_id_global = loc.getInt("loc_id_global");
                                int users_id_global = loc.getInt("users_id_global");
                                //String sys_date = loc.getString("sys_date");
                                double lat = loc.getDouble( "lat" );
                                double lng = loc.getDouble( "lng" );
                                double speed = loc.getDouble( "speed" );
                                double accel = loc.getDouble( "accel" );

                                GPS_Class gps_class = new GPS_Class( loc_id_global, users_id_global,
                                        lat, lng, speed, accel);

                                // save in database and list

                                dbm.addLocationFromJSON( gps_class, Constants.TABLE_GPS_STATS );
                                gps_listAll.add( gps_class );

                                // stats

                                calculateMyStats(users_id_global, lat, lng, speed, accel);

                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e( "VLY-LOC", "error in volley loc - json exception" );

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e( "VLY-LOC", "error in volley loc - error response" );

            }
        });

        requestQuestJSONIncoming.add(requestloc);

    }

    private void retrieveJSONonlineUser(){

        // analog to loc

        JsonObjectRequest requestUsers = new JsonObjectRequest( Request.Method.GET, Constants.URL_USERS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");

                            int number_users = jsonArray.length();
                            dbm.setCount_number_users( number_users );

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject users = jsonArray.getJSONObject(i);

                                int users_id_global = users.getInt("users_id_global");
                                String users_name = users.getString("users_name");
                                String users_password = users.getString("users_password");

                                dbm.addUserFromJSON( users_id_global, users_name, users_password );

                            }

                            List<User_Class> user_listHelp = new ArrayList<>(  );
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                Log.e("USR-L", "List of Users: (name) " + user_listHelp.get( h ).getUsers_name());

                                // check if it has worked by logging

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQuestJSONIncoming.add(requestUsers);

    }

    public void postUser(final User_Class user_ins) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // send one json object to webservice to be saved in phpMyAdmin database

                    URL url = new URL(Constants.URL_POST_USER);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put( "users_name", user_ins.getUsers_name() );
                    jsonParam.put( "users_password", user_ins.getUsers_password() );

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    // hashing algorithm
    public String hashString(String toBeHashed){

        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(toBeHashed.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            Log.e( "HASH", "" + hexString.toString() );

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.e( "HASH", "did not hash" );
        return "";

    }

    public void calculateMyStats(int user_id, double lat, double lng, double speed, double accel){

        // calculate speed and accel average

        count_loops++;

        avg_speed_all = avg_speed_all + speed;
        avg_accel_all = avg_accel_all + accel;

        if (user_id == dbm.getUser_logged().getUsers_id_global()){

            // user specific data

            count_records_user++;

            avg_speed_user = avg_speed_user + speed;
            avg_accel_user = avg_accel_user + accel;

            // calculate minimum and maximum of lat and lng
            if (lat < min_lat)
                min_lat = lat;
            if (lat > max_lat)
                max_lat = lat;
            if (lng < min_lng)
                min_lng = lng;
            if (lng > max_lng)
                max_lng = lng;

        }

        if (count_loops == arraySize){

            avg_speed_all = avg_speed_all / arraySize;
            avg_accel_all = avg_accel_all / arraySize;
            avg_speed_user = avg_speed_user / count_records_user;
            avg_accel_user = avg_accel_user / count_records_user;
            dbm.setCount_records_user( count_records_user );
            dbm.setCount_records_all( count_records_all );
            dbm.setGps_listAll( gps_listAll );
            dbm.setCount_loops( count_loops );
            dbm.setCount_records_all( arraySize );

            Log.e( "M2A", "countLoops: " + dbm.getCount_loops() + ", recordsAll: " + dbm.getCount_records_all() );

        }

        // write to database singleton

        dbm.setMin_lat( min_lat );
        dbm.setMax_lat( max_lat );
        dbm.setMin_lng( min_lng );
        dbm.setMax_lng( max_lng );
        dbm.setAvg_speed_user( avg_speed_user );
        dbm.setAvg_speed_all( avg_speed_all );
        dbm.setAvg_accel_user( avg_accel_user );
        dbm.setAvg_accel_all( avg_speed_all );

    }

}
