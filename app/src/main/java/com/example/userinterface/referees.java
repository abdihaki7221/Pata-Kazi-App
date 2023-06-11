package com.example.userinterface;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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

public class referees extends AppCompatActivity {
    private TextView referees;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    RecyclerView referee_recycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referees);

        setTitle("Referees");



        referee_recycleView = findViewById(R.id.referees_recycler);
        referees = findViewById(R.id.add_referees);
        referees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_referees();

            }
        });

        //getting referees from database
        //get logged in user
        String logged_user = user.getEmail();
        //encode email
        String encoded_email = logged_user.replace(".", ",").replace("@", "+");


        //getting the education details

        List<referees_modal> my_referees = new ArrayList<>();
        databaseReference.child("user_profile").child(encoded_email).child("referees").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot educationSnapshot: snapshot.getChildren()){
                    String name = educationSnapshot.child("name").getValue(String.class);
                    String job_title = educationSnapshot.child("title").getValue(String.class);
                    String email = educationSnapshot.child("email").getValue(String.class);



                    referees_modal modal = new referees_modal(name,job_title,email);
                    my_referees.add(modal);

                }

                referees_adapter adapter = new referees_adapter(referees.this,my_referees);
                referee_recycleView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referee_recycleView.setLayoutManager(new LinearLayoutManager(referees.this));
    }

    private void dialog_referees() {

        // create a new custom dialog box
        final Dialog dialog = new Dialog(referees.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.referees_dialog);
        dialog.setTitle("Skills");

// find the EditText elements in the dialog box layout
        TextInputEditText editText1 = dialog.findViewById(R.id.ref_name);
        TextInputEditText editText2 = dialog.findViewById(R.id.Ref_job);
        TextInputEditText editText3 = dialog.findViewById(R.id.ref_email);
        TextInputEditText editText4 = dialog.findViewById(R.id.ref_phone);
        Button save_ref = dialog.findViewById(R.id.save_referees_btn);


// set any additional properties or listeners for the EditText elements

// show the dialog box
        dialog.show();


        save_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get logged in user
                String logged_user = user.getEmail();
                //encode email
                String encoded_email = logged_user.replace(".", ",").replace("@", "+");
                //get text strings
                String name = editText1.getText().toString().trim();
                String job_title = editText2.getText().toString().trim();
                String ref_email = editText3.getText().toString().trim();
                String ref_phone = editText4.getText().toString().trim();



                //set error text if edittext1 is empty
                if (TextUtils.isEmpty(name)){
                    editText1.setError("This field is required ");
                    return;
                } if (TextUtils.isEmpty(job_title)){
                    editText2.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(ref_email)){
                    editText3.setError("This field is required ");
                    return;
                }
                if (TextUtils.isEmpty(ref_email)){
                    editText4.setError("This field is required ");
                    return;
                }


                databaseReference.child("user_profile").child(encoded_email).child("referees").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        HashMap<String,Object> referees_map = new HashMap<>();



                        referees_map.put("name", name);
                        referees_map.put("title",job_title);
                        referees_map.put("email",ref_email);
                        referees_map.put("phone",ref_phone);


                        databaseReference.child("user_profile").child(encoded_email).child("referees").push().setValue(referees_map).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(referees.this, "added referees successfully", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(referees.this, "failed to insert", Toast.LENGTH_SHORT).show();

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