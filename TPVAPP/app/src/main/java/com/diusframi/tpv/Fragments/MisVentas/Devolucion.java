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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.DevolucionAdapter;
import com.diusframi.tpv.Constructores.Devolucionconstruct;
import com.diusframi.tpv.Constructores.numerodevolucion;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Devolucion extends AppCompatActivity implements numerodevolucion {
    Button cancelarboton;
    Button devolverboton;
    public static CheckBox checkbox;
    TextView tickettext;
    TextView total;
    TextView devolver;
    String ordentexto = "";
    Double totalnumero = 0.0;
    String textoid = "";
    String devolvernumero = "0.0";
    RecyclerView recyclerView;
    ArrayList<Devolucionconstruct> listadevoluciones = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devolucion);

        recyclerView = findViewById(R.id.RecyclerView);
        cancelarboton = findViewById(R.id.cancelar);
        devolverboton = findViewById(R.id.devolver);
        checkbox = findViewById(R.id.checkb);
        tickettext = findViewById(R.id.ticket);
        total = findViewById(R.id.totalticket);
        devolver = findViewById(R.id.totaldevolver);

        BaseDatos resg3 = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
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
        DevolucionAdapter adapter1 = new DevolucionAdapter(Devolucion.this, this, listadevoluciones);
        recyclerView.setAdapter(adapter1);

        int orden = Integer.parseInt(ordentexto);

        @SuppressLint("Recycle") Cursor cursor = bd2.rawQuery("SELECT Total FROM Ordenes WHERE id LIKE '"+ordentexto+"'", null);

            while (cursor.moveToNext()) {
                totalnumero = cursor.getDouble(0);

            }

        DecimalFormat decim = new DecimalFormat("0.00");
        total.setText(decim.format(totalnumero));
        devolver.setText(devolvernumero);



        int idticket = 0;


        Cursor cursorid3 = bd2.rawQuery("SELECT NumeroDevolucion FROM TextoTicketDevolucion", null);
        if(cursorid3.moveToFirst()){
            idticket = cursorid3.getInt(0);
        }



        if(idticket != 0){
            orden = idticket;
            resg3.borrarnumerodevolucion();
        }




        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre,Precio,Numero FROM Vendidos WHERE idorden  LIKE '"+orden+"'", null);

        double precio;
        String nombre;
        int numero;

        while (cursor2.moveToNext()) {
            nombre = cursor2.getString(0);
            precio = cursor2.getDouble(1);
            numero = cursor2.getInt(2);

            @SuppressLint("Recycle") Cursor cursor3 = bd2.rawQuery("SELECT Nombre,Numero FROM Devueltos WHERE idorden  LIKE '"+orden+"'", null);

            String nombred = "";
            int numerod = 0;
            boolean chequeado = false;
            while(cursor3.moveToNext ()){
                nombred = cursor3.getString(0);
                numerod = cursor3.getInt(1);

                if(nombre.equals(nombred)){
                    numero = numero - numerod;
                }
            }



            if(numero==1){
                listadevoluciones.add(new Devolucionconstruct(nombre,numero,precio,orden, chequeado));
            }else{

                for (int inumero=0;inumero < numero; inumero++){
                    listadevoluciones.add(new Devolucionconstruct(nombre,1,precio,orden,chequeado));
                }
            }


            adapter1.setDevolucionconstructLista(listadevoluciones);
        }

        DevolucionAdapter adapter = new DevolucionAdapter(Devolucion.this, getApplicationContext(), listadevoluciones);
        recyclerView.setAdapter(adapter);


        cancelarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                i.putExtra("misventas","si");
                i.putExtra("ticketventa","");
                startActivity(i);
            }
        });

        final int finalOrden = orden;

        devolverboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Integer id = finalOrden;

                BaseDatos resg3 = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
                SQLiteDatabase bd = resg3.getReadableDatabase();

                double totalt = 0.0;
    double preciot = 0.0;
    int numerot = 0;
                @SuppressLint("Recycle") Cursor cursor3 = bd.rawQuery("SELECT Total FROM Devoluciones WHERE  idticket  LIKE'"+id+"'", null);
                if (!cursor3.moveToFirst()){

                    @SuppressLint("Recycle") Cursor cursor4 = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE idorden  LIKE '"+id+"'", null);
                    while (cursor4.moveToNext()){
                        preciot=cursor4.getDouble(0);
                         numerot = cursor4.getInt(1);
                        totalt = totalt + (preciot * numerot);
                    }


                    resg3.anadirdatosdevolucion(id,totalt,Integer.parseInt(ordentexto));
                }else{
                    @SuppressLint("Recycle") Cursor cursor4 = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE idorden  LIKE '"+id+"'", null);
                    while (cursor4.moveToNext()){
                        totalt=cursor4.getDouble(0)*cursor4.getInt(1);
                    }
                    resg3.actualizardatosdevolucion(totalt,Integer.parseInt(ordentexto));
                }


                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });

        if(devolver.getText()=="0,00" ||devolver.getText()=="0.00"){
            devolverboton.setBackgroundResource(R.drawable.botongrisclaro);
            devolverboton.setEnabled(false);
        }
    }

    @Override
    public void sumar(Double numero) {
        Double numerototal = 0.0;
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
        double numerototal = 0.0;
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
    public void checkall() {
        BaseDatos resg3 = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg3.getReadableDatabase();
        DevolucionAdapter adapter1 = new DevolucionAdapter(Devolucion.this, this, listadevoluciones);
        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT Nombre,Precio,Numero FROM Vendidos WHERE idorden  LIKE '" + ordentexto + "'", null);
        double precio;
        String nombre;
        int numero;
        boolean chequeado = true;
        listadevoluciones.clear();
        while (cursor2.moveToNext()) {
            nombre = cursor2.getString(0);
            precio = cursor2.getDouble(1);
            numero = cursor2.getInt(2);

            if (numero == 1) {

                listadevoluciones.add(new Devolucionconstruct(nombre, numero, precio, Integer.parseInt(ordentexto), chequeado));

            } else {

                for (int inumero = 0; inumero < numero; inumero++) {
                    listadevoluciones.add(new Devolucionconstruct(nombre, 1, precio, Integer.parseInt(ordentexto), chequeado));
                }
            }
            adapter1.notifyDataSetChanged();

            adapter1.setDevolucionconstructLista(listadevoluciones);
            adapter1.notifyDataSetChanged();
        }

        DevolucionAdapter adapter = new DevolucionAdapter(Devolucion.this, getApplicationContext(), listadevoluciones);
        recyclerView.setAdapter(adapter);
        }

    @Override
    public void cambiarbotonanaranja() {
        devolverboton.setBackgroundResource(R.drawable.botonnaranja);
        devolverboton.setTextColor(Color.WHITE);
        devolverboton.setEnabled(true);
    }



}