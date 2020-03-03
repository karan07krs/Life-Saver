package com.example.clinic;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class requestFragmentFinalAppointment extends AppCompatActivity {

    int countxyz;
    ArrayList<String> dates1=new ArrayList<>();
    ArrayList<String> dates1slots=new ArrayList<>();
    ArrayList<String> dates2slots=new ArrayList<>();
    ArrayList<String> dates3slots=new ArrayList<>();
    ArrayList<Integer> dateradiobuttonid=new ArrayList<>();
    ArrayList<Integer> slot1radiobuttonid=new ArrayList<>();
    ArrayList<Integer> slot2adiobuttonid=new ArrayList<>();
    ArrayList<Integer> slot3adiobuttonid=new ArrayList<>();
    FirebaseDatabase fb;
    FirebaseAuth auth;
    AlertDialog deleteDialog;
    View deleteDialogView;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    DatabaseReference database,databaseReferenceslot1,databaseReferenceslot2,databaseReferenceslot3;
    RadioGroup radioGroup,radioGroupslot1,radioGroupslot2,radioGroupslot3;
    TextView name,email,phoneno,specialization,nodateclicked,submitbutton;
doctor d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_fragment_final_appointment);
        Intent i = getIntent();
         d = (doctor) i.getSerializableExtra("doctorinfo");
        name=(TextView) findViewById(R.id.name_requestfragment_finalappointment);
        email=(TextView) findViewById(R.id.email_requestfragment_finalappointment);
        phoneno=(TextView) findViewById(R.id.phonenumber_requestfragment_finalappointment);
        specialization=(TextView) findViewById(R.id.specialist_requestfragment_finalappointment);
        submitbutton=(TextView) findViewById(R.id.submitrequestfragment);
        submitbutton.setVisibility(View.INVISIBLE);
        name.setText(d.getFname().toUpperCase()+" "+d.getLname().toUpperCase());
        email.setText(d.getEmail().toString());
        phoneno.setText("+91"+ d.getPhoneno().toString());
        specialization.setText(d.getSpecialist().toString());
        nodateclicked=(TextView)findViewById(R.id.nodateselected);


        auth=FirebaseAuth.getInstance();
        firebaseUser=auth.getCurrentUser();
        radioGroupslot1=(RadioGroup)findViewById(R.id.radiogrp_slot1);
        radioGroupslot2=(RadioGroup)findViewById(R.id.radiogrp_slot2);
        radioGroupslot3=(RadioGroup)findViewById(R.id.radiogrp_slot3);
        radioGroupslot1.setVisibility(View.INVISIBLE);
        radioGroupslot2.setVisibility(View.INVISIBLE);
        radioGroupslot3.setVisibility(View.INVISIBLE);


        radioGroup=(RadioGroup)findViewById(R.id.radiogrp);
        fb=FirebaseDatabase.getInstance();
        database=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/asongira9/Appointments");

        LayoutInflater factory = LayoutInflater.from(this);
          deleteDialogView = factory.inflate(R.layout.customdilogboxrequestfragment, null);
          deleteDialog = new AlertDialog.Builder(this).create();
        progressBar=deleteDialogView.findViewById(R.id.progressBar_dilogbox);
        progressBar.setVisibility(View.INVISIBLE);
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.cance_dilogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                deleteDialog.dismiss();


            }
        });
        deleteDialog.setCanceledOnTouchOutside(false);
        deleteDialogView.findViewById(R.id.confirm_dilogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                int id=radioGroup.getCheckedRadioButtonId();
                if(id==dateradiobuttonid.get(0)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot1.getCheckedRadioButtonId())).getText().toString();
                    System.out.println(dateselectedtext+" "+slotselectedtext);
                    updatedatabase(dateselectedtext.trim(),slotselectedtext.trim());

                }else if(id==dateradiobuttonid.get(1)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot2.getCheckedRadioButtonId())).getText().toString();
                    System.out.println(dateselectedtext+" "+slotselectedtext);
                    updatedatabase(dateselectedtext.trim(),slotselectedtext.trim());

                }else if(id==dateradiobuttonid.get(2)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot3.getCheckedRadioButtonId())).getText().toString();
                    System.out.println(dateselectedtext+" "+slotselectedtext);
                    updatedatabase(dateselectedtext.trim(),slotselectedtext.trim());

                }
            }
        });


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView dilogdoctorname=deleteDialogView.findViewById(R.id.dilogbox_doctorname);
                dilogdoctorname.setText(d.getFname()+" "+d.getLname());
                TextView dilogdoctorfee=deleteDialogView.findViewById(R.id.dilogbox_fee);
                dilogdoctorfee.setText("200 Rs");
                TextView dilogpatientname=deleteDialogView.findViewById(R.id.dilogbox_patientname);
                TextView dilogdate=deleteDialogView.findViewById(R.id.dilogbox_date);
                TextView dilogslot=deleteDialogView.findViewById(R.id.dilogbox_slot);

                int id=radioGroup.getCheckedRadioButtonId();
                if(id==dateradiobuttonid.get(0)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot1.getCheckedRadioButtonId())).getText().toString();
                    dilogdate.setText(dateselectedtext);
                    dilogslot.setText(slotselectedtext);

                }else if(id==dateradiobuttonid.get(1)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot2.getCheckedRadioButtonId())).getText().toString();
                    dilogdate.setText(dateselectedtext);
                    dilogslot.setText(slotselectedtext);
                }else if(id==dateradiobuttonid.get(2)){
                    String dateselectedtext=((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    String slotselectedtext=((RadioButton)findViewById(radioGroupslot3.getCheckedRadioButtonId())).getText().toString();
                    dilogdate.setText(dateselectedtext);
                    dilogslot.setText(slotselectedtext);

                }


                deleteDialog.show();
            }
        });

        radioGroupslot1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

             submitbutton.setVisibility(View.VISIBLE);
            }
        });

        radioGroupslot2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitbutton.setVisibility(View.VISIBLE);
            }
        });

        radioGroupslot3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                submitbutton.setVisibility(View.VISIBLE);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if(i==dateradiobuttonid.get(0)){
                    if(dates1slots.size()!=0) {
                        nodateclicked.setVisibility(View.INVISIBLE);
                        radioGroupslot1.setVisibility(View.VISIBLE);
                        radioGroupslot2.setVisibility(View.INVISIBLE);
                        radioGroupslot3.setVisibility(View.INVISIBLE);
                            radioGroupslot2.clearCheck();
                            radioGroupslot3.clearCheck();

                    }else
                        {
                        nodateclicked.setText("NO SLOT AVAILABLE!!!");
                        nodateclicked.setVisibility(View.VISIBLE);
                            radioGroupslot1.setVisibility(View.INVISIBLE);
                            radioGroupslot2.setVisibility(View.INVISIBLE);
                            radioGroupslot3.setVisibility(View.INVISIBLE);
                            radioGroupslot2.clearCheck();
                            radioGroupslot3.clearCheck();

                        }

                }else if (i==dateradiobuttonid.get(1)) {
                    if(dates2slots.size()!=0) {
                        nodateclicked.setVisibility(View.INVISIBLE);
                        radioGroupslot1.setVisibility(View.INVISIBLE);
                        radioGroupslot2.setVisibility(View.VISIBLE);
                        radioGroupslot3.setVisibility(View.INVISIBLE);
                        radioGroupslot1.clearCheck();
                        radioGroupslot3.clearCheck();

                    }else
                    {
                        nodateclicked.setText("NO SLOT AVAILABLE!!!");
                        nodateclicked.setVisibility(View.VISIBLE);

                        radioGroupslot1.setVisibility(View.INVISIBLE);
                        radioGroupslot2.setVisibility(View.INVISIBLE);
                        radioGroupslot3.setVisibility(View.INVISIBLE);
                        radioGroupslot1.clearCheck();
                        radioGroupslot3.clearCheck();
                    }
                }
                else if (i==dateradiobuttonid.get(2)){
                    if(dates3slots.size()!=0) {
                        nodateclicked.setVisibility(View.INVISIBLE);
                    radioGroupslot1.setVisibility(View.INVISIBLE);
                    radioGroupslot2.setVisibility(View.INVISIBLE);
                    radioGroupslot3.setVisibility(View.VISIBLE);
                        radioGroupslot1.clearCheck();
                        radioGroupslot2.clearCheck();
                    }else
                    {
                        nodateclicked.setText("NO SLOT AVAILABLE!!!");
                        nodateclicked.setVisibility(View.VISIBLE);

                        radioGroupslot1.setVisibility(View.INVISIBLE);
                        radioGroupslot2.setVisibility(View.INVISIBLE);
                        radioGroupslot3.setVisibility(View.INVISIBLE);
                        radioGroupslot1.clearCheck();
                        radioGroupslot3.clearCheck();
                    }
                }


            }
        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                System.out.println(child.getKey().toString());
                 if (dates1.size()<=3) {
                     System.out.println(child.getKey().toString()+"added");
                     dates1.add(child.getKey().toString());
                 }
                }

                addradiobuttons(dates1.size());
                slotfinder();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void updatedatabase(String date, String slot) {
        final String date1=date;
        final String useremail=firebaseUser.getEmail();
        final String finalslot="slot_"+slot.replace(" to ","_");
        System.out.println(finalslot);
        final DatabaseReference databaseReference=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/asongira9/Appointments/"+date+"/"+finalslot);
        final DatabaseReference databaseReferenceuser=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user/"+useremail.substring(0,useremail.indexOf('@')));

        databaseReference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                Integer count = mutableData.child("count").getValue(Integer.class);
                DatabaseReference child1=databaseReference.child("count");
                child1.setValue(count+1);

                child1=databaseReference.child(useremail.substring(0,useremail.indexOf('@')));
                child1.setValue("requested");

                DatabaseReference child2=databaseReferenceuser.child("Appointment").push();
                DatabaseReference child3=child2.child("doctoremailid");
                child3.setValue(d.getEmail().substring(0,d.getEmail().indexOf('@')));

                child3=child2.child("date");
                child3.setValue(date1);

                child3=child2.child("doctorname");
                System.out.println("doctor name added"+d.getFname().toString());
                child3.setValue(d.getFname().toString()+" "+d.getLname().toString());

                child3=child2.child("slot");
                child3.setValue(finalslot);

                child3=child2.child("appointmentstatus");
                child3.setValue("requested");
                System.out.println("Transaction completed");
                deleteDialog.dismiss();
                progressBar.setVisibility(View.INVISIBLE);


                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed, DataSnapshot dataSnapshot) {

            }
        });

    }

    private void setslotsinradiogroups() {


    }

    private void slotfinder() {
        System.out.println("in slot finder");
        databaseReferenceslot1=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/asongira9/Appointments/"+dates1.get(0).trim());
        databaseReferenceslot1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           for (DataSnapshot child1: dataSnapshot.getChildren()){
                   if(child1.child("count").getValue(Integer.class)!=5){
                       String oldslotvalue=child1.getKey().toString();
                       String oldslotvalue1=oldslotvalue.substring(5,oldslotvalue.length());
                       String newslotvalue=oldslotvalue1.replace("_"," to ");
                   System.out.println(newslotvalue);

                   dates1slots.add(newslotvalue);}



           }

           setslotsinradiogroup1(dates1slots.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReferenceslot2=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/asongira9/Appointments/"+dates1.get(1).trim());
        databaseReferenceslot2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1: dataSnapshot.getChildren()){

                    if(child1.child("count").getValue(Integer.class)!=5){
                        String oldslotvalue=child1.getKey().toString();
                        String oldslotvalue1=oldslotvalue.substring(5,oldslotvalue.length());
                        String newslotvalue=oldslotvalue1.replace("_"," to ");
                        System.out.println(newslotvalue);

                        dates2slots.add(newslotvalue);}


                }
                setslotsinradiogroup2(dates2slots.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReferenceslot3=fb.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctors/asongira9/Appointments/"+dates1.get(2).trim());
        databaseReferenceslot3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child1: dataSnapshot.getChildren()){
                    if(child1.child("count").getValue(Integer.class)!=5){
                        String oldslotvalue=child1.getKey().toString();
                        String oldslotvalue1=oldslotvalue.substring(5,oldslotvalue.length());
                        String newslotvalue=oldslotvalue1.replace("_"," to ");
                        System.out.println(newslotvalue);
                        dates3slots.add(newslotvalue);

                    }

                }
                setslotsinradiogroup3(dates3slots.size());
                System.out.println("final");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setslotsinradiogroup1(int number) {
System.out.println("in slotradio1");
        for (int i=0;i<number;i++)
        {
            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(View.generateViewId());
            slot1radiobuttonid.add(radioButton.getId());
            radioButton.setText(dates1slots.get(i));
            radioButton.setTextColor(Color.BLACK);
            radioButton.setHighlightColor(Color.BLACK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                radioButton.setOutlineSpotShadowColor(Color.BLACK);
            }


            radioGroupslot1.addView(radioButton);
        }

    }
    private void setslotsinradiogroup2(int number) {
        System.out.println("in slotradio1");
        for (int i=0;i<number;i++)
        {
            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(View.generateViewId());
            radioButton.setText(dates2slots.get(i));
            slot2adiobuttonid.add(radioButton.getId());
            radioButton.setTextColor(Color.BLACK);
            radioButton.setHighlightColor(Color.BLACK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                radioButton.setOutlineSpotShadowColor(Color.BLACK);
            }
            radioGroupslot2.addView(radioButton);
            System.out.println("in slotradio1 added radio button");
        }

    }
    private void setslotsinradiogroup3(int number) {
        System.out.println("in slotradio1");
        for (int i=0;i<number;i++)
        {
            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(View.generateViewId());
            radioButton.setText(dates3slots.get(i));
            slot3adiobuttonid.add(radioButton.getId());
            radioButton.setTextColor(Color.BLACK);
            radioButton.setHighlightColor(Color.BLACK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                radioButton.setOutlineSpotShadowColor(Color.BLACK);
            }
            radioGroupslot3.addView(radioButton);
            System.out.println("in slotradio1 added radio button");
        }

    }

    private void addradiobuttons(int number) {


        for (int i=0;i<number;i++)
        {
            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(View.generateViewId());
            dateradiobuttonid.add(radioButton.getId());
            radioButton.setText(dates1.get(i));
            radioButton.setTextColor(Color.BLACK);
            radioButton.setHighlightColor(Color.BLACK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                radioButton.setOutlineSpotShadowColor(Color.BLACK);
            }
            radioGroup.addView(radioButton);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        System.out.println("back pressed");
        this.finish();
        System.out.println("back pressed");
    }
}
