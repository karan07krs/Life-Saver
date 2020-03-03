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

public class DoctorLoginActivity extends AppCompatActivity {

    TextView signin;
    EditText email,password;
    ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        email=(EditText)findViewById(R.id.emaillogin);
        password=(EditText)findViewById(R.id.passlogin);
        signin=(TextView)findViewById(R.id.signin);
        firebaseAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity


            //and open profile activity
            Intent i=new Intent(DoctorLoginActivity.this,DoctorsProfileActivity.class);
            startActivity(i);
            finish();
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinmethod();
            }
        });

    }

    private void signinmethod() {

        String emailstring=email.getText().toString().trim();
        String passwordstring=password.getText().toString().trim();

        if (emailstring.isEmpty()) {

            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        else
        if(passwordstring.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        else{
            pd.setMessage("Logging in");
            pd.show();
            firebaseAuth.signInWithEmailAndPassword(emailstring,passwordstring)
                    .addOnCompleteListener(DoctorLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                pd.dismiss();
                                email.setText("");
                                password.setText("");
                                Intent i = new Intent(DoctorLoginActivity.this, DoctorsProfileActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                pd.dismiss();
                                email.setText("");
                                password.setText("");
                                Toast.makeText(DoctorLoginActivity.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();


                            }
                        }
                    });


        }



    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

