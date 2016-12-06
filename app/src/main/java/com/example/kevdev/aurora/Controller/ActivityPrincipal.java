package com.example.kevdev.aurora.Controller;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kevdev.aurora.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 06/12/16.
 */
public class ActivityPrincipal extends AppCompatActivity {
    Toolbar toolbar;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        List<String> items = new ArrayList<>();
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        lista = (ListView) findViewById(R.id.listViewSongs);

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

}
