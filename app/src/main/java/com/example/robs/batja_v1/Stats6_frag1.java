package com.example.robs.batja_v1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robs.batja_v1.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robs on 09.06.18.
 */

public class Stats6_frag1 extends Fragment {
    private static final String TAG = "Stats6 - Frag1";

    //private Button buttonTest;
    //private TextView textTest;

    private DatabaseManagement dbm;

    //List<GPS_Class> gps_list = new ArrayList<>(  );

    private TextView textUsername;
    private TextView textRecords;
    private TextView textLat;
    private TextView textLng;
    private TextView textSpeed;
    private TextView textAccel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag1_stats6, container, false );

        //buttonTest = (Button) view.findViewById(R.id.button1_tab1);
        //textTest = (TextView) view.findViewById( R.id.textView61 );

        //textTest.setText( "testtesttest" );

        dbm = DatabaseManagement.getInstance( getContext() );
        //gps_list = dbm.getGps_list(  );

        textUsername = (TextView) view.findViewById( R.id.textView61username );
        textRecords = (TextView) view.findViewById( R.id.textView61records );
        textLat = (TextView) view.findViewById( R.id.textView61lat );
        textLng = (TextView) view.findViewById( R.id.textView61lng );
        textSpeed = (TextView) view.findViewById( R.id.textView61speed );
        textAccel = (TextView) view.findViewById( R.id.textView61accel );

        setTextsForMyStats();

        /*
        textUsername.setText( "username" );
        textRecords.setText( "73" );
        textLat.setText( "48.2 to 48.5" );
        textLng.setText( "16.3 to 16.8" );
        textSpeed.setText( "35 km/h" );
        textAccel.setText( "3.5 m/s" );*/

        /*buttonTest.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( getActivity(), "TESTING BUTTON 1 TAB 1", Toast.LENGTH_LONG ).show();

            }
        } );*/

        return view;

    }




    public void setTextsForMyStats(){

        //String username = dbm.getUser_logged().getUsers_name();

        //Log.e( "TAB", "Username: " + username + ", Count: " + count_records_user + ", Lat: " + min_lat + " - " + max_lat + ", Lng: " + min_lng + " - " + max_lng + ", Speed: " + avg_speed_user + ", Accel: " + avg_accel_user);

        /*textUsername.setText( username );
        textRecords.setText( Integer.toString( count_records_user ) );
        textLat.setText( Double.toString( min_lat ) + " to " + Double.toString( max_lat ) );
        textLng.setText( Double.toString( min_lng ) + " to " + Double.toString( max_lng ) );
        textSpeed.setText( Double.toString( (avg_speed_user * Constants.CONST_KMH_TO_MS) ) + " km/h" );
        textAccel.setText( Double.toString( avg_accel_user ) + " m/s^2" );*/

        textUsername.setText( dbm.getUser_logged().getUsers_name() );
        textRecords.setText( Integer.toString( dbm.getCount_records_user() ) );
        textLat.setText( Double.toString( dbm.getMin_lat() ) + " to " + Double.toString( dbm.getMax_lat() ) );
        textLng.setText( Double.toString( dbm.getMin_lng() ) + " to " + Double.toString( dbm.getMax_lng() ) );
        textSpeed.setText( Double.toString( round((dbm.getAvg_speed_user() * Constants.CONST_KMH_TO_MS), 2)  ) + " km/h" );
        textAccel.setText( Double.toString( round( dbm.getAvg_accel_user(), 2 ) ) + " m/s^2" );


        /*textViewUserName.setText( "username" );
        textViewUserRecords.setText( "73" );
        textViewUserLat.setText( "48.2 to 48.4" );
        textViewUserLng.setText( "16.1 to 16.5" );
        textViewUserSpeed.setText( "32 km/h" );
        textViewUserSpeed.setText( "3.5 m/s^2" );*/

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
