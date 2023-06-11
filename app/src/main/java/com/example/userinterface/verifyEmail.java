package com.example.userinterface;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import java.util.Random;

import javax.mail.MessagingException;


public class verifyEmail extends AppCompatActivity {


    Button send_code;
    EditText verify_email;
    int code;
    String Subject;

   // DatabaseHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        verify_email = findViewById(R.id.verify_email);

        send_code = findViewById(R.id.Send_code_btn);

       // dbHelper=new DatabaseHelper(verifyEmail.this);

        //firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");

        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = verify_email.getText().toString();

                String encoded_email = email.replace(".", ",").replace("@", "+");


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){


                    verify_email.setError("Please Enter valid email");
                    return;
                }


                //database reference instance
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.hasChild(encoded_email)){
                            LinearLayout linearLayout = findViewById(R.id.linear_login);
                            Snackbar snackbar = Snackbar.make(linearLayout, "Email: " + email + " is not registered", Snackbar.LENGTH_SHORT);
                            // Get the Snack bar view
                            View snackBarView = snackbar.getView();
                            // Set the Snack bar view's background color
                            snackBarView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                            // Add the Snack bar view to the LinearLayout
                            linearLayout.addView(snackBarView,1);
                            // Show the Snack bar
                            snackbar.show();

                        }else {
                            // pass email and OTP code to the confirm_code activity
                            Subject ="Email verification From Post bank careers";
                            Random random =new Random();
                            code = random.nextInt(9999)+1000;

                            //create a new hashmap to update code column in database

                            Map<String, Object> updateMap = new HashMap<>();
                            updateMap.put("code", code);

                            databaseReference.child("users").child(encoded_email).updateChildren(updateMap);

                            try {
                                SendMail.send(email, code,Subject);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                            // dbHelper.update_interns(email,code);

                            Toast.makeText(verifyEmail.this, "Verification Code has been sent To " + email, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(verifyEmail.this, confirm_code.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                   // }

                //else {


                }

            //}
        });
    }
}