package com.diusframi.tpv.Fragments.TotalizarCierreCaja;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.InputDinero;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TotalizarCierreCajaFianzaFragment extends Fragment {

    private Button generararqueo;
    private Button cancelar;
    EditText importe;
    ActionBarDrawerToggle Actionbar;

    Cursor cursorhora;
    Cursor cursorentradas;
    Cursor cursorventas;
    Cursor cursorfecha;
    Cursor cursorsalidas;
    Cursor cursortarjeta;
    Cursor cursorefectivo;
    Cursor cursorefectivo2;
    Cursor cursornumeroarqueo;
    Cursor cursor10baseimponible;
    Cursor cursor21baseimponible;
    Cursor cursornumventas;
    Cursor cursornumventasfechamas;
    Cursor cursornumventasactual;
    Cursor cursornumventastodo;
    Cursor cursordevoluciones;
    Boolean hoy = false;
    long fecha = 0;
    Integer id = 0;
    long hora = 0;
    long horalong = 0;
    Double totalcalculadonumeroefectivo;
    String numeroarqueotexto = "";
    String numerofianzatexto = "";
    Integer numventasnumero = 0;
    Double ventasnumero = 0.0;
    Double movcajanumero = 0.0;
    Double totaldevolucionesnumero = 0.0;
    Double totaldevolucionesnumero10 = 0.0;
    Double totaldevolucionesnumero21 = 0.0;
    Double totalcalculadonumero = 0.0;
    Double nuevosaldoinicialnumero = 0.0;
    Double saldoinicialnumero = 0.0;
    Double ventasefectivonumero = 0.0;
    Double entradasnumero = 0.0;
    Double salidasnumero = 0.0;
    Double devolucionesnumeroefectivo = 0.0;
    Double devolucionesnumerotarjeta = 0.0;
    Double calculadoefectivonumero = 0.0;
    Double recuentoefectivonumero = 0.0;
    Double descuadrenumero = 0.0;
    Double retiradaefectivonumero = 0.0;
    Double fianzanumero = 0.0;
    Double efectivonumero = 0.0;
    Double tarjetanumero = 0.0;
    Double impuestos10baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    Double total10numero = 0.0;
    Double total21numero = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_totalizar_cierre_caja_fianza_fragment, container, false);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        cancelar = view.findViewById(R.id.cancelarbotontotalizar);
        generararqueo = view.findViewById(R.id.generararqueoboton);
        importe = view.findViewById(R.id.procimporteedit);
        InputDinero dinero = new InputDinero();
        importe.setFilters(new InputDinero[]{dinero});

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recuentoefectivonumero = bundle.getDouble("recuentoefectivo", recuentoefectivonumero);
        }

        final Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());
        generararqueo.setEnabled(false);

        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);


        importe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    generararqueo.setEnabled(true);
                    cancelar.setEnabled(true);
                } else {
                    generararqueo.setEnabled(false);
                    cancelar.setEnabled(false);
                }

                if (!generararqueo.isEnabled()) {
                    generararqueo.setBackgroundResource(R.drawable.botongrisclaro);
                } else {
                    generararqueo.setBackgroundResource(R.drawable.botonnaranja);
                }

                if (!cancelar.isEnabled()) {
                    cancelar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {
                    cancelar.setBackgroundResource(R.drawable.botonnaranja);
                    cancelar.setTextColor(Color.WHITE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getContext(), Venta.class);
                startActivity(i);
            }
        });


        generararqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BaseDatos resg = new BaseDatos(getContext(), null);
                SQLiteDatabase bd = resg.getWritableDatabase();


                //Fecha
                cursorfecha = bd.rawQuery("SELECT Fecha FROM Arqueos ORDER BY Fecha DESC", null);

                if (cursorfecha.moveToNext()) {
                    fecha = cursorfecha.getLong(0);

                }
                cursorhora = bd.rawQuery("SELECT Hora FROM Arqueos WHERE Fecha LIKE '"+fecha+"'ORDER BY Hora DESC", null);

                if (cursorhora.moveToNext()) {
                    hora = cursorhora.getLong(0);

                }



                horalong = hora;

                if (fecha == 0 && hora==0) {

                    Date c2 = Calendar.getInstance().getTime();
                    SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String diatexto = df2.format(c2);
                    fecha = Long.parseLong(diatexto);
                    Date c3 = Calendar.getInstance().getTime();
                    SimpleDateFormat df3 = new SimpleDateFormat("HHmmss", Locale.getDefault());
                    String diatexto2 = df3.format(c3);
                    horalong = Long.parseLong(diatexto2);

                    hoy = true;
                }


                //NumeroArqueo
                cursornumeroarqueo = bd.rawQuery("SELECT id FROM Arqueos ORDER BY id DESC", null);


                if (cursornumeroarqueo.moveToNext()) {
                    id = cursornumeroarqueo.getInt(0);

                }
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
                String aniotexto = df.format(c);
                id = id + 1;
                numeroarqueotexto = aniotexto + "/" + id;



                //Ventas
                String a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Ordenes WHERE Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total)  FROM Ordenes WHERE Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        ventasnumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }


                    if (!hoy && a.equals("")) {
                        cursorventas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE Fecha == '" + fecha + "' AND Hora >= '" + horalong + "' ", null);
                        if (cursorventas.moveToNext()) {
                            ventasnumero = cursorventas.getDouble(0);

                        }

                    } else if(hoy && a.equals("")) {
                        cursorventas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes ", null);
                        if (cursorventas.moveToNext()) {
                            ventasnumero = cursorventas.getDouble(0);

                        }
                    }




                // Numero de ventas
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Ordenes WHERE Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT COUNT(*) FROM Ordenes WHERE Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        numventasnumero = cursornumventasfechamas.getInt(0);
                        a = "si";
                    }

                }




    if (!hoy && a.equals("")) {
        cursornumventasactual = bd.rawQuery("SELECT COUNT(*) FROM Ordenes WHERE Fecha == '" + fecha + "' AND Hora >= '" + horalong + "'", null);
        if (cursornumventasactual.moveToNext()) {
            numventasnumero = cursornumventasactual.getInt(0);
        }

    } else if (hoy && a.equals("")){
           cursornumventastodo = bd.rawQuery("SELECT COUNT(*) FROM Ordenes ", null);
        if (cursornumventastodo.moveToNext()) {
            numventasnumero = cursornumventastodo.getInt(0);
        }
      }





                //Fianza
                numerofianzatexto = importe.getText().toString();
                nuevosaldoinicialnumero = Double.parseDouble(numerofianzatexto);
                resg.saldoinicial(nuevosaldoinicialnumero,id);



                //Ventas Efectivo
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Ordenes WHERE Fecha > '" + fecha + "' AND TipoPago = 'Efectivo' ", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo' AND Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        ventasefectivonumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }


                if (!hoy && a.equals("")) {
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo' AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                    if (cursorefectivo.moveToNext()) {
                        ventasefectivonumero = cursorefectivo.getDouble(0);
                    }

                }else  if (hoy && a.equals("")){
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
                    if (cursorefectivo.moveToNext()) {
                        ventasefectivonumero = cursorefectivo.getDouble(0);
                    }

                }


                //Entradas

                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT  Entradas  FROM CuadreCaja WHERE Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Entradas) FROM CuadreCaja WHERE Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        entradasnumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }


                if (!hoy && a.equals("")) {
                    cursorentradas = bd.rawQuery("SELECT SUM(Entradas) FROM CuadreCaja WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong +"'", null);
                    if (cursorentradas.moveToNext()) {

                        entradasnumero = cursorentradas.getDouble(0);
                    }
                } else if (hoy && a.equals("")){
                    cursorentradas = bd.rawQuery("SELECT  SUM(Entradas) FROM CuadreCaja", null);
                    if (cursorentradas.moveToNext()) {

                        entradasnumero = cursorentradas.getDouble(0);
                    }
                }

                //Devoluciones tarjeta

                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Devoluciones WHERE Fecha > '" + fecha + "' AND TipoPago LIKE 'Tarjeta'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE Fecha > '" + fecha + "' AND TipoPago LIKE 'Tarjeta'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        devolucionesnumerotarjeta = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }

                if (!hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones  WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' AND TipoPago LIKE 'Tarjeta'", null);
                    if (cursordevoluciones.moveToNext()) {
                        devolucionesnumerotarjeta = cursordevoluciones.getDouble(0);
                    }
                }else if (hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE TipoPago LIKE 'Tarjeta'", null);
                    if (cursordevoluciones.moveToNext()) {
                        devolucionesnumerotarjeta = cursordevoluciones.getDouble(0);
                    }
                }
                //Devoluciones efectivo

                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Devoluciones WHERE Fecha > '" + fecha + "' AND TipoPago LIKE 'Efectivo'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE Fecha > '" + fecha + "' AND TipoPago LIKE 'Efectivo'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        devolucionesnumeroefectivo = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }

                if (!hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones  WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' AND TipoPago LIKE 'Efectivo'", null);
                    if (cursordevoluciones.moveToNext()) {
                        devolucionesnumeroefectivo = cursordevoluciones.getDouble(0);
                    }
                }else if (hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE TipoPago LIKE 'Efectivo'", null);
                    if (cursordevoluciones.moveToNext()) {
                        devolucionesnumeroefectivo = cursordevoluciones.getDouble(0);
                    }
                }

                //Devoluciones 10
                a = "";

Double precio;
Integer numero;

                cursornumventasfechamas = bd.rawQuery("SELECT Precio FROM Devueltostemporal INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha > '" + fecha + "' AND DevueltosTemporal.Iva LIKE '10'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha > '" + fecha + "' AND DevueltosTemporal.Iva LIKE '10'", null);
                    while (cursornumventasfechamas.moveToNext()){
                        precio = cursornumventasfechamas.getDouble(0);
                        numero = cursornumventasfechamas.getInt(1);
                        totaldevolucionesnumero10 = (precio*numero) + totaldevolucionesnumero10 ;
                        a = "si";
                    }

                }



                if (!hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal  INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha >= '" + fecha + "' AND Devoluciones.Hora >= '" + horalong + "' AND DevueltosTemporal.Iva LIKE '10'", null);
                    while (cursordevoluciones.moveToNext()) {
                        precio = cursordevoluciones.getDouble(0);
                        numero = cursordevoluciones.getInt(1);
                        totaldevolucionesnumero10 = (precio*numero) + totaldevolucionesnumero10 ;
                    }
                }else if (hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT Precio,NumeroFROM Devueltostemporal WHERE Iva LIKE '10'", null);
                    while (cursordevoluciones.moveToNext()) {
                        precio = cursordevoluciones.getDouble(0);
                        numero = cursordevoluciones.getInt(1);
                        totaldevolucionesnumero10 = (precio*numero) + totaldevolucionesnumero10 ;
                    }
                }


                //Devoluciones 21
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Precio FROM Devueltostemporal INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha > '" + fecha + "' AND DevueltosTemporal.Iva LIKE '21'", null);
                while (cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha > '" + fecha + "' AND DevueltosTemporal.Iva LIKE '21'", null);
                    while (cursornumventasfechamas.moveToNext()){
                        precio = cursordevoluciones.getDouble(0);
                        numero = cursordevoluciones.getInt(1);
                        totaldevolucionesnumero21 = (precio*numero) + totaldevolucionesnumero21 ;
                        a = "si";
                    }

                }



                if (!hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal  INNER JOIN Devoluciones ON Devoluciones.id = Devueltostemporal.idorden WHERE Devoluciones.Fecha >= '" + fecha + "' AND Devoluciones.Hora >= '" + horalong + "' AND DevueltosTemporal.Iva LIKE '21'", null);
                    while (cursordevoluciones.moveToNext()) {
                        precio = cursordevoluciones.getDouble(0);
                        numero = cursordevoluciones.getInt(1);
                        totaldevolucionesnumero21 = (precio*numero) + totaldevolucionesnumero21 ;
                    }
                }else if (hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE Iva LIKE '21'", null);
                    while (cursordevoluciones.moveToNext()) {
                        precio = cursordevoluciones.getDouble(0);
                        numero = cursordevoluciones.getInt(1);
                        totaldevolucionesnumero21 = (precio*numero) + totaldevolucionesnumero21 ;
                    }
                }



                //Devoluciones
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Devoluciones WHERE Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        totaldevolucionesnumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }



                if (!hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones  WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                    if (cursordevoluciones.moveToNext()) {
                        totaldevolucionesnumero = cursordevoluciones.getDouble(0);
                    }
                }else if (hoy && a.equals("")) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones", null);
                    if (cursordevoluciones.moveToNext()) {
                        totaldevolucionesnumero = cursordevoluciones.getDouble(0);
                    }
                }


                //Salidas
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Salidas FROM CuadreCaja WHERE Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Salidas) FROM CuadreCaja WHERE Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        salidasnumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }
                if (!hoy && a.equals("")) {
                    cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja WHERE Fecha >= '" + fecha +"' AND Hora >= '" + horalong +"'", null);
                    if (cursorsalidas.moveToNext()) {
                        salidasnumero = cursorsalidas.getDouble(0);
                    }
                }else if (hoy && a.equals("")) {
                    cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja", null);
                    if (cursorsalidas.moveToNext()) {
                        salidasnumero = cursorsalidas.getDouble(0);
                    }
                }



                //Recuento efectivo declarado en intent arriba
                //Movimiento de caja
                movcajanumero = entradasnumero - salidasnumero;
                //Total calculado efectivo
                totalcalculadonumeroefectivo = (ventasefectivonumero + movcajanumero) - devolucionesnumeroefectivo;





                //Fianza es saldo inicial

                //Efectivo
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Ordenes WHERE Fecha > '" + fecha + "' AND TipoPago = 'Efectivo'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo' AND Fecha > '" + fecha + "'AND Hora > '" + horalong + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        efectivonumero = cursornumventasfechamas.getDouble(0);

                        a = "si";
                    }

                }
                if (!hoy && a.equals("")) {
                    cursorefectivo2 = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'  AND Fecha == '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                    if (cursorefectivo2.moveToNext()) {
                        efectivonumero = cursorefectivo2.getDouble(0);
                    }


                }else if (hoy && a.equals("")) {
                    cursorefectivo2 = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
                    if (cursorefectivo2.moveToNext()) {
                        efectivonumero = cursorefectivo2.getDouble(0);

                    }

                }




                //Tarjeta
                a = "";
                cursornumventasfechamas = bd.rawQuery("SELECT Total FROM Ordenes WHERE Fecha > '" + fecha + "' AND TipoPago = 'Tarjeta'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    cursornumventasfechamas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Tarjeta' AND Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        tarjetanumero = cursornumventasfechamas.getDouble(0);
                        a = "si";
                    }

                }
                if (!hoy && a.equals("")) {
                    cursortarjeta = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Tarjeta' AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                    if (cursortarjeta.moveToNext()) {
                        tarjetanumero = cursortarjeta.getDouble(0);
                    }
                }else if (hoy && a.equals("")) {
                    cursortarjeta = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Tarjeta'", null);
                    if (cursortarjeta.moveToNext()) {
                        tarjetanumero = cursortarjeta.getDouble(0);
                    }
                }



                double precio10;
                int numero10;
                double precio21;
                int numero21;

                //Impuestos 10% cuota
                a = "";

                    cursornumventasfechamas = bd.rawQuery("SELECT Vendidos.Precio,Vendidos.Numero FROM Vendidos INNER JOIN Ordenes ON Ordenes.id = Vendidos.idorden WHERE Vendidos.Iva = '10' AND Ordenes.Fecha > '" + fecha + "'", null);
                    if(cursornumventasfechamas.moveToFirst()){
                        precio10 = cursor10baseimponible.getDouble(0);
                        numero10 = cursor10baseimponible.getInt(1);
                        total10numero = (precio10*numero10) + total10numero ;
                        a = "si";
                    }


                if (!hoy && a.equals("")) {
                    cursor10baseimponible = bd.rawQuery("SELECT Vendidos.Precio,Vendidos.Numero FROM Vendidos INNER JOIN Ordenes ON Ordenes.id = Vendidos.idorden WHERE Vendidos.Iva = '10' AND Ordenes.Fecha >= '" + fecha + "' AND Ordenes.Hora >= '" + horalong + "'", null);
     while (cursor10baseimponible.moveToNext()) {
                        precio10 = cursor10baseimponible.getDouble(0);
                        numero10 = cursor10baseimponible.getInt(1);
                        total10numero = (precio10*numero10) + total10numero ;
                    }
                }else if (hoy && a.equals("")) {
                    cursor10baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Vendidos WHERE Iva = '10'", null);
                    while (cursor10baseimponible.moveToNext()) {
                        precio10 = cursor10baseimponible.getDouble(0);
                        numero10 = cursor10baseimponible.getInt(1);
                        total10numero = (precio10*numero10) + total10numero ;
                    }
                }

              total10numero =  total10numero - totaldevolucionesnumero10;
                impuestos10baseimponiblenumero = total10numero/1.10;
                impuestos10cuotanumero =  total10numero -impuestos10baseimponiblenumero;


                //Impuestos 21% cuota
                a = "";

                cursornumventasfechamas = bd.rawQuery("SELECT Vendidos.Precio,Vendidos.Numero FROM Vendidos INNER JOIN Ordenes ON Ordenes.id = Vendidos.idorden WHERE Vendidos.Iva = '21' AND Ordenes.Fecha > '" + fecha + "'", null);
                if(cursornumventasfechamas.moveToFirst()){
                    precio21 = cursor21baseimponible.getDouble(0);
                    numero21 = cursor21baseimponible.getInt(1);
                    total21numero = (precio21*numero21) + total10numero ;
                    a = "si";
                }


                if (!hoy && a.equals("")) {
                    cursor21baseimponible = bd.rawQuery("SELECT Vendidos.Precio,Vendidos.Numero FROM Vendidos INNER JOIN Ordenes ON Ordenes.id = Vendidos.idorden WHERE Vendidos.Iva = '21' AND Ordenes.Fecha >= '" + fecha + "' AND Ordenes.Hora >= '" + horalong + "'", null);
                    while (cursor21baseimponible.moveToNext()) {
                        precio21 = cursor21baseimponible.getDouble(0);
                        numero21 = cursor21baseimponible.getInt(1);
                        total21numero = (precio21*numero21) + total21numero ;
                    }
                }else if (hoy && a.equals("")) {
                    cursor21baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Vendidos WHERE Iva = '21'", null);
                    while (cursor21baseimponible.moveToNext()) {
                        precio21 = cursor21baseimponible.getDouble(0);
                        numero21 = cursor21baseimponible.getInt(1);
                        total21numero = (precio21*numero21) + total21numero ;
                    }
                }



                //Impuestos 21% base imponible
                impuestos21baseimponiblenumero = total21numero/1.21;
                impuestos21cuotanumero =  total21numero -impuestos21baseimponiblenumero;



                //Total calculado
                totalcalculadonumero = ventasnumero + movcajanumero - totaldevolucionesnumero;


                id = id - 1;
                Cursor cursorefectivo = bd.rawQuery("SELECT SaldoInicial FROM SaldoInicial WHERE idticket = '"+id+"'", null);

                if (cursorefectivo.moveToNext()) {
                    saldoinicialnumero = cursorefectivo.getDouble(0);
                }
                cursorefectivo.close();
                fianzanumero = nuevosaldoinicialnumero;
                //Retirada efectivo
                retiradaefectivonumero = recuentoefectivonumero - fianzanumero;
                //Calculado efectivo
                calculadoefectivonumero = (ventasefectivonumero + movcajanumero + saldoinicialnumero) - devolucionesnumeroefectivo;


                tarjetanumero = tarjetanumero - devolucionesnumerotarjeta;


                //Descuadre
                descuadrenumero = recuentoefectivonumero - calculadoefectivonumero ;
                descuadrenumero = Math.round(descuadrenumero * 100.0) / 100.0;
                if (recuentoefectivonumero < calculadoefectivonumero) {
                    openDialogfianza();
                } else {
                    resg.CrearArqueo(numeroarqueotexto, numventasnumero, ventasnumero, movcajanumero, totaldevolucionesnumero, totalcalculadonumero, saldoinicialnumero, ventasefectivonumero, entradasnumero, salidasnumero,
                            devolucionesnumeroefectivo, calculadoefectivonumero, recuentoefectivonumero, descuadrenumero, retiradaefectivonumero, fianzanumero, calculadoefectivonumero, tarjetanumero, impuestos10baseimponiblenumero,impuestos10cuotanumero, impuestos21baseimponiblenumero,impuestos21cuotanumero);



                Date c2 = Calendar.getInstance().getTime();
                SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String diatexto = df2.format(c2);
                Long dia = Long.parseLong(diatexto);
                SimpleDateFormat df3 = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String hora = df3.format(c2);

                Intent i = new Intent(getContext(), ArqueoTotalizarCierreCaja.class);
                i.putExtra("hora", hora);
                i.putExtra("fecha", dia);
                i.putExtra("id",id);
                i.putExtra("numeroarqueo", numeroarqueotexto);
                i.putExtra("numventas", numventasnumero);
                i.putExtra("ventas", ventasnumero);
                i.putExtra("movcaja", movcajanumero);
                i.putExtra("totaldevoluciones", totaldevolucionesnumero);
                i.putExtra("totalcalculado", totalcalculadonumero);
                i.putExtra("saldoinicial", saldoinicialnumero);
                i.putExtra("ventasefectivo", ventasefectivonumero);
                i.putExtra("entradas", entradasnumero);
                i.putExtra("salidas", salidasnumero);
                i.putExtra("devoluciones", devolucionesnumeroefectivo);
                i.putExtra("calculadoefectivo", calculadoefectivonumero);
                i.putExtra("recuentoefectivo", recuentoefectivonumero);
                i.putExtra("descuadre", descuadrenumero);
                i.putExtra("retiradaefectivo", retiradaefectivonumero);
                i.putExtra("fianza", fianzanumero);
                i.putExtra("efectivo", calculadoefectivonumero);
                i.putExtra("tarjeta", tarjetanumero);
                i.putExtra("impuestos10baseimponible", impuestos10baseimponiblenumero);
                i.putExtra("impuestos10cuota", impuestos10cuotanumero);
                i.putExtra("impuestos21baseimponible", impuestos21baseimponiblenumero);
                i.putExtra("impuestos21cuota", impuestos21cuotanumero);

                startActivity(i);

                }
            }
        });


        return view;
    }


    public void openDialogfianza() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_fianzadialog, null);

        Button cancelar = view.findViewById(R.id.cancelar);
        Button aceptar = view.findViewById(R.id.aceptarboton);
        TextView encajatext = view.findViewById(R.id.dineroencaja);
        TextView calculadotext = view.findViewById(R.id.dinerocalculado);
        TextView descuadretext = view.findViewById(R.id.dinerodescuadre);

        DecimalFormat decim = new DecimalFormat("0.00");

        encajatext.setText(String.valueOf(decim.format(recuentoefectivonumero)));
        calculadotext.setText(String.valueOf(decim.format(calculadoefectivonumero)));
        if(descuadrenumero<0) {
        descuadretext.setTextColor(Color.RED);
        }

        descuadretext.setText(String.valueOf(descuadrenumero));
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


        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BaseDatos resg = new BaseDatos(getContext(), null);
                resg.CrearArqueo(numeroarqueotexto, numventasnumero, ventasnumero, movcajanumero, totaldevolucionesnumero, totalcalculadonumero, saldoinicialnumero, ventasefectivonumero, entradasnumero, salidasnumero,
                        devolucionesnumeroefectivo, calculadoefectivonumero, recuentoefectivonumero, descuadrenumero, retiradaefectivonumero, fianzanumero, efectivonumero, tarjetanumero, impuestos10baseimponiblenumero,impuestos10cuotanumero, impuestos21baseimponiblenumero,impuestos21cuotanumero);
                Date c2 = Calendar.getInstance().getTime();
                SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String diatexto = df2.format(c2);
                Long dia = Long.parseLong(diatexto);
                SimpleDateFormat df3 = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String hora = df3.format(c2);

                Intent i = new Intent(getContext(), ArqueoTotalizarCierreCaja.class);
                i.putExtra("hora", hora);
                i.putExtra("fecha", dia);
                i.putExtra("numeroarqueo", numeroarqueotexto);
                i.putExtra("numventas", numventasnumero);
                i.putExtra("ventas", ventasnumero);
                i.putExtra("movcaja", movcajanumero);
                i.putExtra("totaldevoluciones", totaldevolucionesnumero);
                i.putExtra("totalcalculado", totalcalculadonumero);
                i.putExtra("saldoinicial", saldoinicialnumero);
                i.putExtra("ventasefectivo", ventasefectivonumero);
                i.putExtra("entradas", entradasnumero);
                i.putExtra("salidas", salidasnumero);
                i.putExtra("devoluciones", devolucionesnumeroefectivo);
                i.putExtra("calculadoefectivo", calculadoefectivonumero);
                i.putExtra("recuentoefectivo", recuentoefectivonumero);
                i.putExtra("descuadre", descuadrenumero);
                i.putExtra("retiradaefectivo", retiradaefectivonumero);
                i.putExtra("fianza", fianzanumero);
                i.putExtra("efectivo", efectivonumero);
                i.putExtra("tarjeta", tarjetanumero);
                i.putExtra("impuestos10baseimponible", impuestos10baseimponiblenumero);
                i.putExtra("impuestos10cuota", impuestos10cuotanumero);
                i.putExtra("impuestos21baseimponible", impuestos21baseimponiblenumero);
                i.putExtra("impuestos21cuota", impuestos21cuotanumero);

                startActivity(i);

                dialog.dismiss();
            }
        });


        // Display the custom alert dialog on interface
        dialog.show();
    }


}