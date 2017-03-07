package com.example.kevdev.aurora.Model;

/**
 * Created by KevDev on 06/12/16.
 */
public class SongModel {
    private String id;
    private String nombre;
    private String artista;
    private String genero;
    private String album;
    private int imagen;

    public SongModel() {

    }


    public SongModel(String nombre, String artista, String genero, String album, int imagen){
        this.nombre = nombre;
        this.artista = artista;
        this.genero = genero;
        this.album = album;
        this.imagen = imagen;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
