package com.example.clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class email_verification extends AppCompatActivity {

    RegisterUser registerUser;
    TextView sendemail,resendemail,verified,verifyemail;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressDialog progressDialog,progressDialog1,progressDialog2;
    ImageView back;
    FirebaseDatabase database;
    DatabaseReference databaseUserRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        firebaseAuth=FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog1=new ProgressDialog(this);
        progressDialog2=new ProgressDialog(this);
        sendemail=(TextView)findViewById(R.id.send_email_verification);
        resendemail=(TextView)findViewById(R.id.resend_email_verification);
        verifyemail=(TextView)findViewById(R.id.verify_email_verification);
        back=(ImageView)findViewById(R.id.imageView_back_email_verification);
        resendemail.setEnabled(false);
        verifyemail.setEnabled(false);
        verified=(TextView)findViewById(R.id.verified);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sending email..");
                progressDialog.show();

                verify();

            }
        });

        verifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.isEmailVerified()){
                    getverification();
                }
                else {
                    user.reload();
                }


            }
        });

        resendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

    }

    private void verify() {


        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(Task task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            progressDialog.setCanceledOnTouchOutside(false);
                            sendemail.setVisibility(View.INVISIBLE);
                            sendemail.setEnabled(false);

                            verified.setVisibility(View.VISIBLE);
                            verifyemail.setEnabled(true);
                            verifyemail.setVisibility(View.VISIBLE);

                            resendemail.setEnabled(true);
                            Toast.makeText(email_verification.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();


                        } else {

                            Toast.makeText(email_verification.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getverification(){

        if(user.isEmailVerified()){
            progressDialog1.setMessage("verifying");
            progressDialog1.show();
            progressDialog1.setCanceledOnTouchOutside(false);

            String fname=getIntent().getStringExtra("firstname");
            String lname=getIntent().getStringExtra("lastname");
           final String e_mail=getIntent().getStringExtra("email");
           final String pass=getIntent().getStringExtra("password");
            String birthdate=getIntent().getStringExtra("dob");

            int x=e_mail.indexOf('@');
            String username=e_mail.substring(0,x);

            databaseUserRegister=database.getReferenceFromUrl("https://clinic1-94045.firebaseio.com/user");
            registerUser=new RegisterUser(fname,lname,birthdate,e_mail,pass);

            databaseUserRegister.child(username).setValue(registerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
               if(task.isSuccessful()){
                   Toast.makeText(email_verification.this,"user registered successfull",Toast.LENGTH_LONG).show();
                   login(e_mail,pass);
               }else {
                   Toast.makeText(email_verification.this,"user registered unsuccessfull",Toast.LENGTH_LONG).show();

               }

                }
            });


        }


    }
    public void login(String email,String pass){
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(email_verification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                                Toast.makeText(email_verification.this,"sorry login failed", Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(email_verification.this, profileactivity.class);
                            progressDialog1.dismiss();
                            startActivity(intent);
                            finish();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {


delete();

    }

    private void delete() {
        progressDialog2.setMessage("Cancelling registration...");
        progressDialog2.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.signOut();
                                progressDialog2.dismiss();
                                Intent i=new Intent(email_verification.this,loginactivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(email_verification.this, "Failed to cancel your registration!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}


