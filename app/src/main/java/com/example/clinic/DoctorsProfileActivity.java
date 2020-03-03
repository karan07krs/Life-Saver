package com.example.clinic;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorsProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_profile);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation) ;


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new DoctorsTodayFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_today:
                    System.out.println("clicked");
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new DoctorsTodayFragment()).commit();
                    return true;
                case R.id.navigation_search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new searchfragment()).commit();
                    return true;
                case R.id.navigation_patient:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Doctorpatientfragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new doctorprofilefragment()).commit();
                    return true;
            }

            return false;
        }
    };
}
