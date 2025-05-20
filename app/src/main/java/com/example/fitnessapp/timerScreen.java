package com.example.fitnessapp;

import android.content.SharedPreferences;
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
import java.util.TimerTask;

public class timerScreen extends AppCompatActivity implements View.OnClickListener {
    private Button back_button, reset_button, timer_button;
    private TextView timer_txt, total_time_txt, saved_time_txt;

    private boolean pause = true;
    private int count = 0; // session timer
    private int savedSeconds = 0;
    private boolean sessionSaved = false;

    private Timer timer;

    private SharedPreferences preferences;
    private static final String PREFS_NAME = "WorkoutPrefs";
    private static final String SAVED_TIME_KEY = "SavedWorkoutTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back_button = findViewById(R.id.back_button);
        reset_button = findViewById(R.id.reset_button);
        timer_button = findViewById(R.id.timer_button);
        timer_txt = findViewById(R.id.timer_text);
        total_time_txt = findViewById(R.id.total_time_text);
        saved_time_txt = findViewById(R.id.saved_time_text);

        back_button.setOnClickListener(this);
        reset_button.setOnClickListener(this);
        timer_button.setOnClickListener(this);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        savedSeconds = preferences.getInt(SAVED_TIME_KEY, 0);
        updateSavedTimeDisplay();
        updateTimerDisplay();

        start_timer();
    }

    private void start_timer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(() -> {
                    if (!pause) {
                        count++;
                        updateTimerDisplay();
                    }
                });
            }
        }, 0, 1000);
    }

    private void updateTimerDisplay() {
        String timeFormatted = formatTime(count);
        timer_txt.setText(timeFormatted);
        total_time_txt.setText("Current Session: " + timeFormatted);
    }

    private void updateSavedTimeDisplay() {
        int total = savedSeconds + count;
        saved_time_txt.setText("Total Workout Time Saved: " + formatTime(total));
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void saveSessionTime() {
        if (!sessionSaved && count > 0) {
            savedSeconds += count;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(SAVED_TIME_KEY, savedSeconds);
            editor.apply();
            sessionSaved = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == back_button) {
            if (!pause) {
                pause = true;
            }
            saveSessionTime();
            finish();
        } else if (view == timer_button) {
            pause = !pause;
            if (pause) {
                saveSessionTime();
                updateSavedTimeDisplay();
            } else {
                sessionSaved = false;
            }
        } else if (view == reset_button) {
            pause = true;
            saveSessionTime();
            count = 0;
            sessionSaved = false;
            updateTimerDisplay();
            updateSavedTimeDisplay();
        }
    }
}