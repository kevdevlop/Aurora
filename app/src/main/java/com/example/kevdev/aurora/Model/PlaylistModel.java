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
