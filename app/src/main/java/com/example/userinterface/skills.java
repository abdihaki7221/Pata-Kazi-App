package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class skills extends AppCompatActivity {
    private TextView skills;
    private RecyclerView skills_recycle_View;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        setTitle("Skills");

        skills = findViewById(R.id.add_skills);

        skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_skills();

            }
        });





        //save btn

        //recycler view
        skills_recycle_View = findViewById(R.id.skills_recycler);
        //get logged in user
        String logged_user = user.getEmail();
        //encode email
        String encoded_email = logged_user.replace(".", ",").replace("@", "+");


        //getting the skills
        List<skills_modal> skill_list= new ArrayList<>();
        databaseReference.child("user_profile").child(encoded_email).child("skills").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String skill_name = dataSnapshot.getValue(String.class);

                    skills_modal skill = new skills_modal(skill_name);
                    skill_list.add(skill);
                }

                skills_adapter adapter = new skills_adapter(skills.this, skill_list);
                skills_recycle_View.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        skills_recycle_View.setLayoutManager(new GridLayoutManager(this,2));

    }

    private void dialog_skills() {
        // create a new custom dialog box
        final Dialog dialog = new Dialog(skills.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.skills_dialog);
        dialog.setTitle("Skills");

// find the EditText elements in the dialog box layout
        TextInputEditText editText1 = dialog.findViewById(R.id.skill_txt_1);
        TextInputEditText editText2 = dialog.findViewById(R.id.skill_txt_2);
        TextInputEditText editText3 = dialog.findViewById(R.id.skill_txt_3);
        TextInputEditText editText4 = dialog.findViewById(R.id.skill_txt_4);
        TextInputEditText editText5 = dialog.findViewById(R.id.skill_txt_5);
        Button saveBtn=dialog.findViewById(R.id.save_skills_btn);



// set any additional properties or listeners for the EditText elements

// show the dialog box
        dialog.show();

        //adding the skills to database

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get logged in user
                String logged_user = user.getEmail();
                //encode email
                String encoded_email = logged_user.replace(".", ",").replace("@", "+");
                //get text strings
                String skill1 = editText1.getText().toString().trim();
                String skill2 = editText2.getText().toString().trim();
                String skill3 = editText3.getText().toString().trim();
                String skill4 = editText4.getText().toString().trim();
                String skill5 = editText5.getText().toString().trim();

                //set error text if edittext1 is empty
                if (TextUtils.isEmpty(skill1)){
                    editText1.setError("This field is required ");
                    return;
                } if (TextUtils.isEmpty(skill2)){
                    editText2.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(skill3)){
                    editText3.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(skill4)){
                    editText4.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(skill5)){
                    editText5.setError("This field is required ");
                    return;
                }

                databaseReference.child("user_profile").child(encoded_email).child("skills").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        //if skills are more than 5

                        Long num_skills = snapshot.getChildrenCount();

                        if (num_skills ==5){
                            Toast.makeText(skills.this, "You have maximum skills required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        HashMap<String,Object> skills_map = new HashMap<>();

                        skills_map.put("skills1",skill1);
                        skills_map.put("skills2",skill2);
                        skills_map.put("skills3",skill3);
                        skills_map.put("skills4",skill4);
                        skills_map.put("skills5",skill5);

                        databaseReference.child("user_profile").child(encoded_email).child("skills").setValue(skills_map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(skills.this, "added skills successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(skills.this, "failed to insert", Toast.LENGTH_SHORT).show();

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

/*//method to delete skills
    public void deleteSkill(String skillId) {

        String logged_user = user.getEmail();
        String encoded_email = logged_user.replace(".", ",").replace("@", "+");
        DatabaseReference skillsRef = databaseReference.child("user_profile").child(encoded_email).child("skills").child(skillId);
        skillsRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(skills.this, "Skill deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(skills.this, "Failed to delete skill", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}