package com.diusframi.tpv.Fragments.TotalizarCierreCaja;


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

    Cursor cursorentradas;
    Cursor cursorventas;
    Cursor cursorfecha;
    Cursor cursorsalidas;
    Cursor cursortarjeta;
    Cursor cursorefectivo;
    Cursor cursornumeroarqueo;
    Cursor cursor10baseimponible;
    Cursor cursor21baseimponible;
    Cursor cursornumventas;
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
    Double totalcalculadonumero = 0.0;
    Double nuevosaldoinicialnumero = 0.0;
    Double saldoinicialnumero = 0.0;
    Double ventasefectivonumero = 0.0;
    Double entradasnumero = 0.0;
    Double salidasnumero = 0.0;
    Double devolucionesnumero = 0.0;
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
                cursorfecha = bd.rawQuery("SELECT Hora,Fecha FROM Arqueos ORDER BY Fecha,Hora DESC", null);


                if (cursorfecha.moveToNext()) {
                    hora = cursorfecha.getLong(0);
                    fecha = cursorfecha.getLong(1);
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
                if (!hoy) {
                    cursorventas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' ", null);

                } else {
                    cursorventas = bd.rawQuery("SELECT SUM(Total) FROM Ordenes ", null);
                }
                if (cursorventas.moveToNext()) {
                     ventasnumero = cursorventas.getDouble(0);

                }

                // Numero de ventas
                if (!hoy) {
                    cursornumventas = bd.rawQuery("SELECT COUNT(*) FROM Ordenes WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);

                } else {
                    cursornumventas = bd.rawQuery("SELECT COUNT(*) FROM Ordenes ", null);
                }
                if (cursornumventas.moveToNext()) {
                    numventasnumero = cursornumventas.getInt(0);
                }




                //Fianza
                numerofianzatexto = importe.getText().toString();
                nuevosaldoinicialnumero = Double.parseDouble(numerofianzatexto);


                resg.saldoinicial(nuevosaldoinicialnumero,id);
                //Ventas Efectivo
                if (!hoy) {
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo' AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                }else{
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
                }
                if (cursorefectivo.moveToNext()) {
                    ventasefectivonumero = cursorefectivo.getDouble(0);
                }


                //Entradas
                if (!hoy) {
                    cursorentradas = bd.rawQuery("SELECT SUM(Entradas) FROM CuadreCaja WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong +"'", null);
                } else {
                    cursorentradas = bd.rawQuery("SELECT  SUM(Entradas) FROM CuadreCaja", null);
                }

                if (cursorentradas.moveToNext()) {

                    entradasnumero = cursorentradas.getDouble(0);
                }
                //Devoluciones efectivo
                if(!hoy) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones  WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' AND TipoPago LIKE 'Efectivo'", null);
                }else{
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones WHERE TipoPago LIKE 'Efectivo'", null);
                }
                if (cursordevoluciones.moveToNext()) {
                    devolucionesnumero = cursordevoluciones.getDouble(0);
                }

                //Devoluciones
                if(!hoy) {
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones  WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                }else{
                    cursordevoluciones = bd.rawQuery("SELECT SUM(Total) FROM Devoluciones", null);
                }
                if (cursordevoluciones.moveToNext()) {
                    totaldevolucionesnumero = cursordevoluciones.getDouble(0);
                }

                //Salidas
                if (!hoy) {
                    cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja WHERE Fecha >= '" + fecha +"' AND Hora >= '" + horalong +"'", null);
                } else {
                    cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja", null);
                }

                if (cursorsalidas.moveToNext()) {
                    salidasnumero = cursorsalidas.getDouble(0);
                }

                //Recuento efectivo declarado en intent arriba
                //Movimiento de caja
                movcajanumero = entradasnumero - salidasnumero;
                //Total calculado efectivo
                totalcalculadonumeroefectivo = (ventasefectivonumero + movcajanumero) - devolucionesnumero;





                //Fianza es saldo inicial

                //Efectivo
                if(!hoy){
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'  AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                }else{
                    cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
                }

                if (cursorefectivo.moveToNext()) {
                    efectivonumero = cursorefectivo.getDouble(0);
                }



                //Tarjeta
                if(!hoy){
                    cursortarjeta = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Tarjeta' AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
                }else{
                    cursortarjeta = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Tarjeta'", null);
                }

                if (cursortarjeta.moveToNext()) {
                    tarjetanumero = cursortarjeta.getDouble(0);
                }

                double precio10 = 0.0;
                int numero10 = 0;
                double precio21 = 0.0;
                int numero21 = 0;

                //Impuestos 10% cuota
                if (!hoy) {
                    cursor10baseimponible = bd.rawQuery("SELECT Vendidos.Precio,Vendidos.Numero FROM Vendidos INNER JOIN Ordenes ON Ordenes.id = Vendidos.idorden WHERE Vendidos.Iva = '10' AND Ordenes.Fecha >= '" + fecha + "' AND Ordenes.Hora >= '" + horalong + "'", null);

                } else {
                    cursor10baseimponible = bd.rawQuery("SELECT Precio,Numero  FROM Vendidos WHERE Iva = '10'", null);
                }
                while (cursor10baseimponible.moveToNext()) {
                     precio10 = cursor10baseimponible.getDouble(0);
                     numero10 = cursor10baseimponible.getInt(1);
                     total10numero = (precio10*numero10) + total10numero ;
                }

                impuestos10cuotanumero = (total10numero*10)/100;


                //Impuestos 10% base imponible
                impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;


                //Impuestos 21% cuota
                if (!hoy) {
                    cursor21baseimponible = bd.rawQuery("SELECT Ven.Precio,Ven.Numero FROM Vendidos AS Ven JOIN Ordenes As Ord WHERE Ven.Iva = '21' AND Ord.Fecha >= '" + fecha + "' AND Ord.Hora >= '" + horalong + "'", null);
                }else{
                    cursor21baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Vendidos WHERE Iva = '21'", null);
                }
                while (cursor21baseimponible.moveToNext()) {
                    precio21 = cursor21baseimponible.getDouble(0);
                    numero21 = cursor21baseimponible.getInt(1);
                    total21numero = total21numero + (precio21*numero21);
                }

                impuestos21cuotanumero =  (total21numero*21)/100;


                //Impuestos 21% base imponible
                impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;




                //Total calculado
                totalcalculadonumero = ventasnumero + movcajanumero - devolucionesnumero;


                id = id - 1;
                Cursor cursorefectivo = bd.rawQuery("SELECT SaldoInicial FROM SaldoInicial WHERE idticket = '"+id+"'", null);

                if (cursorefectivo.moveToNext()) {
                    saldoinicialnumero = cursorefectivo.getDouble(0);
                }
                fianzanumero = nuevosaldoinicialnumero;
                //Retirada efectivo
                retiradaefectivonumero = saldoinicialnumero - recuentoefectivonumero;
                //Calculado efectivo
                calculadoefectivonumero = (ventasefectivonumero + movcajanumero + saldoinicialnumero) - devolucionesnumero;

                //Descuadre
                descuadrenumero = recuentoefectivonumero - calculadoefectivonumero ;
                descuadrenumero = Math.round(descuadrenumero * 100.0) / 100.0;
                if (recuentoefectivonumero < totalcalculadonumeroefectivo) {
                    openDialogfianza();
                } else {
                    resg.CrearArqueo(numeroarqueotexto, numventasnumero, ventasnumero, movcajanumero, totaldevolucionesnumero, totalcalculadonumero, saldoinicialnumero, ventasefectivonumero, entradasnumero, salidasnumero,
                            devolucionesnumero, calculadoefectivonumero, recuentoefectivonumero, descuadrenumero, retiradaefectivonumero, fianzanumero, efectivonumero, tarjetanumero, impuestos10baseimponiblenumero,impuestos10cuotanumero, impuestos21baseimponiblenumero,impuestos21cuotanumero);



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
                i.putExtra("devoluciones", devolucionesnumero);
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
        calculadotext.setText(String.valueOf(decim.format(totalcalculadonumeroefectivo)));
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
                        devolucionesnumero, calculadoefectivonumero, recuentoefectivonumero, descuadrenumero, retiradaefectivonumero, fianzanumero, efectivonumero, tarjetanumero, impuestos10baseimponiblenumero,impuestos10cuotanumero, impuestos21baseimponiblenumero,impuestos21cuotanumero);
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
                i.putExtra("devoluciones", devolucionesnumero);
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