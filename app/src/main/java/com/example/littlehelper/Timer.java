package com.example.littlehelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Timer extends AppCompatActivity {


    private static final long startInMillis = 1500000 ;
    private TextView mTextViewCountdown;
    private Button mbuttonStartPause, mbuttonReset, backtohome;

    private CountDownTimer countDownTimer;
    private boolean mTimerrunning;
    private long mTimeLeftInMillis = startInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTextViewCountdown = findViewById(R.id. text_view_countdown);
        mbuttonStartPause = findViewById(R.id. start_button);
        mbuttonReset = findViewById(R.id. reset_button);
        backtohome = findViewById(R.id. backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mbuttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mTimerrunning){
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });

        mbuttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetTimer();
            }
        });
        updateCountDownText();

    }
        private void resetTimer() {
            mTimeLeftInMillis = startInMillis;
            updateCountDownText();
            mbuttonReset.setVisibility(View.INVISIBLE);
            mbuttonStartPause.setVisibility(View.VISIBLE);

        }

        private void startTimer() {
            countDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisLeftTillFinish) {
                    mTimeLeftInMillis=millisLeftTillFinish;
                    updateCountDownText();
                }
                @Override
                public void onFinish() {
                    mTimerrunning = false;
                    mbuttonStartPause.setText("Start");
                    mbuttonStartPause.setVisibility(View.INVISIBLE);
                    mbuttonReset.setVisibility(View.VISIBLE);
                }
            }.start();
            mTimerrunning=true;
            mbuttonStartPause.setText("Pause");
            mbuttonReset.setVisibility(View.INVISIBLE);
        }
    private void updateCountDownText() {
        int minute = (int)(mTimeLeftInMillis/1000)/60;
        int seconds = (int)(mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minute,seconds);
        mTextViewCountdown.setText(timeLeftFormatted);
    }
    private void pauseTimer() {
        countDownTimer.cancel();
        mTimerrunning = false;
        mbuttonStartPause.setText("Start");
        mbuttonReset.setVisibility(View.VISIBLE);

        }
}