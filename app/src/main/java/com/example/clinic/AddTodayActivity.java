package com.example.clinic;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddTodayActivity extends AppCompatActivity {

    EditText imagelink,description;
    TextView imagebrowser;
    ImageView backbutton;
    FirebaseDatabase firebasedatabase;
    DatabaseReference databasereference;
    FirebaseAuth firebaseauth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView addinfoontoday;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_today);

        imagelink=(EditText)findViewById(R.id.imagechooser);
        description=(EditText)findViewById(R.id.add_multilineinfo);
        imagebrowser=(TextView)findViewById(R.id.browseimage);
        addinfoontoday=(ImageView)findViewById(R.id.addinfoontoday);
        backbutton=(ImageView)findViewById(R.id.imageView_back);


        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        firebasedatabase=FirebaseDatabase.getInstance();
        databasereference=firebasedatabase.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/today");



        imagebrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  chooseImage();
            }
        });

        addinfoontoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void uploadImage() {
        if (!(imagelink.getText().equals("") && description.getText().equals("") )){
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final StorageReference ref=storageReference.child("todaysimage/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    addDownloadURL(ref);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NotNull Exception e) {

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded"+(int)progress+"%");
                }
            });
        }
    }

    private void addDownloadURL(StorageReference downloadRef) {
        Task<Uri> urlTask= downloadRef.getDownloadUrl();
        while(!urlTask.isSuccessful());
        final Uri downloadUrl=urlTask.getResult();
        DatabaseReference databaseReference1=databasereference.push();
        DatabaseReference databaseReference2=databaseReference1.child("topic");
        databaseReference2.setValue(description.getText().toString().trim());
        databaseReference2=databaseReference1.child("url");
        databaseReference2.setValue(downloadUrl.toString());
    }

    private void chooseImage(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();
            imagelink.setText(filePath.toString());
        }
    }
}
