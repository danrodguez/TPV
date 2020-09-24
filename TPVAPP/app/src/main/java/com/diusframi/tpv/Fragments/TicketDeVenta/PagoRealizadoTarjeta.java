package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;

public class PagoRealizadoTarjeta extends AppCompatActivity {
    Button Imprimir;
    Button ticketregalo;
    Button salirboton;
    TextView totaltext;
    TextView tarjetatext;
    Cursor cursorid;
    Double importet;
    Double tarjetat;
    int id = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_realizado_tarjeta);
        Imprimir = findViewById(R.id.imprimirticket);
        ticketregalo = findViewById(R.id.imprimirticketregalo);
        salirboton = findViewById(R.id.salir);
        totaltext = findViewById(R.id.total);
        tarjetatext = findViewById(R.id.tarjeta);

        Intent intent = getIntent();
        DecimalFormat decim = new DecimalFormat("0.00");
        importet = intent.getDoubleExtra("importe", 0.0);


        totaltext.setText(decim.format(importet)+"€");
        tarjetatext.setText(decim.format(importet)+"€");

        BaseDatos resg = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getWritableDatabase();
 
 
 
 
     Cursor cursorid2 = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);
        if(cursorid2.moveToFirst()){
            id = cursorid2.getInt(0);
        }

        if(id == 0){
            id = 1;
        }else {
            id = id + 1;
        }



int idticket = 0;


        Cursor cursorid3 = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion", null);
        if(cursorid3.moveToFirst()){
            idticket = cursorid3.getInt(0);
        }



if(idticket != 0){
    id = idticket;
}
        resg.anadirdatosorden(id,importet);



        if (id != 0) {
            resg.ordentarjeta(id);
            resg.borrarnumeroticket();
        }


        Imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Todavía por implementar", Toast.LENGTH_SHORT);

                toast1.show();
            }
        });

        ticketregalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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