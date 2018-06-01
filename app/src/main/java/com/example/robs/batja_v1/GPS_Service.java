package com.example.robs.batja_v1;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Robs on 24.04.18.
 */

public class GPS_Service extends Service implements SensorEventListener{

    private LocationListener listener;
    private LocationManager locationManager;
    private DatabaseManagement dbm;
    private TextView txtXml;

    private Sensor accelSensor;
    private SensorManager sm;

    double[] accel = new double[3];
    double accel_maxNotG = 0;
    int accel_maxNotG_dir = 0;


    private RequestQueue requestQuestJSONIncoming;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        requestQuestJSONIncoming = Volley.newRequestQueue( this );

        dbm = new DatabaseManagement( this );


        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);


        Log.e( "SRVC", "onCreate Service" );

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent( "location_update" );

                double lat = location.getLatitude();
                double lng = location.getLongitude();
                double speed = location.getSpeed();

                i.putExtra( "coordinates", (String)(location.getLongitude() + " " + location.getLatitude() + " " + location.getSpeed()) );
                /*i.putExtra( "lat", lat );
                i.putExtra( "lng", lng );
                i.putExtra( "speed", speed );
                i.putExtra( "accel", accel );*/

                /*Bundle bundle = new Bundle(  );

                bundle.putString( "lat", String.valueOf( lat ) );
                bundle.putString( "lng", String.valueOf( lng ) );
                bundle.putString( "speed", String.valueOf( speed ) );
                //bundle.putString( "accel", String.valueOf( accel_maxNotG ) );

                sendBroadcast( i );*/

                Log.e("SRVC", "onLocChanged");


                //currentLoc = new LatLng( location.getLatitude(), location.getLongitude() );



                //dbm.addLocation( lat, lng, speed );

                GPS_Class gps_instance = new GPS_Class( lat, lng, speed, accel_maxNotG);
                dbm.addLocation( gps_instance );

                //postLoc(gps_instance);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity( i );

                Log.e("SRVC", "onProviderDisabled");

            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService( Context.LOCATION_SERVICE );

        //noinspection MissingPermission
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 3000, 0, listener );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);

            Log.e("SRVC", "onDestroy");

        }
    }






    // Sensor Management

    @Override
    public void onSensorChanged(SensorEvent event) {

        accel[0] = event.values[0];             // x
        accel[1] = event.values[1];             // y
        accel[2] = event.values[2];             // z

        // suche zweithöchste beschleunigung, weil hächste beschleunigung G ist



        if (accel[0] > accel[1]){
            if (accel[1] > accel[2])
                accel_maxNotG_dir = 1;
            else {
                if (accel[0] > accel[2])
                    accel_maxNotG_dir = 2;
                else
                    accel_maxNotG_dir = 0;
            }
        } else {
            if (accel[0] > accel[2])
                accel_maxNotG_dir = 1;
            else {
                if (accel[1] > accel[2])
                    accel_maxNotG_dir = 2;
                else
                    accel_maxNotG_dir = 1;
            }
        }



        /*if (accel[0] > accel[1]){
            if (accel[0] > accel[2]){
                if (accel[1] > accel[2]){
                    accel_maxNotG_dir = 1;
                } else {
                    accel_maxNotG_dir = 2;
                }
            }
            else {
                if (accel[2] > accel[0]){
                    accel_maxNotG_dir = 0;
                } else {
                    if (accel[1] > accel[2]){
                        accel_maxNotG_dir = 1;
                    }
                    else {
                        accel_maxNotG_dir = 2;
                    }
                }
            }
        } else if (accel[0] > accel[2]){
            if (accel[1] > accel[2]){
                if (accel[0] > accel[1]){
                    accel_maxNotG_dir = 1;
                } else {
                    accel_maxNotG_dir = 0;
                }
            } else {
                accel_maxNotG_dir = 1;
            }
        }*/




             /*
            } else {
                accel_maxNotG = accel[0];
                accel_maxNotG_dir = 0;
            }
        } else if (accel[1] > accel[2]){
            if (accel[1] > accel[0]){
                accel_maxNotG = accel[0];
                accel_maxNotG_dir = 0;
            } else {
                accel_maxNotG = accel[1];
                accel_maxNotG_dir = 1;
            }
        }*/


        accel_maxNotG = accel[accel_maxNotG_dir];


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }







    public void postLoc(final GPS_Class gps_ins) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(Constants.URL_POST_LOC);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();

                    jsonParam.put( "lat", gps_ins.getLoc_lat() );
                    jsonParam.put( "lng", gps_ins.getLoc_lng() );
                    jsonParam.put( "speed", gps_ins.getLoc_speed() );
                    jsonParam.put( "accel", gps_ins.getAccel() );

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





    private void retrieveJSONonlineUser(){

        String urlJSONUsers = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc.php";

        JsonObjectRequest requestUsers = new JsonObjectRequest( Request.Method.GET, urlJSONUsers, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("loc");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject loc = jsonArray.getJSONObject(i);

                                int loc_id_global = loc.getInt("loc_id_global");
                                int users_id_global = loc.getInt("users_id_global");
                                //Date sys_date = loc.getString("sys_date" );
                                double lat = loc.getDouble("lat");
                                double lng = loc.getDouble("lng");
                                double speed = loc.getDouble("speed");

                                //Date date2 = System.currentTimeMillis();

                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");

                                GPS_Class gps_class = new GPS_Class( loc_id_global, users_id_global, lat, lng, speed  );

                                dbm.addLocationFromJSON( gps_class );



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



}
