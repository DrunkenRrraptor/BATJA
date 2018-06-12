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

    //
    //
    // activity for STATISTICS - tabbed activity to load fragment1 and fragment 2
    //
    //

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

        // make this activity show fragment1 and fragment2

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stats6 );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        mSectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager() );

        mViewPager = (ViewPager) findViewById( R.id.container );

        setupViewPager( mViewPager );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );

        tabLayout.setupWithViewPager( mViewPager );

    }

    private void setupViewPager(ViewPager viewPager){

        // specify fragments
        SectionsPagerAdapter adapter = new SectionsPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( new Stats6_frag1(), "My Stats" );
        adapter.addFragment( new Stats6_frag2(), "All Stats" );
        viewPager.setAdapter( adapter );

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // list of fragments and its titles
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

            // get current position
            return fragmentList.get(position);

        }

        @Override
        public int getCount() {

            // get number of fragments
            return fragmentList.size();

        }
    }
}
