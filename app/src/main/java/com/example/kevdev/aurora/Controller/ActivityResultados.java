package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.ItemAdapter;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;

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
/*
        songs.add(new SongModel("Enter Sadman","Metallica", "Rock", "Metallica",R.drawable.metallica));
        songs.add(new SongModel("That was just your life","Metallica", "Rock", "Death Magnetic",R.drawable.dead));
        songs.add(new SongModel("Frantic","Metallica", "Rock", "ST. Anger",R.drawable.metallica));
        songs.add(new SongModel("Batery","Metallica", "Rock", "Master of puppets",R.drawable.masterofpuppets));
        songs.add(new SongModel("Fade to Black","Metallica", "Rock", "Ride the Ligthing",R.drawable.ride));
**/

        lista.setAdapter(new ItemAdapter(this, songs));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongModel song = (SongModel)lista.getItemAtPosition(position);

                Intent i = new Intent(ActivityResultados.this,ActivityReproductor.class);
                i.putExtra("nombre",song.getNombre());
                i.putExtra("artista", song.getArtista());
                i.putExtra("imagen", song.getImagen());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                this.finish();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            case R.id.profile:
                Intent ip = new Intent(this, ActivityProfile.class);
                startActivity(ip);
                return true;
            case R.id.playList:
                Intent i2 = new Intent(this,ActivityPlaylist.class);
                startActivity(i2);
                return true;
            case R.id.custom:
                Toast.makeText(this, "custom", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
