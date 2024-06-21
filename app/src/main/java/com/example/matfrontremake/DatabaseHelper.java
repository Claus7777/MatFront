package com.example.matfrontremake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.concurrent.SynchronousQueue;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "matFront.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    //region TABELAS MATERIAS E QUESTOES
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_SUBJECT_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_OPTION4 = "option4";
    public static final String COLUMN_ANSWER = "answer";

    //endregion

    //region TABELA USUARIOS
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "nome";
    public static final String COLUMN_USER_POINTS = "pontos";
    //endregion

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_SUBJECT + " TEXT, " +
                    COLUMN_QUESTION + " TEXT, " +
                    COLUMN_OPTION1 + " TEXT, " +
                    COLUMN_OPTION2 + " TEXT, " +
                    COLUMN_OPTION3 + " TEXT, " +
                    COLUMN_OPTION4 + " TEXT, " +
                    COLUMN_ANSWER + " TEXT" +
                    ")";

    private static final String SUBJECT_TABLE_CREATE = "CREATE TABLE " + TABLE_SUBJECTS + " (" +
            COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_SUBJECT + " TEXT" + ")";

    private static final String USER_TABLE_CREATE = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_NAME + " TEXT, " +
            COLUMN_USER_POINTS + " INTEGER" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(SUBJECT_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    Cursor readSubjectData(){
        String query = "SELECT * FROM " + TABLE_SUBJECTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readQuestionData(){
        String query = "SELECT * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readUserData(){
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateRanking(int userId, int pointsToAdd){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_POINTS + " FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            int currentPoints = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_POINTS));
            cursor.close();

            // Incrementa os pontos
            int newPoints = currentPoints + pointsToAdd;

            // Atualiza os pontos no banco de dados
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_USER_POINTS, newPoints);

            db.update(TABLE_USERS, cv, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        }
    }
}
