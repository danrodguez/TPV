package com.diusframi.tpv.Fragments.TicketDeVenta;

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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.ArticuloVenta;
import com.diusframi.tpv.Constructores.ArticuloVentaAdapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TicketDeVentaFragment extends Fragment {
    double totalnumero = 0;
    DrawerLayout drawerLayout;
    LinearLayout LinearLayout;
    int orden = 0;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_ticket_de_venta_fragment, container, false);
        Button cobrarboton = view.findViewById(R.id.cobrar);
        ImageButton imprimirboton = view.findViewById(R.id.imprimir);
        ImageButton editarboton = view.findViewById(R.id.volver);
        TextView totaltexto = view.findViewById(R.id.total);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        LinearLayout = view.findViewById(R.id.linearLayout);
        ArrayList<ArticuloVenta> lista = new ArrayList<>();
        totalnumero = 0;
        BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        GridView gridview = view.findViewById(R.id.gridView);
        ActionBarDrawerToggle actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionbar);
        actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());

        ImageView configuracion = view.findViewById(R.id.configurar);
        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);






        SQLiteDatabase bd = resg.getReadableDatabase();
        final Cursor cursor = bd.rawQuery("SELECT Categorias, Nombre, Numero, Precio, Iva FROM ArticulosVenta WHERE Numero > '0'", null);
        String categorias;
        String nombre;
        int numero;
        double precio;
        int iva;

        int numeroid = 0;

        final Cursor cursorordentexto = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion ", null);

        if (cursorordentexto.moveToNext()) {
            numeroid = cursorordentexto.getInt(0);
        }

        final Cursor cursororden = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);

        if (cursororden.moveToNext()) {
            orden = cursororden.getInt(0);
        }

        orden = orden + 1;
        if(numeroid!=0){
            orden = numeroid;
        }

        while (cursor.moveToNext()) {
            categorias = cursor.getString(0);
            nombre = cursor.getString(1);
            numero = cursor.getInt(2);
            precio = cursor.getDouble(3);
            iva = cursor.getInt(4);
            totalnumero = totalnumero + (precio*numero);
            lista.add(new ArticuloVenta(categorias, nombre, numero, precio, iva));

        }


        cursor.close();
        bd.close();
        cursororden.close();



        ArticuloVentaAdapter adapter2 = new ArticuloVentaAdapter(getContext(), R.layout.ticketdeventaconfiguracion, lista);
        gridview.setAdapter(adapter2);


        totaltexto.setText(String.format("%.2f", totalnumero) + "€");





        configuracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogconfiguracion();
            }

        });


        imprimirboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Todavia por implementar",
                        Toast.LENGTH_LONG).show();
            }
        });


        cobrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDialogcobrar();

            }
        });


        editarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), Venta.class);
                startActivity(i);
            }
        });

        return view;
    }

    public void openDialogcobrar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_ventana_cobrar_ticket_de_venta_fragment, null);


        LinearLayout tarjeta = view.findViewById(R.id.tarjeta);
        LinearLayout efectivo = view.findViewById(R.id.efectivo);
        LinearLayout ticketrestaurante = view.findViewById(R.id.cheque);


        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(true);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        tarjeta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                          ArrayList<ArticuloVenta> lista2 = new ArrayList<>();
totalnumero = 0;
orden = 0;
                BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);


                SQLiteDatabase bd = resg.getReadableDatabase();
                final Cursor cursor = bd.rawQuery("SELECT Categorias, Nombre, Numero, Precio, Iva FROM ArticulosVenta WHERE Numero > '0'", null);
                String categorias;
                String nombre;
                int numero;
                double precio;
                int iva;
                     int numeroid = 0;

                final Cursor cursorordentexto = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion ", null);

                if (cursorordentexto.moveToNext()) {
                    numeroid = cursorordentexto.getInt(0);
                }

                final Cursor cursororden = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);

                if (cursororden.moveToNext()) {
                    orden = cursororden.getInt(0);
                }




                if(orden == 0){
                    orden = 1;
                }else{
                    orden = orden + 1;

                }

                if(numeroid!=0){
                    orden = numeroid;
                }
                while (cursor.moveToNext()) {
                    categorias = cursor.getString(0);
                    nombre = cursor.getString(1);
                    numero = cursor.getInt(2);
                    precio = cursor.getDouble(3);
                    iva = cursor.getInt(4);
                    totalnumero = totalnumero + (precio*numero);
                    lista2.add(new ArticuloVenta(categorias, nombre, numero, precio, iva));

                    resg.crearnuevaorden(orden, categorias, nombre, numero, precio, iva);


                }
                cursor.close();
                bd.close();
                cursororden.close();





                Intent i = new Intent(getContext(), VentaCobroTarjeta.class);
                i.putExtra("totalnumero", totalnumero);
                startActivity(i);
                dialog.dismiss();
            }
        });

        efectivo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ArrayList<ArticuloVenta> lista = new ArrayList<>();
                totalnumero = 0;
                orden = 0;
                BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);


                SQLiteDatabase bd = resg.getReadableDatabase();
                final Cursor cursor = bd.rawQuery("SELECT Categorias, Nombre, Numero, Precio, Iva FROM ArticulosVenta WHERE Numero > '0'", null);
                String categorias;
                String nombre;
                int numero;
                double precio;
                int iva;
                GridView gridview = view.findViewById(R.id.gridView);
                int numeroid = 0;

                final Cursor cursorordentexto = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion ", null);

                if (cursorordentexto.moveToNext()) {
                    numeroid = cursorordentexto.getInt(0);
                }

                final Cursor cursororden = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);

                if (cursororden.moveToNext()) {
                    orden = cursororden.getInt(0);
                }


                if(orden == 0){
                    orden = 1;
                }else{
                    orden = orden + 1;

                }


                if(numeroid!=0){
                    orden = numeroid;
                }

                while (cursor.moveToNext()) {
                    categorias = cursor.getString(0);
                    nombre = cursor.getString(1);
                    numero = cursor.getInt(2);
                    precio = cursor.getDouble(3);
                    iva = cursor.getInt(4);
                    totalnumero = totalnumero + (precio*numero);
                    lista.add(new ArticuloVenta(categorias, nombre, numero, precio, iva));

                    resg.crearnuevaorden(orden, categorias, nombre, numero, precio, iva);


                }
                cursor.close();
                bd.close();
                cursororden.close();





                Intent i = new Intent(getContext(), VentaIntroduceImporteEfectivo.class);
                i.putExtra("totalnumero", totalnumero);
                startActivity(i);
                dialog.dismiss();
            }
        });

        ticketrestaurante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), VentaIntroduceImporteTicketRestaurante.class);
                i.putExtra("totalnumero", totalnumero);
                startActivity(i);
                dialog.dismiss();
            }
        });


        // Display the custom alert dialog on interface
        dialog.show();
    }

    public void openDialogconfiguracion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ventaconfiguraciondialog, null);


        LinearLayout editarlineaslinear = view.findViewById(R.id.editarlineas);
        LinearLayout articulolinear = view.findViewById(R.id.articulo);
        LinearLayout borrarlinear = view.findViewById(R.id.borrar);




        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);
        dialog.setCanceledOnTouchOutside(true);
        editarlineaslinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent i = new Intent(getContext(), EditarLineas.class);
                startActivity(i);

                dialog.dismiss();
            }
        });

        articulolinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ArticuloRapido.class);
                startActivity(i);

                dialog.dismiss();
            }
        });

        borrarlinear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);
                resg.BorrarTicket();
                Intent i = new Intent(getContext(), Venta.class);
                startActivity(i);


                dialog.dismiss();
            }
        });


        // Display the custom alert dialog on interface
        dialog.show();
    }


}
