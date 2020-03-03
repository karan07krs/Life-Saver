package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userloadingactivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    DatabaseReference dbrefd,dbrefu;
    FirebaseUser user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloadingactivity);
        firebaseAuth=FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        dbrefd= FirebaseDatabase.getInstance().getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors");
        dbrefu= FirebaseDatabase.getInstance().getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user");
    progressBar=findViewById(R.id.progressBar_login_useridentificetion);
    progressBar.setVisibility(View.VISIBLE);
        String emailuser=user.getEmail().toString();
        final String keyfind=emailuser.substring(0,emailuser.indexOf("@"));

        dbrefd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(keyfind)) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(userloadingactivity.this, DoctorsProfileActivity.class);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbrefu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(keyfind)){
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent i = new Intent(userloadingactivity.this, profileactivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
