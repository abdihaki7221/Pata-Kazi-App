package com.example.userinterface;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    //colors
    int[] colors = {
            Color.parseColor("#FF9800"), // orange
            Color.parseColor("#D27D2D"), // broen
            Color.parseColor("#FF5722"), // deep orange
            Color.parseColor("#795548"), // brown
            Color.parseColor("#008080"), // teal
            Color.parseColor("#9370DB"), // lavender
            Color.parseColor("#00BFFF"), // sky blue
            Color.parseColor("#7B1FA2"), // deep purple
            Color.parseColor("#00FFFF"),  // cyan
            Color.parseColor("#800000"),  // Maroon
            Color.parseColor("#4A0404"),  // Oxblood
            Color.parseColor("#722F37"),  // wine
            Color.parseColor("#EC5800"),  // Persimmon
            Color.parseColor("#FF4433"),  // wine
            Color.parseColor("#E3735E"),  // Terra Cotta






    };

    private Context context;
    private List<MyJobs> jobList;

    public RecyclerAdapter(Context context, List<MyJobs> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.posted_jobs, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.viewHolder holder, int position) {

        //setting onclick listener to the items



        // Set the background color of the CardView to a random color
        Random random = new Random();
        int randomColorIndex = random.nextInt(colors.length);
        holder.acronym_card.setCardBackgroundColor(colors[randomColorIndex]);

        MyJobs job = jobList.get(position);

        // Set the job title
        holder.job_title_txt.setText(job.getJob_title());

        // Set the posted date
        holder.posted_date_txt.setText(job.getJob_posted_date());

        // Set the deadline date
        holder.deadline_date_txt.setText(job.getJob_deadline_date());

        // Set the deadline date
        holder.acronym_txt.setText(job.getAcronym_text());





        holder.parent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context context = v.getContext();
                Intent intent = new Intent(context, job_details.class);
                intent.putExtra("jobTitle", job.getJob_title());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        CardView parent_card, acronym_card;
        TextView acronym_txt, job_title_txt, deadline_date_txt, posted_date_txt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            parent_card = itemView.findViewById(R.id.parent_card);
            acronym_card = itemView.findViewById(R.id.acronym_Card);
            acronym_txt = itemView.findViewById(R.id.acronym_text);
            job_title_txt = itemView.findViewById(R.id.job_title_data);
            deadline_date_txt = itemView.findViewById(R.id.deadline_date_data);
            posted_date_txt = itemView.findViewById(R.id.posted_date_data);
        }
    }
}

