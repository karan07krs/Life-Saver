package com.example.clinic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class loginactivity extends AppCompatActivity {

    TextView create,signin,doctorlogin,forgetpassword;
    EditText email,password;
    ProgressDialog pd;
    FirebaseUser user;


    private FirebaseAuth firebaseAuth;
    DatabaseReference dbrefd,dbrefu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        create=(TextView) findViewById(R.id.createaccount);
        doctorlogin=(TextView) findViewById(R.id.loginasdoctor);
        email=(EditText)findViewById(R.id.emaillogin);
        password=(EditText)findViewById(R.id.passlogin);
        signin=(TextView)findViewById(R.id.signin);
        forgetpassword=(TextView)findViewById(R.id.login_forgetpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        user=firebaseAuth.getCurrentUser();


        if(firebaseAuth.getCurrentUser() != null){
            Intent i = new Intent(loginactivity.this, userloadingactivity.class);
            startActivity(i);
            finish();

        }
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(loginactivity.this,signupactivity.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinmethod();
            }
        });

        doctorlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginactivity.this,DoctorLoginActivity.class);
                startActivity(i);
            }
        });

    }

    private void signinmethod() {

        String emailstring=email.getText().toString().trim();
        String passwordstring=password.getText().toString().trim();

        if (emailstring.isEmpty()) {

            email.setError("Valid number is required");
            email.requestFocus();
            return;
        }
        else
        if(passwordstring.isEmpty())
        {
            password.setError("Valid number is required");
            password.requestFocus();
            return;
        }
        else{
            pd.setMessage("Logging in");
            pd.show();
            firebaseAuth.signInWithEmailAndPassword(emailstring,passwordstring)
                    .addOnCompleteListener(loginactivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                pd.dismiss();
                                email.setText("");
                                password.setText("");
                                Intent i=new Intent(loginactivity.this,profileactivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                pd.dismiss();
                                email.setText("");
                                password.setText("");
                                Toast.makeText(loginactivity.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();


                            }

                            // ...
                        }
                    });


        }



    }


    public void onBackPressed()
    {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


    }
}

