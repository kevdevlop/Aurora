package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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


////////////////////////////////////////////////////////////////////
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


//////////////////////////////////////////////////////////////////////////////////////////
        AdapterSongsPlayList adapterPlaylist;
        final List<SongModel> songsList = new ArrayList();
        lista = (ListView) findViewById(R.id.listViewSongs);
        nombrePLay = (TextView) findViewById(R.id.nombrePLay);
        //Drawable d;
        nombrePLay.setText(b.getString("Genero"));
       /* Bitmap bmp = null;
        URL url = null;
        try {
            url = new URL("https://firebasestorage.googleapis.com/v0/b/aurora-c5519.appspot.com/o/Songs%2Fimagen%2F1.jpg?alt=media&token=80ed7bb0-2689-4ce4-bf5f-9ff2831f7794");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        int genero = b.getInt("Posicion");
        for (int i=0; i<1; i++){    //necesito doble ciclo para crear lista de cancones
            DatabaseReference mensajeRef = ref.child(genero+"/albums/"+i+"/songs/0/title");

            mensajeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String l= dataSnapshot.getValue(String.class);
                    songsList.add(new SongModel(l,"Rvssian ft. Arcangel, Nicky Jam, Farruko, Konshens", "Trap", "Rvssian" ));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        //imageView.setImageBitmap(bmp);
        //songsList.add(new SongModel("Privado","Rvssian ft. Arcangel, Nicky Jam, Farruko, Konshens", "Trap", "Rvssian" ));
        //songsList.add(new SongModel("Enter Sadman","Metallica", "Rock", "Metallica"));
       /* songsList.add(new SongModel("That was just your life","Metallica", "Rock", "Death Magnetic",R.drawable.dead));
        songsList.add(new SongModel("Frantic","Metallica", "Rock", "ST. Anger",R.drawable.metallica));
        songsList.add(new SongModel("Batery","Metallica", "Rock", "Master of puppets",R.drawable.masterofpuppets));
        songsList.add(new SongModel("Fade to Black","Metallica", "Rock", "Ride the Ligthing",R.drawable.ride));**/

        adapterPlaylist = new AdapterSongsPlayList(songsList,this);
        lista.setAdapter(adapterPlaylist);

    }
}
