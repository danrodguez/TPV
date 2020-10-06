package com.diusframi.tpv.Constructores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.MisArticulos.Editararticulo;
import com.diusframi.tpv.Fragments.MisArticulos.MisarticulosFragment;
import com.diusframi.tpv.R;
import com.diusframi.tpv.SwipeRevealLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

//Adaptador de lista de articulos en ArticulosFragment
public class ArticuloAdaptercategoria extends RecyclerView.Adapter<ArticuloAdaptercategoria.MultiViewHolder> {

    private Context context;
    private ArrayList<Articulo> ArticulosLista;
    private Editararticulo mListener;
    MisarticulosFragment mListener2;


    public ArticuloAdaptercategoria(Editararticulo listener, MisarticulosFragment listener2, Context context, ArrayList<Articulo> ArticulosLista) {
        this.context = context;
        this.ArticulosLista = ArticulosLista;
        mListener = listener;
        mListener2 = listener2;

    }

    public void setArticulosLista(ArrayList<Articulo> articulosLista) {
        this.ArticulosLista = new ArrayList<>();
        this.ArticulosLista = articulosLista;

    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.articuloconfiguracion, viewGroup, false);

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
        BaseDatos resg = new BaseDatos(itemView.getContext(), null);
        Integer favoritos;
        TextView Nombre, Precio;
        ImageView Favorito;
        ImageButton Borrar;
        SwipeRevealLayout recyclerView;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.swipereveal);
            Favorito = itemView.findViewById(R.id.favoritos);
            Nombre = itemView.findViewById(R.id.nombres);
            Precio = itemView.findViewById(R.id.precio);
            Borrar = itemView.findViewById(R.id.borrararticulo);


        }

        @SuppressLint("SetTextI18n")
        void bind(final Articulo Articuloslista) {
            String categoria = "";
            Nombre.setText(Articuloslista.getNombre());
            DecimalFormat decim = new DecimalFormat("0.00");
            Precio.setText(decim.format(Articuloslista.getPrecio()) + " €");
            favoritos = Integer.parseInt(Articuloslista.getFavorito().toString());

            if (favoritos == 0) {
                Favorito.setImageResource(R.drawable.estrellavacia);


            } else if (favoritos == 1) {
                Favorito.setImageResource(R.drawable.estrella);


            }


            @SuppressLint("UseCompatLoadingForDrawables") Drawable img = context.getResources().getDrawable(R.drawable.estrellablanca);
            img.setBounds(0, 0, 20, 20);

            BaseDatos resg2 = new BaseDatos(context, null);
            SQLiteDatabase bd2 = resg2.getReadableDatabase();
            Cursor cursor = bd2.rawQuery("SELECT Categoria FROM Categoriastabla WHERE id LIKE '"+Articuloslista.getCategoria()+"'", null);

            while(cursor.moveToNext()){
                categoria = cursor.getString(0);
            }

            final String finalCategoria = categoria;
            Nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.editararticulo(Nombre.getText().toString(), finalCategoria,Articuloslista.getIva().toString(),Articuloslista.getPrecio().toString());
                }
            });

            Nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.editararticulo(Nombre.getText().toString(),Articuloslista.getCategoria().toString(),Articuloslista.getIva().toString(),Articuloslista.getPrecio().toString());
                }
            });

            Borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BaseDatos resg2 = new BaseDatos(context, null);
                    SQLiteDatabase database = resg2.getWritableDatabase();
                    String sql = " DELETE FROM Articulos WHERE Nombre = ?;";

                    SQLiteStatement statement = database.compileStatement(sql);
                    statement.clearBindings();

                    statement.bindString(1, Articuloslista.getNombre());


                    statement.executeUpdateDelete();


                    ArticulosLista.remove(Articuloslista);
                    mListener2.refrescar(ArticulosLista);


                }
            });


            Favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (favoritos == 0) {
                        Favorito.setImageResource(R.drawable.estrella);
                        resg.actualizafavorito(1, Nombre.getText().toString());
                        favoritos = 1;




                    } else if (favoritos == 1) {
                        Favorito.setImageResource(R.drawable.estrellavacia);
                        resg.actualizafavorito(0, Nombre.getText().toString());
                        favoritos = 0;


                    }

                }


            });
        }


    }


}
