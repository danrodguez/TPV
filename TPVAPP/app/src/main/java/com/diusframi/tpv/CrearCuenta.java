package com.diusframi.tpv;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

public class CrearCuenta extends AppCompatActivity {
    boolean firsEditText;
    EditText crearcontrasena;
    TextView sntext;
    EditText cifedit;
    EditText emailedittext;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        sntext = findViewById(R.id.SNtext);
        cifedit = findViewById(R.id.CIFEdit);
        boton = findViewById(R.id.CrearCuenta);
        emailedittext = findViewById(R.id.emaileditregistro);
        crearcontrasena = findViewById(R.id.crearcontrasenaedit);
        final BaseDatos sqLiteHelper = new BaseDatos(this, null);


//funcion obligatoria para permisos de numero de serie
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        @SuppressLint("HardwareIds") String serial = Build.SERIAL;
        sntext.setText(serial);


        //metodo enable button y cambio de color
        boton.setEnabled(false);
        if (!boton.isEnabled()) {
            boton.setBackgroundResource(R.drawable.botongrisclaro);
        }


        //Funcion que comprueba que emailedittext tiene texto y vuelve el boton de enviar de gris a naranja
        emailedittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 & cifedit.getText().toString().length() > 0 & crearcontrasena.getText().toString().length() > 0) {
                    firsEditText = true;
                    boton.setEnabled(true);
                } else {
                    firsEditText = false;
                    boton.setEnabled(false);
                }

                if (!boton.isEnabled()) {
                    boton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    boton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que crearcontrasena tiene texto y vuelve el boton de enviar de gris a naranja
        crearcontrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 & emailedittext.getText().toString().length() > 0 & cifedit.getText().toString().length() > 0) {
                    firsEditText = true;
                    boton.setEnabled(true);
                } else {
                    firsEditText = false;
                    boton.setEnabled(false);
                }

                if (!boton.isEnabled()) {
                    boton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    boton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        cifedit.addTextChangedListener(new PatternedTextWatcher("#########") {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() > 0 & emailedittext.getText().toString().length() > 0 & crearcontrasena.getText().toString().length() > 0) {
                    firsEditText = true;
                    boton.setEnabled(true);
                } else {
                    firsEditText = false;
                    boton.setEnabled(false);
                }

                if (!boton.isEnabled()) {
                    boton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {
                    boton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        final byte[] vacio = new byte[0];

        boton.setOnClickListener(v -> {
            checkData();
            sqLiteHelper.insertDataUsuarios(
                    emailedittext.getText().toString(),
                    crearcontrasena.getText().toString(),
                    cifedit.getText().toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    vacio


            );
            Intent i = new Intent(getApplicationContext(), ConfiguracionInicial.class);
            i.putExtra("email", emailedittext.getText().toString());
            i.putExtra("contrasena", crearcontrasena.getText().toString());
            i.putExtra("cif", cifedit.getText().toString());
            startActivity(i);
        });
    }

    boolean CIF(EditText text) {
        CharSequence cif = text.getText().toString();
        return cif.length() == 9;
    }


    boolean Email(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    void checkData() {
        if (!Email(emailedittext)) {
            emailedittext.setError("Email no valido");
        }

        if (!CIF(cifedit)) {
            cifedit.setError("CIF no valido");
        }
    }
}

