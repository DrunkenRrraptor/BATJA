package com.example.robs.batja_v1;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;

/**
 * Created by Robs on 11.04.18.
 */

public class LocationManagement extends Service {

    LocationListener locListener;
    LocationManager locManager;

    LatLng currentLoc;

    DatabaseManagement dbm;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }


    @Override
    public void onCreate() {

        super.onCreate();

        Log.e( "SRVC", "onCreate" );

        //dbm = new DatabaseManagement( this );

        //locManager = (LocationManager) getApplicationContext().getSystemService( Context.LOCATION_SERVICE );


        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);

                Log.e( "SRVC", "onLocationChanged" );

                //currentLoc = new LatLng( location.getLatitude(), location.getLongitude() );

                //double lat = location.getLatitude();
                //double lng = location.getLongitude();

                //dbm.addLocation( lat, lng );



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity( i );
            }


        };


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


        locManager = (LocationManager) getApplicationContext().getSystemService( Context.LOCATION_SERVICE );


        locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 3000, 0, locListener );




    }

    public LatLng getLoc(Location location){

        return new LatLng( location.getLatitude(), location.getLongitude() );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locManager != null){
            //noinspection MissingPermission
            locManager.removeUpdates(locListener);
        }
    }


}
