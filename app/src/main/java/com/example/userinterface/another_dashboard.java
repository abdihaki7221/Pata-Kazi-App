package com.example.userinterface;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class another_dashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<MyJobs> jobsList;
    private TextView option_menu;

    private TextView jobs;
    private TextView job_status;
    private TextView news;

    private TextView account;



    private DatabaseReference databaseReference;
    private RelativeLayout rootLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_dashboard);

        getSupportActionBar().hide();
        //intent passing previous email logged in
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        //job_status

        job_status = findViewById(R.id.job_status);

        job_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(another_dashboard.this, job_status.class);
                intent.putExtra("email",email);
                startActivity(intent);

            }
        });

        //news
        news = findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(another_dashboard.this,news.class);
                startActivity(intent);
            }
        });


        //account
        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(another_dashboard.this,MyProfile.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });




        //jobs
        jobs= findViewById(R.id. jobs_available);
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(another_dashboard.this,another_dashboard.class);
                startActivity(intent);
            }
        });

        //root layout

        rootLayout= findViewById(R.id.root_layout);
        View view = LayoutInflater.from(another_dashboard.this).inflate(R.layout.side_menu, null);

        //side menu








        //logout option menu

        option_menu = findViewById(R.id.option_menu);

        option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.logout_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case  R.id.menu_logout:
                                Intent intent = new Intent(another_dashboard.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popupMenu.show();
            }
        });

        MenuInflater menuInflater = getMenuInflater();
       // setTitle("Jobs Available");

        recyclerView = findViewById(R.id.recyclerView);

        jobsList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("Jobs");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //array list
                List<MyJobs> myJobsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String title = dataSnapshot.getKey();
                    String postDateString = dataSnapshot.child("post_date").getValue(String.class);
                    String deadlineDateString = dataSnapshot.child("deadline_date").getValue(String.class);

                    // Get the first two letters of the job title as the acronym
                    String[] words = title.split(" ");
                    String acronym = words[0].substring(0,1) + words[1].substring(0,1);
                    String acronym_upper=acronym.toUpperCase(Locale.ROOT);

                    //getting the difference in the posted date and current date
                    long daysDiff = 0;
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        Date postDate = format.parse(postDateString);
                        Date currentDate = Calendar.getInstance().getTime();
                        daysDiff = getDateDiff(postDate, currentDate, TimeUnit.DAYS);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //creates an object instance of MYjobs modal class

                    MyJobs myJobs = new MyJobs(title, String.valueOf(daysDiff)+"d ago", "Deadline: "+deadlineDateString,acronym_upper);
                    myJobsList.add(myJobs);
                }
                jobsList.addAll(myJobsList);
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });



        adapter = new RecyclerAdapter(this, jobsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout){
            Intent intent = new Intent(another_dashboard.this,MainActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }


       return super.onOptionsItemSelected(item);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}


