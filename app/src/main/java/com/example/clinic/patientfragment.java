package com.example.clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class patientfragment extends Fragment {
    TextView noavailable;
    View v;

    ArrayList<String> doctoremail=new ArrayList<>();
    ArrayList<String> doctorname=new ArrayList<>();
    ArrayList<String> datearry=new ArrayList<>();
    ArrayList<String> slotarray=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;

    RecyclerViewAdapterPatientFragment recyclerViewAdapterPatientFragment;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.patient_fragment,container,false);


        noavailable=v.findViewById(R.id.textView6);
        noavailable.setVisibility(View.INVISIBLE);
        recyclerView=v.findViewById(R.id.patient_fragment_recyclerView);
        recyclerView.setHasFixedSize(true);
        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        firebaseDatabase=FirebaseDatabase.getInstance();

        recyclerViewAdapterPatientFragment=new RecyclerViewAdapterPatientFragment(v.getContext(),doctoremail,doctorname,datearry,slotarray,status);
        recyclerView.setAdapter(recyclerViewAdapterPatientFragment);
        String username=firebaseAuth.getCurrentUser().getEmail().substring(0,firebaseAuth.getCurrentUser().getEmail().indexOf('@'));
        databaseReference=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user/"+username+"/Appointment");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, @Nullable String s) {
                 doctoremail.add(dataSnapshot.child("doctoremailid").getValue(String.class).toString());
                 System.out.println(dataSnapshot.child("doctoremailid").getValue(String.class).toString());
                 System.out.println("patient");
                 doctorname.add(dataSnapshot.child("doctorname").getValue(String.class).toString());
                 System.out.println("doctor name");
                 datearry.add(dataSnapshot.child("date").getValue(String.class).toString());
                 slotarray.add(dataSnapshot.child("slot").getValue(String.class).toString());
                 status.add(dataSnapshot.child("appointmentstatus").getValue(String.class).toString());

                 recyclerViewAdapterPatientFragment.notifyDataSetChanged();


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
