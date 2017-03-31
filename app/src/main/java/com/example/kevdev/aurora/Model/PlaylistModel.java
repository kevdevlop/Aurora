package com.example.kevdev.aurora.Model;

import android.util.Log;
import android.widget.Toast;

import com.example.kevdev.aurora.Controller.ActivitySongListGeneros;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by KevDev on 02/03/17.
 */
public class PlaylistModel {

    private String nombre;
    private String ownerID;
    private ArrayList<SongModel> songs = new ArrayList<SongModel>();
    private DatabaseReference rootRef;

    public PlaylistModel(){

    }
    public PlaylistModel(String nombre, String ownerID) {
        this.nombre = nombre;
        this.ownerID = ownerID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }


    public ArrayList<SongModel> getSongsbyGenero(String genero) {


        rootRef = FirebaseDatabase.getInstance().getReference().child("songs");

        //Realizamos el query para que nos devuelva sólo canciones con el genero seleccionado
        final Query query = rootRef.orderByChild("genero").equalTo(genero);

        ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {

            public ArrayList<SongModel> songlist = new ArrayList<SongModel>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Obtenemos el resultado de la BD
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                    //cada dato obtenido
                    // lo almacenamos en el modelo
                    SongModel song = snap.getValue(SongModel.class);

                    //Agregamos cada elemento a la lista
                    songlist.add(song);
                    Log.i("Dentro del for", songlist.toString());

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.i("Fuera del metodo", songs.toString());
        return songs;
    }

    public ArrayList<SongModel> getSongsbyPlaylistName(String playlistName){
        return songs;
    }

    public void setSongs(ArrayList<SongModel> songs) {
        this.songs = songs;
    }

    public ArrayList<SongModel> getSongs() {
        return songs;
    }



}
