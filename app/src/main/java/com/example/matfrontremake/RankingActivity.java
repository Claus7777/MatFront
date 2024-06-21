package com.example.matfrontremake;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RankingActivity extends AppCompatActivity {
    private DatabaseHelper userDAO;

    RecyclerView recyclerView;
    RankingAdapter rankingAdapter;
    ArrayList<String> user_id, user_name, user_points;
    ArrayList<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        userDAO = new DatabaseHelper(RankingActivity.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_points = new ArrayList<>();
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.reciclerView_act);

        guardaDadosUsuario();
        ordenaUsuarios();
        rankingAdapter = new RankingAdapter(RankingActivity.this, userList);
        recyclerView.setAdapter(rankingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));



    }

    void guardaDadosUsuario(){
        Cursor cursor = userDAO.readUserData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Sem dados no banco de dados", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                int points = cursor.getInt(2);
                userList.add(new User(id, name, points));
            }
        }
    }

    void ordenaUsuarios(){
        Collections.sort(userList, new Comparator<User>(){
            @Override
            public int compare(User u1,User u2){
                return Integer.compare(u2.points, u1.points);
            }
        });
    }
}
