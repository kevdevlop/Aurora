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
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.PlayerAdapter;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 15/03/17.
 */
public class ActivitySongListGeneros extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lista;
    private TextView nombrePLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);
        Intent in = getIntent();
        Bundle b = in.getExtras();


        setSupportActionBar(toolbar);

        PlayerAdapter adapterPlaylist;
        final List<SongModel> songsList = new ArrayList();
        lista = (ListView) findViewById(R.id.listViewSongs);
        nombrePLay = (TextView) findViewById(R.id.nombrePLay);

        //se establece el nombre de la playlist que se selecciono
        nombrePLay.setText(b.getString("namePlaylist"));




        //imageView.setImageBitmap(bmp);
        songsList.add(new SongModel("Privado","Rvssian ft. Arcangel, Nicky Jam, Farruko, Konshens", "Trap", "Rvssian" ));
        songsList.add(new SongModel("Enter Sadman","Metallica", "Rock", "Metallica"));
       /* songsList.add(new SongModel("That was just your life","Metallica", "Rock", "Death Magnetic",R.drawable.dead));
        songsList.add(new SongModel("Frantic","Metallica", "Rock", "ST. Anger",R.drawable.metallica));
        songsList.add(new SongModel("Batery","Metallica", "Rock", "Master of puppets",R.drawable.masterofpuppets));
        songsList.add(new SongModel("Fade to Black","Metallica", "Rock", "Ride the Ligthing",R.drawable.ride));**/

        adapterPlaylist = new PlayerAdapter(this,songsList);
        lista.setAdapter(adapterPlaylist);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongModel song = (SongModel) lista.getItemAtPosition(position);

                Intent i = new Intent(ActivitySongListGeneros.this, ActivityReproductor.class);
                i.putExtra("nombre", song.getNombre());
                i.putExtra("artista", song.getArtista());
                //  i.putExtra("imagen", song.getImagen());
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        /*MenuItem item = menu.getItem(R.id.profile);
        item.setTitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());*/
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
