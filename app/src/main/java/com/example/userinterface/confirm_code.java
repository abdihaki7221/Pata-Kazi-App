package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.userinterface.ui.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class confirm_code extends AppCompatActivity {
    EditText code_sent;
    Button confirm_code;

   // DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);

        code_sent=findViewById(R.id.sent_code);
        confirm_code = findViewById(R.id.confirm_code_btn);

        //database helper context
        //dbHelper=new DatabaseHelper(confirm_code.this);

        //instance of firebase database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");

        //get email from previous activity
        Intent intent = getIntent();
        String userEmail = intent.getStringExtra("email");

        //on click listener

        confirm_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the user code from user
                String userCode = code_sent.getText().toString();
                int convert_code = Integer.parseInt(userCode);
                String encoded_email = userEmail.replace(".", ",").replace("@", "+");

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(encoded_email)){
                            int  user_code = snapshot.child(encoded_email).child("code").getValue(Integer.class);

                            if (user_code == convert_code){
                                int code = 1;

                                //create a new hashmap to update code column in database

                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("code", code);

                                databaseReference.child("users").child(encoded_email).updateChildren(updateMap);

                                Intent intent = new Intent(confirm_code.this,NavigationDashboardActivity.class);
                                intent.putExtra("email",userEmail);
                                startActivity(intent);
                            }else {

                                //snack bar
                                LinearLayout linearLayout = findViewById(R.id.linear_login);
                                Snackbar snackbar = Snackbar.make(linearLayout, "Incorrect Code", Snackbar.LENGTH_SHORT);
                                // Get the Snack bar view
                                View snackBarView = snackbar.getView();
                                // Set the Snack bar view's background color
                                snackBarView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                                // Add the Snack bar view to the LinearLayout
                                linearLayout.addView(snackBarView,1);
                                // Show the Snack bar
                                snackbar.show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });



    }
}