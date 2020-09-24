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
        BaseDatos resg = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
        CheckBox check;
        TextView Articulostext;
        TextView NumeroText;
        TextView ImporteText;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);

            check = itemView.findViewById(R.id.unidades);
            Articulostext = itemView.findViewById(R.id.articulos);
            NumeroText = itemView.findViewById(R.id.numero);
            ImporteText = itemView.findViewById(R.id.importe);


        }

        @SuppressLint("SetTextI18n")
        void bind(final Devolucionconstruct devolucionlista) {
            BaseDatos resg3 = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
            SQLiteDatabase bd3 = resg3.getReadableDatabase();


            Devolucion.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                               @Override
                                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                                if(Devolucion.checkbox.isChecked()){
                                                                      mListener.checkall();



                                                               }}
                                                           });


if(devolucionlista.isChequeado()){
    check.setChecked(true);
    //select del nombre del producto devuelto en vendidos,
    if (check.isChecked()) {
        mListener.sumar(devolucionlista.getPrecio());
        BaseDatos resg2 = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
        @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
        @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
        @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

        cursorselect.moveToFirst();
        int categoria = cursorselect.getInt(0);

        @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
        cursorselect2.moveToFirst();
        String categoriareal = cursorselect2.getString(0);


        int iva = 0;
        int numero = 0;

        if(cursorselect3.moveToFirst()){
            iva = cursorselect3.getInt(0);
        }
        if(cursor3.moveToFirst()){
            numero = cursor3.getInt(0);

        }
        if(!cursor2.moveToFirst()){
            resg2.crearnuevadevoluciontemporal(devolucionlista.getOrden(),categoriareal,devolucionlista.getArticulo(),1,devolucionlista.getPrecio()*numero,iva);
        }else{
            resg2.actualizardevoluciontemporal(numero+1,devolucionlista.getArticulo(),devolucionlista.getOrden());
        }



        mListener.cambiarbotonanaranja();
    } else{

        BaseDatos resg2 = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
        @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
        @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
        @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

        cursorselect.moveToFirst();
        int categoria = cursorselect.getInt(0);

        @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
        cursorselect2.moveToFirst();
        String categoriareal = cursorselect2.getString(0);


        int iva = 0;
        int numero = 0;

        if(cursorselect3.moveToFirst()){
            iva = cursorselect3.getInt(0);
        }
        if(cursor3.moveToFirst()){
            numero = cursor3.getInt(0);

        }
        if(!cursor2.moveToFirst()){
            resg2.crearnuevadevoluciontemporal(devolucionlista.getOrden(),categoriareal,devolucionlista.getArticulo(),1,devolucionlista.getPrecio()*numero,iva);
        }else{
            resg2.actualizardevoluciontemporal(numero-1,devolucionlista.getArticulo(),devolucionlista.getOrden());
        }





        mListener.restar(devolucionlista.getPrecio());


    }

}
            Articulostext.setText(devolucionlista.getArticulo());
            DecimalFormat decim = new DecimalFormat("0.00");
            NumeroText.setText("x"+devolucionlista.getUnidad().toString());
            Double importe = devolucionlista.getPrecio();
            ImporteText.setText(importe * devolucionlista.getUnidad() + "€");
            //select del nombre del producto devuelto en vendidos,


            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    //select del nombre del producto devuelto en vendidos,
                    if (check.isChecked()) {
                        mListener.sumar(devolucionlista.getPrecio());
                        BaseDatos resg2 = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
                        SQLiteDatabase bd2 = resg2.getReadableDatabase();
                        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
                        @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
                        @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
                        @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

                        cursorselect.moveToFirst();
                        int categoria = cursorselect.getInt(0);

                        @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                        cursorselect2.moveToFirst();
                        String categoriareal = cursorselect2.getString(0);


                        int iva = 0;
                        int numero = 0;

                        if(cursorselect3.moveToFirst()){
                            iva = cursorselect3.getInt(0);
                        }
                        if(cursor3.moveToFirst()){
                            numero = cursor3.getInt(0);

                        }
                        if(!cursor2.moveToFirst()){
                            resg2.crearnuevadevoluciontemporal(devolucionlista.getOrden(),categoriareal,devolucionlista.getArticulo(),1,devolucionlista.getPrecio(),iva);
                        }else{
                            resg2.actualizardevoluciontemporal(numero+1,devolucionlista.getArticulo(),devolucionlista.getOrden());
                        }




                    mListener.cambiarbotonanaranja();
                    } else{
                        BaseDatos resg2 = new BaseDatos(itemView.getContext(), "BaseDatos", null, 1);
                        SQLiteDatabase bd2 = resg2.getReadableDatabase();
                        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
                        @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Numero FROM Devueltostemporal WHERE idorden LIKE '" +devolucionlista.getOrden()+ "' AND  Nombre LIKE '"+devolucionlista.getArticulo()+"'", null);
                        @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);
                        @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT IVA FROM Articulos WHERE Nombre LIKE '" + devolucionlista.getArticulo() + "'", null);

                        cursorselect.moveToFirst();
                        int categoria = cursorselect.getInt(0);

                        @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);
                        cursorselect2.moveToFirst();
                        String categoriareal = cursorselect2.getString(0);


                        int iva = 0;
                        int numero = 0;

                        if(cursorselect3.moveToFirst()){
                            iva = cursorselect3.getInt(0);
                        }
                        if(cursor3.moveToFirst()){
                            numero = cursor3.getInt(0);

                        }
                        if(!cursor2.moveToFirst()){
                            resg2.crearnuevadevoluciontemporal(devolucionlista.getOrden(),categoriareal,devolucionlista.getArticulo(),1,devolucionlista.getPrecio(),iva);
                        }else{
                            resg2.actualizardevoluciontemporal(numero-1,devolucionlista.getArticulo(),devolucionlista.getOrden());
                        }




                        mListener.restar(devolucionlista.getPrecio());


                    }


                }
            });


        }
    }

}