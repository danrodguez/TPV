package com.diusframi.tpv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.diusframi.tpv.Fragments.Venta.Venta;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public EditText Email;
    public EditText Contrasena;
    public Button acceder;
    public String usuarios;
    public String contrasenas;
    public Button crearcuenta;
    boolean firsEditText;
    Cursor listaRegistros;
    Cursor listaRegistros2;
    Button recuperarcontrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crearcuenta = findViewById(R.id.CrearCuentag);
        Email = findViewById(R.id.emailedit);
        Contrasena = findViewById(R.id.contrasenaEdit);
        acceder = findViewById(R.id.Acceder);
        recuperarcontrasena = findViewById(R.id.recuperarcontrasenaboton);
        BaseDatos resg = new BaseDatos(this, "BaseDatos", null, 1);
        BaseDatos resg2 = new BaseDatos(this, "BaseDatos", null, 1);
        final SQLiteDatabase bd = resg.getReadableDatabase();
        final SQLiteDatabase bdw = resg2.getWritableDatabase();

        listaRegistros2 = bd.rawQuery("SELECT * FROM Login WHERE log = '0'", null);
        if (!listaRegistros2.moveToFirst()) {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        }


        acceder.setEnabled(false);
        if (!acceder.isEnabled()) {
            acceder.setBackgroundResource(R.drawable.botongrisclaro);
        }


        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 & Contrasena.getText().toString().length() > 0) {
                    firsEditText = true;
                    acceder.setEnabled(true);
                } else {
                    firsEditText = false;
                    acceder.setEnabled(false);
                }

                if (!acceder.isEnabled()) {
                    acceder.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    acceder.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 & Email.getText().toString().length() > 0) {
                    firsEditText = true;
                    acceder.setEnabled(true);
                } else {
                    firsEditText = false;
                    acceder.setEnabled(false);
                }

                if (!acceder.isEnabled()) {
                    acceder.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    acceder.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        acceder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                usuarios = Email.getText().toString();
                contrasenas = Contrasena.getText().toString();
                listaRegistros = bd.rawQuery("SELECT * FROM Usuarios WHERE email = '" + usuarios + "' AND contrasena = '" + contrasenas + "'", null);

//Busca si el usuario esta en la base de datos y chequea si es un mail (el usuario admin dara un bug visual pero funciona)
                checkData();
                if (listaRegistros.moveToFirst() & listaRegistros2.moveToFirst()) {
                    String sentenciaSQL = "UPDATE Login SET log =1;";
                    bdw.execSQL(sentenciaSQL);
                    String sentenciaSQL2 = "UPDATE Usuarios SET activo = 1 WHERE email = '" + usuarios + "';";
                    bdw.execSQL(sentenciaSQL2);
                    Intent i = new Intent(getApplicationContext(), Venta.class);
                    startActivity(i);

                }

            }
        });


        recuperarcontrasena.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RecuperarContrasena.class);
                startActivity(i);

            }


        });


        crearcuenta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CrearCuenta.class);
                startActivity(i);
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }


    }


    boolean Emailfuncion(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    void checkData() {
        if (!Emailfuncion(Email)) {
            Email.setError("Email no valido");
        }
    }



}


