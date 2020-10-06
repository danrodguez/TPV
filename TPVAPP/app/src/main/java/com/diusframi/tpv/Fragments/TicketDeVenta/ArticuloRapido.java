package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.InputDinero;
import com.diusframi.tpv.R;

import java.util.StringTokenizer;

public class ArticuloRapido extends AppCompatActivity {
    //Declaraciones
    EditText precioedit;
    TextView pvpedit;
    EditText articuloedit;
    Button cancelar;
    Button guardar;
    String categoria;
    Double preciotexto = 0.0;
    String precioedittexto = "";
    String ivatextoedit = "";
    private Button BotonIVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_rapido);

        articuloedit = findViewById(R.id.nombrearticuloedit);
        precioedit = findViewById(R.id.pvpedit);
        cancelar = findViewById(R.id.cancelarboton);
        guardar = findViewById(R.id.guardarboton);
        BotonIVA = findViewById(R.id.ivaboton);

        guardar.setEnabled(false);
        guardar.setBackgroundResource(R.drawable.botongrisclaro);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          onBackPressed();
            }
        });
        precioedittexto = precioedit.getText().toString();
        ivatextoedit = BotonIVA.getText().toString();


        precioedit.setFilters(new InputFilter[]{new InputDinero()});




        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        articuloedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & precioedit.getText().toString().length() > 0

                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & precioedit.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & precioedit.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });





        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        precioedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & articuloedit.getText().toString().length() > 0

                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & articuloedit.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & articuloedit.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });



//sumar el articulo a ArticulosVendidos
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseDatos resg = new BaseDatos(getApplicationContext(), null);
                double preciotexto = Double.parseDouble(precioedit.getText().toString());
                StringTokenizer tokens = new StringTokenizer(BotonIVA.getText().toString(), "%");
                String ivacortado = tokens.nextToken();
                    int iva = Integer.parseInt(ivacortado);

                resg.articulonuevolistacompra("", articuloedit.getText().toString(), 1, preciotexto, iva);

                String ticket = "si";
                Intent i = new Intent(getApplicationContext(), Venta.class);
                i.putExtra("ticketventa", ticket);
                i.putExtra("misventas","");
                 startActivity(i);
            }
        });


        BotonIVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogarticuloiva();
            }
        });

    }


    public void openDialogarticuloiva() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ivaeditararticulodialog, null);

        final Button iva21boton = view.findViewById(R.id.iva21);
        final Button iva10boton = view.findViewById(R.id.iva10);
        Button cancelar = view.findViewById(R.id.cancelarboton);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);


        // Set negative/no button click listener
        iva21boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = iva21boton.getText().toString();
                BotonIVA.setText(ivatexto);
                if (!precioedit.getText().toString().equals("") && !BotonIVA.getText().toString().equals("")) {

                    preciotexto = Double.parseDouble(precioedit.getText().toString());
                    StringTokenizer tokens = new StringTokenizer(BotonIVA.getText().toString(), "%");
                    String ivacortado = tokens.nextToken();
                    int iva = Integer.parseInt(ivacortado);

                    guardar.setEnabled(true);

                    guardar.setBackgroundResource(R.drawable.botonnaranja);




            }
                dialog.dismiss();
            }
        });



        iva10boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = iva10boton.getText().toString();
                BotonIVA.setText(ivatexto);
                if (!precioedit.getText().toString().equals("") && !BotonIVA.getText().toString().equals("")) {

                    preciotexto = Double.parseDouble(precioedit.getText().toString());
                    StringTokenizer tokens = new StringTokenizer(BotonIVA.getText().toString(), "%");
                    String ivacortado = tokens.nextToken();
                    int iva = Integer.parseInt(ivacortado);

                    guardar.setEnabled(true);

                    guardar.setBackgroundResource(R.drawable.botonnaranja);

                }

                dialog.dismiss();

            }
        });




        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = "";
                BotonIVA.setText(ivatexto);
                dialog.dismiss();
                guardar.setEnabled(false);

                guardar.setBackgroundResource(R.drawable.botongrisclaro);

            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

}