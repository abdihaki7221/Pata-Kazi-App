package com.example.userinterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class experience_adapter extends RecyclerView.Adapter<experience_adapter.ViewHolder> {

    private Context context;
    private List<experience_modal> exp_modal;

    public experience_adapter(Context context, List<experience_modal> exp_modal) {
        this.context = context;
        this.exp_modal = exp_modal;
    }

    @NonNull
    @Override
    public experience_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.experience_row,parent,false);


       return new ViewHolder(view)  ;
    }

    @Override
    public void onBindViewHolder(@NonNull experience_adapter.ViewHolder holder, int position) {

        experience_modal modal = exp_modal.get(position);
        holder.company_txt.setText(modal.getCompany());
        holder.title_txt.setText(modal.getTitle());
        holder.start_txt.setText(modal.getStart_year());
        holder.end_txt.setText(modal.getEnd_year());



    }

    @Override
    public int getItemCount() {
        return exp_modal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView company_txt,title_txt,start_txt,end_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            company_txt = itemView.findViewById(R.id.company_posted);
            title_txt = itemView.findViewById(R.id.title_posted);
            start_txt = itemView.findViewById(R.id.start_year_exp_posted);
            end_txt = itemView.findViewById(R.id.end_year_exp_posted);

        }


    }
}
