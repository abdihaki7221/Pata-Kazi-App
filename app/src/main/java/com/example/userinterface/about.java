package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
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

public class about extends AppCompatActivity {
    private EditText name, user_number;
    private TextView email;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About");

        //initialize edit text and textviews
        name = findViewById(R.id.full_name_about);
        email = findViewById(R.id.email_about);
        user_number = findViewById(R.id.phone_about);



        //firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        String user_email = user.getEmail();

        String encoded_email = user_email.replace(".", ",").replace("@", "+");
        //fetching the user credentials

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("users").child("users").child(encoded_email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String user_name = snapshot.child("name").getValue(String.class);
                    String number = snapshot.child("number").getValue(String.class);

                    name.setText(user_name);
                    email.setText(user_email);
                    user_number.setText(number);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}