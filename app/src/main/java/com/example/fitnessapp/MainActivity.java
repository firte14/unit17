package com.example.fitnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button timerScreen;
    Button waterScreen;
    Button workoutScreen;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerScreen = findViewById(R.id.timer);
        timerScreen.setOnClickListener(this);

        waterScreen = findViewById(R.id.water);
        waterScreen.setOnClickListener(this);

        workoutScreen = findViewById(R.id.workout);
        workoutScreen.setOnClickListener(this);




    }




@Override
    public void onClick(View v) {
        if (v.getId() == R.id.timer) {
            switchActivity();
        }

        else if (v.getId() == R.id.water) {
            switchActivity1();
        }

        else if (v.getId() == R.id.workout) {
            switchActivity2();
        }

    }

    private void switchActivity() {
        Intent switchActivityIntent = new Intent(this, timerScreen.class);
        startActivity(switchActivityIntent);

    }
    private void switchActivity1() {
        Intent switchActivityIntent = new Intent(this, waterScreen.class);
        startActivity(switchActivityIntent);
    }
    private void switchActivity2() {
        Intent switchActivityIntent = new Intent(this, workoutScreen.class);
        startActivity(switchActivityIntent);
    }

}