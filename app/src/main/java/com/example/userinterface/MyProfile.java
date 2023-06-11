package com.example.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity {
    private TextView profile_account,support,privacy,sign_out,back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        getSupportActionBar().hide();

        //initialize items

        profile_account=findViewById(R.id.my_account);
        support=findViewById(R.id.support);
        privacy=findViewById(R.id.privacy);
        sign_out=findViewById(R.id.Sign_Out);
        back_arrow=findViewById(R.id.back_arrow);

        //on click listeners

        profile_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this,user_profile_info.class);
                startActivity(intent);
            }
        });
        //back arrow
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, another_dashboard.class);
                startActivity(intent);
            }
        });

        //support

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://abdihakimomar.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //privacy
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://www.pdfcomplete.com/cms/company/privacy.aspx";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //signout

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}