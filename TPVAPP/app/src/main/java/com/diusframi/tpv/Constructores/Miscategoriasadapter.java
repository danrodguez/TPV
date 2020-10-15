package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.MisCategorias.Editarcategoria;
import com.diusframi.tpv.Fragments.MisCategorias.Miscategoriasfragment;
import com.diusframi.tpv.R;
import com.diusframi.tpv.SwipeRevealLayout;

import java.util.ArrayList;

public class Miscategoriasadapter extends RecyclerView.Adapter<Miscategoriasadapter.MultiViewHolder> {

    private Context context;
    private ArrayList<MisCategorias> CategoriasLista;
    private Editarcategoria mListener;
    Miscategoriasfragment mListener2;

    public Miscategoriasadapter(Editarcategoria listener,Miscategoriasfragment listener2, Context context, ArrayList<MisCategorias> CategoriasLista) {
        this.context = context;
        this.CategoriasLista = CategoriasLista;
        mListener = listener;
        mListener2 = listener2;
    }

    public void setCategoriasLista(ArrayList<MisCategorias> CategoriasLista) {
        this.CategoriasLista = new ArrayList<>();
        this.CategoriasLista = CategoriasLista;

    }

    @NonNull
    @Override
    public Miscategoriasadapter.MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.miscategoriasconfiguracion, viewGroup, false);

        return new Miscategoriasadapter.MultiViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(CategoriasLista.get(position));
    }

    @Override
    public int getItemCount() {
        return CategoriasLista.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {
        TextView Nombre;
         ImageButton Borrar;
        SwipeRevealLayout recyclerView;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.swipereveal);
            Nombre = itemView.findViewById(R.id.nombres);
            Borrar = itemView.findViewById(R.id.borrararticulo);


        }

        @SuppressLint("SetTextI18n")
        void bind(final MisCategorias Categoriaslista) {
            Nombre.setText(Categoriaslista.getNombre());




            @SuppressLint("UseCompatLoadingForDrawables") Drawable img = context.getResources().getDrawable(R.drawable.estrellablanca);
            img.setBounds(0, 0, 20, 20);




            Nombre.setOnClickListener(v -> mListener.editarcategoria(Nombre.getText().toString()));

            Borrar.setOnClickListener(v -> {

                BaseDatos resg21 = new BaseDatos(context, null);
                SQLiteDatabase database = resg21.getWritableDatabase();
                String sql = " DELETE FROM CategoriasTabla WHERE Categoria = ?;";

                SQLiteStatement statement = database.compileStatement(sql);
                statement.clearBindings();

                statement.bindString(1, Categoriaslista.getNombre());


                statement.executeUpdateDelete();


                CategoriasLista.remove(Categoriaslista);
                mListener2.refrescar(CategoriasLista);


            });



        }


    }


}
