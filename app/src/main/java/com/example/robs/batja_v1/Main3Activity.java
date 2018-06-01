package com.example.robs.batja_v1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class Main3Activity extends AppCompatActivity {


    int i = 0;
    private Button btn_start, btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    DatabaseManagement dbm = DatabaseManagement.getInstance( this );

    private TextView latView;
    private TextView lngView;
    private TextView speedView;

    @Override
    protected void onResume() {

        Log.e("LOC", "onResume");

        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {


                @Override
                public void onReceive(Context context, Intent intent) {

                    //textView.append("\n" + intent.getExtras().get("coordinates"));
                    latView.append("\n" + intent.getExtras().get("coordinates"));

                    /*latView.append( Double.toString( (Double) intent.getExtras().get( "lat" ) ) + "\n");
                    lngView.append( Double.toString( (Double) intent.getExtras().get( "lng" ) ) + "\n");
                    speedView.append( Double.toString( (Double) intent.getExtras().get( "speed" ) ) + "\n");*/

                    /*latView.setText( latView.getText() + Double.toString( (Double) intent.getExtras().get( "lat" ) ) + "\n");
                    lngView.setText( lngView.getText() + Double.toString( (Double) intent.getExtras().get( "lng" ) ) + "\n");
                    speedView.setText( speedView.getText() + Double.toString( (Double) intent.getExtras().get( "speed" ) ) + "\n");*/


                    /*List<GPS_Class> gps_list = dbm.fetch_gps();

                    latView.append( Double.toString( gps_list.get( i ).getLoc_lat() ) + "\n" );
                    lngView.append( Double.toString( gps_list.get( i ).getLoc_lng() ) + "\n" );
                    speedView.append( Double.toString( gps_list.get( i ).getLoc_speed() ) + "\n" );*/

                    /*textView.append(Double.toString( gps_list.get( i ).getLoc_lat() ) + " " +
                            Double.toString( gps_list.get( i ).getLoc_lng() ) + " " +
                            Double.toString( gps_list.get( i ).getLoc_speed() ));*/

                    i++;

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btn_start = (Button) findViewById(R.id.button_startS);
        btn_stop = (Button) findViewById(R.id.button_stopS);

        //textView = (TextView) findViewById(R.id.textView_locS);
        latView = (TextView) findViewById( R.id.textView_locS );
        lngView = (TextView) findViewById( R.id.textView_locS2 );
        speedView = (TextView) findViewById( R.id.textView_locS3 );

        if(!runtime_permissions())
            enable_buttons();

    }

    public void enable_buttons() {

        Log.e("LOC", "enableButtons");

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("LOC", "onClickStart");

                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                startService(i);
            }
        });


        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("LOC", "onClickStop");

                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);

            }
        });

    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            Log.e("LOC", "runtimePermission");

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("LOC", "onRequestPermissionsResult Main");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                Log.e("LOC", "onRequestPermissionsResult");

                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }
}
