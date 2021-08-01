package com.yaseen.therevolutionarystep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button signout;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    Button btnstart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signout=findViewById(R.id.signoutbtn);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnstart=findViewById(R.id.btnstart);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,Quiz_Page.class);
                startActivity(intent);

            }
        });
    }
}