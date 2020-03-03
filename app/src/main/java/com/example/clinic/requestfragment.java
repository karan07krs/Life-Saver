package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class requestfragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View v;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerViewAdapterRequestFragment recyclerViewAdapterRequestFragment;
    ArrayList<doctor> arrayList=new ArrayList<>();

    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.request_fragment,container,false);
        progressBar=v.findViewById(R.id.progressBar_request);
        progressBar.setVisibility(View.VISIBLE);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors");
        recyclerViewAdapterRequestFragment=new RecyclerViewAdapterRequestFragment(v.getContext(),arrayList);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_request);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(recyclerViewAdapterRequestFragment);
        recyclerViewAdapterRequestFragment.setOnItemClickListener(new RecyclerViewAdapterRequestFragment.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent i = new Intent(v.getContext(), requestFragmentFinalAppointment.class);
                i.putExtra("doctorinfo", arrayList.get(position));
                startActivity(i);

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot postSnapshot, @Nullable String s) {
                progressBar.setVisibility(View.INVISIBLE);
                doctor d=new doctor(postSnapshot.child("First Name").getValue(String.class).toString(),postSnapshot.child("Last Name").getValue(String.class).toString(),postSnapshot.child("DOB").getValue(String.class).toString(),postSnapshot.child("Email").getValue(String.class).toString(),postSnapshot.child("Password").getValue(String.class).toString(),postSnapshot.child("phone number").getValue(String.class).toString(),postSnapshot.child("specialists").getValue(String.class).toString());
                arrayList.add(d);
                recyclerViewAdapterRequestFragment.notifyDataSetChanged();

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
        return v;
    }




    }



