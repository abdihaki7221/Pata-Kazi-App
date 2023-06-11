package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class admin_dashboard extends AppCompatActivity {

    CardView add_jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        setTitle("Admin Dashboard");




        //initializing add jobs card view

        add_jobs=findViewById(R.id.add_jobs);

        add_jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(admin_dashboard.this, post_jobs.class);
                startActivity(intent);
            }
        });
    }

    //inflate logout menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    //logout the user to loginActivity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                // Create a new intent to launch the Login activity
                Intent intent = new Intent(this, MainActivity.class);

                // Clear the activity stack, so that the user can't go back
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                // Start the Login activity and finish the Dashboard activity
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}