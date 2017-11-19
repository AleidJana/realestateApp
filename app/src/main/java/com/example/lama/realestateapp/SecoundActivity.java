package com.example.lama.realestateapp;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tabs.tab1;
import tabs.tab2;

import static java.security.AccessController.getContext;

public class SecoundActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
     static String[] array;

    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secound_activity);
        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b=this.getIntent().getExtras();
        array=b.getStringArray("info");
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        // Assign created adapter to viewPager
        viewPager.setAdapter(new TabsExamplePagerAdapter(getSupportFragmentManager()));
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // This method setup all required method for TabLayout with Viewpager
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

          for (int i=0;i<50;i++) {
              if (sharedpreferences.contains("city"+i)&&sharedpreferences.contains("state"+i)&&sharedpreferences.contains("street"+i)){
              if (sharedpreferences.getString("city" + i, null).equals(array[2]) && sharedpreferences.getString("state" + i, null).equals(array[3])
                      && sharedpreferences.getString("street" + i, null).equals(array[0])) {
                  break;
              }} else {
                  SharedPreferences.Editor editor = sharedpreferences.edit();
                  if (!sharedpreferences.contains("city" + i)) {
                      editor.putString("city" + i, array[2]);
                      editor.putString("state" + i, array[3]);
                      editor.putString("street" + i, array[0]);
                      editor.apply();
                      break;
                  }//end if

              }//end else
          }//end for
            item.setIcon(android.R.drawable.btn_star_big_on);
            return true;
        }
        return super.onOptionsItemSelected(item);}

}