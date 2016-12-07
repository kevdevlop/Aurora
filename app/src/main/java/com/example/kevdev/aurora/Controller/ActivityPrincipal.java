package com.example.kevdev.aurora.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.kevdev.aurora.R;

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

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        lista.setAdapter(adapter);

    }

    public void searchAction(View v){
        Intent i = new Intent(this, ActivityResultados.class);
        startActivity(i);
    }

}
