package com.example.littlehelper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayer extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
    }

    public void play(View v){
        if(mediaPlayer==null){
            mediaPlayer= MediaPlayer.create(this,R.raw.rain);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    stopPlayer();
                }
            });
        }
        mediaPlayer.start();
    }

    public void pause(View v){

        if(mediaPlayer!=null){
            mediaPlayer.pause();
        }
    }

    public void stop(View v){
        stopPlayer();

    }

    private void stopPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
            Toast.makeText(this,"Media player released", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onStop(){
        super.onStop();
        stopPlayer();
    }

    public void backtohome(View v){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}

