package com.diusframi.tpv.Fragments.Venta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.Articulo;
import com.diusframi.tpv.Constructores.Carrito;
import com.diusframi.tpv.Constructores.VentaAdapter;
import com.diusframi.tpv.Constructores.VentaNumeroAdapter;
import com.diusframi.tpv.Fragments.IngresarRetirar.IngresarRetirarFragment;
import com.diusframi.tpv.Fragments.MiCuenta.MiCuentaFragment;
import com.diusframi.tpv.Fragments.MisArqueos.MisArqueosFragment;
import com.diusframi.tpv.Fragments.MisArticulos.MisarticulosFragment;
import com.diusframi.tpv.Fragments.MisCategorias.Miscategoriasfragment;
import com.diusframi.tpv.Fragments.MisVentas.MisVentasFragment;
import com.diusframi.tpv.Fragments.TicketDeVenta.TicketDeVentaFragment;
import com.diusframi.tpv.Fragments.TotalizarCierreCaja.TotalizarCierreCajaFragment;
import com.diusframi.tpv.MainActivity;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;

public class Venta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Botonventa {

    //Declaraciones
    Toolbar toolbar;
    FragmentContainerView fragment;
    public Button verticket;
    Button borrarboton;
    LinearLayout lineav;
    RecyclerView recyclerView;
    private VentaAdapter adapter;
    private VentaNumeroAdapter adapter2;
    DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle Actionbar;
    private ArrayList<Articulo> listarticulos = new ArrayList<>();
    private ArrayList<Carrito> listarticulos2 = new ArrayList<>();
    int i;
    String ticketventa = "";
    String misventas = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        //Declaraciones
        fragment = findViewById(R.id.content_fragment);
        borrarboton = findViewById(R.id.borrartodo);
        lineav = findViewById(R.id.linearlinea);
        linearLayout = findViewById(R.id.linearl);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        recyclerView = findViewById(R.id.RecyclerView);
        verticket = findViewById(R.id.Verticket);
        RadioGroup Botoneslinear = findViewById(R.id.botoneslinear);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Actionbar = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        //Hacer menu transparente

        drawerLayout.setDrawerElevation(0);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        i = 0;
        Actionbar = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new VentaNumeroAdapter(Venta.this, this, listarticulos2);
        adapter = new VentaAdapter(this, this, listarticulos);
        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter2);


        Bundle extras = getIntent().getExtras();
        if(extras != null) {
             ticketventa = extras.getString("ticketventa");
             misventas = extras.getString("misventas");
            assert ticketventa != null;
            if(ticketventa.equals("si")){
                Fragment fragment = new TicketDeVentaFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_fragment, fragment);
                ft.commit();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
            }else {
                assert misventas != null;
                if (misventas.equals("si")){
                    Fragment fragment = new MisVentasFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_fragment, fragment);
                    ft.commit();
                    linearLayout.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                }
            }
        }


        final BaseDatos resga = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bda = resga.getReadableDatabase();
        @SuppressLint("Recycle") final Cursor cursortiposa = bda.rawQuery("SELECT * FROM ArticulosVenta", null);
        if (cursortiposa.moveToFirst()) {
            borrarboton.setEnabled(true);
            borrarboton.setBackgroundResource(R.drawable.botonnaranja);
            borrarboton.setTextColor(Color.WHITE);
        }


        final BaseDatos resg = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd = resg.getReadableDatabase();
        @SuppressLint("Recycle") final Cursor cursortipos = bd.rawQuery("SELECT DISTINCT Categoria FROM Categoriastabla", null);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final RadioButton btFavorito = new RadioButton(this);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable img = getApplicationContext().getDrawable(R.drawable.estrellablanca);
        assert img != null;
        img.setBounds(0, 0, size.x / 23, size.x / 23);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
        layoutParams.setMargins(0, size.x / 50, size.x / 25, size.x / 25);
        btFavorito.setText("Favorito");
        btFavorito.setPadding(size.x / 20, size.x / 50, 0, size.x / 50);
        btFavorito.setBackgroundResource(R.drawable.a);
        btFavorito.setLayoutParams(layoutParams);
        btFavorito.setPaddingRelative(size.x / 55, 0, 0, 0);
        btFavorito.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        btFavorito.setCompoundDrawables(img, null, null, null);
        btFavorito.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
        btFavorito.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        btFavorito.setAllCaps(false);
        btFavorito.setButtonDrawable(android.R.color.transparent);
        Botoneslinear.addView(btFavorito);




        while (cursortipos.moveToNext()) {
            final RadioButton btCategory = new RadioButton(this);
            btCategory.setText(cursortipos.getString(0));
            btCategory.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            btCategory.setAllCaps(false);
            btCategory.setBackgroundResource(R.drawable.a);
            btCategory.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btCategory.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
            layoutParams2.setMargins(0, size.x / 50, size.x / 25, size.x / 25);
            btCategory.setLayoutParams(layoutParams2);
            btCategory.setButtonDrawable(android.R.color.transparent);
            btCategory.setPadding(size.x / 25, size.x / 50, size.x / 25, size.x / 50);
            Botoneslinear.addView(btCategory);


            btCategory.setOnClickListener(v -> {
                ArrayList<Articulo> lista = new ArrayList<>();
                ArrayList<Carrito> lista2 = new ArrayList<>();
                ArrayList<Carrito> lista3 = new ArrayList<>();
                BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
                SQLiteDatabase bd2 = resg2.getReadableDatabase();

                @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre,Numero,Precio,Iva FROM ArticulosVenta WHERE Categorias  LIKE '" + btCategory.getText().toString() + "' ORDER BY Nombre ASC", null);
                @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Nombre,Numero,Precio,Iva FROM ArticulosVenta WHERE Categorias  LIKE '" + btCategory.getText().toString() + "' ORDER BY Nombre ASC", null);
                BaseDatos resg3 = new BaseDatos(getApplicationContext(), null);
                SQLiteDatabase bd3 = resg3.getReadableDatabase();
                @SuppressLint("Recycle") Cursor cursor5 = bd3.rawQuery("SELECT Nombre,Precio,Iva FROM Articulos INNER JOIN Categoriastabla on Categoriastabla.id = Articulos.Categorias WHERE Categoriastabla.Categoria  LIKE '" + btCategory.getText().toString() + "' ORDER BY Nombre ASC", null);

                if (cursor3.moveToFirst()) {
                    double precio;
                    String nombre;
                    int numero;
                    int iva;
                    cursor3.close();
                    while (cursor2.moveToNext()) {
                        nombre = cursor2.getString(0);
                        numero = cursor2.getInt(1);
                        precio = cursor2.getDouble(2);
                        iva = cursor2.getInt(3);
                        lista2.add(new Carrito(btCategory.getText().toString(), nombre, numero, precio, iva));
                    }
                    cursor2.close();
                    while (cursor5.moveToNext()) {
                        nombre = cursor5.getString(0);
                        numero = 0;
                        precio = cursor5.getDouble(1);
                        iva = cursor5.getInt(2);
                        lista3.add(new Carrito(btCategory.getText().toString(), nombre, numero, precio, iva));
                    }
                    cursor5.close();
                    lista2.addAll(lista3);
                    ArrayList<Carrito> noRepeat = new ArrayList<>();
                    for (Carrito event : lista2) {
                        boolean isFound = false;
                        // check if the event name exists in noRepeat
                        for (Carrito e : noRepeat) {
                            if (e.getNombre().equals(event.getNombre()) || (e.equals(event))) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) noRepeat.add(event);
                    }
                    adapter2.setArticulosLista(noRepeat);
                    recyclerView.setAdapter(adapter2);
                    if (btCategory.isChecked()) {
                        lineav.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT Nombre,Favorito,Precio,IVA,Base FROM Articulos INNER JOIN Categoriastabla on Categoriastabla.id = Articulos.Categorias WHERE Categoriastabla.Categoria  LIKE '" + btCategory.getText().toString() + "' ORDER BY Nombre ASC", null);
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
                    VentaAdapter adapter = new VentaAdapter(Venta.this, getApplicationContext(), lista);
                    recyclerView.setAdapter(adapter);

                    if (btCategory.isChecked()) {
                        lineav.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    cursor.close();
                    bd2.close();
                }
            });


            btFavorito.setChecked(true);

            ArrayList<Articulo> lista = new ArrayList<>();
            ArrayList<Carrito> lista2 = new ArrayList<>();
            ArrayList<Carrito> lista3 = new ArrayList<>();
            BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
            SQLiteDatabase bd2 = resg2.getReadableDatabase();
            @SuppressLint("Recycle") final Cursor cursor = bd2.rawQuery("SELECT Categorias,Nombre,Favorito,Precio,IVA,Base FROM Articulos WHERE Favorito LIKE '1' ORDER BY Nombre ASC", null);
            String tipo;
            double precio;
            String nombre;
            int favorito;
            int iva;
            double precioiva;
            int numero;
            @SuppressLint("Recycle") final Cursor cursor2 = bd2.rawQuery("SELECT  ArticulosVenta.Categorias,ArticulosVenta.Nombre,ArticulosVenta.Numero,ArticulosVenta.Precio,ArticulosVenta.IVA FROM ArticulosVenta INNER JOIN Articulos ON  ArticulosVenta.Nombre = Articulos.Nombre WHERE Articulos.Favorito = '1' ORDER BY ArticulosVenta.Nombre ASC", null);
            @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT ArticulosVenta.Categorias,ArticulosVenta.Numero,ArticulosVenta.Precio,ArticulosVenta.IVA FROM ArticulosVenta INNER JOIN Articulos ON  ArticulosVenta.Nombre = Articulos.Nombre WHERE Articulos.Favorito = '1' ", null);
            @SuppressLint("Recycle") Cursor cursor5 = bd2.rawQuery("SELECT CategoriasTabla.Categoria,Articulos.Nombre,Articulos.Precio,Articulos.Iva FROM Articulos INNER JOIN CategoriasTabla ON CategoriasTabla.id = Articulos.Categorias AND Articulos.Favorito = '1' ORDER BY Articulos.Nombre ASC", null);

            if (cursor3.moveToFirst()) {
                while (cursor2.moveToNext()) {
                    tipo = cursor2.getString(0);
                    nombre = cursor2.getString(1);
                    numero = cursor2.getInt(2);
                    precio = cursor2.getDouble(3);
                    iva = cursor2.getInt(4);
                    lista2.add(new Carrito(tipo, nombre, numero, precio, iva));
                }
                cursor2.close();
                while (cursor5.moveToNext()) {
                    tipo = cursor5.getString(0);
                    nombre = cursor5.getString(1);
                    numero = 0;
                    precio = cursor5.getDouble(2);
                    iva = cursor5.getInt(3);
                    lista3.add(new Carrito(tipo, nombre, numero, precio, iva));
                }
                cursor5.close();
                lista2.addAll(lista3);
                ArrayList<Carrito> noRepeat = new ArrayList<>();
                for (Carrito event : lista2) {
                    boolean isFound = false;
                    // check if the event name exists in noRepeat
                    for (Carrito e : noRepeat) {
                        if (e.getNombre().equals(event.getNombre()) || (e.equals(event))) {
                            isFound = true;
                            break;
                        }
                    }
                    if (!isFound) noRepeat.add(event);
                }
                adapter2.setArticulosLista(noRepeat);
                recyclerView.setAdapter(adapter2);


            } else {
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
                cursor.close();
                bd2.close();
                VentaAdapter adapter = new VentaAdapter(Venta.this, getApplicationContext(), lista);
                recyclerView.setAdapter(adapter);


            }
            if (btFavorito.isChecked()) {
                lineav.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }


            btFavorito.setOnClickListener(v -> {
                ArrayList<Articulo> lista1 = new ArrayList<>();
                ArrayList<Carrito> lista21 = new ArrayList<>();
                ArrayList<Carrito> lista31 = new ArrayList<>();
                BaseDatos resg21 = new BaseDatos(getApplicationContext(), null);
                SQLiteDatabase bd21 = resg21.getReadableDatabase();

                @SuppressLint("Recycle") Cursor cursor1 = bd21.rawQuery("SELECT Categorias,Nombre,Favorito,Precio,IVA,Base FROM Articulos WHERE Favorito LIKE '1' ORDER BY Nombre ASC", null);
                @SuppressLint("Recycle") Cursor cursor21 = bd21.rawQuery("SELECT  ArticulosVenta.Categorias,ArticulosVenta.Nombre,ArticulosVenta.Numero,ArticulosVenta.Precio,ArticulosVenta.IVA FROM ArticulosVenta INNER JOIN Articulos ON  ArticulosVenta.Nombre = Articulos.Nombre WHERE Articulos.Favorito = '1' ORDER BY ArticulosVenta.Nombre ASC", null);
                @SuppressLint("Recycle") Cursor cursor31 = bd21.rawQuery("SELECT ArticulosVenta.Categorias,ArticulosVenta.Numero,ArticulosVenta.Precio,ArticulosVenta.IVA FROM ArticulosVenta INNER JOIN Articulos ON  ArticulosVenta.Nombre = Articulos.Nombre WHERE Articulos.Favorito = '1' ", null);
                @SuppressLint("Recycle") Cursor cursor51 = bd21.rawQuery("SELECT CategoriasTabla.Categoria,Articulos.Nombre,Articulos.Precio,Articulos.Iva FROM Articulos INNER JOIN CategoriasTabla ON CategoriasTabla.id = Articulos.Categorias AND Articulos.Favorito = '1' ORDER BY Nombre ASC", null);

                String tipo1;
                double precio1;
                String nombre1;
                int favorito1;
                int iva1;
                double precioiva1;
                int numero1;


                if (cursor31.moveToFirst()) {
                    while (cursor21.moveToNext()) {
                        tipo1 = cursor21.getString(0);
                        nombre1 = cursor21.getString(1);
                        numero1 = cursor21.getInt(2);
                        precio1 = cursor21.getDouble(3);
                        iva1 = cursor21.getInt(4);

                        lista21.add(new Carrito(tipo1, nombre1, numero1, precio1, iva1));
                    }
                    cursor31.close();
                    while (cursor51.moveToNext()) {
                        tipo1 = cursor51.getString(0);
                        nombre1 = cursor51.getString(1);
                        numero1 = 0;
                        precio1 = cursor51.getDouble(2);
                        iva1 = cursor51.getInt(3);

                        lista31.add(new Carrito(tipo1, nombre1, numero1, precio1, iva1));
                    }
                    cursor51.close();
                    lista21.addAll(lista31);

                    ArrayList<Carrito> noRepeat = new ArrayList<>();
                    for (Carrito event : lista21) {
                        boolean isFound = false;
                        // check if the event name exists in noRepeat
                        for (Carrito e : noRepeat) {
                            if (e.getNombre().equals(event.getNombre()) || (e.equals(event))) {
                                isFound = true;
                                break;
                            }
                        }
                        if (!isFound) noRepeat.add(event);
                    }

                    adapter2.setArticulosLista(noRepeat);


                    recyclerView.setAdapter(adapter2);

                } else {
                    while (cursor1.moveToNext()) {
                        tipo1 = cursor1.getString(0);
                        nombre1 = cursor1.getString(1);
                        favorito1 = cursor1.getInt(2);
                        precio1 = cursor1.getDouble(3);
                        iva1 = cursor1.getInt(4);
                        precioiva1 = cursor1.getDouble(5);

                        lista1.add(new Articulo(tipo1, nombre1, favorito1, precio1, iva1, precioiva1));
                        adapter.setArticulosLista(lista1);

                    }
                    cursor1.close();
                    VentaAdapter adapter = new VentaAdapter(Venta.this, getApplicationContext(), lista1);
                    recyclerView.setAdapter(adapter);
                }
                if (btFavorito.isChecked()) {
                    lineav.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                bd21.close();
            });
        }


        borrarboton.setOnClickListener(v -> {
            BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
            SQLiteDatabase database = resg2.getWritableDatabase();
            String sql = "DELETE FROM ArticulosVenta";

            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings();
            statement.executeUpdateDelete();

            ArrayList<Articulo> lista = new ArrayList<>();
            BaseDatos resg3 = new BaseDatos(getApplicationContext(), null);
            SQLiteDatabase bd2 = resg3.getReadableDatabase();

            @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT Categorias,Nombre,Favorito,Precio,IVA,Base FROM Articulos WHERE Favorito LIKE '1' ORDER BY Nombre ASC", null);
            if (btFavorito.isChecked()) {
                while (cursor.moveToNext()) {

                    String tipo;
                    double precio;
                    String nombre;
                    int favorito;
                    int iva;
                    double precioiva;


                    tipo = cursor.getString(0);
                    nombre = cursor.getString(1);
                    favorito = cursor.getInt(2);
                    precio = cursor.getDouble(3);
                    iva = cursor.getInt(4);
                    precioiva = cursor.getDouble(5);

                    lista.add(new Articulo(tipo, nombre, favorito, precio, iva, precioiva));
                    adapter.setArticulosLista(lista);

                }

                cursor.close();
                VentaAdapter adapter = new VentaAdapter(Venta.this, getApplicationContext(), lista);
                recyclerView.setAdapter(adapter);
            }


            recyclerView.invalidate();
            recyclerView.setAdapter(adapter);
            borrarboton.setBackgroundResource(R.drawable.botongrisclaro);
            borrarboton.setEnabled(false);
        });


        verticket.setOnClickListener(v -> {
            Fragment fragment = new TicketDeVentaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            linearLayout.setVisibility(View.GONE);
            toolbar.setVisibility(View.GONE);
            ft.replace(R.id.content_fragment, fragment);
            ft.commit();

        });


    }


    public void logout() {
        final BaseDatos resg = new BaseDatos(this, null);
        final SQLiteDatabase bdw = resg.getWritableDatabase();
        String sentenciaSQL = "INSERT INTO Login VALUES (0);";
        String sentenciaSQL2 = "UPDATE Usuarios SET activo = 0 WHERE activo = 1;";


        bdw.execSQL(sentenciaSQL);
        bdw.execSQL(sentenciaSQL2);
        bdw.close();
    }

    //si se cierra el proceso de la app hace logout
    public void onDestroy() {
        logout();
        super.onDestroy();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (Actionbar.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch (menuItem.getItemId()) {

            case R.id.nav_venta:
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_micuenta:
                fragment = new MiCuentaFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_misarticulos:
                fragment = new MisarticulosFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;

            case R.id.nav_miscategorias:
                fragment = new Miscategoriasfragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;

            case R.id.nav_misventas:
                fragment = new MisVentasFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_misarqueos:
                fragment = new MisArqueosFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_ingresarretirar:
                fragment = new IngresarRetirarFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_totalizarcierrecaja:
                fragment = new TotalizarCierreCajaFragment();
                linearLayout.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.nav_cerrarsesion:
                logout();
                Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i2);
                finish();
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment, fragment);
            ft.commit();

        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }


    @Override
    public void cambiarcolorbotonanaranja() {
        verticket.setBackgroundResource(R.drawable.botonnaranja);
        verticket.setEnabled(true);
    }

    @Override
    public void cambiarcolorbotonanaranjaborrar() {
        borrarboton.setBackgroundResource(R.drawable.botonnaranja);
        borrarboton.setTextColor(Color.WHITE);
        borrarboton.setEnabled(true);
    }

    @Override
    public void cambiarcolorbotongrisborrar() {
        borrarboton.setBackgroundResource(R.drawable.botongrisclaro);
        borrarboton.setEnabled(true);
    }

    @Override
    public void intentpreciovariable(String nombre) {
        Intent i = new Intent(getApplicationContext(), Articulopreciovariable.class);
        i.putExtra("nombre", nombre);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}