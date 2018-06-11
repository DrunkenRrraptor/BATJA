package com.example.robs.batja_v1;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Stats4 extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /*TextView textViewUserName;
    TextView textViewUserRecords;
    TextView textViewUserLat;
    TextView textViewUserLng;
    TextView textViewUserSpeed;
    TextView textViewUserAccel;*/
    DatabaseManagement dbm;
    List<GPS_Class> gps_list = new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats4 );

        dbm = DatabaseManagement.getInstance( this );
        gps_list = dbm.fetch_gps();

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById( R.id.container );
        mViewPager.setAdapter( mSectionsPagerAdapter );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );

        mViewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener( mViewPager ) );

        /*FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );*/

        Log.e("STATS", "MAIN");

        /*textViewUserName = (TextView) findViewById( R.id.textViewUsername3 );
        textViewUserRecords = (TextView) findViewById( R.id.textViewUserRecord );
        textViewUserLat = (TextView) findViewById( R.id.textViewUserLat );
        textViewUserLng = (TextView) findViewById( R.id.textViewUserLng );
        textViewUserSpeed = (TextView) findViewById( R.id.textViewUserSpeed );
        textViewUserAccel = (TextView) findViewById( R.id.textViewUserAccel );*/

        setTextsForStats();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_stats4, menu );
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt( ARG_SECTION_NUMBER, sectionNumber );
            fragment.setArguments( args );
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            switch (getArguments().getInt( ARG_SECTION_NUMBER )){
                case 1:
                    rootView = inflater.inflate( R.layout.fragment_stats4_tab3, container, false );
                    break;
                case 2:
                    rootView = inflater.inflate( R.layout.fragment_stats4_tab2, container, false );
                    break;

            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super( fm );
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance( position + 1 );
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "My Stats";
                case 1:
                    return "All Stats";
            }
            return null;
        }
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

        count_records_all = gps_list.size();

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

        }

        setTextsForMyStats(count_records_user, min_lat, max_lat, min_lng, max_lng, avg_speed_user, avg_accel_user);
        setTextsForAllStats(count_records_all, avg_speed_all, avg_accel_all);

    }


    public void setTextsForMyStats(int count_records_user, double min_lat, double max_lat, double min_lng, double max_lng, double avg_speed_user, double avg_accel_user){

        //String username = dbm.getUser_logged().getUsers_name();

        //Log.e( "TAB", "Username: " + username + ", Count: " + count_records_user + ", Lat: " + min_lat + " - " + max_lat + ", Lng: " + min_lng + " - " + max_lng + ", Speed: " + avg_speed_user + ", Accel: " + avg_accel_user);

        /*textViewUserName.setText( username );
        textViewUserRecords.setText( Integer.toString( count_records_user ) );
        textViewUserLat.setText( Double.toString( min_lat ) + " to " + Double.toString( max_lat ) );
        textViewUserLng.setText( Double.toString( min_lng ) + " to " + Double.toString( max_lng ) );
        textViewUserSpeed.setText( Double.toString( (avg_speed_user * Constants.CONST_KMH_TO_MS) ) + " km/h" );
        textViewUserSpeed.setText( Double.toString( avg_accel_user ) + " m/s^2" );*/

        /*textViewUserName.setText( "username" );
        textViewUserRecords.setText( "73" );
        textViewUserLat.setText( "48.2 to 48.4" );
        textViewUserLng.setText( "16.1 to 16.5" );
        textViewUserSpeed.setText( "32 km/h" );
        textViewUserSpeed.setText( "3.5 m/s^2" );*/

    }

    public void setTextsForAllStats(int count_records_all, double avg_speed_all, double avg_accel_all){



    }


}
