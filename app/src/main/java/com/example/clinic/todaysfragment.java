package com.example.clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class todaysfragment extends Fragment {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    RecyclerView.Adapter adapter ;
    List<String> imageurlinfo = new ArrayList<>();
    List<String> title = new ArrayList<>();
    View v;
    ProgressBar progressBar;
    private TextView date;
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.today_fragment,container,false);

        String datestring = new SimpleDateFormat("MMMM-dd").format(new Date());
        String month=datestring.replace('-',' ');
        date=(TextView)v.findViewById(R.id.datetext);
        date.setText(month.toUpperCase());

        progressBar=v.findViewById(R.id.progressBar_todays);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/today");
        adapter = new RecyclerViewAdapter(v.getContext(), imageurlinfo,title);

        recyclerView.setAdapter(adapter);
databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                   progressBar.setVisibility(View.INVISIBLE);
                    imageurlinfo.add(postSnapshot.child("url").getValue(String.class).toString());
                    System.out.println(postSnapshot.child("url").getValue(String.class).toString());
                    title.add(postSnapshot.child("topic").getValue(String.class).toString());
                    adapter.notifyDataSetChanged();

                }



                // Hiding the progress dialog.

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.

            }
        });
        return v;
    }
}
