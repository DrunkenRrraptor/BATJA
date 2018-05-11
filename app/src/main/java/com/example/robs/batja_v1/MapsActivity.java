package com.example.robs.batja_v1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

        dbm = new DatabaseManagement( this );

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
        LatLng sydney = new LatLng( -34, 151 );
        LatLng htw = new LatLng( 48.239078, 16.378282 );


        mMap.addMarker( new MarkerOptions().position( htw ).title( "Marker at the HTW" ) ).setIcon( BitmapDescriptorFactory.defaultMarker(hueThemeColor));
        mMap.moveCamera( CameraUpdateFactory.newLatLng( htw ) );
        mMap.moveCamera( CameraUpdateFactory.zoomTo( 15 ) );

        //mMap.moveCamera( CameraUpdateFactory.newLatLng( lm.currentLoc ));


        //
        //  DUMMY DATEN
        //
        //

        double lat_dummy_1_strt = 48.239789759281074;       // anfang der tour
        double lng_dummy_1_strt = 16.377883947562168;
        double speed_dummy_1_strt = 0;
        LatLng ltlng = new LatLng (lat_dummy_1_strt, lng_dummy_1_strt);

        double lat_dummy_1_nrt = 48.23961882566993;         // en route
        double lng_dummy_1_nrt = 16.37805280888142;
        double speed_dummy_1_nrt = 8.33333333;              //      30 km/h

        double lat_dummy_1_end = 48.23961748587267;         // ende der tour
        double lng_dummy_1_end = 16.378047109187264;
        double speed_dummy_1_end = 0;

        // erste linie

        if ( speed_dummy_1_strt <= Constants.CONST_SPEED_THRESH_1_MS ){

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_strt, lng_dummy_1_strt ), new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ) )
                    .color( Color.RED )
                    .width( 5 )
            );

        } else if( speed_dummy_1_strt > Constants.CONST_SPEED_THRESH_2_MS & speed_dummy_1_strt <= Constants.CONST_SPEED_THRESH_3_MS) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_strt, lng_dummy_1_strt ), new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ) )
                    .color( Color.YELLOW )
                    .width( 5 )
            );

        } else if( speed_dummy_1_strt > Constants.CONST_SPEED_THRESH_3_MS ) {

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

        } else if( speed_dummy_1_nrt > Constants.CONST_SPEED_THRESH_2_MS & speed_dummy_1_nrt <= Constants.CONST_SPEED_THRESH_3_MS) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ), new LatLng( lat_dummy_1_end, lng_dummy_1_end ) )
                    .color( Color.YELLOW )
                    .width( 5 )
            );

        } else if( speed_dummy_1_nrt > Constants.CONST_SPEED_THRESH_3_MS ) {

            Polyline pline = mMap.addPolyline( new PolylineOptions()
                    .add( new LatLng( lat_dummy_1_nrt, lng_dummy_1_nrt ), new LatLng( lat_dummy_1_end, lng_dummy_1_end ) )
                    .color( Color.GREEN )
                    .width( 5 )
            );
        }





        //




        // fetch location









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


