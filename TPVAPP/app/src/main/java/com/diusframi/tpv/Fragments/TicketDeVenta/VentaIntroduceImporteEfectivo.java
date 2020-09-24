package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.text.DecimalFormat;

public class VentaIntroduceImporteEfectivo extends AppCompatActivity {
    EditText efectivo;
    Button cobrar;
    TextView importetext;
    Double total;
    Button cancelarboton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_introduce_importe_efectivo_fragment);
        cobrar = findViewById(R.id.cobrar);
        efectivo = findViewById(R.id.efectivoedit);
        importetext = findViewById(R.id.importe);
        cancelarboton = findViewById(R.id.cancelar);


        cobrar.setEnabled(false);

        Intent intent = getIntent();

        total = intent.getDoubleExtra("totalnumero", 0.0);
        DecimalFormat decim = new DecimalFormat("0.00");
        importetext.setText(String.valueOf(decim.format(total)));


        if (!cobrar.isEnabled()) {
            cobrar.setBackgroundResource(R.drawable.botongrisclaro);
        }

        efectivo.addTextChangedListener(new TextWatcher() {
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
        cancelarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });


        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat decim = new DecimalFormat("0.00");
                String efectivotexto = efectivo.getText().toString();
                Double efectivonumero = Double.parseDouble(efectivotexto);
                Double importe = total;
                if(efectivonumero<total){
                    Toast toast2 =
                            Toast.makeText(getApplicationContext(),
                                    "Total mayor que efectivo", Toast.LENGTH_SHORT);



                    toast2.show();
                }else{
                    Intent i = new Intent(getApplicationContext(), PagoRealizadoEfectivo.class);
                    i.putExtra("efectivo", efectivonumero);
                    i.putExtra("importe", importe);
                    startActivity(i);
                }

            }
        });


    }
}


