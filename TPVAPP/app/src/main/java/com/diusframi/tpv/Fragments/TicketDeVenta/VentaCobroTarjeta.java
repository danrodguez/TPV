package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.R;

import java.text.DecimalFormat;

public class VentaCobroTarjeta extends AppCompatActivity {
    LinearLayout linear;
    Double totalnumero;
    TextView totaltext;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_cobro_tarjeta);
        linear = findViewById(R.id.linearlayout);
        totaltext = findViewById(R.id.total);
        Intent intent = getIntent();
        DecimalFormat decim = new DecimalFormat("0.00");
        totalnumero = intent.getDoubleExtra("totalnumero", 0.0);
        totaltext.setText(decim.format(totalnumero) +"€");
        linear.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), PagoRealizadoTarjeta.class);
            i.putExtra("importe", totalnumero);
            startActivity(i);
        });
    }
}