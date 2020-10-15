package com.diusframi.tpv.Fragments.MisArqueos;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.Arqueo;
import com.diusframi.tpv.Constructores.ArqueoAdapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MisArqueosFragment extends Fragment implements Arqueoint {

    //Declaraciones
    private RecyclerView recyclerView;
    private ArqueoAdapter adapter;
    private ArrayList<Arqueo> listaarqueos = new ArrayList<>();
    private ArrayList<Arqueo> lista = new ArrayList<>();
    ActionBarDrawerToggle Actionbar;
    CalendarView calendario;
    String fecha;
    String numero;
    int i = 0;
    Button quitarfiltrosboton;
    int filtrocalendario = 0;
    int filtronumero = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mis_arqueos_fragment, container, false);

        //Cambiar el nombre a la barra
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Mis arqueos");

        //Declaraciones
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        Button filtronumeroboton = view.findViewById(R.id.filtronumero);
        ImageButton filtrocalendarioboton = view.findViewById(R.id.filtrocalendario);
        Button salirboton = view.findViewById(R.id.salir);
        quitarfiltrosboton = view.findViewById(R.id.quitarfiltros);
        adapter = new ArqueoAdapter(MisArqueosFragment.this,view.getContext(), listaarqueos);
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Añadir listaarqueos al recycler view
        recyclerView.setAdapter(adapter);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());


        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);


        BaseDatos resg = new BaseDatos(getContext(), null);
        SQLiteDatabase bd = resg.getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos", null);

        String id;
        String fecha;
        String descuadre;
        String total;


        while (cursor.moveToNext()) {
            id = cursor.getString(0);
            fecha = cursor.getString(1);
            descuadre = cursor.getString(2);
            total = cursor.getString(3);
            if (i == 0) {
                i = 1;
            } else if (i == 1) {
                i = 0;
            }
            lista.add(new Arqueo(Integer.parseInt(id), fecha, Double.parseDouble(descuadre), Double.parseDouble(total), i));
            adapter.setArqueosLista(lista);
        }
        adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
        recyclerView.setAdapter(adapter);


        //Abrir la opcion de filtro por numero
        filtronumeroboton.setOnClickListener(view1 -> openDialognumero());

        //Abrir la opcion de filtro por calendario
        filtrocalendarioboton.setOnClickListener(view12 -> openDialogcalendario());


        //Boton de salir va a Venta
        salirboton.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), Venta.class);
            startActivity(i);
        });

        //Boton resetea la lista y quita los filtros
        quitarfiltrosboton.setOnClickListener(v -> {


                quitarfiltrosboton.setBackgroundResource(R.drawable.botongrisclaro);
                quitarfiltrosboton.setEnabled(false);

            lista.clear();
            BaseDatos resg1 = new BaseDatos(getContext(), null);
            SQLiteDatabase bd1 = resg1.getReadableDatabase();

            Cursor cursor1 = bd1.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos", null);


            String id1;
            String fecha1;
            String descuadre1;
            String total1;


            while (cursor1.moveToNext()) {
                id1 = cursor1.getString(0);
                fecha1 = cursor1.getString(1);
                descuadre1 = cursor1.getString(2);
                total1 = cursor1.getString(3);

                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new Arqueo(Integer.parseInt(id1), fecha1, Double.parseDouble(descuadre1), Double.parseDouble(total1), i));
                adapter.setArqueosLista(lista);
            }
            ArqueoAdapter adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);
            cursor1.close();
            bd1.close();
        });

        cursor.close();
        bd.close();
        return view;
    }








    //Funcion que va a la clase que usa el filtro calendario
    public void openDialogcalendario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventana_filtro_calendario_mis_arqueos_fragment, null);

        Button cancelar = view.findViewById(R.id.cancelarboton);
        Button filtrar = view.findViewById(R.id.aceptarboton);
        calendario = view.findViewById(R.id.calendario);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        fecha = df.format(c);
        //show the selected date as a toast
        calendario.setOnDateChangeListener((view1, year, month, day) -> {
            month = month + 1;
            @SuppressLint("DefaultLocale") String formattedday = String.format("%02d", day);
            @SuppressLint("DefaultLocale") String formattedmonth = String.format("%02d", month);
            fecha = year + formattedmonth + formattedday;



        });


        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);


        //Boton cancelar no usa el filtro
        cancelar.setOnClickListener(v -> {
            lista.clear();
            filtrocalendario = 0;
            quitarfiltrobotoncolor();
            BaseDatos resg = new BaseDatos(getContext(), null);
            SQLiteDatabase bd = resg.getReadableDatabase();
            Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos", null);

            String id;
            String fecha;
            String descuadre;
            String total;


            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                fecha = cursor.getString(1);
                descuadre = cursor.getString(2);
                total = cursor.getString(3);
                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new Arqueo(Integer.parseInt(id), fecha, Double.parseDouble(descuadre), Double.parseDouble(total), i));
                adapter.setArqueosLista(lista);
            }
            adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);


            dialog.dismiss();
            bd.close();
            cursor.close();
        });

        //Boton filtrar filtra la lista por calendario
        filtrar.setOnClickListener(v -> {
            filtrocalendario = 1;


     quitarfiltrosboton.setBackgroundResource(R.drawable.botonnaranja);
            quitarfiltrosboton.setEnabled(true);
            String fechatexto = fecha;

            if (fechatexto != null) {

                ArrayList<Arqueo> lista = new ArrayList<>();
                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getReadableDatabase();

      Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos WHERE FechaTexto LIKE '" + fechatexto + "'", null);

                String id;
                String fecha;
                String descuadre;
                String total;


                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                    fecha = cursor.getString(1);
                    descuadre = cursor.getString(2);
                    total = cursor.getString(3);
                    if (i == 0) {
                        i = 1;
                    } else if (i == 1) {
                        i = 0;
                    }
                    cursor.close();
                    lista.add(new Arqueo(Integer.parseInt(id), fecha, Double.parseDouble(descuadre), Double.parseDouble(total), i));
                    adapter.setArqueosLista(lista);
                }
                adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
                recyclerView.setAdapter(adapter);
                adapter.setArqueosLista(lista);



            }else{
                lista.clear();



                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getReadableDatabase();


                Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos", null);

                String id;
                String fecha;
                String descuadre;
                String total;


                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                    fecha = cursor.getString(1);
                    descuadre = cursor.getString(2);
                    total = cursor.getString(3);
                    if (i == 0) {
                        i = 1;
                    } else if (i == 1) {
                        i = 0;
                    }
                    cursor.close();
                    lista.add(new Arqueo(Integer.parseInt(id), fecha, Double.parseDouble(descuadre), Double.parseDouble(total), i));
                    adapter.setArqueosLista(lista);
                }
                adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
                recyclerView.setAdapter(adapter);
                adapter.setArqueosLista(lista);


                dialog.dismiss();
            }

            dialog.dismiss();
        });



        // Display the custom alert dialog on interface
        dialog.show();

    }












    //Funcion que va a la clase que usa el filtro numero
    public void openDialognumero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventana_filtro_numero_mis_arqueos_fragment, null);

        Button cancelar = view.findViewById(R.id.cancelarboton);
        Button filtrar = view.findViewById(R.id.filtronumero);
        final EditText Numero = view.findViewById(R.id.numeroedittext);


        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);


        //Boton cancelar no usa el filtro
        cancelar.setOnClickListener(v -> {
            lista.clear();
            filtronumero = 0;
         quitarfiltrobotoncolor();
            BaseDatos resg = new BaseDatos(getContext(), null);
            SQLiteDatabase bd = resg.getReadableDatabase();
            Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos", null);

            String id;
            String fecha;
            String descuadre;
            String total;


            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                fecha = cursor.getString(1);
                descuadre = cursor.getString(2);
                total = cursor.getString(3);
                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new Arqueo(Integer.parseInt(id), fecha, Double.parseDouble(descuadre), Double.parseDouble(total), i));
                adapter.setArqueosLista(lista);
            }
            adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);
            adapter.setArqueosLista(lista);


            bd.close();
            cursor.close();
            dialog.dismiss();
        });

        //Boton filtrar filtra la lista por numero
        filtrar.setOnClickListener(v -> {
            filtronumero = 1;
            quitarfiltrosboton.setBackgroundResource(R.drawable.botonnaranja);
            quitarfiltrosboton.setEnabled(true);
            numero = Numero.getText().toString();

            ArrayList<Arqueo> lista = new ArrayList<>();
                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getReadableDatabase();

                Cursor cursor = bd.rawQuery("SELECT id,Fecha,Descuadre,TotalCalculado FROM Arqueos WHERE id LIKE '" + numero + "'", null);

                int id;
                double descuadre;
                double total;
                String fecha;

                while (cursor.moveToNext()) {
                    id = cursor.getInt(0);
                    fecha = cursor.getString(1);
                    descuadre = cursor.getDouble(2);
                    total = cursor.getDouble(3);


                    lista.add(new Arqueo(id, fecha, descuadre, total, i));
                    adapter.setArqueosLista(lista);


                }
                cursor.close();
                bd.close();
                ArqueoAdapter adapter = new ArqueoAdapter(MisArqueosFragment.this,getContext(), lista);
                recyclerView.setAdapter(adapter);




            dialog.dismiss();
        });



        // Display the custom alert dialog on interface
        dialog.show();
    }



    public void quitarfiltrobotoncolor(){

        if(filtronumero == 0 && filtrocalendario == 0){
            quitarfiltrosboton.setBackgroundResource(R.drawable.botongrisclaro);
            quitarfiltrosboton.setEnabled(false);
        }
    }


    @Override
    public void arqueo (String arqueo) {
        Intent i = new Intent(getContext(), MisArqueosArqueo.class);
        i.putExtra("arqueoid",arqueo);
        startActivity(i);


    }

}
