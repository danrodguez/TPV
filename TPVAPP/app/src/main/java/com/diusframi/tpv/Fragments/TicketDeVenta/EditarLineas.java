package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.EditarLinea;
import com.diusframi.tpv.Constructores.EditarLineasAdapter;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.util.ArrayList;

public class EditarLineas extends AppCompatActivity {

    Button  confirmar;
    RecyclerView recycler;
    int orden = 0;
    private ArrayList<EditarLinea> listaeditar = new ArrayList<>();
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lineas);
        confirmar = findViewById(R.id.confirmarboton);
        recycler = findViewById(R.id.RecyclerView);

            recycler.setLayoutManager(new LinearLayoutManager(this));
            EditarLineasAdapter adapter = new EditarLineasAdapter(this, listaeditar);
            recycler.setAdapter(adapter);


            BaseDatos resg = new BaseDatos(getApplicationContext(), null);
            SQLiteDatabase bd = resg.getReadableDatabase();
            final Cursor cursor = bd.rawQuery("SELECT Categorias, Nombre, Numero, Precio, Iva FROM ArticulosVenta WHERE Numero > '0'", null);
            String nombre;
            int numero;



            final Cursor cursororden = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);

            ArrayList<EditarLinea> lista = new ArrayList<>();
            if (cursororden.moveToNext()) {
                orden = cursororden.getInt(0);
            }

            orden = orden + 1;
            while (cursor.moveToNext()) {
                nombre = cursor.getString(1);
                numero = cursor.getInt(2);
                lista.add(new EditarLinea(nombre, numero));
            }
            cursor.close();
            bd.close();
            cursororden.close();


            EditarLineasAdapter adapter2 = new EditarLineasAdapter( EditarLineas.this, lista);
            recycler.setAdapter(adapter2);

            confirmar.setOnClickListener(v -> {
                Intent i = new Intent(getApplicationContext(), Venta.class);
                i.putExtra("misventas","");
                i.putExtra("ticketventa","si");
                startActivity(i);
            });
    }

}