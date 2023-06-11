package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class education extends AppCompatActivity {

    private TextView education;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    RecyclerView education_recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        setTitle("Education Details");

        education = findViewById(R.id.add_education);
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_education();

            }
        });
        //get logged in user
        String logged_user = user.getEmail();
        //encode email
        String encoded_email = logged_user.replace(".", ",").replace("@", "+");
        education_recycleView = findViewById(R.id.education_recycler);

        //getting the education details

        List<education_modal> my_education = new ArrayList<>();
        databaseReference.child("user_profile").child(encoded_email).child("education").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot educationSnapshot: snapshot.getChildren()){
                    String field = educationSnapshot.child("skills1").getValue(String.class);
                    String level = educationSnapshot.child("skills2").getValue(String.class);
                    String institution = educationSnapshot.child("skills3").getValue(String.class);
                    String start = educationSnapshot.child("skills4").getValue(String.class);
                    String end = educationSnapshot.child("skills5").getValue(String.class);

                    education_modal education = new education_modal(field,level,institution,start,end);
                    my_education.add(education);

                }

                education_Adapter adapter = new education_Adapter(education.this,my_education);
                education_recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        education_recycleView.setLayoutManager(new LinearLayoutManager(education.this));
    }



    private void dialog_education() {

        // create a new custom dialog box
        final Dialog dialog = new Dialog(education.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.education_dialog);
        dialog.setTitle("Skills");

// find the EditText elements in the dialog box layout
        TextInputEditText editText1 = dialog.findViewById(R.id.edu_field);
        TextInputEditText editText2 = dialog.findViewById(R.id.edu_level);
        TextInputEditText editText3 = dialog.findViewById(R.id.edu_institution);
        TextInputEditText editText4 = dialog.findViewById(R.id.edu_start_year);
        TextInputEditText editText5 = dialog.findViewById(R.id.edu_end_year);
        Button saveBtn=dialog.findViewById(R.id.save_education_btn);

// set any additional properties or listeners for the EditText elements

// show the dialog box
        dialog.show();


        //insert data
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get logged in user
                String logged_user = user.getEmail();
                //encode email
                String encoded_email = logged_user.replace(".", ",").replace("@", "+");
                //get text strings
                String field = editText1.getText().toString().trim();
                String level = editText2.getText().toString().trim();
                String institution = editText3.getText().toString().trim();
                String start = editText4.getText().toString().trim();
                String end = editText5.getText().toString().trim();

                //set error text if edittext1 is empty
                if (TextUtils.isEmpty(field)){
                    editText1.setError("This field is required ");
                    return;
                } if (TextUtils.isEmpty(level)){
                    editText2.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(institution)){
                    editText3.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(start)){
                    editText4.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(end)){
                    editText5.setError("This field is required ");
                    return;
                }

                databaseReference.child("user_profile").child(encoded_email).child("education").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        HashMap<String,Object> education_map = new HashMap<>();



                        education_map.put("skills1", field);
                        education_map.put("skills2",level);
                        education_map.put("skills3",institution);
                        education_map.put("skills4",start);
                        education_map.put("skills5",end);

                        databaseReference.child("user_profile").child(encoded_email).child("education").push().setValue(education_map).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(education.this, "added education successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(education.this, "failed to insert", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

    }
}