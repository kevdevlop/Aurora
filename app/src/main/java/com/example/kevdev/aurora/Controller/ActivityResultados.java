package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kevdev.aurora.Model.ItemAdapter;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityResultados extends AppCompatActivity {

    Toolbar toolbar;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        List<SongModel> songs = new ArrayList();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        lista = (ListView) findViewById(R.id.listViewSongs);

        songs.add(new SongModel("Enter Sadman","Metallica", "Rock", "Metallica",R.drawable.metallica));
        songs.add(new SongModel("That was just your life","Metallica", "Rock", "Death Magnetic",R.drawable.dead));
        songs.add(new SongModel("Frantic","Metallica", "Rock", "ST. Anger",R.drawable.metallica));
        songs.add(new SongModel("Batery","Metallica", "Rock", "Master of puppets",R.drawable.masterofpuppets));
        songs.add(new SongModel("Fade to Black","Metallica", "Rock", "Ride the Ligthing",R.drawable.ride));


        lista.setAdapter(new ItemAdapter(this,songs));
    }

}
