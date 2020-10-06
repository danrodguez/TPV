package com.diusframi.tpv.Fragments.MisCategorias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

public class CrearNuevaCategoria extends Fragment {
    DrawerLayout drawer;
    private Fragment fragment = null;
    EditText categoria;
    String nombrecategoria;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_crear_nueva_categoria, container, false);
categoria = view.findViewById(R.id.nombrecategoriaedit);
        drawer = view.findViewById(R.id.drawer_layout);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mis Categorías");
        nombrecategoria = getArguments().getString("nombrecategoria");

        if (nombrecategoria == null) {
            nombrecategoria = "";
        }
        final Button nuevoarticulo = view.findViewById(R.id.nuevoarticuloboton);
        Button salir = view.findViewById(R.id.salirboton);

        if (!nombrecategoria.equals("")) {
            categoria.setText(nombrecategoria);
        }

        if(!nombrecategoria.equals("") ){
            nuevoarticulo.setEnabled(true);
            nuevoarticulo.setBackgroundResource(R.drawable.botonnaranja);
        }



        nuevoarticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           String    nombrecategoria2 = categoria.getText().toString();

                BaseDatos resg = new BaseDatos(view.getContext(), null);
                SQLiteDatabase bd = resg.getWritableDatabase();
                Cursor cursortipos = bd.rawQuery("SELECT Categoria FROM Categoriastabla WHERE Categoria = '" + nombrecategoria + "'", null);


                if (cursortipos.moveToNext()) {
                    BaseDatos resg2 = new BaseDatos(view.getContext(), null);

                    Cursor cursortipos2 = bd.rawQuery("UPDATE Categoriastabla SET Categoria = '"+nombrecategoria2+"' WHERE Categoria = '"+nombrecategoria+"'", null);

                    cursortipos2.moveToFirst();
                    cursortipos2.close();

                    Intent i = new Intent(view.getContext(), Venta.class);
                    startActivity(i);
                } else {
                    int id = 0;

                    resg.CrearCategoria(
                      nombrecategoria2
                    );
                    Intent i = new Intent(view.getContext(), Venta.class);
                    startActivity(i);
                }
                cursortipos.close();
            }
        });



        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Miscategoriasfragment();
                drawer.setVisibility(View.GONE);

                if (fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nombrecategoria", "");
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_fragment, fragment);
                    ft.commit();
                }
            }
        });



        return view;
}}