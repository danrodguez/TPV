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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class MisVentasTicketDevolucion extends Fragment {
    TextView nombrecomercio;
    TextView nombrefiscal;
    TextView cif;
    TextView direccionfiscal;
    TextView factura;
    TextView fecha;
    TextView total;
    TextView hora;
    TextView base10;
    TextView cuota10;
    TextView base21;
    TextView cuota21;
    TextView pago;
    RecyclerView recyclerView;
    Button volverboton;
    ImageView imagenview;
    ImageButton imprimirboton;
    ImageButton correoboton;
    ImageButton devolverboton;
    String orden;
    String ordenticket;
    String fechatexto="";
    String horatexto="";
    String facturatexto="";
    String facturatexto2="";
    String direccionfiscaltexto="";
    String ciftexto="";
    String nombrefiscaltexto="";
    String nombrecomerciotexto="";
    TextView origen;
    Double total10numero=0.0;
    Double total21numero=0.0;
    Double impuestos10baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    Double totalnumero = 0.0;
    byte[] imagen = null;
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_mis_ventas_ticket_devolucion, container, false);

        pago = view.findViewById(R.id.totalpago);
        total = view.findViewById(R.id.total);
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
        imagenview = view.findViewById(R.id.imagen);
        origen = view.findViewById(R.id.origentext);
        base10 = view.findViewById(R.id.base10text);
        cuota10 = view.findViewById(R.id.cuota10text);
        base21 = view.findViewById(R.id.base21text);
        cuota21 = view.findViewById(R.id.cuota21text);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            orden = bundle.getString("orden", orden);
        }
        ProductosTicketAdapter adapter1 = new ProductosTicketAdapter(getContext(), listaproductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter1);

        BaseDatos resg = new BaseDatos(getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor = bd.rawQuery("SELECT Fecha,HoraTexto,Total,TipoPago FROM Devoluciones WHERE id LIKE '" + orden + "'", null);
        while (cursor.moveToNext()) {
            fechatexto = cursor.getString(0);
            horatexto = cursor.getString(1);
            totalnumero = cursor.getDouble(2);
        }

        Cursor cursor3 = bd.rawQuery("SELECT  TextoDevolucion FROM TextoTicketDevolucion", null);
        Cursor cursor4 = bd.rawQuery("SELECT  TextoTicket FROM TextoTicketDevolucion", null);
        Cursor cursor5 = bd.rawQuery("SELECT  idticket FROM Devoluciones WHERE id LIKE '"+orden+"'", null);
        String TextoTicket = "";
        String TextoTicket2 = "";


        while (cursor3.moveToNext()) {
            TextoTicket = cursor3.getString(0);
        }
        while (cursor5.moveToNext()) {
            ordenticket = cursor5.getString(0);
        }


        while (cursor4.moveToNext()) {
            TextoTicket2 = cursor4.getString(0);
        }
        facturatexto = TextoTicket+orden;
        facturatexto2 = TextoTicket2+ordenticket;

        String dia = fechatexto.substring(6, 8);
        String mes = fechatexto.substring(4, 6);
        String anio = fechatexto.substring(0, 4);
        String fechatextocompleto = dia + "/" + mes + "/" + anio;
        fecha.setText(fechatextocompleto);
        hora.setText(horatexto);
        factura.setText(facturatexto);

        BaseDatos resg2 = new BaseDatos(getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo FROM Usuarios WHERE activo LIKE 1", null);
        while (cursor2.moveToNext()) {
            nombrefiscaltexto = cursor2.getString(0);
            nombrecomerciotexto = cursor2.getString(1);
            ciftexto = cursor2.getString(2);
            direccionfiscaltexto = cursor2.getString(3) + "," + cursor2.getString(4) + "," + cursor2.getString(5) + "," + cursor2.getString(6);
            imagen = cursor2.getBlob(7);
        }

        nombrefiscal.setText(nombrefiscaltexto);
        nombrecomercio.setText(nombrecomerciotexto);
        cif.setText(ciftexto);
        direccionfiscal.setText(direccionfiscaltexto);
        origen.setText(facturatexto2);
        if(imagen!=null){
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imagen);
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            imagenview.setImageBitmap(theImage);}


    Double precio10 = 0.0;
        int numero10 = 0;
        Double precio21 = 0.0;
        int numero21 = 0;


        Cursor  cursor10baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE Iva = '10' AND idorden LIKE '"+ordenticket+"' ", null);

        while (cursor10baseimponible.moveToNext()) {
            precio10 = cursor10baseimponible.getDouble(0);
            numero10 = cursor10baseimponible.getInt(1);
            total10numero = total10numero + (precio10 * numero10);
        }

        impuestos10cuotanumero = (total10numero*10)/100;

        Cursor  cursor21baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE Iva = '21' AND idorden LIKE '"+ordenticket+"' ", null);

        while (cursor21baseimponible.moveToNext()) {
            precio21 = cursor21baseimponible.getDouble(0);
            numero21 = cursor21baseimponible.getInt(1);
            total21numero =total21numero + (precio21 * numero21);
        }

        impuestos21cuotanumero = (total21numero*21)/100;

        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;

        DecimalFormat decim = new DecimalFormat("0.00");
        cuota10.setText(String.valueOf(decim.format(impuestos10cuotanumero)));
        cuota21.setText(String.valueOf(decim.format(impuestos21cuotanumero)));
        base10.setText(String.valueOf(decim.format(impuestos10baseimponiblenumero)));
        base21.setText(String.valueOf(decim.format(impuestos21baseimponiblenumero)));
        total.setText(String.valueOf(decim.format(totalnumero)));
        pago.setText(String.valueOf(decim.format(totalnumero)));

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre,Precio FROM Devueltostemporal WHERE idorden LIKE '"+ordenticket+"' ", null);

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




        return view;
    }
}