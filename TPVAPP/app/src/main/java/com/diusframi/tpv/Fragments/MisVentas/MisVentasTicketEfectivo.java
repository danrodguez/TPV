package com.diusframi.tpv.Fragments.MisVentas;

import android.annotation.SuppressLint;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.Articulo;
import com.diusframi.tpv.Constructores.ArticuloAdapter;
import com.diusframi.tpv.Constructores.ProductoTicket;
import com.diusframi.tpv.Constructores.ProductosTicketAdapter;
import com.diusframi.tpv.Constructores.VentaAdapter;
import com.diusframi.tpv.Constructores.VentaNumeroAdapter;
import com.diusframi.tpv.Fragments.Correo;
import com.diusframi.tpv.Fragments.MisArticulos.MisarticulosFragment;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.R;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MisVentasTicketEfectivo extends Fragment {
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
    TextView total;
    TextView efectivo;
    TextView cambio;
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
    int numero10 = 0;
    int numero21 = 0;
    byte[] imagen = null;
    Cursor cursorfechahoratotal;
    Cursor cursorcomercialfiscal;
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_mis_ventas_ticket_efectivo, container, false);

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
        total = view.findViewById(R.id.totaltext);
        efectivo = view.findViewById(R.id.efectivotext);
        cambio = view.findViewById(R.id.cambiotext);
        imagenview = view.findViewById(R.id.imagen);


        ProductosTicketAdapter adapter1 = new ProductosTicketAdapter(getContext(), listaproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter1);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orden = bundle.getString("orden", orden);
        }

        BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getReadableDatabase();

  cursorfechahoratotal = bd.rawQuery("SELECT Fecha,HoraTexto,Total,Cambio FROM Ordenes WHERE id LIKE '"+orden+"'", null);
            while (cursorfechahoratotal.moveToNext()) {
            fechatexto = cursorfechahoratotal.getString(0);
            horatexto = cursorfechahoratotal.getString(1);
            totalnumero = cursorfechahoratotal.getDouble(2);
            cambionumero = cursorfechahoratotal.getDouble(3);
            }

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre,Precio FROM Vendidos WHERE idorden LIKE '"+orden+"' ", null);

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




        Cursor  cursor10baseimponible = bd.rawQuery("SELECT SUM(Precio),Numero FROM Vendidos WHERE Iva = '10' AND idorden LIKE '"+orden+"' ", null);

        if (cursor10baseimponible.moveToNext()) {
            total10numero = cursor10baseimponible.getDouble(0);
            numero10 = cursor10baseimponible.getInt(1);

        }
        total10numero = total10numero * numero10;

        impuestos10cuotanumero = (total10numero*10)/100;

        Cursor  cursor21baseimponible = bd.rawQuery("SELECT SUM(Precio),Numero FROM Vendidos WHERE Iva = '21' AND idorden LIKE '"+orden+"' ", null);

        if (cursor21baseimponible.moveToNext()) {
            total21numero = cursor21baseimponible.getDouble(0);
            numero21 = cursor21baseimponible.getInt(1);
        }

        total21numero = total21numero * numero21;
        impuestos21cuotanumero = (total21numero*21)/100;


        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;

        BaseDatos resg2 = new BaseDatos(getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();






cursorcomercialfiscal = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo FROM Usuarios WHERE activo LIKE 1", null);
        while (cursorcomercialfiscal.moveToNext()) {
            nombrefiscaltexto = cursorcomercialfiscal.getString(0);
            nombrecomerciotexto = cursorcomercialfiscal.getString(1);
            ciftexto = cursorcomercialfiscal.getString(2);
            direccionfiscaltexto = cursorcomercialfiscal.getString(3)+","+cursorcomercialfiscal.getString(4)+","+cursorcomercialfiscal.getString(5)+","+cursorcomercialfiscal.getString(6);
            imagen = cursorcomercialfiscal.getBlob(7);
        }

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
        total.setText(String.valueOf(decim.format(totalnumero)));
        cuota10.setText(String.valueOf(decim.format(impuestos10cuotanumero)));
        cuota21.setText(String.valueOf(decim.format(impuestos21cuotanumero)));
        base10.setText(String.valueOf(decim.format(impuestos10baseimponiblenumero)));
        base21.setText(String.valueOf(decim.format(impuestos21baseimponiblenumero)));
        efectivo.setText(String.valueOf(decim.format(totalnumero)));
        cambio.setText(String.valueOf(decim.format(cambionumero)));

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
                i.putExtra("orden",orden);
                startActivity(i);
            }
        });

        return view;
    }
}