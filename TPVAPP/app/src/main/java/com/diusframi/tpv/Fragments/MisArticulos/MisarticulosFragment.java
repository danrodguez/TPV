package com.diusframi.tpv.Fragments.MisArticulos;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.Articulo;
import com.diusframi.tpv.Constructores.ArticuloAdapter;
import com.diusframi.tpv.Constructores.ArticuloAdaptercategoria;
import com.diusframi.tpv.Fragments.EditarArticulo.EditarArticuloFragment;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MisarticulosFragment extends Fragment implements Editararticulo {

    //Declaraciones
    private RecyclerView recyclerView;
    private Fragment fragment = null;
    private ArticuloAdapter adapter;
    ActionBarDrawerToggle Actionbar;
    private ArrayList<Articulo> listarticulos = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_misarticulos_fragment, container, false);

        //Declaraciones
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        linearLayout = view.findViewById(R.id.linearla);
        RadioGroup Botoneslinear = view.findViewById(R.id.botoneslinear);
        Button salir = view.findViewById(R.id.salirboton);
        Button nuevoarticulo = view.findViewById(R.id.nuevoarticuloboton);
        adapter = new ArticuloAdapter(MisarticulosFragment.this, MisarticulosFragment.this, view.getContext(), listarticulos);
        this.recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());


        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);


        BaseDatos resg = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getReadableDatabase();
        Cursor cursortipos = bd.rawQuery("SELECT DISTINCT Categoria FROM Categoriastabla", null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        Drawable img = getContext().getResources().getDrawable(R.drawable.estrellablanca);
        img.setBounds(0, 0, size.x / 23, size.x / 23);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
        layoutParams.setMargins(0, size.x / 50, size.x / 25, size.x / 25);

//Boton de favorito
        final RadioButton btFavorito = new RadioButton(view.getContext());

        btFavorito.setPadding(size.x / 20, size.x / 50, 0, size.x / 50);
        btFavorito.setText("Favorito");
        btFavorito.setCompoundDrawablesRelative(img, null, null, null);
        btFavorito.setBackgroundResource(R.drawable.a);
        btFavorito.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btFavorito.setLayoutParams(layoutParams);
        btFavorito.setPaddingRelative(size.x / 55, 0, 0, 0);
        btFavorito.setTextColor(ContextCompat.getColor(getActivity(), R.color.primary_text));
        btFavorito.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        btFavorito.setAllCaps(false);
        btFavorito.setButtonDrawable(android.R.color.transparent);
        Botoneslinear.addView(btFavorito);

//Botones de categorias
        while (cursortipos.moveToNext()) {

            final RadioButton btCategory = new RadioButton(view.getContext());
            btCategory.setText(cursortipos.getString(0));
            btCategory.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            btCategory.setAllCaps(false);
            btCategory.setBackgroundResource(R.drawable.a);
            btCategory.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btCategory.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
            layoutParams2.setMargins(0, size.x / 50, size.x / 25, size.x / 25);
            btCategory.setLayoutParams(layoutParams2);
            btCategory.setButtonDrawable(android.R.color.transparent);
            btCategory.setPadding(size.x / 25, size.x / 50, size.x / 25, size.x / 50);
            Botoneslinear.addView(btCategory);

//Al darle click a boton de categoria te muestra todos los articulos de esa categoria
            btCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Articulo> lista = new ArrayList<>();
                    BaseDatos resg2 = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    Cursor cursor = bd2.rawQuery("SELECT Nombre,Favorito,Precio,IVA,Base FROM Articulos INNER JOIN Categoriastabla on Categoriastabla.id = Articulos.Categorias WHERE Categoriastabla.Categoria  LIKE '" + btCategory.getText().toString() + "' ORDER BY Nombre ASC", null);
                    double precio;
                    String nombre;
                    int favorito;
                    int iva;
                    double precioiva;

                    while (cursor.moveToNext()) {
                        nombre = cursor.getString(0);
                        favorito = cursor.getInt(1);
                        precio = cursor.getDouble(2);
                        iva = cursor.getInt(3);
                        precioiva = cursor.getDouble(4);

                        lista.add(new Articulo(btCategory.getText().toString(), nombre, favorito, precio, iva, precioiva));
                        adapter.setArticulosLista(lista);

                    }
                    ArticuloAdaptercategoria adapter = new ArticuloAdaptercategoria(MisarticulosFragment.this, MisarticulosFragment.this, view.getContext(), lista);
                    recyclerView.setAdapter(adapter);

                    if (btCategory.isChecked()) {
                        recyclerView.setVisibility(View.VISIBLE);


                    }


                }
            });

            btFavorito.setChecked(true);
            ArrayList<Articulo> lista = new ArrayList<>();
            BaseDatos resg3 = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
            SQLiteDatabase bd3 = resg3.getReadableDatabase();
            Cursor cursor2 = bd3.rawQuery("SELECT Categorias,Nombre,Favorito,Precio,IVA,Base FROM Articulos WHERE Favorito LIKE '1' ORDER BY Nombre ASC", null);
            String categoria;
            double precio;
            String nombre;
            int favorito;
            int iva;
            double precioiva;

            while (cursor2.moveToNext()) {
                categoria = cursor2.getString(0);
                nombre = cursor2.getString(1);
                favorito = cursor2.getInt(2);
                precio = cursor2.getDouble(3);
                iva = cursor2.getInt(4);
                precioiva = cursor2.getDouble(5);

                lista.add(new Articulo(categoria, nombre, favorito, precio, iva, precioiva));
                adapter.setArticulosLista(lista);
            }
            adapter = new ArticuloAdapter(MisarticulosFragment.this, MisarticulosFragment.this, view.getContext(), lista);
            recyclerView.setAdapter(adapter);

            if (btFavorito.isChecked()) {

                recyclerView.setVisibility(View.VISIBLE);


            }


            btFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Articulo> lista = new ArrayList<>();
                    BaseDatos resg2 = new BaseDatos(getContext(), "BaseDatos", null, 1);
                    SQLiteDatabase bd2 = resg2.getReadableDatabase();
                    Cursor cursor = bd2.rawQuery("SELECT Categorias,Nombre,Favorito,Precio,IVA,Base FROM Articulos WHERE Favorito LIKE '1' ORDER BY Nombre ASC", null);
                    String tipo;
                    double precio;
                    String nombre;
                    int favorito;
                    int iva;
                    double precioiva;

                    while (cursor.moveToNext()) {
                        tipo = cursor.getString(0);
                        nombre = cursor.getString(1);
                        favorito = cursor.getInt(2);
                        precio = cursor.getDouble(3);
                        iva = cursor.getInt(4);
                        precioiva = cursor.getDouble(5);

                        lista.add(new Articulo(tipo, nombre, favorito, precio, iva, precioiva));
                        adapter.setArticulosLista(lista);
                    }

                    ArticuloAdapter adapter = new ArticuloAdapter(MisarticulosFragment.this, MisarticulosFragment.this, getContext(), lista);
                    recyclerView.setAdapter(adapter);

                    if (btFavorito.isChecked()) {

                        recyclerView.setVisibility(View.VISIBLE);


                    }


                }
            });
        }


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), Venta.class);
                startActivity(i);
            }
        });


        nuevoarticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new EditarArticuloFragment();
                linearLayout.setVisibility(View.GONE);

                if (fragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nombreproducto", "");
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_fragment, fragment);
                    ft.commit();
                }
            }
        });


        return view;
    }


    @Override
    public void editararticulo(String nombreproducto,String categoriaproducto, String ivaproducto, String precioproducto) {

        fragment = new EditarArticuloFragment();
        linearLayout.setVisibility(View.GONE);

        if (fragment != null) {

            Bundle bundle = new Bundle();
            bundle.putString("nombreproducto", nombreproducto);
            bundle.putString("categoriaproducto", categoriaproducto);
            bundle.putString("ivaproducto", ivaproducto);
            bundle.putString("precioproducto", precioproducto);
            Fragment fragment = new EditarArticuloFragment();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment, fragment);
            ft.commit();
            linearLayout.setVisibility(View.GONE);

        }
    }

    public void refrescar(ArrayList<Articulo> articulosLista) {
        adapter = new ArticuloAdapter(MisarticulosFragment.this, MisarticulosFragment.this, getContext(), articulosLista);
        adapter.setArticulosLista(articulosLista);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);


    }
}