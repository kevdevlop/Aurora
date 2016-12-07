package com.example.kevdev.aurora.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.List;

/**
 * Created by KevDev on 06/12/16.
 */
public class PlayerAdapter extends BaseAdapter {
    private Context context;
    private List<SongModel> items;
    private LayoutInflater inflador;
    TextView nombre, artista;

    public PlayerAdapter(Context context, List<SongModel> items) {
        this.inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongModel song = items.get(position);
        if (convertView == null){
            convertView = inflador.inflate(R.layout.player_songs, null);
        }

        nombre = (TextView) convertView.findViewById(R.id.nombre);
        artista = (TextView)convertView.findViewById(R.id.artista);

        nombre.setText(song.getNombre());
        artista.setText(song.getArtista());

        return convertView;
    }
}
