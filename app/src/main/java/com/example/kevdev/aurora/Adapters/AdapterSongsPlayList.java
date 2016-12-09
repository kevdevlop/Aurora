package com.example.kevdev.aurora.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kevdev.aurora.Controller.ActivityReproductor;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.List;

/**
 * Created by KevDev on 08/12/16.
 */
public class AdapterSongsPlayList extends BaseAdapter {
    private Context context;
    private LayoutInflater inflador;
    private List<SongModel> songs;
    TextView nombre;

    public AdapterSongsPlayList(List<SongModel> songs, Context context) {
        this.inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return this.songs.size();
    }

    @Override
    public Object getItem(int position) {
        return this.songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflador.inflate(R.layout.songs_playlist_buttons, null);
        }

        nombre = (TextView) convertView.findViewById(R.id.nombre);
        nombre.setText(songs.get(position).getNombre());

        convertView.findViewById(R.id.imgDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Advertencia");
                alertDialog.setMessage("¿Estas seguro de eliminar: " + songs.get(position).getNombre() + " ?");


                alertDialog.setIcon(R.drawable.ic_delete_24dp);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                songs.remove(position);
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

        convertView.findViewById(R.id.nombre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongModel song = (SongModel) songs.get(position);

                Intent i = new Intent(v.getContext(), ActivityReproductor.class);
                i.putExtra("nombre", song.getNombre());
                i.putExtra("artista", song.getArtista());
                i.putExtra("imagen", song.getImagen());
                v.getContext().startActivity(i);
            }
        });

        return convertView;
    }
}
