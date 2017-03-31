package com.example.kevdev.aurora.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Controller.ActivityResultados;
import com.example.kevdev.aurora.Controller.ActivitySongList;
import com.example.kevdev.aurora.Model.PlaylistModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by KevDev on 07/12/16.
 */
public class AdapterPlaylist extends BaseAdapter {
    private Context context;
    private LayoutInflater inflador;
    private List<PlaylistModel> items;
    TextView txtnombre;
    private DatabaseReference rootRef;
    FirebaseUser userF;

    public AdapterPlaylist(List<PlaylistModel> items, Context context) {
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

        //Se crea un modelo para guardar el objeto obtenido de la lista
        final PlaylistModel playlistModel = items.get(position);

        txtnombre = (TextView) convertView.findViewById(R.id.nombre);

        txtnombre.setText(playlistModel.getNombre());

        //Obtenemos la referencia del nodo playlist
        rootRef = FirebaseDatabase.getInstance().getReference().child("playlist");

        //escucha del boton editar playlist
        convertView.findViewById(R.id.imgEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se crea un alertDialog para mostrar el formulario de editar
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());

                //Establecemos el nombre del AlertDialog
                alertDialog.setTitle("Editar playlist");

                //Se crea el editText del formulario
                final EditText input = new EditText(v.getContext());

                //Se crean los parametros para el LinearLayout
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                //Agregamos los parametros al editText
                input.setLayoutParams(lp);

                //Establecemos el hint del editText con el nombre de la playlist anterior
                input.setHint(playlistModel.getNombre());

                //Se agrega el editText y el icono(opcional) al alertDialog
                alertDialog.setView(input);
                alertDialog.setIcon(R.drawable.ic_create_24dp);

                //Creamos un boton positivo con el nombre de OK
                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            //Cuando se haga click
                            public void onClick(DialogInterface dialog, int which) {
                                //Se agrega a la lista
                                items.set(position, playlistModel);
                                //Se establece en el textView el nuevo nombre de la playlist
                                txtnombre.setText(playlistModel.getNombre());

                                //Se escribe en la base de datos el nuevo nombre de la playlist
                                rootRef.child(playlistModel.getNombre())
                                        .child("nombre")
                                        .setValue(input.getText().toString());

                            }
                        });
                //Boton negativo Cancelar la accion
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

                //final Query query = rootRef.orderByChild("ownerID").equalTo(userId);

                alertDialog.setTitle("Advertencia");

                alertDialog.setMessage("¿Estas seguro de eliminar: " + playlistModel.getNombre() + " ?");

                alertDialog.setIcon(R.drawable.ic_delete_24dp);

                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                rootRef.child(playlistModel.getNombre()).removeValue();
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
                //Dependiendo de la playlist que seleccionemos nos manda a la otra vista
                Intent i = new Intent(v.getContext(), ActivitySongList.class);
                i.putExtra("namePlaylist", playlistModel.getNombre().toString());
                i.putExtra("songs",playlistModel.getSongs());
                v.getContext().startActivity(i);
            }
        });

        return convertView;
    }


}
