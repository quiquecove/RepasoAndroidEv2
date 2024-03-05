package com.example.resumenev2.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.resumenev2.R;

import java.util.ArrayList;

public class  MiAdaptador extends RecyclerView.Adapter<MiAdaptador.MiViewHolder> {
    private ArrayList<ItemLista> datos;
    private View.OnClickListener listener;

    public MiAdaptador(ArrayList<ItemLista> datos) {
        this.datos = datos;
        this.listener = listener;
    }

    public static class MiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView textoSup;
        private TextView textoInf;

        public MiViewHolder(View view) {
            super(view);
            imagen = view.findViewById(R.id.imageView);
            textoSup = view.findViewById(R.id.tvSuperior);
            textoInf = view.findViewById(R.id.tvInferior);
        }

        public void bindListaItem(ItemLista li) {
            imagen.setImageResource(li.getImagen());
            textoSup.setText(li.getTextoSup());
            textoInf.setText(li.getTextoInf());
        }
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personal_layout, parent, false);
        v.setOnClickListener(listener);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MiViewHolder holder, int position) {
        holder.bindListaItem(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }
}
