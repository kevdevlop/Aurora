package com.example.kevdev.aurora.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.AdapterAddPlaylist;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityReproductor extends AppCompatActivity {
   // private ImageView imagen;
    private TextView nombre, artista;
    private MediaPlayer player;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private ImageButton btnPlayer;
    private ImageButton btnAddSong;
    private Boolean pausa = true;
    private ImageView imagen;
    private String nom;
    private String url;
    FirebaseUser userF;
    private DatabaseReference rootRef;
    private int currentSongIndex = 0;
    private String dataPlay;
    private List<SongModel> songlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        //Se obtiene la referencia del boton play
        btnPlayer = (ImageButton) findViewById(R.id.play);
        //se configura el boton para añadir una cancion a la playlist
        btnAddSong = (ImageButton) findViewById(R.id.addList);
        btnNext = (ImageButton) findViewById(R.id.next);
        btnPrev = (ImageButton) findViewById(R.id.previous);
        nombre = (TextView) findViewById(R.id.nombre);
        artista = (TextView) findViewById(R.id.artista);
        imagen = (ImageView) findViewById(R.id.imageSong);

        Log.i("Gnero", b.getString("dataPlay"));
        dataPlay = b.getString("dataPlay");
        Log.i("Indice", String.valueOf(b.getInt("indexSong")));
        currentSongIndex = b.getInt("indexSong");

        songlist = new ArrayList<>();

        player = new MediaPlayer();

        rootRef = FirebaseDatabase.getInstance().getReference().child("songs");

        //Realizamos el query para que nos devuelva sólo canciones con el genero seleccionado
        Query query = rootRef.orderByChild("genero").equalTo(dataPlay);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //Obtenemos el resultado de la BD
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        //cada dato obtenido
                        // lo almacenamos en el modelo
                        SongModel song = snap.getValue(SongModel.class);

                        //Agregamos cada elemento a la lista
                        songlist.add(song);
                        Log.i("Dentro del for", songlist.toString());

                    }

                }else {


                }
                Log.i("Fuera del for", songlist.toString());
                stratPlay(currentSongIndex,songlist);
                configureImageButton(songlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Error en DB", databaseError.getMessage());
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            currentSongIndex = data.getExtras().getInt("indexSong");
            dataPlay = data.getExtras().getString("dataPlay");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void configureImageButton(final List<SongModel> listsong) {

        //se agrega un escucha al boton
        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Establecemos un nueva vista para inflarla con el layout del dialog
                View mview = getLayoutInflater().inflate(R.layout.dialog_addplaylist, null);

                //creamos un builder
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ActivityReproductor.this);

                //se configura el edittext del nombre de la playlist
                final EditText mPlaylist =  (EditText) mview.findViewById(R.id.editTextCreate);

                //se crea la referencia hacia la BD
                userF = FirebaseAuth.getInstance().getCurrentUser();

                //Obtenemos el email como id
                final String userId = userF.getEmail();

                //obtenemos la referecncia hacia el nodo de playlist
                rootRef = FirebaseDatabase.getInstance().getReference().child("playlist");

                //Creamos un adaptador para agregar la playlist
                final ArrayAdapter<String> adapterAddPlaylist;
                adapterAddPlaylist = new ArrayAdapter<String>(ActivityReproductor.this
                        , android.R.layout.select_dialog_item);

                //Se agrega un escucha al boton crear playlist
                mview.findViewById(R.id.buttonCreate)
                        .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Se crea un modelo playlist para guardar los datos
                        PlaylistModel playlistModel = new PlaylistModel();
                        playlistModel.setOwnerID(userId);
                        playlistModel.setNombre(mPlaylist.getText().toString());
                        playlistModel.setSongs(null);

                        //Se insertan los datos en la BD
                        rootRef.child(mPlaylist.getText().toString())
                                .setValue(playlistModel);

                        //Agregamos el nombre de la playlist al adaptador
                        adapterAddPlaylist.add(playlistModel.getNombre());
                        //Se notifica al adaptador que hubo un cambio
                        adapterAddPlaylist.notifyDataSetChanged();
                    }
                });
                //Se agrega un boton para cancelar al dialog
                mBuilder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Se hace la consulta a la BD
                Query query = rootRef.orderByChild("ownerID").equalTo(userId);

                //se establece  el adaptador al builder del dialog
                mBuilder.setAdapter(adapterAddPlaylist, new DialogInterface.OnClickListener() {
                    //se utiliza un escucha para la playlist que seleccionamos
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = adapterAddPlaylist.getItem(which);
                        Map<String,Boolean>  map = new HashMap<String, Boolean>();
                        Map<String, Object> childUpdates = new HashMap<>();

                        map.put(nom,true);
                        //childUpdates.put("/" + name + "/songs",map);
                        //rootRef.updateChildren(childUpdates);
                        rootRef.child(name).child("songs").setValue(map);
                        Toast.makeText(ActivityReproductor.this, "Agregada a: "+ name, Toast.LENGTH_LONG).show();
                    }
                });

                //Obtenemos los datos que nos regresa la consulta para mostrar las playlist
                //del usuario
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Se van agregando los datos al modelo playlist
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            PlaylistModel play = snap.getValue(PlaylistModel.class);
                            Log.i("ActivityRepro", snap.getValue().toString());
                            //Se agrega el modelo al adaptador
                            adapterAddPlaylist.add(play.getNombre());
                        }
                        //Se notifica de los cambios
                        adapterAddPlaylist.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    // en caso de error en la extraccion de los datos
                    }
                });

                //Se agrega la vista al builder del dialog
                mBuilder.setView(mview);

                //se crea el dialog
                AlertDialog dialog = mBuilder.create();

                //se muestra el dialog
                dialog.show();

            }
        });


            //se agrega un escucha al boton
        btnPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (player.isPlaying()){
                        if (player!= null){
                            player.pause();

                            btnPlayer.setBackgroundResource(R.drawable.ic_play_circle_outline_24dp);
                        }
                    }else {

                        if (player!= null){
                            player.start();

                            btnPlayer.setBackgroundResource(R.drawable.ic_pause_circle_outline_24dp);
                        }
                    }

                }

        });
        

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex < (listsong.size())-1){
                    stratPlay(currentSongIndex + 1, listsong);
                    currentSongIndex ++;
                }else {
                    stratPlay(0, listsong);
                    currentSongIndex = 0;
                }
            }
        });
        

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSongIndex > 0 ){
                    stratPlay(currentSongIndex - 1 , listsong);
                    currentSongIndex --;
                }else {
                    stratPlay(listsong.size() - 1 , listsong);
                    currentSongIndex = listsong.size() - 1;
                }
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(currentSongIndex < (listsong.size() - 1)){
                    stratPlay(currentSongIndex + 1, listsong);
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    // play first song
                    stratPlay(0 , listsong);
                    currentSongIndex = 0;
                }
            }
        });



    }

    private void stratPlay(int songIndex, List<SongModel> listsong) {


            //intentamos establecer los recursos de la url de la cancion al reproductor
            //y obtenemos cualquier error que pueda ocurrir
            try {
                player.reset();
                player.setDataSource(getApplicationContext()
                        , Uri.parse(listsong.get(songIndex).getURL()));
                player.prepare();

                //Se cambia el boton a pausa
                btnPlayer.setBackgroundResource(R.drawable.ic_pause_circle_outline_24dp);

                //indicamos que ya se empezó a reproducir
                pausa = false;

                //inicia la reproduccion
                player.start();

                if (listsong.get(songIndex).getImagen() != null){
                    //Establecemos la imagen de la cancion con ayuda de la clase DownloadImageTask
                    new DownloadImageTask(imagen).execute(listsong.get(songIndex).getImagen());
                }

                //Establecemos el nombre de la cancion
                String nombreSong = listsong.get(songIndex).getNombre();
                nombre.setText(nombreSong);

                //Establecemos el nombre del artista
                String artistaSong = listsong.get(songIndex).getArtista();
                artista.setText(artistaSong);

                //Manejamos las excepciones que nos pudiera arrojar
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext()
                        , "URL incorrecta", Toast.LENGTH_SHORT).show();
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext()
                        , "URL incorrecta", Toast.LENGTH_SHORT).show();
            } catch (IllegalStateException e) {
                Toast.makeText(getApplicationContext()
                        , "URL incorrecta", Toast.LENGTH_SHORT);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    //Esta clase nos sirve para descargar la imagen desde la url en segundo plano
    //Esto para no interferir con el UI Thread
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("ErrorImage", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}



