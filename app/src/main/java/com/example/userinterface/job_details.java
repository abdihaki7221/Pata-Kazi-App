package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class job_details extends AppCompatActivity {


    private TextView job_title_txt,job_description_txt,job_responsibilities;
    private CardView return_card;
    private Button apply;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        getSupportActionBar().hide();


        String jobTitle = getIntent().getStringExtra("jobTitle");

        job_title_txt = findViewById(R.id.job_title_details);
        return_card = findViewById(R.id.card_back);
        apply=findViewById(R.id.apply_btn);

        job_description_txt=findViewById(R.id.description_job_details);
        job_responsibilities = findViewById(R.id.responsibilities_job_1);

        //firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        //back button
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null){
                    // User is not logged in, redirect to login activity
                    Intent intent = new Intent(job_details.this, user_profile_info.class);
                    intent.putExtra("email",user.getEmail());
                    startActivity(intent);

                }else {
                    // User is not logged in, redirect to login activity
                    Intent intent = new Intent(job_details.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        return_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(job_details.this,another_dashboard.class);
                startActivity(intent);
            }
        });

        //apply button


        databaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("users").child("Jobs").child(jobTitle);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String jobDescription = snapshot.child("description").getValue(String.class);
                    String requirement = snapshot.child("requirement").getValue(String.class);

                    job_title_txt.setText(jobTitle);
                    job_description_txt.setText(jobDescription);
                    job_responsibilities.setText(requirement);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }
}