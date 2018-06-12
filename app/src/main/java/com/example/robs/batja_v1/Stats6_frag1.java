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

    //
    //
    // STATS -> fragment for user specific data
    //
    //

    private DatabaseManagement dbm;

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

        dbm = DatabaseManagement.getInstance( getContext() );

        textUsername = (TextView) view.findViewById( R.id.textView61username );
        textRecords = (TextView) view.findViewById( R.id.textView61records );
        textLat = (TextView) view.findViewById( R.id.textView61lat );
        textLng = (TextView) view.findViewById( R.id.textView61lng );
        textSpeed = (TextView) view.findViewById( R.id.textView61speed );
        textAccel = (TextView) view.findViewById( R.id.textView61accel );

        setTextsForMyStats();

        return view;

    }

    public void setTextsForMyStats(){

        // set text in text views to data from dbm singleton
        textUsername.setText( dbm.getUser_logged().getUsers_name() );
        textRecords.setText( Integer.toString( dbm.getCount_records_user() ) );
        textLat.setText( Double.toString( dbm.getMin_lat() ) + " to " + Double.toString( dbm.getMax_lat() ) );
        textLng.setText( Double.toString( dbm.getMin_lng() ) + " to " + Double.toString( dbm.getMax_lng() ) );
        textSpeed.setText( Double.toString( round((dbm.getAvg_speed_user() * Constants.CONST_KMH_TO_MS), 2)  ) + " km/h" );
        textAccel.setText( Double.toString( round( dbm.getAvg_accel_user(), 2 ) ) + " m/s^2" );

    }

    // round, do not trunc
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
