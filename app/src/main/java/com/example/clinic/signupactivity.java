package com.example.clinic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signupactivity extends AppCompatActivity {


    EditText firstName;
    EditText lastName;
    EditText dob;
    EditText emailtextview;
    EditText password;
    EditText cpassword;
    TextView signup;
    ImageView back;

    FirebaseDatabase database;
    DatabaseReference databaseUserRegister;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        System.out.println();
        database = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.confirm_password);
        emailtextview = (EditText) findViewById(R.id.email);
        dob = (EditText) findViewById(R.id.dob);
        signup = (TextView) findViewById(R.id.SignUp);
        back=(ImageView) findViewById(R.id.imageView_back);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signupactivity.this,loginactivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    private void saveUser() {

        String fname = firstName.getText().toString().trim();
        String lname = lastName.getText().toString().trim();
        String birthdate = dob.getText().toString().trim();
        final String e_mail = emailtextview.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        String cpass = cpassword.getText().toString().trim();

        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) && !TextUtils.isEmpty(e_mail) && !TextUtils.isEmpty(birthdate) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass) && pass.equals(cpass) && pass.length() >= 6) {

            progressBar.setVisibility(View.VISIBLE);
            doAuthentication(e_mail,pass);



        }
        else if (!pass.equals(cpass))
            Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_LONG).show();
        else if (pass.length() < 6)
            Toast.makeText(this, "Password should be of atleast 6 characters", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(fname))
            Toast.makeText(this, "Enter the First Name", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(lname))
            Toast.makeText(this, "Enter the Last Name", Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(e_mail))
            Toast.makeText(this, "Enter an Email ID", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Can not register the User", Toast.LENGTH_LONG).show();


    }

    private void doAuthentication(String email,String pass) {
        System.out.println("reached do");
        final String email1=email;
        final String pass1=pass;
        final String fname = firstName.getText().toString().trim();
        final String lname = lastName.getText().toString().trim();
        final String birthdate = dob.getText().toString().trim();

        System.out.println("reached do1");
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    System.out.println("done");
                    Intent i=new Intent(signupactivity.this,email_verification.class);

                    i.putExtra("firstname",fname);
                    i.putExtra("lastname",lname);
                    i.putExtra("email",email1);
                    i.putExtra("password",pass1);
                    i.putExtra("dob",birthdate);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(signupactivity.this,"Authentication Failed!,check your email and password",Toast.LENGTH_LONG);
                    firstName.setText("");
                    lastName.setText("");
                    dob.setText("");
                    password.setText("");
                    emailtextview.setText("");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(signupactivity.this,loginactivity.class);
        startActivity(i);
        finish();


    }
}
