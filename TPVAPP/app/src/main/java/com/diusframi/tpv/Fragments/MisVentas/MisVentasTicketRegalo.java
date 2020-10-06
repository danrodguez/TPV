package com.diusframi.tpv.Fragments.MisVentas;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.ProductoTicket;
import com.diusframi.tpv.Constructores.ProductosTicketAdapter;
import com.diusframi.tpv.Fragments.Correo;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MisVentasTicketRegalo extends Fragment {


    TextView nombrecomercio;
    TextView nombrefiscal;
    TextView cif;
    TextView direccionfiscal;
    TextView factura;
    TextView fecha;
    TextView hora;
    TextView base10;
    TextView cuota10;
    TextView base21;
    TextView cuota21;
    ImageView imagenview;
    RecyclerView recyclerView;
    Button volverboton;
    ImageButton imprimirboton;
    ImageButton correoboton;
    ImageButton devolverboton;
    String orden;
    String fechatexto="";
    String horatexto="";
    String facturatexto="";
    String direccionfiscaltexto="";
    String ciftexto="";
    String nombrefiscaltexto="";
    String nombrecomerciotexto="";
    Double totalnumero=0.0;
    Double cambionumero=0.0;
    Double total10numero=0.0;
    Double total21numero=0.0;
    Double impuestos10baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    byte[] imagen = null;
    Cursor cursorfechahoratotal;
    Cursor cursorcomercialfiscal;
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();



    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_mis_ventas_ticket_regalo, container, false);

        volverboton = view.findViewById(R.id.volver);
        imprimirboton = view.findViewById(R.id.imprimir);
        correoboton = view.findViewById(R.id.correo);
        devolverboton = view.findViewById(R.id.devolver);
        nombrecomercio = view.findViewById(R.id.nombrecomerciotext);
        nombrefiscal = view.findViewById(R.id.nombrefiscaltext);
        cif = view.findViewById(R.id.ciftext);
        direccionfiscal = view.findViewById(R.id.direccionfiscaltext);
        factura = view.findViewById(R.id.facturatext);
        fecha = view.findViewById(R.id.fechatext);
        hora = view.findViewById(R.id.horatext);
        recyclerView = view.findViewById(R.id.RecyclerView);
        base10 = view.findViewById(R.id.base10text);
        cuota10 = view.findViewById(R.id.cuota10text);
        base21 = view.findViewById(R.id.base21text);
        cuota21 = view.findViewById(R.id.cuota21text);
        imagenview = view.findViewById(R.id.imagen);


        ProductosTicketAdapter adapter1 = new ProductosTicketAdapter(getContext(), listaproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orden = bundle.getString("orden", orden);
        }

        BaseDatos resg = new BaseDatos(getContext(), null);
        SQLiteDatabase bd = resg.getReadableDatabase();

        cursorfechahoratotal = bd.rawQuery("SELECT Fecha,Hora,Total,Cambio FROM Ordenes WHERE id LIKE '"+orden+"'", null);
        while (cursorfechahoratotal.moveToNext()) {
            fechatexto = cursorfechahoratotal.getString(0);
            horatexto = cursorfechahoratotal.getString(1);
            totalnumero = cursorfechahoratotal.getDouble(2);
            cambionumero = cursorfechahoratotal.getDouble(3);
        }

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre FROM Vendidos WHERE idorden LIKE '"+orden+"' ", null);

        String Nombre = "";
        int Numero = 0;
        double Precio = 0.0;
        double Importe = 0.0;

        while (cursorlista.moveToNext()) {
            Numero = cursorlista.getInt(0);
            Nombre = cursorlista.getString(1);
            Precio = cursorlista.getDouble(2);

            Importe = Precio * Numero;
            listaproductos.add(new ProductoTicket(Numero,Nombre,Precio,Importe));
            adapter1.setProductoLista(listaproductos);
        }
        ProductosTicketAdapter adapter = new ProductosTicketAdapter(getContext(),  listaproductos);
        recyclerView.setAdapter(adapter);







        BaseDatos resg2 = new BaseDatos(getContext(), null);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        cursorcomercialfiscal = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo FROM Usuarios WHERE activo LIKE 1", null);
        while (cursorcomercialfiscal.moveToNext()) {
            nombrefiscaltexto = cursorcomercialfiscal.getString(0);
            nombrecomerciotexto = cursorcomercialfiscal.getString(1);
            ciftexto = cursorcomercialfiscal.getString(2);
            direccionfiscaltexto = cursorcomercialfiscal.getString(3)+","+cursorcomercialfiscal.getString(4)+","+cursorcomercialfiscal.getString(5)+","+cursorcomercialfiscal.getString(6);
            imagen = cursorcomercialfiscal.getBlob(7);
        }

        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;

        DecimalFormat decim = new DecimalFormat("0.00");


        nombrefiscal.setText(nombrefiscaltexto);
        nombrecomercio.setText(nombrecomerciotexto);
        cif.setText(ciftexto);
        if(imagen!=null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imagen);
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            imagenview.setImageBitmap(theImage);}
        direccionfiscal.setText(direccionfiscaltexto);
        Cursor cursor2 = bd2.rawQuery("SELECT  TextoTicket FROM TextoTicketDevolucion", null);

        String TextoTicket = "";



        while (cursor2.moveToNext()) {
            TextoTicket = cursor2.getString(0);
        }
        facturatexto = TextoTicket+orden;
        String dia = fechatexto.substring(6, 8);
        String mes = fechatexto.substring(4, 6);
        String anio = fechatexto.substring(0, 4);
        String fechatextocompleto = dia + "/" + mes + "/" + anio;
        fecha.setText(fechatextocompleto);
        hora.setText(horatexto);
        factura.setText(facturatexto);



        volverboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Venta.class);
                i.putExtra("misventas","si");
                i.putExtra("ticketventa","");
                startActivity(i);
            }
        });

        imprimirboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Todavia por implementar",
                        Toast.LENGTH_LONG).show();
            }
        });

        correoboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Correo.class);
                startActivity(i);
            }
        });

        devolverboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Devolucion.class);
                startActivity(i);
            }
        });

        return view;
    }
}