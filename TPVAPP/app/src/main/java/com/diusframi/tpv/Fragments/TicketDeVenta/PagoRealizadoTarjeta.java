package com.diusframi.tpv.Fragments.TicketDeVenta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Constructores.ProductoTicket;
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

public class PagoRealizadoTarjeta extends AppCompatActivity {
    Button Imprimir;
    Button ticketregalo;
    Button salirboton;
    TextView totaltext;
    TextView tarjetatext;
    Double importet = 0.0;
    Double efectivot = 0.0;
    byte[] imagen = null;
    String respStr = "";
    String numerotelefono = "";
    int orden;
    String fechatexto="";
    String horatexto="";
    String facturatexto="";
    String direccionfiscaltexto="";
    String ciftexto="";
    String nombrefiscaltexto="";
    String nombrecomerciotexto="";
    Double totalnumero=0.0;
    Double total10numero=0.0;
    Double total21numero=0.0;
    Double impuestos10baseimponiblenumero = 0.0;
    Double impuestos10cuotanumero = 0.0;
    Double impuestos21baseimponiblenumero = 0.0;
    Double impuestos21cuotanumero = 0.0;
    int id = 0;
    Cursor cursorfechahoratotal;
    Cursor cursorcomercialfiscal;
    ArrayList<Double> listanumero = new ArrayList<>();
    ArrayList<Double> listaimporte = new ArrayList<>();
    ArrayList<Double> listaprecio = new ArrayList<>();
    ArrayList<ProductoTicket> listaproductos = new ArrayList<>();
    ArrayList<String> listanombres = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_realizado_tarjeta);
        Imprimir = findViewById(R.id.imprimirticket);
        ticketregalo = findViewById(R.id.imprimirticketregalo);
        salirboton = findViewById(R.id.salir);
        totaltext = findViewById(R.id.total);
        tarjetatext = findViewById(R.id.tarjeta);
        DatosCajaR datos = new DatosCajaR();
        Eticket crearPDF =new Eticket();
        Intent intent = getIntent();
        DecimalFormat decim = new DecimalFormat("0.00");
        importet = intent.getDoubleExtra("importe", 0.0);


        totaltext.setText(decim.format(importet)+"€");
        tarjetatext.setText(decim.format(importet)+"€");

        BaseDatos resg = new BaseDatos(getApplicationContext(), null);
        SQLiteDatabase bd = resg.getWritableDatabase();
 
 
 
 
     Cursor cursorid2 = bd.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);
        if(cursorid2.moveToFirst()){
            id = cursorid2.getInt(0);
        }

        if(id == 0){
            id = 1;
        }else {
            id = id + 1;
        }
cursorid2.close();


int idticket = 0;


        Cursor cursorid3 = bd.rawQuery("SELECT NumeroTicket FROM TextoTicketDevolucion", null);
        if(cursorid3.moveToFirst()){
            idticket = cursorid3.getInt(0);
        }

cursorid3.close();

if(idticket != 0){
    id = idticket;
}
        resg.anadirdatosorden(id,importet);



        if (id != 0) {
            resg.ordentarjeta(id);
            resg.borrarnumeroticket();
        }

        //Datos Ticket


        orden = id;



        cursorfechahoratotal = bd.rawQuery("SELECT Fecha,Hora,Total FROM Ordenes WHERE id LIKE '"+orden+"'", null);
        while (cursorfechahoratotal.moveToNext()) {
            fechatexto = cursorfechahoratotal.getString(0);
            horatexto = cursorfechahoratotal.getString(1);
            totalnumero = cursorfechahoratotal.getDouble(2);
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
            listanombres.add(Nombre);
            listaimporte.add(Precio*Numero);
            listaprecio.add(Precio);
            listanumero.add((double) Numero);

        }
        cursorlista.close();




        Cursor  cursor10baseimponible = bd.rawQuery("SELECT SUM(Precio) FROM Vendidos WHERE Iva = '10' AND idorden LIKE '"+orden+"' ", null);

        if (cursor10baseimponible.moveToNext()) {
            total10numero = cursor10baseimponible.getDouble(0);
        }
        cursor10baseimponible.close();
        impuestos10cuotanumero = (total10numero*10)/100;

        Cursor  cursor21baseimponible = bd.rawQuery("SELECT SUM(Precio) FROM Vendidos WHERE Iva = '21' AND idorden LIKE '"+orden+"' ", null);

        if (cursor21baseimponible.moveToNext()) {
            total21numero = cursor21baseimponible.getDouble(0);
        }
        cursor21baseimponible.close();
        impuestos21cuotanumero = (total21numero*21)/100;

        BaseDatos resg2 = new BaseDatos(getApplicationContext(), null);
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

        impuestos10baseimponiblenumero = total10numero - impuestos10cuotanumero;
        impuestos21baseimponiblenumero = total21numero - impuestos21cuotanumero;






        Cursor cursor2 = bd2.rawQuery("SELECT  TextoTicket FROM TextoTicketDevolucion", null);

        String TextoTicket = "";

        while (cursor2.moveToNext()) {
            TextoTicket = cursor2.getString(0);
        }

        cursor2.close();


        facturatexto = TextoTicket+orden;






        cursor2.close();


        final int finalIdticket = idticket;



        Imprimir.setOnClickListener(v -> {


            datos.setTotal(totalnumero);
            datos.setEfectivo(efectivot);
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


            crearPDF.generarQr();

            if(crearPDF.createPdf(datos.getNombreComercio(),datos.getNombreFiscal(),datos.getNumero(),datos.getDireccionFiscal(),datos.getCif(),datos.getFactura(),datos.getEfectivo(),datos.getCambio(),datos.getTotal(),datos.getBase10(),datos.getCuota10(),datos.getBase21(),datos.getCuota21(),datos.getListArticulos(),datos.getListunidades(),listaprecio,listaimporte)){
                Toast.makeText(getApplicationContext(),"Factura creada con exito",Toast.LENGTH_LONG).show();
                PagoRealizadoTarjeta.Mostrar enviarticket = new PagoRealizadoTarjeta.Mostrar();
                enviarticket.execute();
            }else{
                Toast.makeText(getApplicationContext(),"Factura no creada",Toast.LENGTH_LONG).show();
            }
        });


        ticketregalo.setOnClickListener(v -> {

        });

        salirboton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), Venta.class);
            startActivity(i);
        });
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
                Toast.makeText(getApplicationContext(), "Subido Correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error Servidor", Toast.LENGTH_SHORT).show();
            }
        }

    }}