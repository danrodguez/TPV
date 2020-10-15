package com.diusframi.tpv.Fragments.MisVentas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
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
import com.example.etickets_sdk.DatosCajaR;
import com.example.etickets_sdk.Eticket;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    String respStr = "";
    String numerotelefono = "";
    ArrayList<String> listanombres = new ArrayList<>();
    ArrayList<Integer> listanumero = new ArrayList<>();
    ArrayList<Double> listaimporte = new ArrayList<>();
    ArrayList<Double> listaprecio = new ArrayList<>();
    byte[] imagen = null;
    DatosCajaR datos = new DatosCajaR();
    Eticket crearPDF =new Eticket();
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

        BaseDatos resg = new BaseDatos(getContext(), null);
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
        cursor3.close();
        cursor4.close();
        cursor5.close();
        facturatexto = TextoTicket+orden;
        facturatexto2 = TextoTicket2+ordenticket;

        String dia = fechatexto.substring(6, 8);
        String mes = fechatexto.substring(4, 6);
        String anio = fechatexto.substring(0, 4);
        String fechatextocompleto = dia + "/" + mes + "/" + anio;
        fecha.setText(fechatextocompleto);
        hora.setText(horatexto);
        factura.setText(facturatexto);

        BaseDatos resg2 = new BaseDatos(getContext(), null);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();

        @SuppressLint("Recycle") Cursor cursor2 = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo,telefonocomercial FROM Usuarios WHERE activo LIKE 1", null);
        while (cursor2.moveToNext()) {
            nombrefiscaltexto = cursor2.getString(0);
            nombrecomerciotexto = cursor2.getString(1);
            ciftexto = cursor2.getString(2);
            direccionfiscaltexto = cursor2.getString(3) + "," + cursor2.getString(4) + "," + cursor2.getString(5) + "," + cursor2.getString(6);
            imagen = cursor2.getBlob(7);
            numerotelefono = cursor2.getString(8);
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


        double precio10;
        int numero10;
        double precio21;
        int numero21;


        Cursor  cursor10baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE Iva = '10' AND idorden LIKE '"+orden+"' ", null);

        while (cursor10baseimponible.moveToNext()) {
            precio10 = cursor10baseimponible.getDouble(0);
            numero10 = cursor10baseimponible.getInt(1);
            total10numero = total10numero + (precio10 * numero10);
        }
cursor10baseimponible.close();
        impuestos10cuotanumero = (total10numero*10)/100;

        Cursor  cursor21baseimponible = bd.rawQuery("SELECT Precio,Numero FROM Devueltostemporal WHERE Iva = '21' AND idorden LIKE '"+orden+"' ", null);

        while (cursor21baseimponible.moveToNext()) {
            precio21 = cursor21baseimponible.getDouble(0);
            numero21 = cursor21baseimponible.getInt(1);
            total21numero =total21numero + (precio21 * numero21);
        }
cursor21baseimponible.close();
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

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre,Precio FROM Devueltostemporal WHERE idorden LIKE '"+orden+"' ", null);

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
            listanombres.add(Nombre);
            listaimporte.add(Precio*Numero);
            listaprecio.add(Precio);
            listanumero.add( Numero);
        }
        cursorlista.close();
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

                //Datos Ticket
                int idticket = 0;

                Cursor cursorid3 = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion", null);
                if(cursorid3.moveToFirst()){
                    idticket = cursorid3.getInt(0);
                }

                cursorid3.close();

                if(idticket != 0){
                    int id = idticket;
                }


                final int finalIdticket = idticket;






                datos.setTotal(totalnumero);
                datos.setEfectivo(totalnumero);
                datos.setFactura(String.valueOf(finalIdticket));
                datos.setCif(ciftexto);
                datos.setBase10((impuestos10baseimponiblenumero));
                datos.setBase21(impuestos21baseimponiblenumero);
                datos.setCuota10(impuestos10cuotanumero);
                datos.setCuota21(impuestos21cuotanumero);
                datos.setCambio(0.0);
                datos.setDireccionFiscal(direccionfiscaltexto);
                datos.setFecha(fechatexto);
                datos.setNombreComercio(nombrecomerciotexto);
                datos.setNombreFiscal(nombrefiscaltexto);
                datos.setHora(horatexto);
                datos.setPrecio(totalnumero);
                datos.setNumero(String.valueOf(numerotelefono));
                datos.setListArticulos(listanombres);
                datos.setListPrecios(listaprecio);
                datos.setListImportes(listaimporte);
                datos.setListunidades(listanumero);


                crearPDF.generarQr();

                if (crearPDF.createPdf(datos.getNombreComercio(), datos.getNombreFiscal(), datos.getNumero(), datos.getDireccionFiscal(), datos.getCif(), datos.getFactura(), datos.getEfectivo(), datos.getCambio(), datos.getTotal(), datos.getBase10(), datos.getCuota10(), datos.getBase21(), datos.getCuota21(), listanombres, datos.getListunidades(), listaprecio, listaimporte)) {
                    Toast.makeText(getContext(), "Factura creada con exito", Toast.LENGTH_LONG).show();
                    MisVentasTicketDevolucion.Mostrar enviarticket = new Mostrar();
                    enviarticket.execute();
                } else {
                    Toast.makeText(getContext(), "Factura no creada", Toast.LENGTH_LONG).show();
                }
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

    @SuppressLint("StaticFieldLeak")
    public class Mostrar extends AsyncTask<String, String, Boolean> {
        private Date date = new Date();

        @SuppressLint("SimpleDateFormat")
        public Boolean doInBackground(String... params) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
            String fecha = dateFormat.format(this.date);

            dateFormat = new SimpleDateFormat("HH:mm:ss");
            String hora = dateFormat.format(this.date);
            boolean resul;
            HttpClient httpClient = new DefaultHttpClient();
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Factura_" + fecha + "&" + hora + ".pdf");

            HttpPost post = new HttpPost("https://cr289fri24.execute-api.eu-west-1.amazonaws.com/test/uploadToS3");
            post.setHeader("content-type", "application/json");
            try {
                JSONObject dato = new JSONObject();
                dato.put("username", "0320002");
                dato.put("menu_desc", "/Factura_23245");
                dato.put("xml", false); // false: pdf, true: xml
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                FileInputStream fis = new FileInputStream(f);
                byte[] data = new byte[1024];
                while ((nRead = fis.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                dato.put("menu_photo", Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT));
                StringEntity entity = new StringEntity(dato.toString());
                post.setEntity(entity);
                HttpResponse resp = httpClient.execute(post);
                respStr = EntityUtils.toString(resp.getEntity());
                StatusLine statusLine = resp.getStatusLine();
                resul = statusLine.getStatusCode() == HttpStatus.SC_OK;

            } catch (FileNotFoundException ex) {
                Log.e("FileNotFoundException", "Error!", ex);
                resul = false;

            } catch (UnsupportedEncodingException ex) {
                Log.e("UnsupportedEnco", "Error!", ex);
                resul = false;

            } catch (ClientProtocolException ex) {
                Log.e("ClientProtocolException", "Error!", ex);
                resul = false;

            } catch (IOException ex) {
                Log.e("IOException", "Error!", ex);
                resul = false;

            } catch (ParseException ex) {
                Log.e("ParseException", "Error!", ex);
                resul = false;

            } catch (JSONException ex) {
                Log.e("JSONException", "Error!", ex);
                resul = false;

            }
            return resul;
        }

        public void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getContext(), "Subido Correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error Servidor", Toast.LENGTH_SHORT).show();
            }
        }

    }
}