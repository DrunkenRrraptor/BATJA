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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Robs on 09.06.18.
 */

public class Stats6_frag2 extends Fragment {
    private static final String TAG = "Stats6 - Frag2";

    private TextView textRecordsAll;
    private TextView textSpeedAll;
    private TextView textAccelAll;
    private TextView textNumberAll;

    private DatabaseManagement dbm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.frag2_stats6, container, false );

        dbm = DatabaseManagement.getInstance( getContext() );

        textRecordsAll = (TextView) view.findViewById( R.id.textView62records );
        textSpeedAll = (TextView) view.findViewById( R.id.textView62speed );
        textAccelAll = (TextView) view.findViewById( R.id.textView62accel );
        textNumberAll = (TextView) view.findViewById( R.id.textView62number );

        setTextsForAllStats();

        return view;

    }

    public void setTextsForAllStats(){

        textRecordsAll.setText( Integer.toString( dbm.getCount_records_all() ) );
        textSpeedAll.setText( Double.toString( round((dbm.getAvg_speed_all() * Constants.CONST_KMH_TO_MS), 2) ) + " km/h" );
        textAccelAll.setText( Double.toString( round((dbm.getAvg_accel_all()), 2)  ) + " m/s^2" );
        textNumberAll.setText( Integer.toString( dbm.getCount_number_users() ) );

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
