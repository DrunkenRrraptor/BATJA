package com.example.robs.batja_v1;

import android.nfc.Tag;
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

public class Stats6 extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e( "ST6", "onCreate: Starting." );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats6 );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById( R.id.container );
        //mViewPager.setAdapter( mSectionsPagerAdapter );

        setupViewPager( mViewPager );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );

        //mViewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );
        //tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener( mViewPager ) );

        tabLayout.setupWithViewPager( mViewPager );

    }

    private void setupViewPager(ViewPager viewPager){

        SectionsPagerAdapter adapter = new SectionsPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( new Stats6_frag1(), "My Stats" );
        adapter.addFragment( new Stats6_frag2(), "All Stats" );
        viewPager.setAdapter( adapter );

    }




//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt( ARG_SECTION_NUMBER, sectionNumber );
//            fragment.setArguments( args );
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate( R.layout.fragment_stats6, container, false );
//            TextView textView = (TextView) rootView.findViewById( R.id.section_label );
//            textView.setText( getString( R.string.section_format, getArguments().getInt( ARG_SECTION_NUMBER ) ) );
//            return rootView;
//        }
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>(  );
        private final List<String> fragmentTitleList = new ArrayList<>(  );

        public void addFragment(Fragment fragment, String title){

            fragmentList.add( fragment );
            fragmentTitleList.add( title );

        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super( fm );
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance( position + 1 );
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            // return 2;
            return fragmentList.size();
        }
    }
}
