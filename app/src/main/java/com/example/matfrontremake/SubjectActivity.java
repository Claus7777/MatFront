package com.example.matfrontremake;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {
    private QuestionDAO questionDAO;
    private DatabaseHelper subjectDAO;
    ArrayList<String> subject_id, subject_text;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subjectDAO = new DatabaseHelper(SubjectActivity.this);
        subject_id = new ArrayList<>();
        subject_text = new ArrayList<>();

        recyclerView = findViewById(R.id.reciclerView);

        questionDAO = new QuestionDAO(this);
        questionDAO.open();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "";
                if(v.getId() == R.id.resolver_button){
                    subject = (String) v.getTag();
                }
                Intent intent = new Intent(SubjectActivity.this, QuestionActivity.class);
                intent.putExtra("subject", subject);
                startActivity(intent);
            }
        };

        guardaDadosemArray();
        customAdapter = new CustomAdapter(SubjectActivity.this, subject_id, subject_text, listener);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubjectActivity.this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionDAO.close();
    }

    void guardaDadosemArray(){
        Cursor cursor = subjectDAO.readSubjectData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "Sem dados no banco de dados", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                subject_id.add(cursor.getString(0));
                subject_text.add(cursor.getString(1));
            }
        }
    }
}
