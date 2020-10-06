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
import com.diusframi.tpv.Fragments.TicketDeVenta.PagoRealizadoTarjeta;
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
    String numerotelefono = "";
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
    ArrayList<String> listanombres = new ArrayList<>();
    int id = 0;
    ArrayList<Integer> listanumero = new ArrayList<>();
    ArrayList<Double> listaimporte = new ArrayList<>();
    ArrayList<Double> listaprecio = new ArrayList<>();
    String respStr = "";
    int numero10 = 0;
    int numero21 = 0;
    byte[] imagen = null;
    Cursor cursorfechahoratotal;
    Cursor cursorcomercialfiscal;
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();
    DatosCajaR datos = new DatosCajaR();
    Eticket crearPDF =new Eticket();


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

        BaseDatos resg = new BaseDatos(getContext(), null);
        SQLiteDatabase bd = resg.getReadableDatabase();

  cursorfechahoratotal = bd.rawQuery("SELECT Fecha,HoraTexto,Total,Cambio FROM Ordenes WHERE id LIKE '"+orden+"'", null);
            while (cursorfechahoratotal.moveToNext()) {
            fechatexto = cursorfechahoratotal.getString(0);
            horatexto = cursorfechahoratotal.getString(1);
            totalnumero = cursorfechahoratotal.getDouble(2);
            cambionumero = cursorfechahoratotal.getDouble(3);
            }

        Cursor  cursorlista = bd.rawQuery("SELECT Numero,Nombre,Precio FROM Vendidos WHERE idorden LIKE '"+orden+"' ", null);

            String Nombre;
            int Numero;
            double Precio;
            double Importe;

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
        ProductosTicketAdapter adapter = new ProductosTicketAdapter(getContext(),  listaproductos);
         recyclerView.setAdapter(adapter);

cursorlista.close();


        Cursor  cursor10baseimponible = bd.rawQuery("SELECT SUM(Precio),Numero FROM Vendidos WHERE Iva = '10' AND idorden LIKE '"+orden+"' ", null);

        if (cursor10baseimponible.moveToNext()) {
            total10numero = cursor10baseimponible.getDouble(0);
            numero10 = cursor10baseimponible.getInt(1);

        }
        total10numero = total10numero * numero10;

        impuestos10cuotanumero = (total10numero*10)/100;
cursor10baseimponible.close();
        Cursor  cursor21baseimponible = bd.rawQuery("SELECT SUM(Precio),Numero FROM Vendidos WHERE Iva = '21' AND idorden LIKE '"+orden+"' ", null);

        if (cursor21baseimponible.moveToNext()) {
            total21numero = cursor21baseimponible.getDouble(0);
            numero21 = cursor21baseimponible.getInt(1);
        }
cursor21baseimponible.close();
        total21numero = total21numero * numero21;
        impuestos21cuotanumero = (total21numero*21)/100;


        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;

        BaseDatos resg2 = new BaseDatos(getContext(), null);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();






        cursorcomercialfiscal = bd2.rawQuery("SELECT nombrefiscal,nombrecomercial,cif,domiciliofiscal,localidadfiscal,codigopostalfiscal,provinciafiscal,logo,telefonocomercial FROM Usuarios WHERE activo LIKE 1", null);
        while (cursorcomercialfiscal.moveToNext()) {
            nombrefiscaltexto = cursorcomercialfiscal.getString(0);
            nombrecomerciotexto = cursorcomercialfiscal.getString(1);
            ciftexto = cursorcomercialfiscal.getString(2);
            direccionfiscaltexto = cursorcomercialfiscal.getString(3)+","+cursorcomercialfiscal.getString(4)+","+cursorcomercialfiscal.getString(5)+","+cursorcomercialfiscal.getString(6);
            imagen = cursorcomercialfiscal.getBlob(7);
            numerotelefono = cursorcomercialfiscal.getString(8);
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
cursor2.close();
bd2.close();
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

        volverboton.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), Venta.class);
            i.putExtra("misventas","si");
            i.putExtra("ticketventa","");
            startActivity(i);
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
                    id = idticket;
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
                        MisVentasTicketEfectivo.Mostrar enviarticket = new Mostrar();
                        enviarticket.execute();
                    } else {
                        Toast.makeText(getContext(), "Factura no creada", Toast.LENGTH_LONG).show();
                    }
                }
            });
        correoboton.setOnClickListener(v -> Toast.makeText(getActivity(), "Factura creada y enviada",
                Toast.LENGTH_LONG).show());

        devolverboton.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), Devolucion.class);
            i.putExtra("orden",orden);
            startActivity(i);
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

    }}