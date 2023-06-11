package com.example.userinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class skills_adapter extends RecyclerView.Adapter<skills_adapter.ViewHolder> {

    private Context context;
    private List<skills_modal> my_skills;







    public skills_adapter(Context context, List<skills_modal> my_skills) {
        this.context = context;
        this.my_skills = my_skills;
    }

    @NonNull
    @Override
    public skills_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.skills_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull skills_adapter.ViewHolder holder, int position) {
        //initialize skills modal class
        skills_modal my_skill_set = my_skills.get(position);

        holder.skills_posted.setText(my_skill_set.getSkill_title());



    }

    @Override
    public int getItemCount() {
        return my_skills.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        String id;
        Button skills_posted;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            skills_posted = itemView.findViewById(R.id.skills_btn_posted);




        }


    }
}
