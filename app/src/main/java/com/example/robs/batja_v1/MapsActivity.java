package com.example.robs.batja_v1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //
    //
    // GOOGLE MAPS
    //
    //

    private GoogleMap mMap;
    private int request_code_loc1 = 1;
    private UiSettings mUiSettings;
    private int argFromMenu;

    private RequestQueue requestQuestJSONIncoming;

    private List<GPS_Class> gps_list_for_NowDay = new ArrayList<>(  );

    private DatabaseManagement dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        dbm = DatabaseManagement.getInstance( this );

        requestQuestJSONIncoming = Volley.newRequestQueue( this );

    }

    @Override
    protected void onResume() {
        super.onResume();
        gps_list_for_NowDay.getClass();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e( "MAP", "onMapReady" );

        argFromMenu = getIntent().getExtras().getInt("VAL");

        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setCompassEnabled( true );
        mUiSettings.setAllGesturesEnabled( true );


        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {       // = 23

                requestPermissions( new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, request_code_loc1);

            }

            return;
        } else {

            mMap.setMyLocationEnabled( true );

        }

        // Add a dummy LatLng at the HTW and move camera on it to focus on Vienna an the UAS
        LatLng htw = new LatLng( 48.239078, 16.378282 );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( htw ) );
        mMap.moveCamera( CameraUpdateFactory.zoomTo( 15 ) );

        // fetch location
        // draw polylines

        double plineStartLat = 0;
        double pLineStartLng = 0;
        double pLineStartSpeed = 0;
        double pLineStartAccel = 0;
        double pLineEndLat = 0;
        double pLineEndLng = 0;
        double pLineEndSpeed = 0;
        double pLineEndAccel = 0;

        if (argFromMenu == 3)
            gps_list_for_NowDay = dbm.getGps_listAll();
        else
            gps_list_for_NowDay = dbm.fetch_gps();

        /*else if (argFromMenu == 2)
            retrieveJSONonlineLoc( 2 );
        else if (argFromMenu == 1)
            retrieveJSONonlineLoc( 1 );
        else
            retrieveJSONonlineLoc( 0 );*/

        Log.e("MAP", "gps_list size: " + gps_list_for_NowDay.size());

        GPS_Class gps_startLoc = gps_list_for_NowDay.get( 0 );

        plineStartLat = gps_startLoc.getLoc_lat();
        pLineStartLng = gps_startLoc.getLoc_lng();
        pLineStartSpeed = gps_startLoc.getLoc_speed();
        pLineStartAccel = gps_startLoc.getAccel();

        int first_time = 0;

        for (GPS_Class gps_list_iterator : gps_list_for_NowDay){

            if(first_time == 0){

                // do almost nothing in first iteration
                first_time = 1;
                continue;

            } else if (first_time == 1){

                // only get new data for end-point
                pLineEndLat = gps_list_iterator.getLoc_lat();
                pLineEndLng = gps_list_iterator.getLoc_lng();
                pLineEndSpeed = gps_list_iterator.getLoc_speed();
                pLineEndAccel = gps_list_iterator.getAccel();

                first_time = 2;

            } else {

                // switch end-point to new start-point and get new data for end-point
                plineStartLat = pLineEndLat;
                pLineStartLng = pLineEndLng;
                pLineStartSpeed = pLineEndSpeed;
                pLineStartAccel = pLineEndAccel;

                pLineEndLat = gps_list_iterator.getLoc_lat();
                pLineEndLng = gps_list_iterator.getLoc_lng();
                pLineEndSpeed = gps_list_iterator.getLoc_speed();
                pLineEndAccel = gps_list_iterator.getAccel();

                first_time++;

            }

            Log.e( "MAP", "Iteration: " + first_time + "Start Loc: " + plineStartLat + ", " + pLineStartLng + ", " + pLineStartSpeed + ", " + pLineStartAccel);
            Log.e( "MAP", "Iteration: " + first_time + "Start End: " + pLineEndLat + ", " + pLineEndLng + ", " + pLineEndSpeed + ", " + pLineEndAccel );

            // only draw lines if points are nearer than 500 meters
            if (latlngDist( plineStartLat, pLineStartLng, pLineEndLat, pLineEndLng ) > 500)
                continue;

            if ( pLineStartSpeed <= Constants.CONST_SPEED_THRESH_1_MS ){

                // speed is lower that THRESHOLD 1 and accel is lower 0.5 -> jam is more likeley
                if ( pLineStartAccel < 0.5 ){
                    Polyline pline = mMap.addPolyline( new PolylineOptions()
                            .add( new LatLng( plineStartLat, pLineStartLng ), new LatLng( pLineEndLat, pLineEndLng ) )
                            .color( Color.BLACK )
                            .width( 5 )
                    );
                } else {
                    Polyline pline = mMap.addPolyline( new PolylineOptions()
                            .add( new LatLng( plineStartLat, pLineStartLng ), new LatLng( pLineEndLat, pLineEndLng ) )
                            .color( Color.RED )
                            .width( 5 )
                    );
                }

            } else if( pLineStartSpeed > Constants.CONST_SPEED_THRESH_1_MS & pLineStartSpeed <= Constants.CONST_SPEED_THRESH_2_MS) {

                Polyline pline = mMap.addPolyline( new PolylineOptions()
                        .add( new LatLng( plineStartLat, pLineStartLng ), new LatLng( pLineEndLat, pLineEndLng ) )
                        .color( Color.YELLOW )
                        .width( 5 )
                );

            } else if( pLineStartSpeed > Constants.CONST_SPEED_THRESH_2_MS ) {

                Polyline pline = mMap.addPolyline( new PolylineOptions()
                        .add( new LatLng( plineStartLat, pLineStartLng ), new LatLng( pLineEndLat, pLineEndLng ) )
                        .color( Color.GREEN )
                        .width( 5 )
                );
            }

        }

    }

    // return distance betweeen lat - lng and lat - lng; nevermind curvature of earth
    public static double latlngDist(double lat1, double lng1, double lat2, double lng2) {

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a =          Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (float) (Constants.EARTH_RADIUS * c);

        return dist;

    }


//    private void retrieveJSONonlineLoc(int arg){
//
//        String urlJSONloc = "";
//
//        //urlJSONloc = "https://ieslamp.technikum-wien.at/2018-bvu-sys-teamb/batja/query_loc_moc_full.php";
//
//        if(arg == 0)
//            urlJSONloc = Constants.URL_1H_NOW;
//        else if(arg == 1)
//            //urlJSONloc = Constants.URL_TODAY;
//            urlJSONloc = Constants.URL_TODAY;
//        else if(arg == 2)
//            //urlJSONloc = Constants.URL_HIST;
//            urlJSONloc = Constants.URL_HIST;
//
//        Log.e( "JSON", "MAP - chosen url: " + urlJSONloc );
//
//        JsonObjectRequest requestloc = new JsonObjectRequest( Request.Method.GET, urlJSONloc, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("loc");
//
//                            Log.e( "JSON", "MAP - json array length " + jsonArray.length());
//
//                            /*if (jsonArray.length() == 0){
//                                GPS_Class gps_class = new GPS_Class( 0, 1, 48.239078, 16.378282, 0, 0 );
//                                gps_list.add( gps_class );
//
//                            }*/
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//
//                                JSONObject loc = jsonArray.getJSONObject(i);
//
//                                int loc_id_global = loc.getInt("loc_id_global");
//                                int users_id_global = loc.getInt("users_id_global");
//                                //String sys_date = loc.getString("sys_date");
//                                double lat = loc.getDouble( "lat" );
//                                double lng = loc.getDouble( "lng" );
//                                double speed = loc.getDouble( "speed" );
//                                double accel = loc.getDouble( "accel" );
//
//
//                                //textViewJSONOutput.append(String.valueOf(users_id_global) + ", " + users_name + ", " + users_password + "\n\n");
//
//                                //dbm.addUserFromJSON( users_id_global, users_name, users_password );
//
//                                GPS_Class gps_class = new GPS_Class( loc_id_global, users_id_global,
//                                        lat, lng, speed, accel);
//
//                                gps_list_for_NowDay.add( gps_class );
//
//                            }
//
//
//                            //dbm.setGps_list( dbm.fetch_gps() );
//
//
//                            /*List<GPS_Class> gps_listHelp;
//                            gps_listHelp = dbm.fetch_gps();
//
//                            for (int h = 0; h < gps_listHelp.size(); h++){
//
//                                //textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password());
//                                //textViewJSONOutput.append( user_listHelp.get( h ).getUsers_id_global() + " " + user_listHelp.get( h ).getUsers_name() + " " + user_listHelp.get( h ).getUsers_password() + "\n");
//
//
//                            }*/
//
//
//                            /*List<User_Class> user_listHelp;
//                            user_listHelp = dbm.fetch_users();
//
//                            for (int h = 0; h < user_listHelp.size(); h++){
//
//                                textViewJSONOutput.setText( user_listHelp.get( h ).getUsers_name());
//
//                            }*/
//
//
//                        } catch (JSONException e) {
//
//                            e.printStackTrace();
//                            Log.e( "VLY-LOC", "MAP - error in volley loc - json exception" );
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                error.printStackTrace();
//                Log.e( "VLY-LOC", "MAP - error in volley loc - error response" );
//
//            }
//        });
//
//        Log.e( "MAP", "add request loc to resquestjsonincoming");
//
//        requestQuestJSONIncoming.add(requestloc);
//
//    }

}


