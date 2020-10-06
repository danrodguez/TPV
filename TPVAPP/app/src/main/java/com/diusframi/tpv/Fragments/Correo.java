package com.diusframi.tpv.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

public class Correo extends AppCompatActivity {
Button Cancelar;
Button Enviar;
EditText Correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo);
        Correo = findViewById(R.id.correo);
        Cancelar = findViewById(R.id.cancelarboton);
        Enviar = findViewById(R.id.enviarboton);

        Enviar.setEnabled(true);
        Enviar.setBackgroundResource(R.drawable.botonnaranja);
        Cancelar.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        });

        Enviar.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Factura enviada",
                Toast.LENGTH_LONG).show());
    }
}