package com.diusframi.tpv.Fragments.MisArqueos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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


    Cursor cursorentradas;
    Cursor cursorventas;
    Cursor cursorfecha;
    Cursor cursorsalidas;
    Cursor cursortarjeta;
    Cursor cursorefectivo;
    Cursor cursor10baseimponible;
    Cursor cursor21baseimponible;
    Cursor cursornumventas;
    long horalong = 0;
    String numeroarqueotexto = "";
    String numerofianzatexto = "";
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
    Double total10numero = 0.0;
    Double total21numero = 0.0;
    Boolean hoy = false;
    Integer id = 0;


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


        BaseDatos resg2 = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT nombrecomercial FROM Usuarios WHERE activo  LIKE 1", null);

        if (cursor.moveToFirst()) {
            nombrecomercialtexto = cursor.getString(0);
        }


        BaseDatos resg = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getWritableDatabase();


        //Fecha
        cursorfecha = bd.rawQuery("SELECT Fecha,Hora,SaldoInicial,HoraTexto FROM Arqueos ORDER BY Fecha ASC", null);


        if (cursorfecha.moveToNext()) {
            fechatexto = cursorfecha.getLong(0);
            horatexto = cursorfecha.getLong(1);
            saldoinicialnumero = cursorfecha.getDouble(2);
            horat = cursorfecha.getString(3);
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
            cursornumventas = bd.rawQuery("SELECT COUNT(*) FROM Ordenes WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' ", null);

        } else {
            cursornumventas = bd.rawQuery("SELECT COUNT(*) FROM Ordenes ", null);
        }
        if (cursornumventas.moveToNext()) {
            numventasnumero = cursornumventas.getInt(0);
        }


        //Total calculado
        totalcalculadonumero = ventasnumero + movcajanumero - devolucionesnumero;




        //Ventas Efectivo
        cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
        if (cursorefectivo.moveToNext()) {
            ventasefectivonumero = cursorefectivo.getDouble(0);
        }


        //Entradas
        if (!hoy) {
            cursorentradas = bd.rawQuery("SELECT SUM(Entradas) FROM CuadreCaja WHERE Fecha >= '" + fecha + "'AND Hora >= '" + horalong + "' ", null);
        } else {
            cursorentradas = bd.rawQuery("SELECT  SUM(Entradas) FROM CuadreCaja", null);
        }

        if (cursorentradas.moveToNext()) {

            entradasnumero = cursorentradas.getDouble(0);
        }

        //Salidas
        if (!hoy) {
            cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja WHERE Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "' ", null);
        } else {
            cursorsalidas = bd.rawQuery("SELECT  SUM(Salidas) FROM CuadreCaja", null);
        }

        if (cursorsalidas.moveToNext()) {
            salidasnumero = cursorsalidas.getDouble(0);
        }

        //Calculado efectivo
        calculadoefectivonumero = fianzanumero + ventasefectivonumero + entradasnumero - salidasnumero - devolucionesnumero;

        //Recuento efectivo declarado en intent arriba

        //Descuadre
        descuadrenumero = recuentoefectivonumero - totalcalculadonumero;
        descuadrenumero = Math.round(descuadrenumero * 100.0) / 100.0;


        //Retirada efectivo
        retiradaefectivonumero = fianzanumero - recuentoefectivonumero;

        //Fianza es saldo inicial


        //Ventas Efectivo
        if (!hoy) {
            cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo' AND Fecha >= '" + fecha + "' AND Hora >= '" + horalong + "'", null);
        }else{
            cursorefectivo = bd.rawQuery("SELECT SUM(Total) FROM Ordenes WHERE TipoPago = 'Efectivo'", null);
        }
        if (cursorefectivo.moveToNext()) {
            ventasefectivonumero = cursorefectivo.getDouble(0);
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

        Double precio10 = 0.0;
        int numero10 = 0;
        Double precio21 = 0.0;
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


        //Movimiento de caja
        movcajanumero = entradasnumero - salidasnumero;

        //Fianza

        fianzanumero = saldoinicialnumero;




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
        volverboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });



    }
}

