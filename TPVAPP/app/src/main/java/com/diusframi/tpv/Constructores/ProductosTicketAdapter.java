package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductosTicketAdapter extends RecyclerView.Adapter<ProductosTicketAdapter.MultiViewHolder> {
    private Context context;
    private ArrayList<ProductoTicket> ProductoLista;


    public ProductosTicketAdapter(Context context, ArrayList<ProductoTicket> ProductoLista) {
        this.context = context;
        this.ProductoLista = ProductoLista;

    }

    public void setProductoLista(ArrayList<ProductoTicket> productoLista) {
        this.ProductoLista = new ArrayList<>();
        this.ProductoLista = productoLista;

    }

    @NonNull
    @Override
    public ProductosTicketAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.productosticketconfiguracion, viewGroup, false);

        return new ProductosTicketAdapter.MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductosTicketAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(ProductoLista.get(position));
    }

    @Override
    public int getItemCount() {
        return ProductoLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView Unidades,Articulos,Precio,Importe;



        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            Unidades = itemView.findViewById(R.id.unidades);
            Articulos = itemView.findViewById(R.id.articulos);
            Precio = itemView.findViewById(R.id.preciounidad);
            Importe = itemView.findViewById(R.id.importe);
        }


        @SuppressLint("SetTextI18n")
        void bind(final ProductoTicket ProductoLista) {
            DecimalFormat decim = new DecimalFormat("0.00");

            Unidades.setText(ProductoLista.getUnidad().toString());
            Articulos.setText(ProductoLista.getArticulos().toString());
            Precio.setText(ProductoLista.getPreciou().toString());
            Importe.setText(decim.format(ProductoLista.getImporte()));
        }
    }
}
