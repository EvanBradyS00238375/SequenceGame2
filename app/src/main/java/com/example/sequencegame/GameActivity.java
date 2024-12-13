package com.example.sequencegame;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private Button blueButton, greenButton, redButton, yellowButton;
    private TextView instructionTextView, scoreTextView;

    private List<String> originalSequence;
    private List<String> playerSequence = new ArrayList<>();
    private int currentSequenceIndex = 0;
    private int score = 0;

    private static final float TILT_THRESHOLD = 3.0f;
    private boolean isTilting = false;
    private long lastTiltTime = 0;
    private static final long TILT_COOLDOWN = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        blueButton = findViewById(R.id.blue);
        greenButton = findViewById(R.id.green);
        redButton = findViewById(R.id.red);
        yellowButton = findViewById(R.id.yellow);
        instructionTextView = findViewById(R.id.tvDirection);
        scoreTextView = findViewById(R.id.scoreTextView);

        originalSequence = getIntent().getStringArrayListExtra("sequence");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        instructionTextView.setText("Tilt phone to match sequence");
        updateScoreDisplay();

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        blueButton.setOnClickListener(v -> processPlayerInput("blue"));
        greenButton.setOnClickListener(v -> processPlayerInput("green"));
        redButton.setOnClickListener(v -> processPlayerInput("red"));
        yellowButton.setOnClickListener(v -> processPlayerInput("yellow"));
    }

    private void processPlayerInput(String color) {
        if (color.equals(originalSequence.get(currentSequenceIndex))) {
            playerSequence.add(color);
            currentSequenceIndex++;
            highlightButton(color);

            if (currentSequenceIndex == originalSequence.size()) {
                score++;
                updateScoreDisplay();
                startNextRound();
            }
        } else {
            gameOver();
        }
    }

    private void startNextRound() {
        playerSequence.clear();
        currentSequenceIndex = 0;

        Intent intent = new Intent(this, SequenceActivity.class);
        startActivity(intent);
        finish();
    }

    private void gameOver() {
        Toast.makeText(this, "Game Over! Score: " + score, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            if (!isTilting && (Math.abs(x) > TILT_THRESHOLD || Math.abs(y) > TILT_THRESHOLD)) {
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastTiltTime > TILT_COOLDOWN) {
                    processTiltInput(x, y);
                    lastTiltTime = currentTime;
                }
            }
        }
    }

    private void processTiltInput(float x, float y) {
        String tiltColor = determineTiltColor(x, y);
        if (tiltColor != null) {
            processPlayerInput(tiltColor);
        }
    }

    private String determineTiltColor(float x, float y) {
        if (Math.abs(x) > Math.abs(y)) {
            return x > 0 ? "red" : "blue";
        } else {
            return y > 0 ? "yellow" : "green";
        }
    }

    private void highlightButton(String color) {
        blueButton.setAlpha(1.0f);
        greenButton.setAlpha(1.0f);
        redButton.setAlpha(1.0f);
        yellowButton.setAlpha(1.0f);

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

    private void updateScoreDisplay() {
        scoreTextView.setText("Score: " + score);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}