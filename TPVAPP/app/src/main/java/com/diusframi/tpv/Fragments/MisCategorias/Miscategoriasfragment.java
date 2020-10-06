package com.diusframi.tpv.Fragments.MisCategorias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.MisCategorias;
import com.diusframi.tpv.Constructores.Miscategoriasadapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Miscategoriasfragment extends Fragment implements Editarcategoria {

    //Declaraciones
    private RecyclerView recyclerView;
    private Fragment fragment = null;
    private Miscategoriasadapter adapter;
    ActionBarDrawerToggle Actionbar;
    private ArrayList<MisCategorias> listacategorias = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_miscategoriasfragment, container, false);

        //Declaraciones
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        linearLayout = view.findViewById(R.id.linearla);
        RadioGroup Botoneslinear = view.findViewById(R.id.botoneslinear);
        Button salir = view.findViewById(R.id.salirboton);
        Button nuevacategoria = view.findViewById(R.id.nuevacategoriaboton);
        adapter = new Miscategoriasadapter(Miscategoriasfragment.this,Miscategoriasfragment.this,  view.getContext(), listacategorias);
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


        BaseDatos resg = new BaseDatos(view.getContext(), null);
        SQLiteDatabase bd = resg.getReadableDatabase();
        Cursor cursortipos = bd.rawQuery("SELECT DISTINCT Categoria FROM Categoriastabla", null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        Drawable img = getContext().getResources().getDrawable(R.drawable.estrellablanca);
        img.setBounds(0, 0, size.x / 23, size.x / 23);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
        layoutParams.setMargins(0, size.x / 50, size.x / 25, size.x / 25);





            ArrayList<MisCategorias> lista = new ArrayList<>();
            BaseDatos resg3 = new BaseDatos(view.getContext(), null);
            SQLiteDatabase bd3 = resg3.getReadableDatabase();
            Cursor cursor2 = bd3.rawQuery("SELECT Categoria FROM Categoriastabla", null);
            String categoria;

            while (cursor2.moveToNext()) {
                categoria = cursor2.getString(0);


                lista.add(new MisCategorias(categoria));
                adapter.setCategoriasLista(lista);
            }
            adapter = new Miscategoriasadapter(Miscategoriasfragment.this, Miscategoriasfragment.this, view.getContext(), lista);
            recyclerView.setAdapter(adapter);







        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), Venta.class);
                startActivity(i);
            }
        });


        nuevacategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CrearNuevaCategoria();
                linearLayout.setVisibility(View.GONE);

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
    }




    public void refrescar(ArrayList<MisCategorias> CategoriasLista) {
        adapter = new Miscategoriasadapter(Miscategoriasfragment.this, Miscategoriasfragment.this, getContext(), CategoriasLista);
        adapter.setCategoriasLista(CategoriasLista);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);


    }

    @Override
    public void editarcategoria(String categoria) {
        fragment = new CrearNuevaCategoria();
        linearLayout.setVisibility(View.GONE);

        if (fragment != null) {

            Bundle bundle = new Bundle();
            bundle.putString("nombrecategoria", categoria);
            Fragment fragment = new CrearNuevaCategoria();
            fragment.setArguments(bundle);
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment, fragment);
            ft.commit();
            linearLayout.setVisibility(View.GONE);

        }
    }
}