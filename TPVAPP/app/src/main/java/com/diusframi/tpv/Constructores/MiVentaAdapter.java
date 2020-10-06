package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.MisVentas.Ticket;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MiVentaAdapter extends RecyclerView.Adapter<MiVentaAdapter.MultiViewHolder> {
    private Context context;
    private ArrayList<MiVenta> MisVentasLista;
    private Ticket mListener;
    String pago="";

    public MiVentaAdapter(Ticket listener, Context context, ArrayList<MiVenta> MisVentasLista) {
        this.context = context;
        this.MisVentasLista = MisVentasLista;
        mListener = listener;


    }

    public void setMisVentasLista(ArrayList<MiVenta> misVentasLista) {
        this.MisVentasLista = new ArrayList<>();
        this.MisVentasLista = misVentasLista;

    }

    @NonNull
    @Override
    public MiVentaAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.miventaconfiguracion, viewGroup, false);

        return new MiVentaAdapter.MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MiVentaAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(MisVentasLista.get(position));
    }

    @Override
    public int getItemCount() {
        return MisVentasLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView Orden;
        TextView Fecha;
        TextView total;
        LinearLayout MiVenta;


        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            Orden = itemView.findViewById(R.id.ordenes);
            Fecha = itemView.findViewById(R.id.fechas);
            total = itemView.findViewById(R.id.totales);
            MiVenta = itemView.findViewById(R.id.miventa);
        }


        @SuppressLint("SetTextI18n")
        void bind(final MiVenta MisVentasLista) {
            int i = MisVentasLista.getI();

            BaseDatos resg = new BaseDatos(context, null);
            SQLiteDatabase bd = resg.getReadableDatabase();

if(!MisVentasLista.isDevolucion()){

    Cursor cursor = bd.rawQuery("SELECT TipoPago FROM Ordenes WHERE id LIKE '" + MisVentasLista.getOrden() + "'", null);

    while (cursor.moveToNext()) {
                pago = cursor.getString(0);

            }


            if (pago.equals("Efectivo")) {
                MiVenta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.ticketefectivo(MisVentasLista.getOrden().toString());
                    }
                });

            }else if (pago.equals("Tarjeta")){
                MiVenta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.tickettarjeta(MisVentasLista.getOrden().toString());
                    }
                });
            }


            if (i == 0) {
                MiVenta.setBackgroundResource(0);
                MiVenta.setBackgroundResource(R.drawable.fondogrisclaro);
                i = 1;
            } else if (i == 1) {
                MiVenta.setBackgroundResource(0);
                MiVenta.setBackgroundResource(R.drawable.fondoazul);

                i = 0;
            }

            String fechastring = String.valueOf(MisVentasLista.getFecha());

            String anio = fechastring.substring(0, 4);
            String mes = fechastring.substring(4, 6);
            String dia = fechastring.substring(6, 8);
            String fechatextocompleto = dia + "/" + mes + "/" + anio;

        cursor.close();



            Cursor cursor2 = bd.rawQuery("SELECT  TextoTicket FROM TextoTicketDevolucion", null);

            String TextoTicket = "";
    while (cursor2.moveToNext()) {
        TextoTicket = cursor2.getString(0);
    }





    Orden.setText(TextoTicket +  MisVentasLista.getOrden().toString());




            Fecha.setText(fechatextocompleto);
            DecimalFormat decim = new DecimalFormat("0.00");
            total.setText(decim.format(MisVentasLista.getTotal()) + "€");


            cursor2.close();




}else{

    MiVenta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.ticketdevolucion(MisVentasLista.getOrden().toString());
        }
    });




    if (i == 0) {
        MiVenta.setBackgroundResource(0);
        MiVenta.setBackgroundResource(R.drawable.fondogrisclaro);
        i = 1;
    } else if (i == 1) {
        MiVenta.setBackgroundResource(0);
        MiVenta.setBackgroundResource(R.drawable.fondoazul);

        i = 0;
    }

    String fechastring = String.valueOf(MisVentasLista.getFecha());

    String anio = fechastring.substring(0, 4);
    String mes = fechastring.substring(4, 6);
    String dia = fechastring.substring(6, 8);
    String fechatextocompleto = dia + "/" + mes + "/" + anio;





    Cursor cursor4 = bd.rawQuery("SELECT TextoDevolucion FROM TextoTicketDevolucion", null);

    String TextoTicket = "";


    while (cursor4.moveToNext()) {
        TextoTicket = cursor4.getString(0);
    }



        Orden.setText(TextoTicket +  MisVentasLista.getOrden().toString());



    Fecha.setText(fechatextocompleto);
    DecimalFormat decim = new DecimalFormat("0.00");
    total.setText(decim.format(MisVentasLista.getTotal()) + "€");


    cursor4.close();
    bd.close();
}



        }
    }
}
