package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

//Lista de articulos vendidos en TicketDeVenta
public class ArticuloVentaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ArticuloVenta> ArticulosLista;


    public ArticuloVentaAdapter(Context context, int layout, ArrayList<ArticuloVenta> articulosLista) {
        this.context = context;
        this.layout = layout;
        ArticulosLista = articulosLista;
    }

    @Override
    public int getCount() {
        return ArticulosLista.size();
    }

    @Override
    public ArticuloVenta getItem(int position) {
        return ArticulosLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row = view;
        RecyclerView holder = new RecyclerView();


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.Nombre = row.findViewById(R.id.nombres);
            holder.Precio = row.findViewById(R.id.precio);
            holder.Numero = row.findViewById(R.id.numero);


            row.setTag(holder);

        } else {
            holder = (RecyclerView) row.getTag();

        }
        //Declaraciones
        com.diusframi.tpv.Constructores.ArticuloVenta articuloVenta = ArticulosLista.get(position);
        holder.Nombre.setText(articuloVenta.getNombre());
        DecimalFormat decim = new DecimalFormat("0.00");
        holder.Precio.setText(decim.format(articuloVenta.getPrecio()*articuloVenta.getNumero()) + " €");
        holder.Numero.setText("X" + articuloVenta.getNumero().toString());

        return row;

    }

    private static class RecyclerView {
        TextView Nombre, Precio, Numero;

    }
}
