package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.ItemAdapter;
import com.example.kevdev.aurora.Adapters.PlayerAdapter;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.PlaylistModel;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 15/03/17.
 */
public class ActivitySongListGeneros extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lista;
    private TextView nombrePLay;
    private ItemAdapter adapterPlaylist;
    private FirebaseUser userF;
    private DatabaseReference rootRef;
    private ArrayList<SongModel> songsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Se obtiene la info del intent anterior
        Intent in = getIntent();

        //Se guarda lo obtenido del intent anterior
        Bundle b = in.getExtras();


        String genero = null;

        //Recibimos el dato de la view anterior
        if ( b != null ) {
            genero =  b.getString("Genero");
        }

        //Creamos una lista de las canciones que vamos a obtener de la BD
        songsList = new ArrayList();

        lista = (ListView) findViewById(R.id.listViewSongs);
        nombrePLay = (TextView) findViewById(R.id.nombrePLay);

        //se establece el nombre de la playlist que se selecciono
        nombrePLay.setText(b.getString("Genero"));

        //Se obtienen los datos de la BD
        userF = FirebaseAuth.getInstance().getCurrentUser();

        rootRef = FirebaseDatabase.getInstance().getReference().child("songs");

        /* ArrayList<SongModel> songListData = new ArrayList<SongModel>();

        PlaylistModel plm = new PlaylistModel();

        this.songsList = plm.getSongsbyGenero(genero);

        for (int i = 0 ; i < songsList.size(); i++){
            SongModel song = songsList.get(i);

            songListData.add(song);
        }*/

        //Verificamos si se obtuvieron los datos
        /*if (songsList.isEmpty()) {
            //Notificamos en caso de no obtenerlos
            Toast.makeText(ActivitySongListGeneros.this
                    ,"Lo siento no hay canciones de este genero :S"
                    , Toast.LENGTH_LONG).show();
        }*/

        //Se crea el adaptador para mostrar las canciones
        adapterPlaylist = new ItemAdapter(this,songsList);

        lista.setAdapter(adapterPlaylist);

        //Realizamos el query para que nos devuelva sólo canciones con el genero seleccionado
        Query query = rootRef.orderByChild("genero").equalTo(genero);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                songsList.removeAll(songsList);
                //Obtenemos el resultado de la BD
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    //cada dato obtenido lo almacenamos en el modelo
                    SongModel song = snap.getValue(SongModel.class);

                    //Agregamos cada elemento a la lista
                    songsList.add(song);
                }

                //Verificamos si se obtuvieron los datos
                if (songsList.isEmpty()) {
                    //Notificamos en caso de no obtenerlos
                    Toast.makeText(ActivitySongListGeneros.this
                            ,"Lo siento no hay canciones de este genero :S"
                            , Toast.LENGTH_LONG).show();
                }

                //Notificamos al adaptador que hubo un cambio
                adapterPlaylist.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Añadmos un escucha a los elementos de la lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Al hacer click realiza una accion
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Se obtiene el elemento que se lecciono de la lista
                SongModel song = (SongModel) lista.getItemAtPosition(position);

                //Se crea un intent para especificar a que view se va a ir
                Intent i = new Intent(ActivitySongListGeneros.this, ActivityReproductor.class);

                //Se mandan los datos a la siguiente view
                i.putExtra("nombre", song.getNombre());
                i.putExtra("artista", song.getArtista());
                i.putExtra("url", song.getURL());
                i.putExtra("dataPlay", song.getGenero());
                i.putExtra("indexSong",position);
                //  i.putExtra("imagen", song.getImagen());


                //Se crea la nueva view
                startActivityForResult(i,100);
                finish();
            }
        });

    }

    //Se crean la opciones del menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Se crea un escucha en el menu
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
