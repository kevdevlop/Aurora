package com.example.kevdev.aurora.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.kevdev.aurora.Adapters.AdapterPlaylist;
import com.example.kevdev.aurora.MainActivity;
import com.example.kevdev.aurora.Model.PlaylistModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KevDev on 07/12/16.
 */
public class ActivityPlaylist extends AppCompatActivity{
    Toolbar toolbar;
    private ListView lista;
    private FloatingActionButton actionButton;
    private DatabaseReference rootRef;
    private FirebaseUser userF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final AdapterPlaylist adapterPlaylist;

        final List<PlaylistModel> playlist = new ArrayList();

        lista = (ListView) findViewById(R.id.listViewSongs);

        actionButton = (FloatingActionButton) findViewById(R.id.addList);

        userF = FirebaseAuth.getInstance().getCurrentUser();

        final String userId = userF.getEmail();

        rootRef = FirebaseDatabase.getInstance().getReference().child("playlist");

        adapterPlaylist = new AdapterPlaylist(playlist,this);

        lista.setAdapter(adapterPlaylist);

        Query query = rootRef.orderByChild("ownerID").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playlist.removeAll(playlist);
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    PlaylistModel play = snap.getValue(PlaylistModel.class);
                    playlist.add(play);
                }
                adapterPlaylist.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

                                PlaylistModel playlistModel = new PlaylistModel();
                                playlistModel.setOwnerID(userId);
                                playlistModel.setNombre(input.getText().toString());
                                playlistModel.setSongs(null);

                                rootRef.child(input.getText().toString())
                                        .setValue(playlistModel);
                                playlist.add(playlistModel);
                            }

                        });

                adapterPlaylist.notifyDataSetChanged();

                alertDialog.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                alertDialog.show();

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
}
