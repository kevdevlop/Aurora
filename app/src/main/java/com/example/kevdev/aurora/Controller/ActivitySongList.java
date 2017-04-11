package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.AdapterPlaylist;
import com.example.kevdev.aurora.Adapters.AdapterSongsPlayList;
import com.example.kevdev.aurora.Adapters.ItemAdapter;
import com.example.kevdev.aurora.Adapters.PlayerAdapter;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KevDev on 08/12/16.
 */
public class ActivitySongList extends AppCompatActivity {
    Toolbar toolbar;
    ListView lista;
    TextView nombrePLay;
    private DatabaseReference rootRef;
    FirebaseUser userF;
    private HashMap<String,Boolean> songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);

        //recibir datos de vista anterior
        Intent in = getIntent();
        Bundle b = in.getExtras();

        String nombrePlaylist = null;

        if (b!= null){
            nombrePlaylist = b.getString("namePlaylist");
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ItemAdapter adapterPlaylist;
        final List<SongModel> songsList = new ArrayList();
        lista = (ListView) findViewById(R.id.listViewSongs);
        nombrePLay = (TextView) findViewById(R.id.nombrePLay);

        //se establece el nombre de la playlist que se selecciono
        nombrePLay.setText(nombrePlaylist);

        userF = FirebaseAuth.getInstance().getCurrentUser();

        final String userId = userF.getEmail();

        rootRef = FirebaseDatabase.getInstance().getReference().child("songs");

        Query query = rootRef.orderByChild("genero").equalTo("Dupstep");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                songsList.removeAll(songsList);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    SongModel song = snap.getValue(SongModel.class);
                    songsList.add(song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.i("MyAPP", songsList.toString());
        adapterPlaylist = new ItemAdapter(this,songsList);

        lista.setAdapter(adapterPlaylist);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongModel song = (SongModel) lista.getItemAtPosition(position);
                Log.i("NOmbre", song.getNombre());
                Log.i("URL", song.getURL());
                Log.i("Artista", song.getArtista());
                Intent i = new Intent(ActivitySongList.this, ActivityReproductor.class);
                i.putExtra("nombre", song.getNombre());
                i.putExtra("artista", song.getArtista());
                i.putExtra("url", song.getURL());
                i.putExtra("dataPlay", song.getGenero());
                i.putExtra("indexSong", position);
                //  i.putExtra("imagen", song.getImagen());
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                this.finish();
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
                Toast.makeText(this, "custom", Toast.LENGTH_LONG ).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
