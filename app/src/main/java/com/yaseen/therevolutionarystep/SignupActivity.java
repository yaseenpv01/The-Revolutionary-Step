package com.yaseen.therevolutionarystep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText email,name,password,cnfmpassword,phoneno;
    Button btnsignup;
    ProgressBar progressBar;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email=findViewById(R.id.signupemail);
        name=findViewById(R.id.signupname);
        password=findViewById(R.id.signuppassword);
        cnfmpassword=findViewById(R.id.signupcnfmpassword);
        phoneno=findViewById(R.id.signupmobileno);
        btnsignup=findViewById(R.id.signupbutton);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnsignup.setClickable(false);
                String userEmail=email.getText().toString();
                String userPassword=password.getText().toString();
                signUpFirebase(userEmail,userPassword);
            }
        });

    }

    public void signUpFirebase(String userEmail,String userPassword)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SignupActivity.this,"Your Account is created",Toast.LENGTH_LONG).show();
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this,"There is a problem in creating the account",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}