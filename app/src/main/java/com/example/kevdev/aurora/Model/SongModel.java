package com.example.kevdev.aurora.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by KevDev on 06/12/16.
 */
public class SongModel {
    private int id;
    private String nombre;
    private String artista;
    private String genero;
    private String album;
    private String URL;


    public SongModel() {

    }


    public SongModel(String nombre, String artista, String genero, String album){
        this.nombre = nombre;
        this.artista = artista;
        this.genero = genero;
        this.album = album;
     //   this.imagen = imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public String getAlbum() {
        return album;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAlbum(String album) {
        this.album = album;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
