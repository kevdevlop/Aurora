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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Controller.ActivitySongList;
import com.example.kevdev.aurora.R;

import java.util.List;

/**
 * Created by KevDev on 07/12/16.
 */
public class AdapterPlaylist extends BaseAdapter {
    private Context context;
    private LayoutInflater inflador;
    private List<String> items;
    TextView nombre;

    public AdapterPlaylist(List<String> items, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflador.inflate(R.layout.playlist_buttons, null);
        }

        nombre = (TextView) convertView.findViewById(R.id.nombre);
        nombre.setText(items.get(position));

        //escucha del boton editar playlist
        convertView.findViewById(R.id.imgEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Editar playlist");

                final EditText input = new EditText(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setHint(items.get(position));
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_create_24dp);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                items.set(position, String.valueOf(input.getText()));
                                nombre.setText(items.get(position));
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

        //escucha del boton eliminar playlist
        convertView.findViewById(R.id.imgDel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                alertDialog.setTitle("Advertencia");
                alertDialog.setMessage("¿Estas seguro de eliminar: " + items.get(position) + " ?");


                alertDialog.setIcon(R.drawable.ic_delete_24dp);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                items.remove(position);
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

        //escucha del textview de la playlist
        convertView.findViewById(R.id.nombre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dependiende de la playlist que seleccionemos nos manda a la otra vista
                Intent i = new Intent(v.getContext(), ActivitySongList.class);
                i.putExtra("namePlaylist",items.get(position));
                v.getContext().startActivity(i);
            }
        });

        return convertView;
    }


}
