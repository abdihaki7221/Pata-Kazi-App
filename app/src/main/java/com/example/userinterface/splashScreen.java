package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class splashScreen extends AppCompatActivity {
    private ProgressBar progressBar;


    int counter =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();


        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                progressBar.setProgress(counter);

                if(counter == 100){
                    Intent intent = new Intent(splashScreen.this,another_dashboard.class);
                    startActivity(intent);
                    t.cancel();
                }

            }
        };

        t.schedule(tt,0,100);


// Update progress every second

    }
}