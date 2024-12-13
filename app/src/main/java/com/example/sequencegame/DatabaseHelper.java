package com.example.sequencegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "high_scores.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SCORE + " INTEGER, " +
                COLUMN_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void insertScore(int score, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_DATE, date);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public List<String> getTopScores() {
        List<String> scoresList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCORES,
                new String[]{COLUMN_SCORE, COLUMN_DATE},
                null, null, null, null,
                COLUMN_SCORE + " DESC", "5");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                scoresList.add("Score: " + score + " - " + date);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return scoresList;
    }
}
