package com.diusframi.tpv;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.Fragments.Venta.Venta;

public class ConfiguracionInicial extends AppCompatActivity {
    boolean firsEditText;
    String email;
    String contrasena;
    String cif;
    EditText nombrecomercial;
    EditText nombrefiscal;
    EditText domiciliofiscal;
    EditText localidadfiscal;
    EditText codigopostalfiscal;
    EditText provinciafiscal;
    EditText telefonofiscal;
    Button crearcuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_inicial);
        final BaseDatos sqLiteHelper = new BaseDatos(this, "BDUsuarios", null, 1);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        contrasena = intent.getStringExtra("contrasena");
        cif = intent.getStringExtra("cif");
        nombrecomercial = findViewById(R.id.nombrecomercialedit);
        nombrefiscal = findViewById(R.id.nombrefiscalconfiguracioninicialedit);
        domiciliofiscal = findViewById(R.id.domicilioedit);
        localidadfiscal = findViewById(R.id.localidadedit);
        codigopostalfiscal = findViewById(R.id.codigopostaledit);
        provinciafiscal = findViewById(R.id.provinciaedit);
        telefonofiscal = findViewById(R.id.telefonoedit);
        crearcuenta = findViewById(R.id.CrearCuenta);


        //metodo enable button y cambio de color
        crearcuenta.setEnabled(false);
        if (!crearcuenta.isEnabled()) {
            crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
        }

        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        nombrecomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que nombrefiscal tiene texto y vuelve el boton de enviar de gris a naranja
        nombrefiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que domiciliofiscal tiene texto y vuelve el boton de enviar de gris a naranja
        domiciliofiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0) {

                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que localidadfiscal tiene texto y vuelve el boton de enviar de gris a naranja
        localidadfiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {
                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que codigopostalfiscal tiene texto y vuelve el boton de enviar de gris a naranja
        codigopostalfiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {
                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que provinciafiscal tiene texto y vuelve el boton de enviar de gris a naranja
        provinciafiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que telefonofiscal tiene texto y vuelve el boton de enviar de gris a naranja
        telefonofiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    firsEditText = true;
                    crearcuenta.setEnabled(true);
                } else {
                    firsEditText = false;
                    crearcuenta.setEnabled(false);
                }

                if (!crearcuenta.isEnabled()) {
                    crearcuenta.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    crearcuenta.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que crearcuenta tiene texto y vuelve el boton de enviar de gris a naranja
        final byte[] vacio = new byte[0];
        crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sqLiteHelper.insertDataUsuarios(
                        email,
                        contrasena,
                        cif,
                        nombrecomercial.getText().toString(),
                        nombrefiscal.getText().toString(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        domiciliofiscal.getText().toString(),
                        localidadfiscal.getText().toString(),
                        codigopostalfiscal.getText().toString(),
                        provinciafiscal.getText().toString(),
                        telefonofiscal.getText().toString(),
                        vacio,
                        1
                );
                Intent intent = new Intent(getApplicationContext(), Venta.class);
                startActivity(intent);
            }
        });
    }
}