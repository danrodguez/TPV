package com.diusframi.tpv.Fragments.Venta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.R;

import java.util.StringTokenizer;

public class Articulopreciovariable extends AppCompatActivity {
    //Declaraciones
    EditText precio;
    Button cancelar;
    Button guardar;
    String categoria;
    Integer numero;
    private Button BotonIVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulopreciovariable);

        final String nombre = getIntent().getStringExtra("nombre");
        cancelar = findViewById(R.id.cancelarboton);
        guardar = findViewById(R.id.guardarboton);
        precio = findViewById(R.id.precioedit);
        BotonIVA = findViewById(R.id.ivaboton);

        guardar.setEnabled(false);
        guardar.setBackgroundResource(R.drawable.botongrisclaro);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                startActivity(i);
            }
        });

//sumar el articulo a ArticulosVendidos
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseDatos resg = new BaseDatos(getApplicationContext(), "BaseDatos", null, 1);
                SQLiteDatabase bd = resg.getReadableDatabase();
                @SuppressLint("Recycle") Cursor cursor = bd.rawQuery("SELECT Categorias FROM Articulos WHERE Nombre LIKE '" + nombre + "'", null);
                if (cursor.moveToNext()) {
                    categoria = cursor.getString(0);
                }
                @SuppressLint("Recycle") Cursor cursor2 = bd.rawQuery("SELECT Numero FROM ArticulosVenta WHERE Nombre LIKE '" + nombre + "'", null);
                if (cursor2.moveToNext()) {
                    numero = cursor2.getInt(0);
                    numero = numero + 1;
                } else {
                    numero = 1;
                }
                double preciotexto = Double.parseDouble(precio.getText().toString());

                StringTokenizer tokens = new StringTokenizer(BotonIVA.getText().toString(), " ");
                String ivacortado = tokens.nextToken();

                int iva = Integer.parseInt(ivacortado);
                resg.articulonuevolistacompra(categoria, nombre, numero, preciotexto, iva);
                Intent i = new Intent(getApplicationContext(), Venta.class);
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
        ImageButton salir = view.findViewById(R.id.salirboton);
        Button cancelar = view.findViewById(R.id.cancelar);
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
                guardar.setEnabled(true);
                guardar.setBackgroundResource(R.drawable.botonnaranja);
                dialog.dismiss();

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        iva10boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = iva10boton.getText().toString();
                BotonIVA.setText(ivatexto);
                guardar.setEnabled(true);
                guardar.setBackgroundResource(R.drawable.botonnaranja);
                dialog.dismiss();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = "";
                BotonIVA.setText(ivatexto);
                dialog.dismiss();

            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

}