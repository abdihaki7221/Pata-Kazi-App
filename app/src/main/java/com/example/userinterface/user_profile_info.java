package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class user_profile_info extends AppCompatActivity {

    private TextView about, education,skills,experience,referees,Cover_letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_info);

        //get email from previous page
        Intent intent = getIntent();
        String logged_email = intent.getStringExtra("email");

        setTitle("Application profile");

        about=findViewById(R.id.about);
        education=findViewById(R.id.Education_details);
        skills=findViewById(R.id.skills);
        experience=findViewById(R.id.Experience);
        referees=findViewById(R.id.referees);
        Cover_letter = findViewById(R.id.Cover_letter);

        //on click listeners

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_profile_info.this,about.class);
                intent.putExtra("email",logged_email);
                startActivity(intent);
            }
        });

        //education
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(user_profile_info.this,education.class);
                startActivity(intent);
            }
        });

        //skills
        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(user_profile_info.this,skills.class);
                startActivity(intent);
            }
        });

        //experience
        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(user_profile_info.this,experience.class);
                startActivity(intent);
            }
        });

        //referees
        referees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(user_profile_info.this,referees.class);
                startActivity(intent);
            }
        });
       //cover letter
        Cover_letter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(user_profile_info.this,cover_letter.class);
                startActivity(intent);
            }
        });
    }
}