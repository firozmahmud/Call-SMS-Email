package com.example.firoz.phone_call_sms_email.activity;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.firoz.phone_call_sms_email.R;
import com.example.firoz.phone_call_sms_email.fragment.Call_Fragment;
import com.example.firoz.phone_call_sms_email.fragment.Email_Fragment;
import com.example.firoz.phone_call_sms_email.fragment.SMS_Fragment;

public class MainActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private long newTime, oldTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return new Call_Fragment();
                case 1:
                    return new SMS_Fragment();
                case 2:
                    return new Email_Fragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


    @Override
    public void onBackPressed() {

        oldTime = newTime;
        newTime = System.currentTimeMillis();

        if (newTime - oldTime < 250) {
            finish();
        } else {
            Toast.makeText(this, "press aggain to exit", Toast.LENGTH_SHORT).show();
        }

    }
}
