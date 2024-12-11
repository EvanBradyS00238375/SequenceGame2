package com.example.sequencegame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView instructions = findViewById(R.id.tvDirection);
        instructions.setText("Tilt phone to match sequence!");

        // Retrieve the sequence from the intent
        List<String> sequence = getIntent().getStringArrayListExtra("sequence");

        if (sequence != null) {
            // Debugging: Print the sequence to check if it is received correctly
            for (String color : sequence) {
                System.out.println(color);
            }
        }
    }
}


