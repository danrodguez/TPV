package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.R;

import java.util.ArrayList;

public class EditarLineasAdapter extends RecyclerView.Adapter<EditarLineasAdapter.MultiViewHolder> {

    //Declaraciones
    private Context context;
    private ArrayList<EditarLinea> EditarLista;



    public EditarLineasAdapter(Context context, ArrayList<EditarLinea> EditarLista) {
        this.context = context;
        this.EditarLista = EditarLista;

    }


    @NonNull
    @Override
    public EditarLineasAdapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.editarlineasconfiguracion, viewGroup, false);

        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditarLineasAdapter.MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(EditarLista.get(position));
    }


    @Override
    public int getItemCount() {
        return EditarLista.size();
    }


    static class MultiViewHolder extends RecyclerView.ViewHolder {
        Integer numeroarticulos = 0;
        TextView Nombre, Numero;
        ImageButton Menos,Mas;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            Nombre = itemView.findViewById(R.id.nombres);
            Numero = itemView.findViewById(R.id.numero);
            Menos = itemView.findViewById(R.id.menos);
            Mas = itemView.findViewById(R.id.mas);
        }

        @SuppressLint("SetTextI18n")
        void bind(final EditarLinea EditarLista) {

            Nombre.setText(EditarLista.getNombre());
            Numero.setText("x"+EditarLista.getNumero().toString());
            numeroarticulos = EditarLista.getNumero();

            Menos.setOnClickListener(v -> {

                if (numeroarticulos > 0) {
                    numeroarticulos--;
                    Menos.setVisibility(View.VISIBLE);
                    Numero.setText("x" + numeroarticulos);
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

                }else if (numeroarticulos == 0) {
                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    resg2.quitararticulo();
                    Menos.setVisibility(View.GONE);

                }


        });

            Mas.setOnClickListener(v -> {
                String categoriareal = "";
                String numerotokenizado = "";

if(numeroarticulos == 0){
numeroarticulos = 1;
String preciotokenizado = "";
String IVA = "";
BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
SQLiteDatabase bd2 = resg2.getReadableDatabase();

@SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT * FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);

if (!cursor.moveToFirst()) {
    @SuppressLint("Recycle") Cursor cursorselect = bd2.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
    cursorselect.moveToFirst();
    int categoria = cursorselect.getInt(0);
    @SuppressLint("Recycle") Cursor cursorselect2 = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '" + categoria + "'", null);

    @SuppressLint("Recycle") Cursor cursorselect3 = bd2.rawQuery("SELECT Precio,IVA FROM Articulos WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
    cursorselect2.moveToFirst();
    while (cursorselect3.moveToNext()) {
        preciotokenizado = String.valueOf(cursorselect3.getDouble(0));
        IVA = String.valueOf(cursorselect3.getInt(1));
    }
    categoriareal = cursorselect2.getString(0);
    numerotokenizado = EditarLista.getNumero().toString();



}
resg2.quitararticulo();
resg2.articulonuevolistacompra(
        categoriareal,
        EditarLista.getNombre(),
        Integer.parseInt(numerotokenizado),
        Double.parseDouble(preciotokenizado),
        Integer.parseInt(IVA)

);
}else{
numeroarticulos++;
}

                    Menos.setVisibility(View.VISIBLE);
                    Numero.setText("x" + numeroarticulos);
                    Numero.setVisibility(View.VISIBLE);

                    BaseDatos resg2 = new BaseDatos(itemView.getContext(), null);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + Nombre.getText().toString() + "'", null);
                    cursor2.moveToFirst();


                    resg2.actualizarnumeroarticulos(
                            Nombre.getText().toString(),
                            numeroarticulos


                    );



            });


        }

    }
}
