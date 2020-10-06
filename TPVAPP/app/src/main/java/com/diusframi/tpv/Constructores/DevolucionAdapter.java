package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.MisVentas.Devolucion;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DevolucionAdapter  extends RecyclerView.Adapter<DevolucionAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<Devolucionconstruct> devolucionconstructLista;
    private numerodevolucion mListener;

    public DevolucionAdapter(numerodevolucion listener,Context context, ArrayList<Devolucionconstruct> devolucionconstructLista) {
        this.context = context;
        this.devolucionconstructLista = devolucionconstructLista;
        mListener = listener;

    }

    public void setDevolucionconstructLista(ArrayList<Devolucionconstruct> devolucionconstructLista) {
        this.devolucionconstructLista = new ArrayList<>();
        this.devolucionconstructLista = devolucionconstructLista;

    }

    @NonNull
    @Override
    public DevolucionAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.devolucionconfiguracion, viewGroup, false);

        return new DevolucionAdapter.MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DevolucionAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(devolucionconstructLista.get(position));
    }

    @Override
    public int getItemCount() {
        return devolucionconstructLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        CheckBox check;
        TextView Articulostext;
        TextView NumeroText;
        TextView ImporteText;
        int iddevolucion = 0;
        int idorden = 0;
        int categoria = 0;
        String categoriareal = "";
        MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.unidades);
            Articulostext = itemView.findViewById(R.id.articulos);
            NumeroText = itemView.findViewById(R.id.numero);
            ImporteText = itemView.findViewById(R.id.importe);


        }

        @SuppressLint("SetTextI18n")
        void bind(final Devolucionconstruct devolucionlista) {



            if(devolucionlista.isChequeado()){
                check.setChecked(true);
            }else{
                check.setChecked(false);
            }

            mListener.cambiarbotonanaranja();


            check.setOnCheckedChangeListener((buttonView, isChecked) -> {

                        if (check.isChecked()) {

                            BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                            SQLiteDatabase bd2 = resg2.getReadableDatabase();
                            Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idticket LIKE '" + devolucionlista.getOrdenticket() + "' AND  Nombre LIKE '" + devolucionlista.getArticulo() + "' AND idorden LIKE '" + devolucionlista.getOrden() + "'", null);
                            Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idticket LIKE '" + devolucionlista.getOrdenticket() + "' AND  Nombre LIKE '" + devolucionlista.getArticulo() + "'  AND idorden LIKE '" + devolucionlista.getOrden() + "'", null);
                            Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
                            Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

                            if (cursorselect.moveToFirst()) {
                                categoria = cursorselect.getInt(0);
                            }

                            cursorselect.close();


                            Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                            if (cursorselect2.moveToFirst()) {
                                categoriareal = cursorselect2.getString(0);
                            }

                            cursorselect2.close();
                            int iva = 0;
                            int numero = 0;

                            if (cursorselect3.moveToFirst()) {
                                iva = cursorselect3.getInt(0);
                            }
                            cursorselect3.close();
                            if (cursor3.moveToFirst()) {
                                numero = cursor3.getInt(0);

                            }
                            cursor3.close();

                            @SuppressLint("Recycle") Cursor cursor5 = bd2.rawQuery("SELECT id FROM Devoluciones ORDER BY id ASC", null);
                            while (cursor5.moveToNext()) {
                                iddevolucion = cursor5.getInt(0);
                            }
                            cursor5.close();
                            if (iddevolucion == 0) {
                                iddevolucion = 1;
                            } else {
                                iddevolucion = iddevolucion + 1;
                            }

                            @SuppressLint("Recycle") Cursor cursor6 = bd2.rawQuery("SELECT idorden FROM Devueltostemporal ORDER BY idorden DESC ", null);
                            while (cursor6.moveToNext()) {
                                idorden = cursor6.getInt(0);
                                idorden = idorden + 1;
                            }
                            cursor6.close();

                            if (iddevolucion == 0) {
                                iddevolucion = 1;
                            } else {
                                iddevolucion = iddevolucion + 1;
                            }


                            if (!cursor2.moveToFirst()) {
                                resg2.crearnuevadevoluciontemporal(devolucionlista.getOrden(), categoriareal, devolucionlista.getArticulo(), 1, devolucionlista.getPrecio() * 1, iva, devolucionlista.getOrdenticket());
                            } else {
                                resg2.actualizardevoluciontemporal(numero + 1, devolucionlista.getArticulo(), devolucionlista.getOrden());
                            }


                            cursor2.close();

                            mListener.cambiarbotonanaranja();

                            mListener.sumar(devolucionlista.getPrecio());


                        } else if (!check.isChecked()) {
                            BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                            SQLiteDatabase bd2 = resg2.getReadableDatabase();
                            @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idticket LIKE '" + devolucionlista.getOrdenticket() + "' AND  Nombre LIKE '" + devolucionlista.getArticulo() + "' AND idorden LIKE '" + devolucionlista.getOrden() + "'", null);
                            @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idticket LIKE '" + devolucionlista.getOrdenticket() + "' AND  Nombre LIKE '" + devolucionlista.getArticulo() + "' AND idorden LIKE '" + devolucionlista.getOrden() + "' ", null);
                            @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
                            @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

                            if (cursorselect.moveToFirst()) {
                                categoria = cursorselect.getInt(0);
                            }


                            @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                            if (cursorselect2.moveToFirst()) {
                                categoriareal = cursorselect2.getString(0);
                            }

                            int iva = 0;
                            int numero = 0;

                            if (cursorselect3.moveToFirst()) {
                                iva = cursorselect3.getInt(0);
                            }
                            if (cursor3.moveToFirst()) {
                                numero = cursor3.getInt(0);

                            }

                            @SuppressLint("Recycle") Cursor cursor5 = bd2.rawQuery("SELECT id FROM Devoluciones ORDER BY id ASC", null);
                            while (cursor5.moveToNext()) {
                                iddevolucion = cursor5.getInt(0);
                            }

                            if (iddevolucion == 0) {
                                iddevolucion = 1;
                            } else {
                                iddevolucion = iddevolucion + 1;
                            }


                            resg2.actualizardevoluciontemporal(numero, devolucionlista.getArticulo(), devolucionlista.getOrden());

                            mListener.restar(devolucionlista.getPrecio());


                        }
                    });


            Articulostext.setText(devolucionlista.getArticulo());
            NumeroText.setText("x"+devolucionlista.getUnidad().toString());
            Double importe = devolucionlista.getPrecio();
            ImporteText.setText(importe * devolucionlista.getUnidad() + "€");
        }
    }

}