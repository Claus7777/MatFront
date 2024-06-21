package com.example.matfrontremake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private ArrayList subject_id, subject_title;
    private View.OnClickListener listener;
    CustomAdapter(Context context, ArrayList subject_id, ArrayList subject_title, View.OnClickListener listener){
        this.context = context;
        this.subject_id = subject_id;
        this.subject_title = subject_title;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.subject_txt.setText(String.valueOf(subject_title.get(position)));
        holder.subject_id_txt.setText(String.valueOf(subject_id.get(position)));
        holder.resolveButton.setOnClickListener(listener);
        holder.resolveButton.setTag(subject_title.get(position));
    }

    @Override
    public int getItemCount() {
        return subject_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView subject_id_txt, subject_txt;
        Button resolveButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            subject_id_txt = itemView.findViewById(R.id.subject_id_text);
            subject_txt = itemView.findViewById(R.id.materia_text);
            resolveButton = itemView.findViewById(R.id.resolver_button);
        }
    }
}
