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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class experience extends AppCompatActivity {
    private TextView experience;
    private RecyclerView experience_recycleView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        setTitle("Experience");

        experience = findViewById(R.id.add_experience);
        experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_experience();

            }
        });
        //recycler
        experience_recycleView=findViewById(R.id.experience_recycler);
        //get logged in user
        String logged_user = user.getEmail();
        //encode email
        String encoded_email = logged_user.replace(".", ",").replace("@", "+");


        //array list to store the data

        List<experience_modal>exp_list = new ArrayList<>();
        //get the experience data
        databaseReference.child("user_profile").child(encoded_email).child("experience").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot experienceSnap: snapshot.getChildren()){
                    String company = experienceSnap.child("company").getValue(String.class);
                    String title = experienceSnap.child("title").getValue(String.class);
                    String start = experienceSnap.child("start_year").getValue(String.class);
                    String end = experienceSnap.child("end_year").getValue(String.class);

                    experience_modal modal = new experience_modal(company,title,start,end);
                    exp_list.add(modal);
                }
                experience_adapter adapter =new experience_adapter(experience.this, exp_list);
                experience_recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        experience_recycleView.setLayoutManager(new LinearLayoutManager(experience.this));
    }

    private void dialog_experience() {

        // create a new custom dialog box
        final Dialog dialog = new Dialog(experience.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.experience_dialog);
        dialog.setTitle("Skills");

// find the EditText elements in the dialog box layout
        TextInputEditText editText1 = dialog.findViewById(R.id.Company_exp);
        TextInputEditText editText2 = dialog.findViewById(R.id.title_exp);
        TextInputEditText editText3 = dialog.findViewById(R.id.start_year_exp);
        TextInputEditText editText4 = dialog.findViewById(R.id.end_year_exp);

        Button experienceBtn=dialog.findViewById(R.id.save_experience_btn);



// set any additional properties or listeners for the EditText elements

// show the dialog box
        dialog.show();

        experienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get logged in user
                String logged_user = user.getEmail();
                //encode email
                String encoded_email = logged_user.replace(".", ",").replace("@", "+");
                //get text strings
                String company = editText1.getText().toString().trim();
                String job_title = editText2.getText().toString().trim();
                String start_year = editText3.getText().toString().trim();
                String end_year = editText4.getText().toString().trim();


                //set error text if edittext1 is empty
                if (TextUtils.isEmpty(company)){
                    editText1.setError("This field is required ");
                    return;
                } if (TextUtils.isEmpty(job_title)){
                    editText2.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(start_year)){
                    editText3.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(end_year)){
                    editText4.setError("This field is required ");
                    return;
                }


                databaseReference.child("user_profile").child(encoded_email).child("experience").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        HashMap<String,Object> experience_map = new HashMap<>();



                        experience_map.put("company", company);
                        experience_map.put("title",job_title);
                        experience_map.put("start_year",start_year);
                        experience_map.put("end_year",end_year);


                        databaseReference.child("user_profile").child(encoded_email).child("experience").push().setValue(experience_map).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(experience.this, "added experience successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(experience.this, "failed to insert", Toast.LENGTH_SHORT).show();

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