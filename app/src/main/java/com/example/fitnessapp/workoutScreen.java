package com.example.fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class workoutScreen extends AppCompatActivity implements View.OnClickListener {
    Button back_button;
    private int workoutCount = 0;
    private TextView tvWorkoutCount, tvSavedWorkouts;
    private EditText etWorkoutType;
    private ArrayList<String> savedWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(this);

        tvWorkoutCount = findViewById(R.id.tvWorkoutCount);
        tvSavedWorkouts = findViewById(R.id.tvSavedWorkouts);
        etWorkoutType = findViewById(R.id.etWorkoutType);

        Button btnIncrease = findViewById(R.id.btnIncrease);
        Button btnDecrease = findViewById(R.id.btnDecrease);
        Button btnSave = findViewById(R.id.btnSaveWorkout);

        savedWorkouts = new ArrayList<>();

        btnIncrease.setOnClickListener(view -> {
            workoutCount++;
            updateWorkoutDisplay();
        });

        btnDecrease.setOnClickListener(view -> {
            if (workoutCount > 0) {
                workoutCount--;
                updateWorkoutDisplay();
            }
        });

        btnSave.setOnClickListener(view -> {
            String type = etWorkoutType.getText().toString().trim();
            if (type.isEmpty()) {
                Toast.makeText(this, "Enter workout type", Toast.LENGTH_SHORT).show();
                return;
            }

            String entry = type + " - " + workoutCount + " session(s)";
            savedWorkouts.add(entry);
            displaySavedWorkouts();

            // Reset input fields
            etWorkoutType.setText("");
            workoutCount = 0;
            updateWorkoutDisplay();
        });
    }

    private void updateWorkoutDisplay() {
        tvWorkoutCount.setText(String.valueOf(workoutCount));
    }

    private void displaySavedWorkouts() {
        StringBuilder builder = new StringBuilder();
        for (String workout : savedWorkouts) {
            builder.append("• ").append(workout).append("\n");
        }
        tvSavedWorkouts.setText(builder.toString());
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
