package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.R;

import java.io.IOException;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityReproductor extends AppCompatActivity {
    private ImageView imagen;
    private TextView nombre, artista;
    private MediaPlayer player;
    private ImageButton btnPlayer;
    private Boolean pausa = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        //SongModel song = new SongModel("Enter Sadman","Metallica", "Rock", "Metallica",R.drawable.metallica);

        nombre = (TextView) findViewById(R.id.nombre);
        artista = (TextView) findViewById(R.id.artista);
        imagen = (ImageView) findViewById(R.id.imageSong);


        if(b!=null)
        {
            String nom =b.getString("nombre");
            nombre.setText(nom);
            String art = b.getString("artista");
            artista.setText(art);
            int imag = b.getInt("imagen");
            imagen.setImageResource(imag);
            imagen.setScaleType(ImageView.ScaleType.FIT_END);
        }

        configureImageButton();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void configureImageButton() {
        ImageButton btn = (ImageButton) findViewById(R.id.addList);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityReproductor.this, ActivityPlaylist.class);
                startActivity(i);
            }
        });



            btnPlayer = (ImageButton) findViewById(R.id.play);

            btnPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (player == null ) {
                        player = new MediaPlayer();

                        final String url = "" +
                                "https://firebasestorage.googleapis.com/v0/b/aurora-c5519.appspot.com/o/Songs%2F01%20Aguja%20(Original%20Mix).mp3?alt=media&token=c83d6f58-71df-4fce-9a95-f00ae5e0666f";

                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

                        try {
                            player.setDataSource(getApplicationContext(), Uri.parse(url));
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(getApplicationContext(), "URL incorrecta", Toast.LENGTH_SHORT).show();
                        } catch (SecurityException e) {
                            Toast.makeText(getApplicationContext(), "URL incorrecta", Toast.LENGTH_SHORT).show();
                        } catch (IllegalStateException e) {
                            Toast.makeText(getApplicationContext(), "URL incorrecta", Toast.LENGTH_SHORT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            player.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //player.prepareAsync();
                        btnPlayer.setBackgroundResource(R.drawable.ic_pause_circle_outline_24dp);
                        pausa = false;
                        player.start();



                    }else if (pausa){
                        btnPlayer.setBackgroundResource(R.drawable.ic_pause_circle_outline_24dp);
                        pausa = false;
                        player.start();


                    }else {
                        if (player != null && player.isPlaying()){
                            btnPlayer.setBackgroundResource(R.drawable.ic_play_circle_outline_24dp);
                            pausa = true;
                            player.pause();
                        }
                    }



                }
            });



    }

}
