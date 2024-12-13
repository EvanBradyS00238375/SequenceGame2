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

        Button playButton = findViewById(R.id.playButton);
        Button highScoresButton = findViewById(R.id.highScoresButton);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SequenceActivity.class);
            startActivity(intent);
        });

        highScoresButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HighScoresActivity.class);
            startActivity(intent);
        });
    }
}