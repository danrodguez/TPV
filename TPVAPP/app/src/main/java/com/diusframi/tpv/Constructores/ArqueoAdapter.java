package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.Fragments.MisArqueos.Arqueoint;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ArqueoAdapter extends RecyclerView.Adapter<ArqueoAdapter.MultiViewHolder> {
    private Context context;
    private ArrayList<Arqueo> ArqueosLista;
    private Arqueoint mListener;

    public ArqueoAdapter(Arqueoint listener, Context context, ArrayList<Arqueo> ArqueosLista) {
        this.context = context;
        this.ArqueosLista = ArqueosLista;
        mListener = listener;
    }

    public void setArqueosLista(ArrayList<Arqueo> arqueoLista) {
        this.ArqueosLista = new ArrayList<>();
        this.ArqueosLista = arqueoLista;

    }

    @NonNull
    @Override
    public ArqueoAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.arqueoconfiguracion, viewGroup, false);

        return new MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ArqueoAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(ArqueosLista.get(position));
    }

    @Override
    public int getItemCount() {
        return ArqueosLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView Orden;
        TextView Fecha;
        TextView Descuadre;
        TextView total;
        LinearLayout MiArqueo;



        MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            Orden = itemView.findViewById(R.id.ordenes);
            Fecha = itemView.findViewById(R.id.fechas);
            Descuadre = itemView.findViewById(R.id.descuadres);
            total = itemView.findViewById(R.id.totales);
            MiArqueo = itemView.findViewById(R.id.miarqueo);
        }

        @SuppressLint("SetTextI18n")
        void bind(final Arqueo ArqueosLista) {

            int i = ArqueosLista.getI();

            if (i == 0) {
                MiArqueo.setBackgroundResource(0);
                MiArqueo.setBackgroundResource(R.drawable.fondogrisclaro);
                i = 1;
            } else if (i == 1) {
                MiArqueo.setBackgroundResource(0);
                MiArqueo.setBackgroundResource(R.drawable.fondoazul);

                i = 0;
            }

            MiArqueo.setOnClickListener(v -> mListener.arqueo(ArqueosLista.getOrden().toString()));


            String fechastring = String.valueOf(ArqueosLista.getFecha());
            String anio = fechastring.substring(0, 4);
            String mes = fechastring.substring(4, 6);
            String dia = fechastring.substring(6, 8);
            String fechatextocompleto = dia + "/" + mes + "/" + anio;

            Orden.setText("AZ" + ArqueosLista.getOrden().toString());
            Fecha.setText(fechatextocompleto);
            Descuadre.setText(ArqueosLista.getDescuadre().toString() + "€");

            DecimalFormat decim = new DecimalFormat("0.00");
            total.setText(decim.format(ArqueosLista.getTotal()) + "€");
        }
    }
}