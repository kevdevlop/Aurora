package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kevdev.aurora.Adapters.AdapterPlaylist;
import com.example.kevdev.aurora.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 07/12/16.
 */
public class ActivityPlaylist extends AppCompatActivity{
    Toolbar toolbar;
    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdapterPlaylist adapterPlaylist;
        List<String> playlist = new ArrayList();
        lista = (ListView) findViewById(R.id.listViewSongs);

        playlist.add("Perreo");
        playlist.add("lo mejor del 2015");
        playlist.add("romanticas");
        playlist.add("para estudiar");

        adapterPlaylist = new AdapterPlaylist(playlist,this);
        lista.setAdapter(adapterPlaylist);


        

    }
}
