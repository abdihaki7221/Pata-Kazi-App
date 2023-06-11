package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;


import com.example.userinterface.ui.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView Sign_Up;
    EditText email_login, password_login;
    Button login;
    private FirebaseAuth auth;
    //firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Sign_Up = findViewById(R.id.Sign_Up);
        login= findViewById(R.id.login);

        email_login = findViewById(R.id.login_email);
        password_login = findViewById(R.id.login_password);
        //firebase
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");

        FirebaseUser user = auth.getCurrentUser();




        //set title
        setTitle("Login");
        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

        //setting conditions if users clicks the login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email = email_login.getText().toString();
               String password = password_login.getText().toString();

                String encoded_email = email.replace(".", ",").replace("@", "+");

               if (TextUtils.isEmpty(email)){
                   email_login.setError("Please Enter your email");
                   return;
               }
                if (TextUtils.isEmpty(password)){
                    password_login.setError("Please Enter your password");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email_login.setError("Please enter valid email");
                    return;
                }

                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                         databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    if (snapshot.hasChild(encoded_email)){

                                        //get the value of the code in the database
                                        int code = snapshot.child(encoded_email).child("code").getValue(Integer.class);
                                        String userType = snapshot.child(encoded_email).child("user_type").getValue(String.class);
                                        //check for the password
                                        String user_password = snapshot.child(encoded_email).child("password").getValue(String.class);
                                        if (password.equals(user_password)){
                                            if (code == 1){
                                                if(userType.equals("user")){
                                                    Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                                    Intent intent =new Intent(MainActivity.this,another_dashboard.class);
                                                    intent.putExtra("email",email);
                                                    startActivity(intent);
                                                }else if (userType.equals("admin")){
                                                    Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                                    Intent intent =new Intent(MainActivity.this,admin_dashboard.class);
                                                    intent.putExtra("email",email);
                                                    startActivity(intent);
                                                }else {
                                                    Toast.makeText(MainActivity.this, "An Error occurred", Toast.LENGTH_SHORT).show();

                                                }






                                            }else {
                                                //verify email if not verified
                                                Intent intent =new Intent(MainActivity.this,verifyEmail.class);
                                                intent.putExtra("email",email);
                                                startActivity(intent);
                                            }


                                        }else {
                                            getSnack();
                                        }
                                    }else {
                                        getSnack();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    if (snapshot.hasChild(encoded_email)){

                                        //get the value of the code in the database
                                        int code = snapshot.child(encoded_email).child("code").getValue(Integer.class);
                                        String userType = snapshot.child(encoded_email).child("user_type").getValue(String.class);
                                        //check for the password
                                        String user_password = snapshot.child(encoded_email).child("password").getValue(String.class);
                                        if (password.equals(user_password)){
                                            if (code == 1){
                                                if(userType.equals("user")){
                                                    Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                                    Intent intent =new Intent(MainActivity.this,another_dashboard.class);
                                                    intent.putExtra("email",email);
                                                    startActivity(intent);
                                                }else if (userType.equals("admin")){
                                                    Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                                    Intent intent =new Intent(MainActivity.this,admin_dashboard.class);
                                                    intent.putExtra("email",email);
                                                    startActivity(intent);
                                                }else {
                                                    Toast.makeText(MainActivity.this, "An Error occurred", Toast.LENGTH_SHORT).show();

                                                }






                                            }else {
                                                //verify email if not verified
                                                Intent intent =new Intent(MainActivity.this,verifyEmail.class);
                                                intent.putExtra("email",email);
                                                startActivity(intent);
                                            }


                                        }else {
                                            getSnack();
                                        }
                                    }else {
                                        getSnack();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    }
                });


               /* databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.hasChild(encoded_email)){

                            //get the value of the code in the database
                            int code = snapshot.child(encoded_email).child("code").getValue(Integer.class);
                            String userType = snapshot.child(encoded_email).child("user_type").getValue(String.class);
                            //check for the password
                            String user_password = snapshot.child(encoded_email).child("password").getValue(String.class);
                            if (password.equals(user_password)){
                                if (code == 1){
                                    if(userType.equals("user")){
                                        Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(MainActivity.this,another_dashboard.class);
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    }else if (userType.equals("admin")){
                                        Toast.makeText(MainActivity.this, "successfully logged in", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(MainActivity.this,admin_dashboard.class);
                                        intent.putExtra("email",email);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(MainActivity.this, "An Error occurred", Toast.LENGTH_SHORT).show();

                                    }






                                }else {
                                    //verify email if not verified
                                    Intent intent =new Intent(MainActivity.this,verifyEmail.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                }


                            }else {
                                getSnack();
                            }
                        }else {
                            getSnack();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }










        });
    }

    public void getSnack(){
        LinearLayout linearLayout = findViewById(R.id.linear_login);
        Snackbar snackbar = Snackbar.make(linearLayout, "Incorrect Username or Password", Snackbar.LENGTH_SHORT);

        // Get the Snack bar view
        View snackbarView = snackbar.getView();

        // Set the Snackbar view's background color
        snackbarView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

        // Add the Snackbar view to the LinearLayout
        linearLayout.addView(snackbarView,0);

        // Show the Snackbar
        snackbar.show();
    }
}