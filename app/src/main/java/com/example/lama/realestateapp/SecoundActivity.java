package com.example.lama.realestateapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tabs.tab1;
import tabs.tab2;

public class SecoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secound_activity);
        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // Assign created adapter to viewPager
        viewPager.setAdapter(new TabsExamplePagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // This method setup all required method for TabLayout with Viewpager

        tabLayout.setupWithViewPager(viewPager);
    }

    public static class TabsExamplePagerAdapter extends FragmentPagerAdapter {
        // As we are implementing two tabs
        private static final int NUM_ITEMS = 2;

        public TabsExamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        // For each tab different fragment is returned
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new tab1();
                case 1:
                    return new tab2();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "chart";
                case 1:
                    return "details";
                default:
                    return null;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.star, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.item_one) {
            item.setIcon(android.R.drawable.btn_star_big_on);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}