package com.diusframi.tpv.Fragments.MisVentas;


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

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.MiVenta;
import com.diusframi.tpv.Constructores.MiVentaAdapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

public class MisVentasFragment extends Fragment implements Ticket {

    //Declaraciones
    private RecyclerView recyclerView;
    private MiVentaAdapter adapter;
    private ArrayList<MiVenta> listamisventas = new ArrayList<>();
    private ArrayList<MiVenta> lista = new ArrayList<>();
    ActionBarDrawerToggle Actionbar;
    CalendarView calendario;
    String fecha;
    String numero = "";
    int filtrocalendario = 0;
    int filtronumero = 0;
    int i = 0;
    Button quitarfiltros;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_mis_ventas_fragment, container, false);

        //Cambiar el nombre a la barra
        requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Mis ventas");

        //Declaraciones
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        ImageButton filtroqr = view.findViewById(R.id.filtroqr);
        ImageButton filtrocalendario = view.findViewById(R.id.filtrocalendario);
        Button filtronumero = view.findViewById(R.id.filtronumero);
        Button salir = view.findViewById(R.id.salirboton);
        quitarfiltros = view.findViewById(R.id.quitarfiltros);
        adapter = new MiVentaAdapter(MisVentasFragment.this,view.getContext(), listamisventas);
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
        Cursor cursor = bd.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes ORDER BY id DESC", null);

        String id;
        String fecha;
        String total;


        while (cursor.moveToNext()) {
            id = cursor.getString(0);
            fecha = cursor.getString(1);
            total = cursor.getString(2);
            if (i == 0) {
                i = 1;
            } else if (i == 1) {
                i = 0;
            }
            lista.add(new MiVenta(Integer.parseInt(id), fecha, Double.parseDouble(total), i,false));


        }
cursor.close();
        Cursor cursor2 = bd.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones ORDER BY id DESC", null);

        String id2;
        String fecha2;
        String total2;


        while (cursor2.moveToNext()) {
            id2 = cursor2.getString(0);
            fecha2 = cursor2.getString(1);
            total2 = cursor2.getString(2);
            if (i == 0) {
                i = 1;
            } else if (i == 1) {
                i = 0;
            }
            lista.add(new MiVenta(Integer.parseInt(id2), fecha2, Double.parseDouble(total2), i,true));


        }
cursor2.close();


        adapter.setMisVentasLista(lista);
        cursor.close();
        bd.close();
        adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
        recyclerView.setAdapter(adapter);


        quitarfiltros.setEnabled(false);
        if (!quitarfiltros.isEnabled()) {
            quitarfiltros.setBackgroundResource(R.drawable.botongrisclaro);
        } else {
            quitarfiltros.setBackgroundResource(R.drawable.botonnaranja);
        }


        salir.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), Venta.class);
            startActivity(i);
        });


        filtrocalendario.setOnClickListener(v -> openDialogcalendario());

        filtronumero.setOnClickListener(v -> openDialognumero());

        filtroqr.setOnClickListener(v -> openDialogqr());

//Boton resetea la lista y quita los filtros
        quitarfiltros.setOnClickListener(v -> {


            quitarfiltros.setBackgroundResource(R.drawable.botongrisclaro);
            quitarfiltros.setEnabled(false);

            lista.clear();



            BaseDatos resg1 = new BaseDatos(getContext(), null);
            SQLiteDatabase bd1 = resg1.getReadableDatabase();
            Cursor cursor1 = bd1.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes  ORDER BY id DESC", null);

            String id1;
            String fecha1;
            String total1;


            while (cursor1.moveToNext()) {
                id1 = cursor1.getString(0);
                fecha1 = cursor1.getString(1);
                total1 = cursor1.getString(2);
                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new MiVenta(Integer.parseInt(id1), fecha1, Double.parseDouble(total1), i,false));


            }
cursor1.close();
            Cursor cursor21 = bd1.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones  ORDER BY id DESC", null);

            String id21;
            String fecha21;
            String total21;


            while (cursor21.moveToNext()) {
                id21 = cursor21.getString(0);
                fecha21 = cursor21.getString(1);
                total21 = cursor21.getString(2);

                lista.add(new MiVenta(Integer.parseInt(id21), fecha21, Double.parseDouble(total21), i,true));


            }

cursor21.close();

            adapter.setMisVentasLista(lista);
            cursor1.close();
            bd1.close();
            adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);




});
        return view;
    }






    public void openDialogcalendario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventana_filtro_calendario_mis_ventas_fragment, null);

        Button cancelar = view.findViewById(R.id.cancelarboton);
        Button filtrar = view.findViewById(R.id.aceptarboton);
        calendario = view.findViewById(R.id.calendario);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        builder.setView(view);

        // Get the custom alert dialog view widgets reference
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
            Cursor cursor = bd.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes  ORDER BY id DESC", null);

            String id;
            String fecha;
            String total;


            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                fecha = cursor.getString(1);
                total = cursor.getString(2);
                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new MiVenta(Integer.parseInt(id), fecha, Double.parseDouble(total), i,false));


            }
            cursor.close();

            Cursor cursor2 = bd.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones  ORDER BY id DESC", null);

            String id2;
            String fecha2;
            String total2;


            while (cursor2.moveToNext()) {
                id2 = cursor2.getString(0);
                fecha2 = cursor2.getString(1);
                total2 = cursor2.getString(2);

                lista.add(new MiVenta(Integer.parseInt(id2), fecha2, Double.parseDouble(total2), i,true));


            }

cursor2.close();

            adapter.setMisVentasLista(lista);
            cursor.close();
            bd.close();
            adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);

            dialog.dismiss();
        });

        //Boton filtrar filtra la lista por calendario
        filtrar.setOnClickListener(v -> {

            filtrocalendario = 1;


            quitarfiltros.setBackgroundResource(R.drawable.botonnaranja);
            quitarfiltros.setEnabled(true);

             String fechatexto = fecha;

            if (fechatexto != null) {

                ArrayList<MiVenta> lista = new ArrayList<>();
                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getReadableDatabase();

                Cursor cursor2 = bd.rawQuery("SELECT Id,FechaTexto,Total FROM Ordenes WHERE FechaTexto LIKE '" + fechatexto + "'  ORDER BY id DESC", null);

                int id;
                double total;
                String fechat;

                while (cursor2.moveToNext()) {
                    id = cursor2.getInt(0);
                    fechat = cursor2.getString(1);
                    total = cursor2.getDouble(2);
                    if (i == 0) {
                        i = 1;
                    } else if (i == 1) {
                        i = 0;
                    }
                    lista.add(new MiVenta(id, fechat, total, i, false));

                }

                Cursor cursor3 = bd.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones  WHERE FechaTexto LIKE '" + fechatexto +"'  ORDER BY id DESC", null);

                String id2;
                String fecha2;
                String total2;


                while (cursor3.moveToNext()) {
                    id2 = cursor3.getString(0);
                    fecha2 = cursor3.getString(1);
                    total2 = cursor3.getString(2);

                    lista.add(new MiVenta(Integer.parseInt(id2), fecha2, Double.parseDouble(total2), i,true));


                }


                cursor2.close();
                cursor3.close();
                bd.close();
                MiVentaAdapter adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
                recyclerView.setAdapter(adapter);
                adapter.setMisVentasLista(lista);

            }else{
                lista.clear();




                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getReadableDatabase();
                Cursor cursor = bd.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes  ORDER BY id DESC", null);

                String id;
                String fecha;
                String total;


                while (cursor.moveToNext()) {
                    id = cursor.getString(0);
                    fecha = cursor.getString(1);
                    total = cursor.getString(2);
                    if (i == 0) {
                        i = 1;
                    } else if (i == 1) {
                        i = 0;
                    }
                    lista.add(new MiVenta(Integer.parseInt(id), fecha, Double.parseDouble(total), i, true));
                    adapter.setMisVentasLista(lista);

                }

                cursor.close();
                bd.close();
                adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
                recyclerView.setAdapter(adapter);
                adapter.setMisVentasLista(lista);
                dialog.dismiss();
            }

            dialog.dismiss();
        });



        // Display the custom alert dialog on interface
        dialog.show();
    }

    public void openDialognumero() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventana_filtro_numero_mis_ventas_fragment, null);

        Button cancelar = view.findViewById(R.id.cancelarboton);
        Button filtrar = view.findViewById(R.id.filtronumero);
        final EditText numeroedit = view.findViewById(R.id.numeroedittext);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        builder.setView(view);


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
            Cursor cursor = bd.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes  ORDER BY id DESC", null);

            String id;
            String fecha;
            String total;


            while (cursor.moveToNext()) {
                id = cursor.getString(0);
                fecha = cursor.getString(1);
                total = cursor.getString(2);
                if (i == 0) {
                    i = 1;
                } else if (i == 1) {
                    i = 0;
                }
                lista.add(new MiVenta(Integer.parseInt(id), fecha, Double.parseDouble(total), i,false));


            }

            Cursor cursor2 = bd.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones  ORDER BY id DESC", null);

            String id2;
            String fecha2;
            String total2;


            while (cursor2.moveToNext()) {
                id2 = cursor2.getString(0);
                fecha2 = cursor2.getString(1);
                total2 = cursor2.getString(2);

                lista.add(new MiVenta(Integer.parseInt(id2), fecha2, Double.parseDouble(total2), i,true));


            }

cursor2.close();

            adapter.setMisVentasLista(lista);
            cursor.close();
            bd.close();
            adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
            recyclerView.setAdapter(adapter);
            dialog.dismiss();
        });

        //Boton filtrar filtra la lista por calendario
        filtrar.setOnClickListener(v -> {

            filtronumero = 1;


            quitarfiltros.setBackgroundResource(R.drawable.botonnaranja);
            quitarfiltros.setEnabled(true);
             numero = numeroedit.getText().toString();
            lista.clear();

                                            BaseDatos resg = new BaseDatos(getContext(), null);
                            SQLiteDatabase bd = resg.getReadableDatabase();
                            Cursor cursor = bd.rawQuery("SELECT id,FechaTexto,Total FROM Ordenes WHERE id LIKE '"+numero+"'  ORDER BY id DESC", null);

                            String id;
                            String fecha;
                            String total;


                            while (cursor.moveToNext()) {
                                id = cursor.getString(0);
                                fecha = cursor.getString(1);
                                total = cursor.getString(2);
                                if (i == 0) {
                                    i = 1;
                                } else if (i == 1) {
                                    i = 0;
                                }
                                lista.add(new MiVenta(Integer.parseInt(id), fecha, Double.parseDouble(total), i, false));
                                                              }


cursor.close();


        Cursor cursor2 = bd.rawQuery("SELECT id,FechaTexto,Total FROM Devoluciones  WHERE id LIKE '"+numero+"'  ORDER BY id DESC", null);

        String id2;
        String fecha2;
        String total2;


            while (cursor2.moveToNext()) {
            id2 = cursor2.getString(0);
            fecha2 = cursor2.getString(1);
            total2 = cursor2.getString(2);

            lista.add(new MiVenta(Integer.parseInt(id2), fecha2, Double.parseDouble(total2), i,true));


        }
            cursor2.close();
            adapter.setMisVentasLista(lista);
                            cursor.close();
                            bd.close();
                            adapter = new MiVentaAdapter(MisVentasFragment.this,getContext(), lista);
                            recyclerView.setAdapter(adapter);

            dialog.dismiss();
        });

        dialog.show();
    }


    public void openDialogqr() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventana_filtro_q_r_mis_ventas_fragment, null);

        Button cancelar = view.findViewById(R.id.cancelarboton);
        Button filtrar = view.findViewById(R.id.filtronumero);
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
        cancelar.setOnClickListener(v -> dialog.dismiss());

        //Boton filtrar filtra la lista por calendario
        filtrar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }


    public void quitarfiltrobotoncolor(){

        if(filtronumero == 0 && filtrocalendario == 0){
            quitarfiltros.setBackgroundResource(R.drawable.botongrisclaro);
            quitarfiltros.setEnabled(false);
        }
    }


    @Override
    public void ticketefectivo(String ticketefectivo) {
        Bundle bundle = new Bundle();
        bundle.putString("orden", ticketefectivo);
        Fragment fragment = new MisVentasTicketEfectivo();
        fragment.setArguments(bundle);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment, fragment);
        ft.commit();
    }


    @Override
    public void ticketdevolucion(String ticketdevolucion) {
        Bundle bundle = new Bundle();
        bundle.putString("orden", ticketdevolucion);
        Fragment fragment = new MisVentasTicketDevolucion();
        fragment.setArguments(bundle);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment, fragment);
        ft.commit();
    }



    @Override
    public void tickettarjeta(String tickettarjeta) {
        Bundle bundle = new Bundle();
        bundle.putString("orden", tickettarjeta);
        Fragment fragment = new MisVentasTicketTarjeta();
        fragment.setArguments(bundle);
        FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_fragment, fragment);
        ft.commit();
    }
}
//}