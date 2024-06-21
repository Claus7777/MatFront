package com.example.matfrontremake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public QuestionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SUBJECT, question.getSubject());
        values.put(DatabaseHelper.COLUMN_QUESTION, question.getQuestion());
        values.put(DatabaseHelper.COLUMN_OPTION1, question.getOption1());
        values.put(DatabaseHelper.COLUMN_OPTION2, question.getOption2());
        values.put(DatabaseHelper.COLUMN_OPTION3, question.getOption3());
        values.put(DatabaseHelper.COLUMN_OPTION4, question.getOption4());
        values.put(DatabaseHelper.COLUMN_ANSWER, question.getAnswer());
        database.insert(DatabaseHelper.TABLE_QUESTIONS, null, values);
    }

    public List<Question> getQuestionsBySubject(String subject) {
        List<Question> questions = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_QUESTIONS,
                null, DatabaseHelper.COLUMN_SUBJECT + "=?",
                new String[]{subject}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question question = cursorToQuestion(cursor);
            questions.add(question);
            cursor.moveToNext();
        }
        cursor.close();
        return questions;
    }

    private Question cursorToQuestion(Cursor cursor) {
        Question question = new Question();
        question.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
        question.setSubject(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SUBJECT)));
        question.setQuestion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_QUESTION)));
        question.setOption1(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION1)));
        question.setOption2(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION2)));
        question.setOption3(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION3)));
        question.setOption4(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_OPTION4)));
        question.setAnswer(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ANSWER)));
        return question;
    }
}
