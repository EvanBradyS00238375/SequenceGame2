package com.example.sequencegame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity {
    private Button blueButton, greenButton, redButton, yellowButton, playGameButton;
    private TextView directionTextView;

    private List<String> sequenceToFollow = new ArrayList<>();
    private Handler handler = new Handler();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        blueButton = findViewById(R.id.blue);
        greenButton = findViewById(R.id.green);
        redButton = findViewById(R.id.red);
        yellowButton = findViewById(R.id.yellow);
        playGameButton = findViewById(R.id.playgame);
        directionTextView = findViewById(R.id.tvDirection);

        playGameButton.setOnClickListener(v -> startGame());

        setColorButtonsEnabled(false);
    }

    private void startGame() {
        sequenceToFollow.clear();
        currentIndex = 0;

        generateSequence(3);

        showSequence();
    }

    private void generateSequence(int length) {
        String[] colors = {"blue", "green", "red", "yellow"};
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            sequenceToFollow.add(colors[random.nextInt(colors.length)]);
        }
    }

    private void showSequence() {
        directionTextView.setText("Watch the sequence");
        playGameButton.setEnabled(false);

        currentIndex = 0;
        showNextColor();
    }

    private void showNextColor() {
        if (currentIndex < sequenceToFollow.size()) {
            String color = sequenceToFollow.get(currentIndex);
            highlightButton(color);

            handler.postDelayed(() -> {
                resetButtonColors();
                currentIndex++;
                showNextColor();
            }, 1000);
        } else {
            handler.postDelayed(() -> {
                Intent intent = new Intent(SequenceActivity.this, GameActivity.class);
                intent.putStringArrayListExtra("sequence", new ArrayList<>(sequenceToFollow));
                startActivity(intent);
            }, 1000);
        }
    }

    private void highlightButton(String color) {
        resetButtonColors();

        switch (color) {
            case "blue":
                blueButton.setAlpha(0.5f);
                break;
            case "green":
                greenButton.setAlpha(0.5f);
                break;
            case "red":
                redButton.setAlpha(0.5f);
                break;
            case "yellow":
                yellowButton.setAlpha(0.5f);
                break;
        }
    }

    private void resetButtonColors() {
        blueButton.setAlpha(1.0f);
        greenButton.setAlpha(1.0f);
        redButton.setAlpha(1.0f);
        yellowButton.setAlpha(1.0f);
    }

    private void setColorButtonsEnabled(boolean enabled) {
        blueButton.setEnabled(enabled);
        greenButton.setEnabled(enabled);
        redButton.setEnabled(enabled);
        yellowButton.setEnabled(enabled);
    }
}
