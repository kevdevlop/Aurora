package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityPrincipal extends AppCompatActivity {
    Toolbar toolbar;
    ListView lista;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        List<String> items = new ArrayList<>();
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.listViewSongs);
        searchView = (SearchView) findViewById(R.id.searchView);
        items.add("Pop");
        items.add("Rock");
        items.add("Electronica");
        items.add("Banda");
        items.add("Blues");
        items.add("Hip-hop");
        items.add("Reaggue");

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this
                , android.R.layout.simple_list_item_1, items);

        lista.setAdapter(adapter);


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
