package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userinterface.ui.DatabaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class register extends AppCompatActivity {

    private EditText  register_name, register_email, register_number,register_password,confirmPassword;
    Button register_btn;
    TextView login;

    //firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth auth;





    int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_name=findViewById(R.id.reg_name);

        register_email=findViewById(R.id.reg_email);
        register_number=findViewById(R.id.reg_number);
        register_password=findViewById(R.id.reg_password);
        register_btn=findViewById(R.id.Register);
        confirmPassword = findViewById(R.id.edit_confirm);
        login=findViewById(R.id.login_txt_btn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("users");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseApp.initializeApp(this);



        //login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //register btn


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = register_name.getText().toString();
                String email = register_email.getText().toString();
                String number = register_number.getText().toString();
                String password = register_password.getText().toString();
                String confirm_password = confirmPassword.getText().toString();

                String encoded_email = email.replace(".", ",").replace("@", "+");
                int code = 0;
                String user_type = "user";


                if (TextUtils.isEmpty(name)){
                    register_name.setError("Please fill in your name");
                    return;
                }
                    if (TextUtils.isEmpty(email)){
                        register_email.setError("Please fill in your email");

                        return;

                    }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    register_email.setError("Invalid mail address");
                    return;
                }
                if (TextUtils.isEmpty(number)){
                        register_number.setError("Please Enter your number");
                        return;

                    }if (TextUtils.isEmpty(password)){
                        register_password.setError("Please enter your password");
                        return;
                    }if (TextUtils.isEmpty(confirm_password)){
                        confirmPassword.setError("Please confirm your password");
                        return;
                    }
                if (!password.equals(confirm_password)){

                    confirmPassword.setError("Passwords don't not match");
                    register_password.setError("Passwords don't not match");
                    return;


                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //check if the user is registered

                                if (snapshot.hasChild(encoded_email)){
                                    Toast.makeText(register.this, "user with " + email +" already exists", Toast.LENGTH_SHORT).show();
                                }else {
                                    //creates a new user
                                    databaseReference.child("users").child(encoded_email).child("name").setValue(name);

                                    databaseReference.child("users").child(encoded_email).child("number").setValue(number);
                                    databaseReference.child("users").child(encoded_email).child("password").setValue(password);
                                    databaseReference.child("users").child(encoded_email).child("code").setValue(code);
                                    databaseReference.child("users").child(encoded_email).child("user_type").setValue(user_type);

                                    Intent intent  = new Intent(register.this, verifyEmail.class);
                                    intent.putExtra("email",email);
                                    startActivity(intent);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        }else {
                            Toast.makeText(register.this, "authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


               /* databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if the user is registered

                        if (snapshot.hasChild(encoded_email)){
                            Toast.makeText(register.this, "user with " + email +" already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            //creates a new user
                            databaseReference.child("users").child(encoded_email).child("name").setValue(name);

                            databaseReference.child("users").child(encoded_email).child("number").setValue(number);
                            databaseReference.child("users").child(encoded_email).child("password").setValue(password);
                            databaseReference.child("users").child(encoded_email).child("code").setValue(code);
                            databaseReference.child("users").child(encoded_email).child("user_type").setValue(user_type);

                            Intent intent  = new Intent(register.this, verifyEmail.class);
                            intent.putExtra("email",email);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/










            }
        });

    }
}