package com.example.robs.batja_v1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Stats4_tab3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Stats4_tab3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Stats4_tab3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseManagement dbm;
    TextView textViewUserName;
    TextView textViewUserRecords;
    TextView textViewUserLat;
    TextView textViewUserLng;
    TextView textViewUserSpeed;
    TextView textViewUserAccel;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Stats4_tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Stats4_tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Stats4_tab3 newInstance(String param1, String param2) {
        Stats4_tab3 fragment = new Stats4_tab3();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }

        Log.e("STATS", "TAB 3");






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //View v = inflater.inflate( R.layout.fragment_stats4_tab3, container, false );

        LayoutInflater li = getActivity().getLayoutInflater();
        View v = li.inflate( R.layout.fragment_stats4_tab3, container, false );

        ((TextView) v.findViewById( R.id.textViewUsername3 )).setText( "username" );
        ((TextView) v.findViewById( R.id.textViewUserLat )).setText( "13" );

        textViewUserRecords = (TextView) v.findViewById( R.id.textViewUserRecords );
        textViewUserRecords.setText( "hello" );

        //textViewUserName = (TextView) v.findViewById( R.id.textViewUsername3 );
        //textViewUserRecords = (TextView) v.findViewById( R.id.textViewUserRecords );
        textViewUserLat = (TextView) v.findViewById( R.id.textViewUserLat );
        textViewUserLng = (TextView) v.findViewById( R.id.textViewUserLng );
        textViewUserSpeed = (TextView) v.findViewById( R.id.textViewUserSpeed );
        textViewUserAccel = (TextView) v.findViewById( R.id.textViewUserAccel );

        setTextsForStats();

        return v;

        //return inflater.inflate( R.layout.fragment_stats4_tab3, container, false );
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnFragmentInteractionListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setTextsForStats(){

        int count_loops = 0;
        int count_records_all = 0;
        int count_records_user = 0;
        double min_lat = -90;                   // apply those only for logged in user
        double min_lat_temp = -90;
        double max_lat = 90;
        double max_lat_temp = 90;
        double min_lng = -90;
        double min_lng_temp = -90;
        double max_lng = 90;
        double max_lng_temp = 90;
        double avg_speed_user = 0;
        double avg_speed_all = 0;
        double avg_accel_user = 0;
        double avg_accel_all = 0;

        /*count_records_all = gps_list.size();

        //min_lat = gps_list.get( 0 ).getLoc_lat();
        //max_lat = gps_list.get( 0 ).getLoc_lat();
        //min_lng = gps_list.get( 0 ).getLoc_lng();
        //max_lng = gps_list.get( 0 ).getLoc_lng();

        for (GPS_Class gps_list_inst : gps_list){

            count_loops++;

            avg_speed_all = avg_speed_all + gps_list_inst.getLoc_speed();
            avg_accel_all = avg_accel_all + gps_list_inst.getAccel();

            if (gps_list_inst.getUser_id_fk() == dbm.getUser_logged().getUsers_id_global()){

                count_records_user++;

                avg_speed_user = avg_speed_user + gps_list_inst.getLoc_speed();
                avg_accel_user = avg_accel_user + gps_list_inst.getAccel();

                min_lat_temp = gps_list_inst.getLoc_lat();
                max_lat_temp = gps_list_inst.getLoc_lat();
                min_lng_temp = gps_list_inst.getLoc_lng();
                max_lng_temp = gps_list_inst.getLoc_lng();

                if (min_lat_temp < min_lat)
                    min_lat = min_lat_temp;
                if (max_lat_temp > max_lat)
                    max_lat = max_lat_temp;
                if (min_lng_temp < min_lng)
                    min_lng = min_lng_temp;
                if (max_lng_temp > max_lng)
                    max_lng = max_lng_temp;

            }

            if (count_loops == count_records_all){

                avg_speed_all = avg_speed_all / count_records_all;
                avg_accel_all = avg_accel_all / count_records_all;
                avg_speed_user = avg_speed_user / count_records_user;
                avg_accel_user = avg_accel_user / count_records_user;

            }

        }*/

        //setTextsForMyStats(count_records_user, min_lat, max_lat, min_lng, max_lng, avg_speed_user, avg_accel_user);
        //setTextsForAllStats(count_records_all, avg_speed_all, avg_accel_all);

    }

    private void setTextsForAllStats(int count_records_all, double avg_speed_all, double avg_accel_all) {
    }

    public void setTextsForMyStats(int count_records_user, double min_lat, double max_lat, double min_lng, double max_lng, double avg_speed_user, double avg_accel_user){

        String username = dbm.getUser_logged().getUsers_name();

        Log.e( "TAB3", "Username: " + username + ", Count: " + count_records_user + ", Lat: " + min_lat + " - " + max_lat + ", Lng: " + min_lng + " - " + max_lng + ", Speed: " + avg_speed_user + ", Accel: " + avg_accel_user);

        /*textViewUserName.setText( username );
        textViewUserRecords.setText( Integer.toString( count_records_user ) );
        textViewUserLat.setText( Double.toString( min_lat ) + " to " + Double.toString( max_lat ) );
        textViewUserLng.setText( Double.toString( min_lng ) + " to " + Double.toString( max_lng ) );
        textViewUserSpeed.setText( Double.toString( (avg_speed_user * Constants.CONST_KMH_TO_MS) ) + " km/h" );
        textViewUserSpeed.setText( Double.toString( avg_accel_user ) + " m/s^2" );*/

        //textViewUserName.setText( "username" );
        textViewUserRecords.setText( "73" );
        textViewUserLat.setText( "48.2 to 48.4" );
        textViewUserLng.setText( "16.1 to 16.5" );
        textViewUserSpeed.setText( "32 km/h" );
        textViewUserSpeed.setText( "3.5 m/s^2" );

    }
}
