package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.ProductoTicket;
import com.diusframi.tpv.Constructores.ProductosTicketAdapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.MostrarPdf;
import com.diusframi.tpv.R;
import com.example.etickets_sdk.DatosCajaR;
import com.example.etickets_sdk.Eticket;


import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PagoRealizadoEfectivo extends AppCompatActivity  {
    Button Imprimir;
    Button ticketregalo;
    Button salirboton;
    TextView totaltexto;
    TextView efectivotexto;
    TextView cambiotexto;
    Double importet;
    Double efectivot;
    CheckBox checkbox;
    Cursor cursorid;
    MostrarPdf tarea = new MostrarPdf();
    int orden;
    String fechatexto="";
    String horatexto="";
    String facturatexto="";
    String direccionfiscaltexto="";
    String ciftexto="";
    String nombrefiscaltexto="";
    String nombrecomerciotexto="";
    Double totalnumero=0.0;
    Double cambionumero=0.0;
    Double total10numero=0.0;
    Double total21numero=0.0;
    Double impuestos10baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    byte[] imagen = null;
    Cursor cursorfechahoratotal;
    Cursor cursorcomercialfiscal;
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();
    ArrayList<String> listanombres = new ArrayList<>();
    int id = 0;
    DatosCajaR datos = new DatosCajaR();
    Eticket crearPDF =new Eticket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_realizado_efectivo);
        Imprimir = findViewById(R.id.imprimirticket);
        ticketregalo = findViewById(R.id.imprimirticketregalo);
        salirboton = findViewById(R.id.salir);
        checkbox = findViewById(R.id.checkb);
        totaltexto = findViewById(R.id.total);
        efectivotexto = findViewById(R.id.efectivo);
        cambiotexto = findViewById(R.id.cambio);

        Intent intent = getIntent();
        final DecimalFormat decim = new DecimalFormat("0.00");
        importet = intent.getDoubleExtra("importe", 0.0);
        efectivot = intent.getDoubleExtra("efectivo", 0.0);


        totaltexto.setText(decim.format(importet));
        efectivotexto.setText(decim.format(efectivot));

        Double cambionumero = efectivot - importet;

        //Datos Ticket


        BaseDatos resg = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getReadableDatabase();

        Cursor cursorid2 = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);
        if(cursorid2.moveToFirst()){
            id = cursorid2.getInt(0);
        }

        if(id == 0){
            id = 1;
        }else{
            id = id + 1;
        }
orden = id;


        int idticket = 0;
        cursorfechahoratotal = bd.rawQuery("SELECT Fecha,Hora,Total,Cambio FROM Ordenes WHERE id LIKE '"+orden+"'", null);
        while (cursorfechahoratotal.moveToNext()) {
            fechatexto = cursorfechahoratotal.getString(0);
            horatexto = cursorfechahoratotal.getString(1);
            totalnumero = cursorfechahoratotal.getDouble(2);
            cambionumero = cursorfechahoratotal.getDouble(3);
        }

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre,Precio FROM Vendidos WHERE idorden LIKE '"+orden+"' ", null);

        String Nombre = "";
        int Numero = 0;
        double Precio = 0.0;
        double Importe = 0.0;

        while (cursorlista.moveToNext()) {
            Numero = cursorlista.getInt(0);
            Nombre = cursorlista.getString(1);
            Precio = cursorlista.getDouble(2);

            Importe = Precio * Numero;
            listaproductos.add(new ProductoTicket(Numero,Nombre,Precio,Importe));
            listanombres.add(Nombre);
        }





        Cursor  cursor10baseimponible = bd.rawQuery("SELECT SUM(Precio) FROM Vendidos WHERE Iva = '10' AND idorden LIKE '"+orden+"' ", null);

        if (cursor10baseimponible.moveToNext()) {
            total10numero = cursor10baseimponible.getDouble(0);
        }

        impuestos10cuotanumero = (total10numero*10)/100;

        Cursor  cursor21baseimponible = bd.rawQuery("SELECT SUM(Precio) FROM Vendidos WHERE Iva = '21' AND idorden LIKE '"+orden+"' ", null);

        if (cursor21baseimponible.moveToNext()) {
            total21numero = cursor21baseimponible.getDouble(0);
        }

        impuestos21cuotanumero = (total21numero*21)/100;

        BaseDatos resg2 = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        cursorcomercialfiscal = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo FROM Usuarios WHERE activo LIKE 1", null);
        while (cursorcomercialfiscal.moveToNext()) {
            nombrefiscaltexto = cursorcomercialfiscal.getString(0);
            nombrecomerciotexto = cursorcomercialfiscal.getString(1);
            ciftexto = cursorcomercialfiscal.getString(2);
            direccionfiscaltexto = cursorcomercialfiscal.getString(3)+","+cursorcomercialfiscal.getString(4)+","+cursorcomercialfiscal.getString(5)+","+cursorcomercialfiscal.getString(6);
            imagen = cursorcomercialfiscal.getBlob(7);
        }

        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;






        Cursor cursor2 = bd2.rawQuery("SELECT  TextoTicket FROM TextoTicketDevolucion", null);

        String TextoTicket = "";

        while (cursor2.moveToNext()) {
            TextoTicket = cursor2.getString(0);
        }



        facturatexto = TextoTicket+orden;

        final String cambiodoble = decim.format(cambionumero);
        cambiotexto.setText(cambiodoble);










        Cursor cursorid3 = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion", null);
        if(cursorid3.moveToFirst()){
            idticket = cursorid3.getInt(0);
        }



        if(idticket != 0){
            id = idticket;
        }
        resg.anadirdatosorden(id,importet);



        if (id != 0) {
            resg.ordenefectivo(cambionumero,id);
            resg.borrarnumeroticket();
        }


        final Double finalCambionumero = cambionumero;
        final int finalIdticket = idticket;
        final Double finalCambionumero1 = cambionumero;
        Imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Todavía por implementar", Toast.LENGTH_SHORT);

                toast1.show();

                final DecimalFormat decim = new DecimalFormat("0.00");
                datos.setTotal(totalnumero);
                datos.setEfectivo(efectivot);
                datos.setFactura(String.valueOf(finalIdticket));
                datos.setCif(ciftexto);
                datos.setBase10((impuestos10baseimponiblenumero));
                datos.setBase21(impuestos21baseimponiblenumero);
                datos.setCuota10(impuestos10cuotanumero);
                datos.setCuota21(impuestos21cuotanumero);
                datos.setCambio(finalCambionumero1);
                datos.setDireccionFiscal(direccionfiscaltexto);
                datos.setFecha(fechatexto);
                datos.setNombreComercio(nombrecomerciotexto);
                datos.setNombreFiscal(nombrefiscaltexto);
                datos.setHora(horatexto);
                datos.setPrecio(totalnumero);
                datos.setNumero(String.valueOf(totalnumero));
                datos.setListArticulos(listanombres);

            crearPDF.generarQr();
                tarea.execute();
                ArrayList<Double> listadoble1 = new ArrayList<>();
                ArrayList<Double> listadoble2 = new ArrayList<>();
             if(crearPDF.createPdf(datos.getNombreComercio(),datos.getNombreFiscal(),datos.getNumero(),datos.getDireccionFiscal(),datos.getCif(),datos.getFactura(),datos.getEfectivo(),datos.getCambio(),datos.getTotal(),datos.getBase10(),datos.getCuota10(),datos.getBase21(),datos.getCuota21(),datos.getListArticulos(),datos.getListArticulos(),listadoble1,listadoble2)){
                 Toast.makeText(getApplicationContext(),"Factura creada con exito",Toast.LENGTH_LONG).show();
                }else{
                 Toast.makeText(getApplicationContext(),"Factura no creada",Toast.LENGTH_LONG).show();
             }
                ;            }
        });

        ticketregalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Todavía por implementar", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });

        salirboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });
    }
}