package com.example.clinic;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profileactivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation) ;


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new todaysfragment()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_today:
                    System.out.println("clicked");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new todaysfragment()).commit();
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new searchfragment()).commit();
                    return true;
                case R.id.navigation_request:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new requestfragment()).commit();
                    return true;
                case R.id.navigation_patient:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new patientfragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new profilefragment()).commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
