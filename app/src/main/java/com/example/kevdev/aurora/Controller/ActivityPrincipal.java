package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityPrincipal extends AppCompatActivity {
    Toolbar toolbar; //Encuentra música
    ListView lista; // Estilos predefinidos
    SearchView searchView; //proporciona la busqueda




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        final List<String> items = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.listViewSongs);
        //searchView = (SearchView) findViewById(R.id.searchView);
        ///////
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //int position[10000];
        for (int i = 1; i >= 0; i--) {
            DatabaseReference mensajeRef = ref.child(i + "/Genre");

            mensajeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String l = dataSnapshot.getValue(String.class);
                    items.add(l);
                    System.out.println(items.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

       // Collections.reverse(items);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this
                , android.R.layout.simple_list_item_1, items);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // La posición donde se hace clic en el elemento de lista se obtiene de la
                // la posición de parámetro de la vista de lista de Android
                int posicion = position;

                //obtengo el valor del string del elemento donde se hizo clic
                String itemValue = (String) lista.getItemAtPosition(position);

                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención
                Intent intent = new Intent(getApplicationContext(),ActivitySongList.class);
                intent.putExtra("Posicion", items.indexOf(items.get(posicion)));
                intent.putExtra("Genero",items.get(posicion));



                // Aquí pasaremos el parámetro de la intención creada previamente
                startActivity(intent);
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
                Toast.makeText(this, "custom", Toast.LENGTH_LONG ).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void searchAction(View v){
        Intent i = new Intent(this, ActivityResultados.class);
        startActivity(i);
    }

}
