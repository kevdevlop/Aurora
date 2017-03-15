package com.example.kevdev.aurora.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.AdapterPlaylist;
import com.example.kevdev.aurora.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 07/12/16.
 */
public class ActivityPlaylist extends AppCompatActivity{
    Toolbar toolbar;
    ListView lista;
    FloatingActionButton actionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdapterPlaylist adapterPlaylist;
        final List<String> playlist = new ArrayList();
        lista = (ListView) findViewById(R.id.listViewSongs);
        actionButton = (FloatingActionButton) findViewById(R.id.addList);

        playlist.add("Perreo");
        playlist.add("lo mejor del 2015");
        playlist.add("romanticas");
        playlist.add("para estudiar");

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Crear playlist");

                final EditText input = new EditText(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_add);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                playlist.add(input.getText().toString());

                            }
                        });

                alertDialog.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

            }

        });

        adapterPlaylist = new AdapterPlaylist(playlist,this);
        lista.setAdapter(adapterPlaylist);

        

    }
}
