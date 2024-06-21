package com.example.matfrontremake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {


    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private DatabaseHelper questionDAO;
    ArrayList<String> question_id, question_subject,
            question_text, question_alt1,
            question_alt2, question_alt3,
            question_alt4, question_answer;

    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionDAO = new DatabaseHelper(this);
        question_id = new ArrayList<>();
        question_subject = new ArrayList<>();
        question_text= new ArrayList<>();
        question_alt1= new ArrayList<>();
        question_alt2= new ArrayList<>();
        question_alt3= new ArrayList<>();
        question_alt4= new ArrayList<>();
        question_answer= new ArrayList<>();
        escolheQuestao();

        subject = getIntent().getStringExtra("subject");
        boolean databaseCheck = false;
        for(int i = 0; i < question_subject.size(); i++){
            if(Objects.equals(subject, question_subject.get(i))){
                databaseCheck = true;
            }
        }
        if(!databaseCheck){
            Toast.makeText(this, "Nenhuma questão dessa matéria encontrada no banco de dados", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //Escolhendo questão aleatória
        Random rng = new Random();
        int n = rng.nextInt(question_id.size());
        if(n==2) n--;
        while(!Objects.equals(question_subject.get(n), subject)){
            n = rng.nextInt(question_id.size());
        }

        questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(question_text.get(n));
        option1Button = findViewById(R.id.option1Button);
        option1Button.setText(question_alt1.get(n));
        option2Button = findViewById(R.id.option2Button);
        option2Button.setText((question_alt2.get(n)));
        option3Button = findViewById(R.id.option3Button);
        option3Button.setText((question_alt3.get(n)));
        option4Button = findViewById(R.id.option4Button);
        option4Button.setText((question_alt4.get(n)));

        // Listener para verificar a resposta escolhida
        int finalN = n;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String selectedAnswer = clickedButton.getText().toString();
                String correctAnswer = question_answer.get(finalN).toString();

                // Verifica se a resposta está correta
                if (selectedAnswer.equals(correctAnswer)) {
                    questionDAO.updateRanking(4);
                    Toast.makeText(QuestionActivity.this, "Resposta Correta!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionActivity.this, RankingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(QuestionActivity.this, "Resposta Errada!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QuestionActivity.this, RankingActivity.class);
                    startActivity(intent);
                }
            }
        };

        // Configurando os botões com o listener
        option1Button.setOnClickListener(listener);
        option2Button.setOnClickListener(listener);
        option3Button.setOnClickListener(listener);
        option4Button.setOnClickListener(listener);
    }

    void escolheQuestao(){
        Cursor cursor = questionDAO.readQuestionData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Sem questões no banco de dados", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                question_id.add(cursor.getString(0));
                question_subject.add(cursor.getString(1));
                question_text.add(cursor.getString(2));
                question_alt1.add(cursor.getString(3));
                question_alt2.add(cursor.getString(4));
                question_alt3.add(cursor.getString(5));
                question_alt4.add(cursor.getString(6));
                question_answer.add(cursor.getString(7));
            }
        }
    }
}
