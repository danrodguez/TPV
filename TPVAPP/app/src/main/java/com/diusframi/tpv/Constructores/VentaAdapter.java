package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Botonventa;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class VentaAdapter extends RecyclerView.Adapter<VentaAdapter.MultiViewHolder> {

    //Declaraciones
    private Context context;
    private ArrayList<Articulo> ArticulosLista;
    private Botonventa mListener;
    Double precionumero;

    public VentaAdapter(Botonventa listener, Context context, ArrayList<Articulo> ArticulosLista) {
        this.context = context;
        this.ArticulosLista = ArticulosLista;
        mListener = listener;
    }


    public void setArticulosLista(ArrayList<Articulo> articulosLista) {
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
        LinearLayout linear;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linearventa);
            Nombre = itemView.findViewById(R.id.nombres);
            Numero = itemView.findViewById(R.id.numero);
            Precio = itemView.findViewById(R.id.precio);
            Menos = itemView.findViewById(R.id.menos);
            Iva = itemView.findViewById(R.id.iva);

        }

        @SuppressLint("SetTextI18n")
        void bind(final Articulo Articuloslista) {

            Nombre.setText(Articuloslista.getNombre());
            DecimalFormat decim = new DecimalFormat("0.00");

            Precio.setText(decim.format(Articuloslista.getPrecio()) + " €");
            Iva.setText(Articuloslista.getIva().toString());

            precionumero = Articuloslista.getPrecio();

            linear.setOnClickListener(v -> {
                mListener.cambiarcolorbotonanaranjaborrar();
                numeroarticulos = numeroarticulos + 1;

                if (Articuloslista.getPrecio().toString().equals("0.0")) {
                    mListener.intentpreciovariable(Nombre.getText().toString());

                } else {
                    if (numeroarticulos > 0) {
                        mListener.cambiarcolorbotonanaranjaborrar();
                        Menos.setVisibility(View.VISIBLE);
                        Numero.setText("X " + numeroarticulos);
                        Numero.setVisibility(View.VISIBLE);
                        BaseDatos resg = new BaseDatos(itemView.getContext(), null);
                        SQLiteDatabase bd = resg.getReadableDatabase();


                        @SuppressLint("Recycle") Cursor cursor = bd.rawQuery("SELECT * FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);

                        if (!cursor.moveToFirst()) {
                            BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                            SQLiteDatabase bd2 = resg2.getReadableDatabase();
                            @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                            cursorselect.moveToFirst();
                            int categoria = cursorselect.getInt(0);
                            @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                            cursorselect2.moveToFirst();
                            String categoriareal = cursorselect2.getString(0);
                            String numerotokenizado = Numero.getText().toString();
                            StringTokenizer st = new StringTokenizer(numerotokenizado, " ");
                            st.nextToken();
                            String numeroreal2 = st.nextToken();
                            String preciotokenizado = Articuloslista.getPrecio().toString();
                            StringTokenizer stp = new StringTokenizer(preciotokenizado, " ");
                            String precioreal = stp.nextToken();
                            double precior = Double.parseDouble(precioreal);
                            resg.quitararticulo();
                          int numeroint =  Integer.parseInt(numeroreal2);
                            resg.articulonuevolistacompra(
                                    categoriareal,
                                    Nombre.getText().toString(),
                                    Integer.parseInt(numeroreal2),
                                    precior*numeroint,
                                    Integer.parseInt(Iva.getText().toString())

                            );
                        } else {

                            @SuppressLint("Recycle") Cursor cursor2 = bd.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                            cursor2.moveToFirst();
                            int numeroarticulos = cursor2.getInt(0);

                            resg.actualizarnumeroarticulos(
                                    Nombre.getText().toString(),
                                    numeroarticulos + 1


                            );
                        }
                    } else {
                        BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                        SQLiteDatabase bd2 = resg2.getReadableDatabase();
                        @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT * FROM ArticulosVenta WHERE Numero > '0'", null);

                        if (cursor3.moveToFirst()) {
                            mListener.cambiarcolorbotonanaranjaborrar();
                        } else {
                            mListener.cambiarcolorbotongrisborrar();
                        }

                        Menos.setVisibility(View.GONE);
                        Numero.setVisibility(View.GONE);
                    }

                }
            });


            Menos.setOnClickListener(v -> {

                if (numeroarticulos > 0) {
                    mListener.cambiarcolorbotonanaranjaborrar();
                    numeroarticulos--;
                    Menos.setVisibility(View.VISIBLE);
                    Numero.setText("X " + numeroarticulos);
                    Numero.setVisibility(View.VISIBLE);

                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                    cursor2.moveToFirst();
                    int numeroarticulos = Integer.parseInt(cursor2.getString(0));

                    resg2.actualizarnumeroarticulos(
                            Nombre.getText().toString(),
                            numeroarticulos - 1


                    );

                }

                if (numeroarticulos == 0) {
                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT * FROM ArticulosVenta WHERE Numero > '0'", null);

                    if (cursor3.moveToFirst()) {
                        mListener.cambiarcolorbotonanaranjaborrar();
                    } else {
                        mListener.cambiarcolorbotongrisborrar();
                    }
                    Menos.setVisibility(View.GONE);
                    Numero.setVisibility(View.GONE);
                }

            });


        }

    }
}
