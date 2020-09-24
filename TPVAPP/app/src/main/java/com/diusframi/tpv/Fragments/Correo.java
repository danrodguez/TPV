package com.diusframi.tpv.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.MainActivity;
import com.diusframi.tpv.R;

import java.util.Calendar;

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

        Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}