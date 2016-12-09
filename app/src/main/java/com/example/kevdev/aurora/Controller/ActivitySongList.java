package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.AdapterPlaylist;
import com.example.kevdev.aurora.Adapters.AdapterSongsPlayList;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 08/12/16.
 */
public class ActivitySongList extends AppCompatActivity {
    Toolbar toolbar;
    ListView lista;
    TextView nombrePLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);

        //recibir datos de vista anterior
        Intent in = getIntent();
        Bundle b = in.getExtras();

        List<SongModel> songsList = new ArrayList();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = null;

                switch (item.getItemId()) {
                    case R.id.profile:
                        Toast.makeText(ActivitySongList.this, "Ir a perfil de usuario", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.playList:
                        i = new Intent(ActivitySongList.this,ActivityPlaylist.class);
                        startActivity(i);
                        break;
                    case  R.id.custom:
                        Toast.makeText(ActivitySongList.this, "Ir a perfil de settings", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logout:
                        ActivitySongList.this.finish();
                        i = new Intent(ActivitySongList.this, MainActivity.class);
                        startActivity(i);
                        break;

                }

                return true;
            }
        });
        toolbar.inflateMenu(R.menu.main);

        AdapterSongsPlayList adapterPlaylist;

        lista = (ListView) findViewById(R.id.listViewSongs);
        nombrePLay = (TextView) findViewById(R.id.nombrePLay);

        nombrePLay.setText(b.getString("namePlaylist"));

        songsList.add(new SongModel("Enter Sadman","Metallica", "Rock", "Metallica",R.drawable.metallica));
        songsList.add(new SongModel("That was just your life","Metallica", "Rock", "Death Magnetic",R.drawable.dead));
        songsList.add(new SongModel("Frantic","Metallica", "Rock", "ST. Anger",R.drawable.metallica));
        songsList.add(new SongModel("Batery","Metallica", "Rock", "Master of puppets",R.drawable.masterofpuppets));
        songsList.add(new SongModel("Fade to Black","Metallica", "Rock", "Ride the Ligthing",R.drawable.ride));

        adapterPlaylist = new AdapterSongsPlayList(songsList,this);
        lista.setAdapter(adapterPlaylist);

    }
}
