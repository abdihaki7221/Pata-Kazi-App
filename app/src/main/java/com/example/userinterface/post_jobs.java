package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userinterface.ui.DatabaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class post_jobs extends AppCompatActivity {

    EditText job_title, job_req,job_description,no_vacancies,dateEditText,deadlineDate;
    Button post_jobs;

    //db helper
    //DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jobs);

        setTitle("Post jobs");


        //set initializer
        job_title=findViewById(R.id.job_title);
        job_req=findViewById(R.id.job_requirement);
        job_description=findViewById(R.id.job_description);
        no_vacancies=findViewById(R.id.job_no_vacancies);
         dateEditText = findViewById(R.id.dateEditText);
        deadlineDate=findViewById(R.id.deadline);


        post_jobs = findViewById(R.id.add_job_btn);
       //dbHelper = new DatabaseHelper(post_jobs.this);

        //firebase instance

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");






        //setting date on click listener deadline date
        deadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDeadlineDate();
            }
        });


         //set calender date posted
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });




        //setting add_jobs onclick listener

        post_jobs.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String title = job_title.getText().toString();
                String requirement = job_req.getText().toString();
                String description = job_description.getText().toString();
                String no_positions = no_vacancies.getText().toString();
                String post_date = dateEditText.getText().toString();
                String deadline = deadlineDate.getText().toString();

                if (TextUtils.isEmpty(title)){
                    job_title.setError("Job title is required!");
                    return;

                }
                if (TextUtils.isEmpty(requirement)){
                    job_req.setError("Job requirement is required!");
                    return;
                }
                if (TextUtils.isEmpty(description)){
                    job_description.setError("Job description is required!");

                }
                if (TextUtils.isEmpty(no_positions)){
                    no_vacancies.setError("No of vacancies is required!");
                    return;
                }
                databaseReference.child("Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Jobs").child(title).child("requirement").setValue(requirement);
                        databaseReference.child("Jobs").child(title).child("description").setValue(description);
                        databaseReference.child("Jobs").child(title).child("positions").setValue(no_positions);
                        databaseReference.child("Jobs").child(title).child("post_date").setValue(post_date);
                        databaseReference.child("Jobs").child(title).child("deadline_date").setValue(deadline);

                        Toast.makeText(post_jobs.this, "Posted Job successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });



    }

    public  void getDate(){
        // Get the current date as the default date for the picker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(post_jobs.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        dateEditText.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));

                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
    public  void getDeadlineDate(){
        // Get the current date as the default date for the picker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(post_jobs.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date

                        deadlineDate.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    //validations

}