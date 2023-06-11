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

public class referees_adapter extends RecyclerView.Adapter<referees_adapter.ViewHolder> {

    private Context context;
    private List<referees_modal> ref_modal;

    public referees_adapter(Context context, List<referees_modal> ref_modal) {
        this.context = context;
        this.ref_modal = ref_modal;
    }

    @NonNull
    @Override
    public referees_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.referees_row,parent,false);


        return new ViewHolder(view)  ;
    }

    @Override
    public void onBindViewHolder(@NonNull referees_adapter.ViewHolder holder, int position) {

        referees_modal modal = ref_modal.get(position);
        holder.name_txt.setText(modal.getName());
        holder.title_txt.setText(modal.getTitle());
        holder.email_txt.setText(modal.getEmail());




    }

    @Override
    public int getItemCount() {
        return ref_modal.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_txt,title_txt,email_txt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_posted);
            title_txt = itemView.findViewById(R.id.ref_title_posted);
            email_txt = itemView.findViewById(R.id.ref_email_posted);


        }


    }
}
