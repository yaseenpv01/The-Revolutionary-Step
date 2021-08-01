package com.yaseen.therevolutionarystep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Score_Page extends AppCompatActivity {

    TextView scoreCorrect,scoreWrong;
    Button playAgain,exit;

    String userCorrect;
    String userWrong;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=database.getReference("scores");

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__page);

        scoreCorrect=findViewById(R.id.scorecorrectanswer);
        scoreWrong=findViewById(R.id.scorewronganswer);
        playAgain=findViewById(R.id.playagainbtn);
        exit=findViewById(R.id.exitbtn);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userUID=user.getUid();

                userCorrect=snapshot.child(userUID).child("correct").getValue().toString();
                userWrong=snapshot.child(userUID).child("wrong").getValue().toString();
                scoreCorrect.setText(userCorrect);
                scoreWrong.setText(userWrong);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Score_Page.this,HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }
}