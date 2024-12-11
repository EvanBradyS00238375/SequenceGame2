package com.example.sequencegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons by their IDs
        Button playButton = findViewById(R.id.playButton);
        Button highScoresButton = findViewById(R.id.highScoresButton);

        // Set click listener for Play button
        playButton.setOnClickListener(v -> {
            // Create an Intent to start SequenceActivity
            Intent intent = new Intent(MainActivity.this, SequenceActivity.class);
            startActivity(intent);
        });

        // Set click listener for High Scores button
        highScoresButton.setOnClickListener(v -> {
            // Create an Intent to start HighScoresActivity
            Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
            startActivity(intent);
        });
    }
}