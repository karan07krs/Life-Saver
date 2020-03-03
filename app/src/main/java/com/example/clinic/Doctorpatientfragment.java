package com.example.clinic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Doctorpatientfragment extends Fragment  {
    @Nullable
    View v;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView textView;
    RecyclerView recyclerView;
    ArrayList<String> patientemail=new ArrayList<>();
    ArrayList<String> patientname=new ArrayList<>();
    ArrayList<String> datearr=new ArrayList<>();
    ArrayList<String> slotarr=new ArrayList<>();
    RecyclerViewAdapterDoctorPatientFragment recyclerViewAdapterDoctorPatientFragment;
String pname,pemail;
String username;
ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.doctor_patient_fragment,container,false);
         firebaseAuth=FirebaseAuth.getInstance();
         firebaseDatabase=FirebaseDatabase.getInstance();
         username=firebaseAuth.getCurrentUser().getEmail().substring(0,firebaseAuth.getCurrentUser().getEmail().indexOf('@'));
         progressBar=(ProgressBar)v.findViewById(R.id.progressBar5);
         progressBar.setVisibility(View.VISIBLE);
         textView=(TextView)v.findViewById(R.id.no_request_Avaialabel);
         recyclerView=(RecyclerView)v.findViewById(R.id.doctor_patient_fragment_recyclerView);
         recyclerView.setHasFixedSize(true);
         recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
         recyclerViewAdapterDoctorPatientFragment=new RecyclerViewAdapterDoctorPatientFragment(new RecyclerViewAdapterDoctorPatientFragment.DetailsAdapterListener() {
             @Override
             public void onClick(View v, final int position) {

                 DatabaseReference databaseReference2=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/"+username+"/Appointments/"+datearr.get(position)+"/"+slotarr.get(position)+"/"+patientemail.get(position).substring(0,patientemail.get(position).indexOf('@')));
                 databaseReference2.setValue("confirm");
                 DatabaseReference databaseReference3=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user/"+patientemail.get(position).substring(0,patientemail.get(position).indexOf('@'))+"/Appointment");
                 databaseReference3.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if(dataSnapshot.child("doctoremailid").getValue(String.class).equals(username)&&dataSnapshot.child("slot").getValue(String.class).equals(slotarr.get(position))&&dataSnapshot.child("date").getValue(String.class).equals(datearr.get(position))){

           DatabaseReference xyz =dataSnapshot.child("appointmentstatus").getRef();
           xyz.setValue("confirm");
           patientemail.remove(position);
           patientname.remove(position);
           slotarr.remove(position);
           datearr.remove(position);
           recyclerViewAdapterDoctorPatientFragment.notifyDataSetChanged();

        }
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
             }
         },v.getContext(),patientemail,patientname,datearr,slotarr);

         recyclerView.setAdapter(recyclerViewAdapterDoctorPatientFragment);
         textView.setVisibility(View.INVISIBLE);
        databaseReference=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/"+username+"/Appointments");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                System.out.println("inside ");

                    for(DataSnapshot child2: dataSnapshot.getChildren()){
                            System.out.println("inside child 2");
                            for (DataSnapshot child3: child2.getChildren()){
                                System.out.println("inside child 3");
                                if(child3.getKey().toString().equals("count")){
                                    continue;
                                }
                                else{
                                    if (child3.getValue().toString().equals("requested")){
                                        System.out.println(child3.getKey());
                                        insertintolist(dataSnapshot.getKey(),child2.getKey(),child3.getKey());
                                    }
                                }
                            }

                    }



                recyclerViewAdapterDoctorPatientFragment.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;

    }

    public void insertintolist(final String date, final String slot, String username)
    {
        progressBar.setVisibility(View.INVISIBLE);
        DatabaseReference databaseReference2 = firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user/"+username);

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pemail = dataSnapshot.child("email").getValue(String.class).toString();
                pname = dataSnapshot.child("fname").getValue(String.class).toString();
                adddetail(pemail,pname,date,slot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void adddetail(String pemail,String pname,String date,String slot)
    {
        patientemail.add(pemail);
        patientname.add(pname);
        slotarr.add(slot);
        datearr.add(date);
        recyclerViewAdapterDoctorPatientFragment.notifyDataSetChanged();


    }

}
