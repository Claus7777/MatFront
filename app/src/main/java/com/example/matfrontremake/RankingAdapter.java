package com.example.matfrontremake;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<User> userList;

    private Map<String, Integer> userImageMap;


    RankingAdapter(Context context, ArrayList<User> userList){
        this.context = context;
        this.userList = userList;

        userImageMap = new HashMap<>();
        userImageMap.put("Pedro Parques", R.drawable.spidey);
        userImageMap.put("Rui", R.drawable.ryuzeras);
        userImageMap.put("Tonho Starqui", R.drawable.toninho);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rank_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.user_name.setText(user.name);
        holder.user_points.setText(String.valueOf(user.points));

        if(userImageMap.containsKey(user.name)){
            holder.imageView.setImageResource(userImageMap.get(user.name));
        } else {
        holder.imageView.setImageResource(R.drawable.serv);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView  user_name, user_points;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            user_points = itemView.findViewById(R.id.user_points);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
