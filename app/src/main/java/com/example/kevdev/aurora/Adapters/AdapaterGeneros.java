package com.example.kevdev.aurora.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevdev.aurora.Controller.ActivitySongList;
import com.example.kevdev.aurora.Model.SongModel;
import com.example.kevdev.aurora.R;

import java.util.List;

/**
 * Created by KevDev on 28/03/17.
 */
public class AdapaterGeneros extends RecyclerView.Adapter<AdapaterGeneros.GenerosViewHolder>{
    private List<Object> items;

    public static class GenerosViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public GenerosViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textoGenero);
        }
    }
    public AdapaterGeneros(List<Object> items) {
        this.items = items;
    }

    @Override
    public GenerosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.generos_cards, parent, false);

        return new GenerosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GenerosViewHolder holder, final int position) {
        holder.textView.setText(items.get(position).toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Con el fin de empezar a mostrar una nueva actividad lo que necesitamos es una intención
                Intent intent = new Intent(v.getContext(), ActivitySongList.class);
                intent.putExtra("Genero", items.get(position).toString());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
