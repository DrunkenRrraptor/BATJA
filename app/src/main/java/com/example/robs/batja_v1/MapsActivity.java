package com.example.robs.batja_v1;

import android.Manifest;
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

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int request_code_loc1 = 1;
    private LocationManagement lm;
    private UiSettings mUiSettings;

    private DatabaseManagement dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_maps );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        //dbm = new DatabaseManagement( this );
        dbm = DatabaseManagement.getInstance( this );

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

        mMap = googleMap;

        mUiSettings = mMap.getUiSettings();


        //lm = (LocationManagement) new LocationManagement();



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

        //mMap.setMyLocationEnabled( true );


        float hueThemeColor = 0.545098f;

        //
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng( -34, 151 );
        LatLng htw = new LatLng( 48.239078, 16.378282 );


        //mMap.addMarker( new MarkerOptions().position( htw ).title( "Marker at the HTW" ) ).setIcon( BitmapDescriptorFactory.defaultMarker(hueThemeColor));
        mMap.moveCamera( CameraUpdateFactory.newLatLng( htw ) );
        mMap.moveCamera( CameraUpdateFactory.zoomTo( 15 ) );

        //mMap.moveCamera( CameraUpdateFactory.newLatLng( lm.currentLoc ));





        //
        //  DUMMY DATEN
        //
        //

        GPS_Class gps_ins1 = new GPS_Class( 48.239789759281074, 48.239789759281074, 0, 0 );
        GPS_Class gps_ins2 = new GPS_Class( 48.23961882566993, 16.37805280888142, 8.3333333333, 0 );
        GPS_Class gps_ins3 = new GPS_Class( 48.23948648443996, 16.378197756512236, 0, 0 );


/*

        double lat_dummy_1_strt = 48.23978731239047;       // anfang der tour
        double lng_dummy_1_strt = 16.377881684593262;
        double speed_dummy_1_strt = 0;
        LatLng ltlng = new LatLng (lat_dummy_1_strt, lng_dummy_1_strt);

        double lat_dummy_1_nrt = 48.239583663472466;         // en route
        double lng_dummy_1_nrt = 16.378080168060364;
        double speed_dummy_1_nrt = 6;

        double lat_dummy_1_end = 48.23944432427199;         // ende der tour
        double lng_dummy_1_end = 16.378249147228303;
        double speed_dummy_1_end = 0;

        // erste linie

        if ( speed_dummy_1_strt <= Constants.CONST_SPEED_THRESH_1_MS ){

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_strt, lng_dummy_1_strt ), new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ) )
                    .color( Color.RED )
                    .width( 5 )
            );

        } else if( speed_dummy_1_strt > Constants.CONST_SPEED_THRESH_1_MS & speed_dummy_1_strt <= Constants.CONST_SPEED_THRESH_2_MS) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_strt, lng_dummy_1_strt ), new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ) )
                    .color( Color.YELLOW )
                    .width( 5 )
            );

        } else if( speed_dummy_1_strt > Constants.CONST_SPEED_THRESH_2_MS ) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_strt, lng_dummy_1_strt ), new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ) )
                    .color( Color.GREEN )
                    .width( 5 )
            );
        }

        // zweite linie

        if ( speed_dummy_1_nrt <= Constants.CONST_SPEED_THRESH_1_MS ){

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ), new LatLng( lat_dummy_1_end, lng_dummy_1_end ) )
                    .color( Color.RED )
                    .width( 5 )
            );

        } else if( speed_dummy_1_nrt > Constants.CONST_SPEED_THRESH_1_MS & speed_dummy_1_nrt <= Constants.CONST_SPEED_THRESH_2_MS) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ), new LatLng( lat_dummy_1_end, lng_dummy_1_end ) )
                    .color( Color.YELLOW )
                    .width( 5 )
            );

        } else if( speed_dummy_1_nrt > Constants.CONST_SPEED_THRESH_2_MS ) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ), new LatLng( lat_dummy_1_end, lng_dummy_1_end ) )
                    .color( Color.GREEN )
                    .width( 5 )
            );
        }
*/




        //



        // fetch location
        // draw polylines

        List<GPS_Class> gps_list = dbm.fetch_gps();

        Log.e("MAP", "gps_list size: " + gps_list.size());

        GPS_Class gps_startLoc = gps_list.get( 0 );

        double plineStartLat = 0;
        double pLineStartLng = 0;
        double pLineStartSpeed = 0;
        double pLineStartAccel = 0;
        double pLineEndLat = 0;
        double pLineEndLng = 0;
        double pLineEndSpeed = 0;
        double pLineEndAccel = 0;

        plineStartLat = gps_startLoc.getLoc_lat();
        pLineStartLng = gps_startLoc.getLoc_lng();
        pLineStartSpeed = gps_startLoc.getLoc_speed();
        pLineStartAccel = gps_startLoc.getAccel();

        int first_time = 0;

        // schleife

        for (GPS_Class gps_list_iterator : gps_list){

            if(first_time == 0){

                first_time = 1;
                continue;

            } else if (first_time == 1){

                pLineEndLat = gps_list_iterator.getLoc_lat();
                pLineEndLng = gps_list_iterator.getLoc_lng();
                pLineEndSpeed = gps_list_iterator.getLoc_speed();
                pLineEndAccel = gps_list_iterator.getAccel();

                first_time = 2;

            } else {

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

            if (latlngDist( plineStartLat, pLineStartLng, pLineEndLat, pLineEndLng ) > 500)
                continue;

            if ( pLineStartSpeed <= Constants.CONST_SPEED_THRESH_1_MS ){

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

/*

/*
    private void enable_buttons() {

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),LocationManagement.class);
                startService(i);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),LocationManagement.class);
                stopService(i);

            }
        });

    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                //enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }*/

}


