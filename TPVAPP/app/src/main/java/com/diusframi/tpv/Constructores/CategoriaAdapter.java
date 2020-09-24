package com.diusframi.tpv.Constructores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<Categoria> CategoriaLista;
    int i = 0;

    public CategoriaAdapter(Context context, ArrayList<Categoria> CategoriaLista) {
        this.context = context;
        this.CategoriaLista = CategoriaLista;

    }

    public void setCategoriaLista(ArrayList<Categoria> categoriaLista) {
        this.CategoriaLista = new ArrayList<>();
        this.CategoriaLista = categoriaLista;

    }

    @NonNull
    @Override
    public CategoriaAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoriaconfiguracion, viewGroup, false);

        return new CategoriaAdapter.MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(CategoriaLista.get(position));
    }

    @Override
    public int getItemCount() {
        return CategoriaLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        BaseDatos resg = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
        TextView Nombre, Precio;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            Nombre = itemView.findViewById(R.id.nombres);
            Precio = itemView.findViewById(R.id.precio);

        }


        void bind(final Categoria Categorialista) {
            Nombre.setText(Categorialista.getNombre());
            DecimalFormat decim = new DecimalFormat("0.00");
            Precio.setText(decim.format(Categorialista.getPrecio()));

        }
    }
}