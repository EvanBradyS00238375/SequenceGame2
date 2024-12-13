package com.example.sequencegame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private ListView scoresListView;
    private DatabaseHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        scoresListView = findViewById(R.id.scoresListView);
        dbHelper = new DatabaseHelper(this);

        displayTopScores();
    }

    private void displayTopScores() {
        List<String> topScores = dbHelper.getTopScores();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, topScores);
        scoresListView.setAdapter(adapter);
    }
}
