package com.example.fitnessapp;

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
    Button back_button;
    Button reset_button;

    TextView timer_txt ;
    Button timer_button;
    Boolean pause = true;
    int count = 0;
    Timer timer;


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
        back_button.setOnClickListener(this);

        reset_button = findViewById(R.id.reset_button);
        reset_button.setOnClickListener(this);


        timer_txt = findViewById(R.id.timer_text);
        timer_button = findViewById(R.id.timer_button);
        timer_button.setOnClickListener(this);
        start_timer();


    }

    public void start_timer(){
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if (!pause){
                            count = count+1;
                            timer_txt.setText(Integer.toString(count));
                        }

                    }
                });
            }
        },0,1000);

    }

    @Override
    public void onClick(View view) {
        if (view == back_button) {
            finish();
        } else if (view == timer_button) {
            pause = !pause;
        } else if (view == reset_button) {

            pause = true;
            count = 0;
            timer_txt.setText(Integer.toString(count));
            start_timer();
        }
    }

}