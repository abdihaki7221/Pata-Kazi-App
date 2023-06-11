package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class news extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setTitle("Latest News");
    }
}