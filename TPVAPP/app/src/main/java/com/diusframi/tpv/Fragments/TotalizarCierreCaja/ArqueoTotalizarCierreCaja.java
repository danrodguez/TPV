package com.diusframi.tpv.Fragments.TotalizarCierreCaja;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;

public class  ArqueoTotalizarCierreCaja extends AppCompatActivity {

    Button volverboton;



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

    long fechatexto;
    String horatexto;
    String numeroarqueotexto;
    String nombrecomercialtexto;
    String numerofianzatexto;
    Integer numventasnumero = 0;
    Double ventasnumero = 0.0;
    Double movcajanumero = 0.0;
    Double totaldevolucionesnumero = 0.0;
    Double totalcalculadonumero = 0.0;
    Double saldoinicialnumero = 0.0;
    Double nuevosaldoinicialnumero = 0.0;
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
    Integer id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arqueo_totalizar_cierre_caja);

        Intent intent = getIntent();
        nuevosaldoinicialnumero = intent.getDoubleExtra("saldoinicial",0);
        id = intent.getIntExtra("id",0);
        fechatexto = intent.getLongExtra("fecha", 0);
        horatexto = intent.getStringExtra("hora");
        numeroarqueotexto = intent.getStringExtra("numeroarqueo");
        numventasnumero = intent.getIntExtra("numventas", 0);
        ventasnumero = intent.getDoubleExtra("ventas", 0);
        movcajanumero = intent.getDoubleExtra("movcaja", 0);
        totaldevolucionesnumero = intent.getDoubleExtra("totaldevoluciones", 0);
        totalcalculadonumero = intent.getDoubleExtra("totalcalculado", 0);
         ventasefectivonumero = intent.getDoubleExtra("ventasefectivo", 0);
        entradasnumero = intent.getDoubleExtra("entradas", 0);
        salidasnumero = intent.getDoubleExtra("salidas", 0);
        devolucionesnumero = intent.getDoubleExtra("devoluciones", 0);
        calculadoefectivonumero = intent.getDoubleExtra("calculadoefectivo", 0);
        recuentoefectivonumero = intent.getDoubleExtra("recuentoefectivo", 0);
        descuadrenumero = intent.getDoubleExtra("descuadre", 0);
        retiradaefectivonumero = intent.getDoubleExtra("retiradaefectivo", 0);
        fianzanumero = intent.getDoubleExtra("fianza", 0);
        efectivonumero = intent.getDoubleExtra("efectivo", 0);
        tarjetanumero = intent.getDoubleExtra("tarjeta", 0);
        impuestos10baseimponiblenumero = intent.getDoubleExtra("impuestos10baseimponible", 0);
        impuestos21baseimponiblenumero = intent.getDoubleExtra("impuestos21baseimponible", 0);
        impuestos10cuotanumero = intent.getDoubleExtra("impuestos10cuota", 0);
        impuestos21cuotanumero = intent.getDoubleExtra("impuestos21cuota", 0);

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
        recuentoefectivo = findViewById(R.id.recuentoefectivotext);
        Descuadre = findViewById(R.id.descuadretext);
        RetiradaEfectivo = findViewById(R.id.retiradaefectivotext);
        Fianza = findViewById(R.id.fianzatext);
        Efectivo = findViewById(R.id.efectivotext);
        Tarjeta = findViewById(R.id.tarjetatext);
        impuesto10baseimponible = findViewById(R.id.baseimponible10text);
        impuesto10cuota = findViewById(R.id.cuota10text);
        impuesto21baseimponible = findViewById(R.id.baseimponible21text);
        impuesto21cuota = findViewById(R.id.cuota21text);
        volverboton = findViewById(R.id.volver);

        BaseDatos resg = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd = resg.getWritableDatabase();
        id = id - 1;
         Cursor cursorefectivo = bd.rawQuery("SELECT SaldoInicial FROM SaldoInicial WHERE idticket = '"+id+"'", null);

        if (cursorefectivo.moveToNext()) {
            saldoinicialnumero = cursorefectivo.getDouble(0);
        }

        if (descuadrenumero < 0) {
            Descuadre.setTextColor(Color.RED);
        }


        DecimalFormat decim = new DecimalFormat("0.00");


        String fechastring = String.valueOf(fechatexto);
        String dia = fechastring.substring(6, 8);
        String mes = fechastring.substring(4, 6);
        String anio = fechastring.substring(0, 4);
        String fechatextocompleto = dia + "/" + mes + "/" + anio;

        BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT nombrecomercial FROM Usuarios WHERE activo  LIKE 1", null);

        if (cursor.moveToFirst()) {
            nombrecomercialtexto = cursor.getString(0);
        }



        nombrecomercio.setText(nombrecomercialtexto);
        numeroarqueo.setText(numeroarqueotexto);
        fecha.setText(fechatextocompleto);
        hora.setText(String.valueOf(horatexto));
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
        Efectivo.setText(String.valueOf(decim.format(calculadoefectivonumero)));
        Tarjeta.setText(String.valueOf(decim.format(tarjetanumero)));
        impuesto10baseimponible.setText(String.valueOf(decim.format(impuestos10baseimponiblenumero)));
        impuesto10cuota.setText(String.valueOf(decim.format(impuestos10cuotanumero)));
        impuesto21baseimponible.setText(String.valueOf(decim.format(impuestos21baseimponiblenumero)));
        impuesto21cuota.setText(String.valueOf(decim.format(impuestos21cuotanumero)));


        volverboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });


    }
}
