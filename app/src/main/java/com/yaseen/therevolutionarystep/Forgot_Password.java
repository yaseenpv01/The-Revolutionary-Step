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
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {

    EditText email;
    Button btncontinue;
    ProgressBar progressBar;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        email=findViewById(R.id.emailforgotpasswd);
        btncontinue=findViewById(R.id.continuebutton);
        progressBar=findViewById(R.id.progressbarfgtpasswd);
        progressBar.setVisibility(View.INVISIBLE);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail=email.getText().toString();
                resetPassword(userEmail);

            }
        });
    }

    public void resetPassword(String userEmail)
    {

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {

                            Toast.makeText(Forgot_Password.this,"We sent you an email to reset your password",Toast.LENGTH_LONG).show();
                            btncontinue.setClickable(false);
                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        }
                        else
                        {

                            Toast.makeText(Forgot_Password.this,"Sorry there is a problem,try again later...",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}