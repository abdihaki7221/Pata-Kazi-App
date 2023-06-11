package com.example.userinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class education_Adapter extends RecyclerView.Adapter<education_Adapter.ViewHolder> {

    private Context context;
    private List<education_modal> education_details;

    public education_Adapter(Context context, List<education_modal> education_details) {
        this.context = context;
        this.education_details = education_details;
    }

    @NonNull
    @Override
    public education_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.education_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull education_Adapter.ViewHolder holder, int position) {

        education_modal modal = education_details.get(position);

        holder.field_txt.setText(modal.getField());
        holder.level_txt.setText(modal.getLevel());
        holder.institution_txt.setText(modal.getInstitution());
        holder.start_year_txt.setText(modal.getStart_year());
        holder.end_year_txt.setText(modal.getEnd_year());

    }

    @Override
    public int getItemCount() {
        return education_details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



       TextView field_txt,level_txt,institution_txt,start_year_txt,end_year_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            field_txt = itemView.findViewById(R.id.field_posted);
            level_txt = itemView.findViewById(R.id.level_posted);
            institution_txt = itemView.findViewById(R.id.institution_posted);
            start_year_txt = itemView.findViewById(R.id.start_year_posted);
            end_year_txt = itemView.findViewById(R.id.end_year_posted);






        }


    }
}
