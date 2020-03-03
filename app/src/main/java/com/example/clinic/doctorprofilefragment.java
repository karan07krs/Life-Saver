package com.example.clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class doctorprofilefragment extends Fragment {

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    FirebaseAuth firebaseAuth;
    View v;
    ProgressBar progressBar;
    FirebaseStorage storage;
    StorageReference storageReference;
    CardView showappointment,signout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView nametext,emailtext,usernametext,dobtext;
    String username,email,name,dob,imageurl;
    CircularImageView editimage,profileimage;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.doctor_profile_fragment,container,false);
        nametext = (TextView) v.findViewById(R.id.fragmentprofilename);
        emailtext=(TextView) v.findViewById(R.id.email_profile);
        usernametext=(TextView)v.findViewById(R.id.username_profile);
        dobtext=(TextView)v.findViewById(R.id.dob_profile);
        showappointment=(CardView)v.findViewById(R.id.cardview_profile_show_appointment);
        signout=(CardView)v.findViewById(R.id.cardview_profile_sign_out);
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        editimage=(CircularImageView)v.findViewById(R.id.iv_camera);
        profileimage=(CircularImageView)v.findViewById(R.id.profilepicture);
        username=firebaseAuth.getCurrentUser().getEmail().substring(0,firebaseAuth.getCurrentUser().getEmail().indexOf('@'));
        email=firebaseAuth.getCurrentUser().getEmail();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/doctor/"+username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("email").getValue(String.class).equals(email)){
                    name=dataSnapshot.child("fname").getValue(String.class)+" "+dataSnapshot.child("lname").getValue(String.class);
                    dob=dataSnapshot.child("dob").getValue(String.class);
                    if(!dataSnapshot.child("imageurl").getValue(String.class).equals(" ")){

                        imageurl=dataSnapshot.child("imageurl").getValue(String.class);
                        Glide.with(v.getContext())
                                .load(imageurl)
                                .into(profileimage);

                    }

                    nametext.setText(name);
                    usernametext.setText(username);
                    dobtext.setText(dob);
                    emailtext.setText(email);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Intent i=new Intent(v.getContext(), loginactivity.class);

                    startActivity(i);

                }
            }
        };
        firebaseAuth.addAuthStateListener(authListener);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
            }
        });

        //uploading image
        editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        return v;
    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

        }
        uploadimage();
    }

    private void uploadimage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            final ProgressDialog progressDialog1 = new ProgressDialog(v.getContext());
            progressDialog1.setTitle("loading..");
            progressDialog1.setCanceledOnTouchOutside(false);
            //progressDialog.show();

            final StorageReference ref = storageReference.child("userprofileimages/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess( UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            progressDialog1.show();
                            adddownloadurl(ref);
                            Toast.makeText(v.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            profileimage.setImageURI(filePath);
                            progressDialog1.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(v.getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void adddownloadurl(StorageReference ref) {
        Task<Uri> urlTask = ref.getDownloadUrl();
        while (!urlTask.isSuccessful());
        final Uri downloadUrl = urlTask.getResult();
        System.out.println(downloadUrl.toString());
        databaseReference.child("imageurl").setValue(downloadUrl.toString());

    }

}
