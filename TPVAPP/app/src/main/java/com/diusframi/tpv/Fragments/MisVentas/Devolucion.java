package com.diusframi.tpv.Fragments.MisVentas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.Articulo;
import com.diusframi.tpv.Constructores.ArticuloAdapter;
import com.diusframi.tpv.Constructores.DevolucionAdapter;
import com.diusframi.tpv.Constructores.Devolucionconstruct;
import com.diusframi.tpv.Constructores.numerodevolucion;
import com.diusframi.tpv.Fragments.MisArticulos.MisarticulosFragment;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Devolucion extends AppCompatActivity implements numerodevolucion {
    Button cancelarboton;
    Button devolverboton;
    @SuppressLint("StaticFieldLeak")
    public static CheckBox checkbox;
    TextView tickettext;
    TextView total;
    TextView devolver;
    String ordentexto = "";
    Double totalnumero = 0.0;
    String textoid = "";
    String devolvernumero = "0.0";
    RecyclerView recyclerView;
    int ordenticket = 0;
    DevolucionAdapter adapter1;
    String tipopago = "";
    int orden = 0;
    ArrayList<Devolucionconstruct> listadevoluciones = new ArrayList<>();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devolucion);

        recyclerView = findViewById(R.id.RecyclerView);
        cancelarboton = findViewById(R.id.cancelar);
        devolverboton = findViewById(R.id.devolver);
        tickettext = findViewById(R.id.ticket);
        total = findViewById(R.id.totalticket);
        devolver = findViewById(R.id.totaldevolver);
        BaseDatos resg3 = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd2 = resg3.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursortexto = bd2.rawQuery("SELECT TextoTicket FROM TextoTicketDevolucion", null);

        while (cursortexto.moveToNext()) {
            textoid = cursortexto.getString(0);

        }



        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        ordentexto = extras.getString("orden");}

        tickettext.setText(textoid+""+ordentexto);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter1 = new DevolucionAdapter(Devolucion.this, this, listadevoluciones);
        recyclerView.setAdapter(adapter1);


//NUMERO TICKET
         ordenticket = Integer.parseInt(ordentexto);


        @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT Total,TipoPago FROM Ordenes WHERE id LIKE '"+ordentexto+"'", null);

            while (cursor.moveToNext()) {
                totalnumero = cursor.getDouble(0);
                tipopago = cursor.getString(1);
            }

        DecimalFormat decim = new DecimalFormat("0.00");
        total.setText(decim.format(totalnumero));
        devolver.setText(devolvernumero);




        int idticket = 0;
        int idticket2;

        Cursor cursorid3 = bd2.rawQuery("SELECT NumeroDevolucion FROM TextoTicketDevolucion", null);
        if(cursorid3.moveToFirst()){
            idticket = cursorid3.getInt(0);
        }
cursorid3.close();
        Cursor cursorid4 = bd2.rawQuery("SELECT id FROM Devoluciones ORDER BY id DESC", null);
        if(cursorid4.moveToFirst()){
            idticket2 = cursorid4.getInt(0);
            idticket2 = idticket2 + 1;
        }else{
            idticket2 = 1;
        }

cursorid4.close();
        if(idticket != 0){
            orden = idticket;
            resg3.borrarnumerodevolucion();
        }else{
            orden = idticket2;
        }




        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre,Precio,Numero FROM Vendidos WHERE idorden  LIKE '"+ordenticket+"'", null);

        double precio;
        String nombre;
        int numero;

        while (cursor2.moveToNext()) {
            nombre = cursor2.getString(0);
            precio = cursor2.getDouble(1);
            numero = cursor2.getInt(2);

            @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Nombre,Numero FROM Devueltos WHERE idticket LIKE '"+ordenticket+"'", null);

            String nombred;
            int numerod;
            while(cursor3.moveToNext ()){
                nombred = cursor3.getString(0);
                numerod = cursor3.getInt(1);

                if(nombre.equals(nombred)){
                    numero = numero - numerod;
                }
            }


    
            if(numero==1){
                listadevoluciones.add(new Devolucionconstruct(orden, ordenticket, nombre,numero,precio, false));
            }else{

                for (int inumero=0;inumero < numero; inumero++){
                    listadevoluciones.add(new Devolucionconstruct(orden, ordenticket, nombre,1,precio, false));
                }
            }


            adapter1.setDevolucionconstructLista(listadevoluciones);
        }

        DevolucionAdapter adapter = new DevolucionAdapter(Devolucion.this, getApplicationContext(), listadevoluciones);
        recyclerView.setAdapter(adapter);


        cancelarboton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            i.putExtra("misventas","si");
            i.putExtra("ticketventa","");
            startActivity(i);
        });

        final int finalOrden = orden;

        devolverboton.setOnClickListener(v -> {



            int id = finalOrden;
            BaseDatos resg31 = new BaseDatos(getApplicationContext(), null);
            SQLiteDatabase bd = resg31.getReadableDatabase();
            if(id ==0){
                id = 1;
            }
            double totalt = 0.0;
            double preciot;
            int numerot;


                @SuppressLint("Recycle") Cursor cursor4 = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE idorden  LIKE '"+id+"'", null);
            while (cursor4.moveToNext()){
                preciot=cursor4.getDouble(0);
                numerot = cursor4.getInt(1);
                totalt = totalt + (preciot * numerot);
            }



                resg31.anadirdatosdevolucion(finalOrden,totalt,Integer.parseInt(ordentexto),tipopago);



            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        });

        if(devolver.getText()=="0,00" ||devolver.getText()=="0.00"){
            devolverboton.setBackgroundResource(R.drawable.botongrisclaro);
            devolverboton.setEnabled(false);
        }
    }

    @Override
    public void sumar(Double numero) {
        Double numerototal;
        try {  numerototal = Double.parseDouble(devolver.getText().toString());
        } catch (NumberFormatException e)
        { String numerotexto = devolver.getText().toString().replace(",", "."); numerototal = Double.parseDouble(numerotexto); }

        DecimalFormat decim = new DecimalFormat("0.00");
        devolver.setText(decim.format(numero + numerototal));

        if(devolver.getText().toString().equals("0,00")){
            devolverboton.setBackgroundResource(R.drawable.botongrisclaro);
            devolverboton.setEnabled(false);
        }
    }


    @Override
    public void restar(Double numero) {
        double numerototal;
        try {  numerototal = Double.parseDouble(devolver.getText().toString());
        } catch (NumberFormatException e)
        { String numerotexto = devolver.getText().toString().replace(",", "."); numerototal = Double.parseDouble(numerotexto); }

        DecimalFormat decim = new DecimalFormat("0.00");
        devolver.setText(decim.format(numerototal - numero));
        if(devolver.getText().toString().equals("0,00")){
            devolverboton.setBackgroundResource(R.drawable.botongrisclaro);
            devolverboton.setEnabled(false);
        }

    }





    @Override
    public void cambiarbotonanaranja() {
        devolverboton.setBackgroundResource(R.drawable.botonnaranja);
        devolverboton.setTextColor(Color.WHITE);
        devolverboton.setEnabled(true);
    }



}