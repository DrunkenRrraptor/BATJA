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


    //AlertDialog.Builder missingTextAlert = new AlertDialog.Builder(this);

    DatabaseManagement dbm;
    int m;
    private RequestQueue requestQuestJSONIncoming;

    private int arraySize = 0;

    private int count_loops = 0;
    private int count_records_all = 0;
    private int count_records_user = 0;
    private double min_lat = 90;                   // apply those only for logged in user
    private double max_lat = -90;
    private double min_lng = 90;
    private double max_lng = -90;
    private double avg_speed_user = 0;
    private double avg_speed_all = 0;
    private double avg_accel_user = 0;
    private double avg_accel_all = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        //dbm = new DatabaseManagement( this);
        dbm = DatabaseManagement.getInstance( this );

        requestQuestJSONIncoming = Volley.newRequestQueue( this );

        //Button buttonJSON = (Button) findViewById( R.id.buttonJSONtest );
        //buttonJSON.setOnClickListener( this );

        Button button1Log = (Button) findViewById( R.id.buttonLog );
        Button button2New = (Button) findViewById( R.id.buttonNew );
        //Button button4Show = (Button) findViewById( R.id.buttonShow );
        button1Log.setOnClickListener( this );
        button2New.setOnClickListener( this );
        //button4Show.setOnClickListener( this );


        dbm.onClearBoth();

        retrieveJSONonlineUser();


        //dbm.deleteFromTable( Constants.TABLE_USERS );

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.e( "Main2A", "on resume" );

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

        String userName = textUserName.getText().toString();
        String userPassword = textUserPassword.getText().toString();
        String userPasswordHashed = hashString(userPassword);

        Log.e( "HASH", "PASSWORD: " + userPasswordHashed );

        if (userName.matches("" ) || userPassword.matches( "" )) {
            if (userName.matches( "" )) {
                Toast.makeText( this, "You did not enter a username", Toast.LENGTH_SHORT ).show();
                //return;
            }
            if (userPassword.matches( "" )) {
                Toast.makeText( this, "You did not enter a password", Toast.LENGTH_SHORT ).show();
                //return;
            }
            //return;
        } else {
            switch (v.getId()){

                case R.id.buttonLog:
                    button1LogOnClickHandler(userName, userPasswordHashed);
                    break;
                case R.id.buttonNew:
                    button2NewOnClickHandler(userName, userPasswordHashed);
                    break;
                /*case R.id.buttonShow:
                    button4ShowOnClickHandler();
                    break;*/

                /*case R.id.buttonJSONtest:
                    buttonJSONTestHandler();
                    break;*/

                default: break;

            }
        }

    }

    /*public void buttonJSONTestHandler(){

        Intent intent = new Intent(this, Acitivity_for_Testing.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }*/

    /*public void button4ShowOnClickHandler(){

        Intent intent = new Intent(this, Main3Activity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }*/

    private void retrieveJSONonlineLoc(){


        JsonObjectRequest requestloc = new JsonObjectRequest( Request.Method.GET, Constants.URL_FULL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("loc");

                            arraySize = jsonArray.length();
                            dbm.setCount_records_all( arraySize );

                            Log.e( "JSON", "json array length " + jsonArray.length());

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject loc = jsonArray.getJSONObject(i);

                                int loc_id_global = loc.getInt("loc_id_global");
                                int users_id_global = loc.getInt("users_id_global");
                                //String sys_date = loc.getString("sys_date");
                                double lat = loc.getDouble( "lat" );
                                double lng = loc.getDouble( "lng" );
                                double speed = loc.getDouble( "speed" );
                                double accel = loc.getDouble( "accel" );


                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                                //dbm.addUserFromJSON( users_id_global, users_name, users_password );

                                GPS_Class gps_class = new GPS_Class( loc_id_global, users_id_global,
                                        lat, lng, speed, accel);


                                dbm.addLocationFromJSON( gps_class, Constants.TABLE_GPS_STATS );

                                calculateMyStats(users_id_global, lat, lng, speed, accel);


                            }

                            dbm.setGps_list( dbm.fetch_gps() );


                            /*List<GPS_Class> gps_listHelp;
                            gps_listHelp = dbm.fetch_gps();

                            for (int h = 0; h < gps_listHelp.size(); h++){

                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password());
                                //textViewJSONOutput.append( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password() + "\n");


                            }*/


                            /*List<User_Class> user_listHelp;
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());

                            }*/


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


    public void button1LogOnClickHandler (String userName, String userPassword) {                           // einloggen


        //Toast.makeText( this, "Button 'Log In' works", Toast.LENGTH_SHORT ).show();

        Toast toast1 = Toast.makeText(this, "Name: " + userName + ", PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();

        /*if(userName.isEmpty() || userPassword.isEmpty())
        {

            Toast toast3 = Toast.makeText(this, "Y'moron, either ya name or password are empty. Like mee ol' rum.", Toast.LENGTH_SHORT);
            toast3.show();

            //showAlert( "Y'moron, either ya name or password are empty. Like mee ol' rum." );
            // dbm.checkUser( userName, userPassword );
        }*/

        m = dbm.checkUser( userName, userPassword );


        switch (m){
            case 0: Toast toast4 = Toast.makeText(this, "Your name and password are both wrong.", Toast.LENGTH_SHORT);
                    toast4.show();
                    break;
            case 1: Toast toast5 = Toast.makeText(this, "Welcome.", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(this, MainMenu.class);
                    retrieveJSONonlineLoc();
                    toast5.show();
                    startActivity(intent);
                    break;
            case 2: Toast toast2 = Toast.makeText(this, "Your password is wrong.", Toast.LENGTH_SHORT);
                    toast2.show();
                    break;

            default: break;
        }

        //Toast toast2 = Toast.makeText(this, "Message: (blank = log in)" + m, Toast.LENGTH_SHORT);
        //toast2.show();





        /*Intent intent = new Intent(this, MapsActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/


    }

    public void button2NewOnClickHandler (String userName, String userPassword) {                           // registrieren

        //Toast.makeText( this, "Button 'New Here' works", Toast.LENGTH_SHORT ).show();

        User_Class user = new User_Class( userName, userPassword );

        //dbm.addUser( userName, userPassword );

        postUser( user );

        dbm.onClearBoth();

        retrieveJSONonlineUser();


        /*if(userName.isEmpty() || userPassword.isEmpty())
        {
            showAlert( "Ya either ya name or password are empty. Like mee ol' rum." );
        } else {
            dbm.addUser( userName, userPassword );
        }*/


        /*Intent intent = new Intent(this, MainMenu.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        //Toast toast2 = Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT);
        //toast2.show();

        Toast toast2 = Toast.makeText(this, "Registration successful. Please sign in", Toast.LENGTH_SHORT);
        toast2.show();


        /*Toast toast1 = Toast.makeText(this, "Name: " + userName + " , PWD: " + userPassword, Toast.LENGTH_SHORT);
        toast1.show();*/

        //"Ya password happens to be wrong. Try again or create user.";

    }

    /*public void showAlert(String message){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable( true );
        alertDialogBuilder.setTitle( "Alert" );
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton( "got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });*/

        /*AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(this);

        alertDialog1.setMessage( message )
                .setPositiveButton(  )*/

        /*alertDialog1.setPositiveButton( "Got it",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText( this, "you clicked something bloody stupid", Toast.LENGTH_LONG ).show();
                    }
                } );


    }*/


    private void retrieveJSONonlineUser(){

        String urlJSONUsers = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_users.php";

        JsonObjectRequest requestUsers = new JsonObjectRequest( Request.Method.GET, urlJSONUsers, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");

                            dbm.setCount_number_users( jsonArray.length() );

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject users = jsonArray.getJSONObject(i);

                                int users_id_global = users.getInt("users_id_global");
                                String users_name = users.getString("users_name");
                                String users_password = users.getString("users_password");



                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                                dbm.addUserFromJSON( users_id_global, users_name, users_password );


                            }

                            List<User_Class> user_listHelp = new ArrayList<>(  );
                            user_listHelp = dbm.fetch_users();

                            for (int h = 0; h < user_listHelp.size(); h++){

                                Log.e("USR-L", "List of Users: (name) " + user_listHelp.get( h ).getUsers_name());
                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());

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


                    /*
                    jsonParam.put("timestamp", 1488873360);
                    jsonParam.put("uname", gps_ins.);
                    jsonParam.put("message", message.getMessage());
                    jsonParam.put("latitude", 0D);
                    jsonParam.put("longitude", 0D);*/

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
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

        //count_records_all = arraySize;

/*        min_lat = gps_list.get( 0 ).getLoc_lat();
        max_lat = gps_list.get( 0 ).getLoc_lat();
        min_lng = gps_list.get( 0 ).getLoc_lng();
        max_lng = gps_list.get( 0 ).getLoc_lng();*/

        //for (GPS_Class gps_list_inst : gps_list){

            count_loops++;

            avg_speed_all = avg_speed_all + speed;
            avg_accel_all = avg_accel_all + accel;

            if (user_id == dbm.getUser_logged().getUsers_id_global()){

                count_records_user++;

                avg_speed_user = avg_speed_user + speed;
                avg_accel_user = avg_accel_user + accel;

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

            }

            dbm.setCount_records_user( count_records_user );
            dbm.setCount_records_all( count_records_all );
            dbm.setMin_lat( min_lat );
            dbm.setMax_lat( max_lat );
            dbm.setMin_lng( min_lng );
            dbm.setMax_lng( max_lng );
            dbm.setAvg_speed_user( avg_speed_user );
            dbm.setAvg_speed_all( avg_speed_all );
            dbm.setAvg_accel_user( avg_accel_user );
            dbm.setAvg_accel_all( avg_speed_all );

        //}

    }




}
