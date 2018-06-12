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
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    double accel_maxG = 0;
    int accel_maxG_dir = 0;
    double accel_leastG = 0;
    int accel_leastG_dir = 0;
    double accel_norm = 0;

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

        dbm = DatabaseManagement.getInstance( this );

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Log.e( "SRVC", "onCreate Service" );

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Intent i = new Intent("location_update");
                i.putExtra("coordinates",Double.toString( round( location.getLatitude(), 5 ) ) + " // " + Double.toString( round( location.getLongitude(), 5 ) ) + " // " + Double.toString( round( location.getSpeed() * Constants.CONST_KMH_TO_MS, 1 )  ) + " km/h");
                sendBroadcast(i);

                double lat = location.getLatitude();
                double lng = location.getLongitude();
                double speed = location.getSpeed();

                sendBroadcast( i );

                Log.e("SRVC", "onLocChanged");

                GPS_Class gps_instance = new GPS_Class( lat, lng, speed, accel_norm);
                dbm.addLocation( gps_instance );

                postLoc(gps_instance);

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

    // Acceleration Sensor Management

    @Override
    public void onSensorChanged(SensorEvent event) {

        accel[0] = event.values[0];             // x
        accel[1] = event.values[1];             // y
        accel[2] = event.values[2];             // z

        if (accel[0] > accel[1]){
            if (accel[1] > accel[2]){
                accel_maxG_dir = 0;
                accel_maxNotG_dir = 1;
                accel_leastG_dir = 2;
            }
            else {
                if (accel[0] > accel[2]){
                    accel_maxG_dir = 0;
                    accel_maxNotG_dir = 2;
                    accel_leastG_dir = 1;
                }
                else {
                    accel_maxG_dir = 2;
                    accel_maxNotG_dir = 0;
                    accel_leastG_dir = 1;
                }
            }
        } else {
            if (accel[0] > accel[2]) {
                accel_maxG_dir = 1;
                accel_maxNotG_dir = 0;
                accel_leastG_dir = 2;
            }
            else {
                if (accel[1] > accel[2]) {
                    accel_maxG_dir = 1;
                    accel_maxNotG_dir = 2;
                    accel_leastG_dir = 0;
                }
                else {
                    accel_maxG_dir = 2;
                    accel_maxNotG_dir = 1;
                    accel_leastG_dir = 0;
                }
            }
        }

        accel_maxG = accel[accel_maxG_dir] - (SensorManager.GRAVITY_EARTH);
        accel_maxNotG = accel[accel_maxNotG_dir];
        accel_leastG = accel[accel_leastG_dir];

        accel_norm = Math.sqrt( Math.pow( accel_maxG, 2 ) * Math.pow( accel_maxNotG, 2 ) * Math.pow( accel_leastG, 2 ));

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

                    jsonParam.put("users_id_global", dbm.getUser_logged().getUsers_id_global());
                    jsonParam.put( "lat", gps_ins.getLoc_lat() );
                    jsonParam.put( "lng", gps_ins.getLoc_lng() );
                    jsonParam.put( "speed", gps_ins.getLoc_speed() );
                    jsonParam.put( "accel", gps_ins.getAccel() );

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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
