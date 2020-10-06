package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

public class PagorealizadoTicketRestaurante extends AppCompatActivity {
    Button Imprimir;
    Button ticketregalo;
    Button salirboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagorealizadoticketrestaurante);
        Imprimir = findViewById(R.id.imprimirticket);
        ticketregalo = findViewById(R.id.imprimirticketregalo);
        salirboton = findViewById(R.id.salir);


        Imprimir.setOnClickListener(v -> {
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Todavía por implementar", Toast.LENGTH_SHORT);

            toast1.show();
        });

        ticketregalo.setOnClickListener(v -> {

        });

        salirboton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        });
    }
}
