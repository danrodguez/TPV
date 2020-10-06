package com.diusframi.tpv;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.Fragments.Venta.Venta;

public class RecuperarContrasena extends AppCompatActivity {
    EditText emailedit;
    EditText contrasenaedit;
    String emailtexto;
    String contrasenatexto;
    Cursor listaRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        emailedit = findViewById(R.id.emailrecuperacionedit);
        contrasenaedit = findViewById(R.id.ContrasenarecuperacionEdit);

        BaseDatos resg = new BaseDatos(this, null);
        BaseDatos resg2 = new BaseDatos(this, null);
        final SQLiteDatabase bd = resg.getReadableDatabase();
        final SQLiteDatabase bdw = resg2.getReadableDatabase();
        emailtexto = emailedit.getText().toString();
        contrasenatexto = contrasenaedit.getText().toString();
        listaRegistros = bd.rawQuery("SELECT * FROM Usuarios WHERE email = '" + emailtexto + "'", null);

        if (listaRegistros.moveToFirst()) {
            String sentenciaSQL = "UPDATE Usuarios SET contrasena = '" + contrasenatexto + "' WHERE  email = '" + emailtexto + "';";
            bdw.execSQL(sentenciaSQL);
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);

        }
    }
}
