package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Botonventa;
import com.diusframi.tpv.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class VentaNumeroAdapter extends RecyclerView.Adapter<VentaNumeroAdapter.MultiViewHolder> {
    private Context context;
    private ArrayList<Carrito> ArticulosLista;
    Botonventa mListener;


    public VentaNumeroAdapter(Botonventa listener, Context context, ArrayList<Carrito> ArticulosLista) {
        this.context = context;
        this.ArticulosLista = ArticulosLista;
        mListener = listener;
    }


    public void setArticulosLista(ArrayList<Carrito> articulosLista) {
        this.ArticulosLista = new ArrayList<>();
        this.ArticulosLista = articulosLista;

    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ventaconfiguracion, viewGroup, false);
        return new MultiViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(ArticulosLista.get(position));
    }


    @Override
    public int getItemCount() {
        return ArticulosLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        Integer numeroarticulos = 0;
        TextView Nombre, Precio, Numero, Iva;
        Button Menos;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.nombres);
            Numero = itemView.findViewById(R.id.numero);
            Precio = itemView.findViewById(R.id.precio);
            Menos = itemView.findViewById(R.id.menos);
            Iva = itemView.findViewById(R.id.iva);
        }

        @SuppressLint("SetTextI18n")
        void bind(Carrito Articuloslista) {

            Nombre.setText(Articuloslista.getNombre());
            Precio.setText(Articuloslista.getPrecio().toString() + " €");
            Iva.setText(Articuloslista.getIva().toString());
            Numero.setText("X " + Articuloslista.getNumero().toString());
            numeroarticulos = Integer.parseInt(Articuloslista.getNumero().toString());


            if (numeroarticulos > 0) {
                Numero.setVisibility(View.VISIBLE);
                Menos.setVisibility(View.VISIBLE);
            } else {
                Numero.setVisibility(View.GONE);
                Menos.setVisibility(View.GONE);
            }
            Nombre.setOnClickListener(v -> {

                numeroarticulos = numeroarticulos + 1;


                if (numeroarticulos > 0) {
                    mListener.cambiarcolorbotonanaranjaborrar();
                    Menos.setVisibility(View.VISIBLE);
                    Numero.setText("X " + numeroarticulos);
                    Numero.setVisibility(View.VISIBLE);
                    BaseDatos resg = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd = resg.getReadableDatabase();


                    Cursor cursor = bd.rawQuery("SELECT * FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);

                    if (!cursor.moveToFirst()) {
                        BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                        SQLiteDatabase bd2 = resg2.getReadableDatabase();
                        Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                        cursorselect.moveToFirst();

                        int categoria = cursorselect.getInt(0);
                        Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                        cursorselect2.moveToFirst();

                        String categoriareal = cursorselect2.getString(0);
                        String numerotokenizado = Numero.getText().toString();
                        StringTokenizer st = new StringTokenizer(numerotokenizado, " ");
                        String numeroreal = st.nextToken();
                        String numeroreal2 = st.nextToken();
                        String preciotokenizado = Precio.getText().toString();
                        StringTokenizer stp = new StringTokenizer(preciotokenizado, " ");
                        String precioreal = stp.nextToken();
                        cursorselect2.close();
                        resg.articulonuevolistacompra(
                                categoriareal,
                                Nombre.getText().toString(),
                                Integer.parseInt(numeroreal2),
                                Double.parseDouble(precioreal),
                                Integer.parseInt(Iva.getText().toString())


                        );

                        cursorselect.close();
                    } else {

                        Cursor cursor2 = bd.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                        cursor2.moveToFirst();

                        int numeroarticulos = cursor2.getInt(0);
                        resg.actualizarnumeroarticulos(
                                Nombre.getText().toString(),
                                numeroarticulos + 1


                        );
                        cursor.close();
                        cursor2.close();
                    }
                } else {
                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    Cursor cursor3 = bd2.rawQuery("SELECT * FROM ArticulosVenta WHERE Numero > '0'", null);

                    if (cursor3.moveToFirst()) {
                        mListener.cambiarcolorbotonanaranjaborrar();
                    } else {
                        mListener.cambiarcolorbotongrisborrar();
                    }
cursor3.close();
                    Menos.setVisibility(View.GONE);
                    Numero.setVisibility(View.GONE);
                }

            });


            Menos.setOnClickListener(v -> {

                if (numeroarticulos > 0) {
                    mListener.cambiarcolorbotonanaranja();
                    numeroarticulos--;
                    Menos.setVisibility(View.VISIBLE);
                    Numero.setText("X " + numeroarticulos);
                    Numero.setVisibility(View.VISIBLE);

                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    Cursor cursor2 = bd2.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                    cursor2.moveToFirst();
                    int numeroarticulos = Integer.parseInt(cursor2.getString(0));
                    cursor2.close();
                    resg2.actualizarnumeroarticulos(
                            Nombre.getText().toString(),
                            numeroarticulos - 1


                    );

                }

                if (numeroarticulos == 0) {
                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    Cursor cursor3 = bd2.rawQuery("SELECT * FROM ArticulosVenta WHERE Numero > '0'", null);

                    if (cursor3.moveToFirst()) {
                        mListener.cambiarcolorbotonanaranjaborrar();
                    } else {
                        mListener.cambiarcolorbotongrisborrar();
                    }

                    cursor3.close();
                    Menos.setVisibility(View.GONE);
                    Numero.setVisibility(View.GONE);
                }

            });

        }
    }


}











































