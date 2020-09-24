package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.R;

public class VentaIntroduceImporteTicketRestaurante extends AppCompatActivity {
    EditText importe;
    Button cobrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_introduce_importe_ticket_restaurante_fragment);
        cobrar = findViewById(R.id.cobrar);
        importe = findViewById(R.id.importeedit);
        cobrar.setEnabled(false);


        if (!cobrar.isEnabled()) {
            cobrar.setBackgroundResource(R.drawable.botongrisclaro);
        }

        importe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    cobrar.setEnabled(true);
                    cobrar.setBackgroundResource(R.drawable.botonnaranja);
                } else {
                    cobrar.setEnabled(false);
                    cobrar.setBackgroundResource(R.drawable.botongrisclaro);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PagoRealizadoEfectivo.class);
                startActivity(i);
            }
        });


    }
}
