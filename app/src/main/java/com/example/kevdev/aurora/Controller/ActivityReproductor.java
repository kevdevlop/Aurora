package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.EventListener;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityReproductor extends AppCompatActivity{
    ImageView imagen;
    TextView nombre, artista;
    ImageButton play;
    ImageButton prev;
    ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        SongModel song = new SongModel("Enter Sadman","Metallica", "Rock", "Metallica",R.drawable.metallica);

        nombre = (TextView) findViewById(R.id.nombre);
        artista = (TextView) findViewById(R.id.artista);
        imagen = (ImageView) findViewById(R.id.imageSong);


        if(b!=null)
        {
            String nom =(String) b.get("nombre");
            nombre.setText(nom);
            String art =(String) b.get("artista");
            artista.setText(art);
            int imag =(int) b.get("imagen");
            imagen.setImageResource(imag);
            imagen.setScaleType(ImageView.ScaleType.FIT_END);
        }



    }



}
