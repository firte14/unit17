package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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

public class waterScreen extends AppCompatActivity implements View.OnClickListener {
    private Button back_button, add_water_button, reset_water_button;
    private EditText input_water_ml;
    private TextView water_count_text, water_ml_text, goal_text;
    private int waterCount = 0; // in cups
    private int totalMl = 0; // in ml

    private SharedPreferences preferences;
    private static final String PREFS_NAME = "WaterPrefs";
    private static final String WATER_COUNT_KEY = "WaterCount";
    private static final String TOTAL_ML_KEY = "TotalMl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_water_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        back_button = findViewById(R.id.back_button);
        add_water_button = findViewById(R.id.add_water_button);
        reset_water_button = findViewById(R.id.reset_water_button);
        input_water_ml = findViewById(R.id.input_water_ml);
        water_count_text = findViewById(R.id.water_count_text);
        water_ml_text = findViewById(R.id.water_ml_text);
        goal_text = findViewById(R.id.goal_text);

        // Set listeners
        back_button.setOnClickListener(this);
        add_water_button.setOnClickListener(this);
        reset_water_button.setOnClickListener(this);

        // Load saved data
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        waterCount = preferences.getInt(WATER_COUNT_KEY, 0);
        totalMl = preferences.getInt(TOTAL_ML_KEY, 0);
        updateDisplay();
    }

    private void updateDisplay() {
        // Update UI with current values
        water_count_text.setText("Cups Drunk: " + waterCount);
        water_ml_text.setText("Total Water: " + totalMl + " ml");
        goal_text.setText("Goal: 2000 ml (8 Cups) - " + totalMl + " / 2000 ml");
    }

    private void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(WATER_COUNT_KEY, waterCount);
        editor.putInt(TOTAL_ML_KEY, totalMl);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if (v == back_button) {
            finish();
        } else if (v == add_water_button) {
            String inputText = input_water_ml.getText().toString();
            if (!inputText.isEmpty()) {
                int inputMl = Integer.parseInt(inputText);
                totalMl += inputMl;
                waterCount = totalMl / 250; // 1 cup = 250ml
                updateDisplay();
                saveData();
                input_water_ml.setText("");
            } else {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            }
        } else if (v == reset_water_button) {
            totalMl = 0;
            waterCount = 0;
            updateDisplay();
            saveData();
        }
    }
}