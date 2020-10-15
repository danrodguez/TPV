package com.diusframi.tpv.Fragments.MisArqueos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MisArqueosArqueo extends AppCompatActivity {
    //Declaraciones
    ImageButton imprimirboton;
    Button volverboton;
    ImageButton correoboton;
    TextView numeroarqueo;
    TextView nombrecomercio;
    TextView fecha;
    TextView hora;
    TextView tickets;
    TextView Ventas;
    TextView movimientocaja;
    TextView totaldevoluciones;
    TextView TotalCalculado;
    TextView SaldoInicial;
    TextView Ventasefectivo;
    TextView Entradas;
    TextView Salidas;
    TextView devoluciones;
    TextView calculadoefectivo;
    TextView recuentoefectivo;
    TextView Descuadre;
    TextView RetiradaEfectivo;
    TextView Fianza;
    TextView Efectivo;
    TextView Tarjeta;
    TextView impuesto10baseimponible;
    TextView impuesto10cuota;
    TextView impuesto21baseimponible;
    TextView impuesto21cuota;

    Cursor cursormovcaja;
    Cursor cursorretiradaefectivo;
    Cursor cursordescuadre;
    Cursor cursorrecuentoefectivo;
    Cursor cursorentradas;
    Cursor cursorventas;
    Cursor cursorfecha;
    Cursor cursorsalidas;
    Cursor cursortarjeta;
    Cursor cursorefectivo;
    Cursor cursor10baseimponible;
    Cursor cursor21baseimponible;
    Cursor cursornumventas;
    Cursor cursortotalcalculado;
    Cursor cursorcalculadoefectivo;
    long horalong = 0;
    String numeroarqueotexto = "";
    String arqueo = "";
    long fechatexto = 0;
    long horatexto = 0;
    String nombrecomercialtexto = "";
    Integer numventasnumero = 0;
    Double ventasnumero = 0.0;
    Double movcajanumero = 0.0;
    Double totaldevolucionesnumero = 0.0;
    Double totalcalculadonumero = 0.0;
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
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    Boolean hoy = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_arqueos_arqueo);

        //Declaraciones
        numeroarqueo = findViewById(R.id.arqueotexto);
        nombrecomercio = findViewById(R.id.nombrecomerciotext);
        fecha = findViewById(R.id.fechatext);
        hora = findViewById(R.id.horatext);
        tickets = findViewById(R.id.ticketstext);
        Ventas = findViewById(R.id.ventastotaltext);
        movimientocaja = findViewById(R.id.movimientoscajatext);
        totaldevoluciones = findViewById(R.id.totaldevolucionestext);
        TotalCalculado = findViewById(R.id.totalcalculadotext);
        SaldoInicial = findViewById(R.id.saldoinicialtext);
        Ventasefectivo = findViewById(R.id.ventasefectivotext);
        Entradas = findViewById(R.id.entradastext);
        Salidas = findViewById(R.id.salidastext);
        devoluciones = findViewById(R.id.devolucionestext);
        calculadoefectivo = findViewById(R.id.calculadoefectivotext);
        recuentoefectivo = findViewById(R.id.totalrecuentotext);
        Descuadre = findViewById(R.id.descuadretext);
        RetiradaEfectivo = findViewById(R.id.retiradaefectivotext);
        Fianza = findViewById(R.id.fianzatext);
        Efectivo = findViewById(R.id.efectivotext);
        Tarjeta = findViewById(R.id.tarjetatext);
        impuesto10baseimponible = findViewById(R.id.baseimponible10text);
        impuesto10cuota = findViewById(R.id.cuota10text);
        impuesto21baseimponible = findViewById(R.id.baseimponible21text);
        impuesto21cuota = findViewById(R.id.cuota21text);
        imprimirboton = findViewById(R.id.imprimir);
        volverboton = findViewById(R.id.volver);
        correoboton = findViewById(R.id.correo);



         arqueo = getIntent().getStringExtra("arqueoid");
        String horat = "";


        DecimalFormat decim = new DecimalFormat("0.00");


        BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT nombrecomercial FROM Usuarios WHERE activo  LIKE 1", null);

        if (cursor.moveToFirst()) {
            nombrecomercialtexto = cursor.getString(0);
        }


        BaseDatos resg = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd = resg.getWritableDatabase();


        //Fecha
        cursorfecha = bd.rawQuery("SELECT Fecha,Hora,SaldoInicial,HoraTexto,Fianza FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorfecha.moveToNext()) {
            fechatexto = cursorfecha.getLong(0);
            horatexto = cursorfecha.getLong(1);
            saldoinicialnumero = cursorfecha.getDouble(2);
            horat = cursorfecha.getString(3);
            fianzanumero = cursorfecha.getDouble(4);
        }
        horalong = horatexto;
        if (fechatexto == 0 && horatexto ==0) {

            Date c2 = Calendar.getInstance().getTime();
            SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            String diatexto = df2.format(c2);
            fechatexto = Long.parseLong(diatexto);
            Date c3 = Calendar.getInstance().getTime();
            SimpleDateFormat df3 = new SimpleDateFormat("HHmmss", Locale.getDefault());
            String diatexto2 = df3.format(c3);
            horalong = Long.parseLong(diatexto2);

            hoy = true;
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        String aniotexto = df.format(c);
        numeroarqueotexto = aniotexto + "/" + arqueo;

        String fechastring = String.valueOf(fechatexto);
        String dia = fechastring.substring(6, 8);
        String mes = fechastring.substring(4, 6);
        String anio = fechastring.substring(0, 4);
        String fechatextocompleto = dia + "/" + mes + "/" + anio;

        //Ventas

            cursorventas = bd.rawQuery("SELECT Ventas FROM Arqueos WHERE id LIKE '"+arqueo+"' ", null);


        if (cursorventas.moveToNext()) {
            ventasnumero = cursorventas.getDouble(0);
        }

        // Numero de ventas


            cursornumventas = bd.rawQuery("SELECT NumeroVentas FROM Arqueos WHERE id LIKE '"+arqueo+"' ", null);

        if (cursornumventas.moveToNext()) {
            numventasnumero = cursornumventas.getInt(0);
        }


        //Total calculado
        cursortotalcalculado = bd.rawQuery("SELECT TotalCalculado FROM Arqueos WHERE id LIKE '"+arqueo+"' ", null);

        if (cursortotalcalculado.moveToNext()) {
            totalcalculadonumero = cursortotalcalculado.getDouble(0);
        }







        //Ventas Efectivo
        cursorefectivo = bd.rawQuery("SELECT VentasEfectivo FROM Arqueos WHERE id LIKE '"+arqueo+"' ", null);
        if (cursorefectivo.moveToNext()) {
            ventasefectivonumero = cursorefectivo.getDouble(0);
        }


        //Entradas

            cursorentradas = bd.rawQuery("SELECT  Entradas FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorentradas.moveToNext()) {

            entradasnumero = cursorentradas.getDouble(0);
        }

        //Salidas

            cursorsalidas = bd.rawQuery("SELECT Salidas FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorsalidas.moveToNext()) {
            salidasnumero = cursorsalidas.getDouble(0);
        }

        //Calculado efectivo

        cursorcalculadoefectivo = bd.rawQuery("SELECT CalculadoEfectivo FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorcalculadoefectivo.moveToNext()) {
            calculadoefectivonumero = cursorcalculadoefectivo.getDouble(0);
        }

        //Recuento efectivo
        cursorrecuentoefectivo = bd.rawQuery("SELECT RecuentoEfectivo FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorrecuentoefectivo.moveToNext()) {
            recuentoefectivonumero = cursorrecuentoefectivo.getDouble(0);
        }



        //Descuadre
        cursordescuadre = bd.rawQuery("SELECT Descuadre FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursordescuadre.moveToNext()) {
            descuadrenumero = cursordescuadre.getDouble(0);
        }



        //Retirada efectivo
        cursorretiradaefectivo = bd.rawQuery("SELECT RetiradaEfectivo FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);


        if (cursorretiradaefectivo.moveToNext()) {
            retiradaefectivonumero = cursorretiradaefectivo.getDouble(0);
        }



        //Fianza es saldo inicial


        //Ventas Efectivo
            cursorefectivo = bd.rawQuery("SELECT Efectivo FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);

        if (cursorefectivo.moveToNext()) {
            efectivonumero = cursorefectivo.getDouble(0);
        }



        //Tarjeta
        cursortarjeta = bd.rawQuery("SELECT Tarjeta FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);

        if (cursortarjeta.moveToNext()) {
            tarjetanumero = cursortarjeta.getDouble(0);
        }




        //Impuestos 10% cuota

            cursor10baseimponible = bd.rawQuery("SELECT Impuestos10baseimponible,Impuestos10cuota  FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);

        while (cursor10baseimponible.moveToNext()) {
            impuestos10baseimponiblenumero = cursor10baseimponible.getDouble(0);
            impuestos10cuotanumero = cursor10baseimponible.getDouble(1);
        }





        //Impuestos 21% cuota
        cursor21baseimponible = bd.rawQuery("SELECT Impuestos21baseimponible,Impuestos21cuota  FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);

        while (cursor21baseimponible.moveToNext()) {
            impuestos21baseimponiblenumero = cursor21baseimponible.getDouble(0);
            impuestos21cuotanumero = cursor21baseimponible.getDouble(1);
        }


        //Movimiento de caja
        cursormovcaja = bd.rawQuery("SELECT  MovimientosCaja FROM Arqueos WHERE id LIKE '"+arqueo+"'", null);

        while (cursormovcaja.moveToNext()) {
            movcajanumero = cursormovcaja.getDouble(0);

        }






        if (descuadrenumero < 0) {
            Descuadre.setTextColor(Color.RED);
        }


        nombrecomercio.setText(nombrecomercialtexto);
        numeroarqueo.setText(numeroarqueotexto);
        fecha.setText(fechatextocompleto);
        hora.setText(String.valueOf(horat));
        tickets.setText(String.valueOf(numventasnumero));
        Ventas.setText(String.valueOf(decim.format(ventasnumero)));
        movimientocaja.setText(String.valueOf(decim.format(movcajanumero)));
        totaldevoluciones.setText(String.valueOf(decim.format(totaldevolucionesnumero)));
        TotalCalculado.setText(String.valueOf(decim.format(totalcalculadonumero)));
        SaldoInicial.setText(String.valueOf(decim.format(saldoinicialnumero)));
        Ventasefectivo.setText(String.valueOf(decim.format(ventasefectivonumero)));
        Entradas.setText(String.valueOf(decim.format(entradasnumero)));
        Salidas.setText(String.valueOf(decim.format(salidasnumero)));
        devoluciones.setText(String.valueOf(decim.format(devolucionesnumero)));
        calculadoefectivo.setText(String.valueOf(decim.format(calculadoefectivonumero)));
        recuentoefectivo.setText(String.valueOf(decim.format(recuentoefectivonumero)));
        Descuadre.setText(String.valueOf(decim.format(descuadrenumero)));
        RetiradaEfectivo.setText(String.valueOf(decim.format(retiradaefectivonumero)));
        Fianza.setText(String.valueOf(decim.format(fianzanumero)));
        Efectivo.setText(String.valueOf(decim.format(efectivonumero)));
        Tarjeta.setText(String.valueOf(decim.format(tarjetanumero)));
        impuesto10baseimponible.setText(String.valueOf(decim.format(impuestos10baseimponiblenumero)));
        impuesto10cuota.setText(String.valueOf(decim.format(impuestos10cuotanumero)));
        impuesto21baseimponible.setText(String.valueOf(decim.format(impuestos21baseimponiblenumero)));
        impuesto21cuota.setText(String.valueOf(decim.format(impuestos21cuotanumero)));




        //Boton volver va a Venta
        volverboton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        });



    }
}

