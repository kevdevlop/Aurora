package com.example.kevdev.aurora.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevdev.aurora.Model.PlaylistModel;
import com.example.kevdev.aurora.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by KevDev on 21/03/17.
 */
public class AdapterAddPlaylist extends BaseAdapter {
    private Context context;
    private LayoutInflater inflador;
    private List<PlaylistModel> items;
    TextView nombre;
    private DatabaseReference rootRef;
    FirebaseUser userF;

    public AdapterAddPlaylist(Context context, List<PlaylistModel> items) {
        this.context = context;
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
            convertView = inflador.inflate(R.layout.addplaylist_buttons, null);
        }

        userF = FirebaseAuth.getInstance().getCurrentUser();

        final String userId = userF.getEmail();

        final PlaylistModel playlistModel = items.get(position);

        nombre = (TextView) convertView.findViewById(R.id.nombre);

        nombre.setText(playlistModel.getNombre());

        rootRef = FirebaseDatabase.getInstance().getReference().child("playlist");

        convertView.findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.child(playlistModel.getNombre())
                        .child("songs").setValue(playlistModel.getSongs().get(position));
            }
        });

        return convertView;
    }
}
