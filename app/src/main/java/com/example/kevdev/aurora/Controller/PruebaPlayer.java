package com.example.kevdev.aurora.Controller;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kevdev.aurora.R;

import java.io.IOException;

/**
 * Created by KevDev on 01/03/17.
 */
public class PruebaPlayer extends AppCompatActivity {
    private Button play;
    private Button stop;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_prueba);
        player = new MediaPlayer();


        final String url = "https://firebasestorage.googleapis.com/v0/b/aurora-c5519.appspot.com/o/Songs%2FSexo%20A%20Lo%20Rudo%20%5BUrbanaNew.net%5D.mp3?alt=media&token=8474922e-0956-4adf-aff5-f7ff126ad86b";

        play = (Button )findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    player.setDataSource(url);
                }catch (IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(),"URL incorrecta",Toast.LENGTH_SHORT).show();
                }catch (SecurityException e){
                    Toast.makeText(getApplicationContext(),"URL incorrecta",Toast.LENGTH_SHORT).show();
                }catch (IllegalStateException e){
                    Toast.makeText(getApplicationContext(), "URL incorrecta", Toast.LENGTH_SHORT);
                }catch (IOException e) {
                    e.printStackTrace();
                }

                try{
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (IllegalArgumentException e){
                    Toast.makeText(getApplicationContext(), "URL incorrecta", Toast.LENGTH_SHORT);
                }
                player.start();
            }
        });
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null && player.isPlaying()){
                    player.pause();
                }
            }
        });



    }
}
