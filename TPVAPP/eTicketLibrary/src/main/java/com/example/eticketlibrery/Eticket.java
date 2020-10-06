package com.example.eticketlibrery;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Eticket extends AppCompatActivity {

    private TextView ticket;
    private Bitmap tradeContentBitmap;
    Spinner item1,item2;
    String nombre_directorio="MisPdfs";
    String nombre_docmento="MiPDF.xml";
    private EditText datos1,dato2,numero_item1,numero_item2;
    private Button guardar;
    Bitmap bitmap,scale;
    int pageW=1200;
    Date date;
    String fecha,hora,respStr,pdf;
    String msj ="642953467";
    DateFormat dateFormat;
    Bitmap bitmapQr;
    float[] prices=new float[]{0,200,300,450,325,500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eticket);
        //ticket=findViewById(R.id.textView3);
        item1=findViewById(R.id.spinner);
        item2=findViewById(R.id.spinner2);
        datos1=findViewById(R.id.editTextTextPersonName2);
        dato2=findViewById(R.id.editTextTextPersonName3);
        numero_item1=findViewById(R.id.editTextNumber);
        numero_item2=findViewById(R.id.editTextNumber2);
        guardar=findViewById(R.id.button2);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.pizza);
        scale=Bitmap.createScaledBitmap(bitmap,1200,515,false);

        if(ContextCompat.checkSelfPermission(Eticket.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Eticket.this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(Eticket.this, new String[]{Manifest.permission.SEND_SMS}, 1);

            } else {
                ActivityCompat.requestPermissions(Eticket.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }else{


        }
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);
        generarQr();
        createPdf();
        }




    public void createPdf(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new Date();

                if (datos1.getText().toString().length() == 0 ||
                        dato2.getText().toString().length() == 0 ||
                        numero_item1.getText().toString().length() == 0 ||
                        numero_item2.getText().toString().length() == 0) {
                    Toast.makeText(Eticket.this, "Factura Vacia", Toast.LENGTH_SHORT).show();

                } else {


                    PdfDocument myPdf = new PdfDocument();
                    Paint miPaint = new Paint();
                    Paint tittle = new Paint();
                    PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage = myPdf.startPage(myPageInfo);
                    Canvas canvas = myPage.getCanvas();
                    canvas.drawBitmap(scale, 0, 0, miPaint);
                    tittle.setTextAlign(Paint.Align.CENTER);
                    tittle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    tittle.setTextSize(70);
                    canvas.drawText("Dimond Pizza", pageW / 2, 270, tittle);

                    miPaint.setColor(Color.rgb(0, 113, 188));
                    miPaint.setTextSize(30f);
                    miPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Call: 965-34-45-34", 1160, 40, miPaint);
                    canvas.drawText("953-34-34-32", 1160, 80, miPaint);

                    tittle.setTextAlign(Paint.Align.CENTER);
                    tittle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                    tittle.setTextSize(70);
                    canvas.drawText("Factura", pageW / 2, 570, tittle);

                    miPaint.setTextAlign(Paint.Align.LEFT);
                    miPaint.setTextSize(35f);
                    miPaint.setColor(Color.BLACK);
                    canvas.drawText("Nombre de Cliente: " + datos1.getText(), 20, 620, miPaint);
                    canvas.drawText("Email de Contacto.: " + dato2.getText(), 20, 670, miPaint);

                    miPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText("Factura Nº: " + "23245", pageW - 20, 620, miPaint);

                    dateFormat = new SimpleDateFormat("dd-MM-yy");
                    fecha=dateFormat.format(date);
                    canvas.drawText("Fecha: " + dateFormat.format(date), pageW - 20, 660, miPaint);



                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    hora=dateFormat.format(date);
                    canvas.drawText("Hora: " + dateFormat.format(date), pageW - 20, 700, miPaint);


                    miPaint.setStyle(Paint.Style.STROKE);
                    miPaint.setStrokeWidth(2);
                    canvas.drawRect(20, 780, pageW - 20, 860, miPaint);

                    miPaint.setTextAlign(Paint.Align.LEFT);
                    miPaint.setStyle(Paint.Style.FILL);
                    canvas.drawText("Si. No.", 40, 830, miPaint);
                    canvas.drawText("Item Descripcion", 200, 830, miPaint);
                    canvas.drawText("Precio", 700, 830, miPaint);
                    canvas.drawText("Cantidad", 887, 830, miPaint);
                    canvas.drawText("Total", 1050, 830, miPaint);

                    canvas.drawLine(180, 790, 180, 840, miPaint);
                    canvas.drawLine(680, 790, 680, 840, miPaint);
                    canvas.drawLine(880, 790, 880, 840, miPaint);
                    canvas.drawLine(1030, 790, 1030, 840, miPaint);

                    float total1 = 0, total2 = 0;

                    if (item1.getSelectedItemPosition() != 0) {
                        canvas.drawText("1.", 40, 950, miPaint);
                        canvas.drawText(item1.getSelectedItem().toString(), 200, 950, miPaint);
                        canvas.drawText(String.valueOf(prices[item1.getSelectedItemPosition()]), 700, 950, miPaint);
                        canvas.drawText(numero_item1.getText().toString(), 900, 950, miPaint);
                        total1 = Float.parseFloat(numero_item1.getText().toString()) * prices[item1.getSelectedItemPosition()];
                        miPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total1), pageW - 40, 950, miPaint);
                        miPaint.setTextAlign(Paint.Align.LEFT);

                    }
                    if (item2.getSelectedItemPosition() != 0) {
                        canvas.drawText("2.", 40, 1050, miPaint);
                        canvas.drawText(item2.getSelectedItem().toString(), 200, 1050, miPaint);
                        canvas.drawText(String.valueOf(prices[item2.getSelectedItemPosition()]), 700, 1050, miPaint);
                        canvas.drawText(numero_item2.getText().toString(), 900, 1050, miPaint);
                        total2 = Float.parseFloat(numero_item2.getText().toString()) * prices[item2.getSelectedItemPosition()];
                        miPaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(String.valueOf(total2), pageW - 40, 1050, miPaint);
                        miPaint.setTextAlign(Paint.Align.LEFT);


                    }

                    float subTotal = total1 + total2;
                    canvas.drawLine(680, 1200, pageW - 20, 1200, miPaint);
                    canvas.drawText("SubTotal ", 700, 1250, miPaint);
                    canvas.drawText(":", 900, 1250, miPaint);
                    miPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subTotal), pageW - 40, 1250, miPaint);

                    miPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Iva (12%)", 700, 1300, miPaint);
                    canvas.drawText(":", 900, 1300, miPaint);
                    miPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subTotal * 12 / 100), pageW - 40, 1300, miPaint);
                    miPaint.setTextAlign(Paint.Align.LEFT);

                    miPaint.setColor(Color.rgb(247, 147, 30));
                    canvas.drawRect(680, 1350, pageW - 20, 1450, miPaint);

                    miPaint.setColor(Color.BLACK);
                    canvas.drawBitmap(bitmapQr, 0, 1200, miPaint);
                    miPaint.setTextSize(50f);
                    miPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("Total", 700, 1415, miPaint);
                    miPaint.setTextAlign(Paint.Align.RIGHT);
                    canvas.drawText(String.valueOf(subTotal + (subTotal * 12 / 100)), pageW - 40, 1415, miPaint);

                    tittle.setTextAlign(Paint.Align.CENTER);
                    tittle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                    tittle.setTextSize(50);
                    canvas.drawText("Gracias por su Visita", pageW / 2, 1700, tittle);


                    myPdf.finishPage(myPage);
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Factura_"+fecha+"&"+hora+".pdf");
                    Toast.makeText(Eticket.this, "Factura creada con exito", Toast.LENGTH_SHORT).show();


                    //String encodeFileToBase64Binary = encodeFileToBase64Binary(file);
                   try {
                        myPdf.writeTo(new FileOutputStream(file));
                       //mensaje();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdf.close();
                   // crearXml(datos1.getText().toString(),dato2.getText().toString(),item1.getSelectedItem().toString(),item2.getSelectedItem().toString(),numero_item1.getText().toString(),numero_item2.getText().toString());
                   Mostrar tarea = new Mostrar();
                   tarea.execute();
                }
            }
        });
    }

   /* private String encodeFileToBase64Binary(File file) throws IOException{
        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encodeBase64(bytes);
        String encodedString = new String(encoded);
        return encodedString;

    }*/

    public  String crearXml(String nombre,String numero,String item1,String item2,String valor_item1,String valor_item2) {


        try{
            File fileXml = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Factura_" + fecha+" "+hora + ".csv");
            //XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream osWriter=new FileOutputStream(fileXml.toString());
            //OutputStreamWriter osWriter = new OutputStreamWriter((openFileOutput(fileXml.getName().toString(), Context.MODE_APPEND)));
            String espacio="\n";
            byte nombreBytes[]=nombre.getBytes();
            byte espaciobyte[]=espacio.getBytes();
            byte numeroBytes[]=numero.getBytes();
            byte itemBytes[]=item1.getBytes();
            byte item2Bytes[]=item2.getBytes();
            byte valor_item1Bytes[]=valor_item1.getBytes();
            byte valor_item2Bytes[]=valor_item2.getBytes();
            osWriter.write(nombreBytes);
            osWriter.write(espaciobyte);
            osWriter.write(numeroBytes);
            osWriter.write(espaciobyte);
            osWriter.write(itemBytes);
            osWriter.write(espaciobyte);
            osWriter.write(valor_item1Bytes);
            osWriter.write(espaciobyte);
            osWriter.write(item2Bytes);
            osWriter.write(espaciobyte);
            osWriter.write(valor_item2Bytes);
            //try {
               /* StringWriter out = new StringWriter();
                Serializer serializer = new Persister();
               serializer.write(nombre,out);*/
                String xml = osWriter.toString();


                return xml;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
            //StringWriter Writer=new StringWriter();

       /* xmlSerializer.startDocument("utf-8",false);
        xmlSerializer.startTag("","Clientes");
        xmlSerializer.startTag("","Cliente");
        xmlSerializer.startTag("","Nombre");
        xmlSerializer.text(nombre);
        xmlSerializer.endTag("","Nombre");

        xmlSerializer.startTag("","Numero");
        xmlSerializer.text(numero);
        xmlSerializer.endTag("","Numero");

        xmlSerializer.startTag("","Item1");
        xmlSerializer.text(item1);
        xmlSerializer.endTag("","Item1");

        xmlSerializer.startTag("","Item2");
        xmlSerializer.text(item2);
        xmlSerializer.endTag("","Item2");

        xmlSerializer.startTag("","Cantidad_Item1");
        xmlSerializer.text(valor_item1);
        xmlSerializer.endTag("","Cantidad_Item1");

        xmlSerializer.startTag("","Cantidad_Item2");
        xmlSerializer.text(valor_item2);
        xmlSerializer.endTag("","Cantidad_Item2");

        xmlSerializer.endTag("","Cliente");

        xmlSerializer.endTag("","Clientes");

        xmlSerializer.endDocument();
*/

            //String result=osWriter.toString();

                    // fileXml.mkdir();
                  // ticket.setText("From writeToXmlFile\n" + result);




        public class Mostrar extends AsyncTask<String, String, Boolean> {
        public Boolean doInBackground(String... params) {
            boolean resul = true;
            HttpClient httpClient = new DefaultHttpClient();

            File ruta_sd = Environment.getExternalStorageDirectory();
            //File f = new File(ruta_sd.getAbsolutePath(), "/Factura_"+fecha+"&"+hora+".pdf");
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Factura_"+fecha+"&"+hora+".pdf");

            HttpPost post = new HttpPost("https://cr289fri24.execute-api.eu-west-1.amazonaws.com/test/uploadToS3");
            post.setHeader("content-type", "application/json");

            try {
                JSONObject dato = new JSONObject();
                dato.put("username","0320002");
                dato.put("menu_desc", "/Factura_"+fecha+"&"+hora);
                dato.put("xml", false); // false: pdf, true: xml

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                FileInputStream fis = new FileInputStream(f);
                byte[] data = new byte[1024];
                while ((nRead = fis.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                dato.put("menu_photo",  Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT));

                StringEntity entity = new StringEntity(dato.toString());
                post.setEntity(entity);

                HttpResponse resp = httpClient.execute(post);
                respStr = EntityUtils.toString(resp.getEntity());
                StatusLine statusLine = resp.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    resul = true;

                } else {
                    resul = false;

                }
            } catch (Exception ex) {
                Log.e("ServicioRest", "Error!", ex);
                resul = false;
            }

            return resul;
        }


        public void onPostExecute(Boolean result) {
            if (result == true) {
                Toast.makeText(getApplicationContext(), "Subido Correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error Servidor", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void generarQr() {
        String text = "Diusframi";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmapQr = barcodeEncoder.createBitmap(bitMatrix);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


   /* public void mensaje(){
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage("655393154",null,"hola",null,null);
        Toast.makeText(getApplicationContext(), "mensaje", Toast.LENGTH_SHORT).show();
    }*/
   /* @Override
    public void onRequestPermissionsResult(int requesCode,String []permision,int[]grantResults){
        switch (requesCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(Eticket.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getApplicationContext(), "Permiso Aceptado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }*/

    /*public void crearPdf(){
        Document documento= new Document();
            try{
                File file=crearFichero(nombre_docmento);
                FileOutputStream ficheroPdf=new FileOutputStream(file.getAbsolutePath());

                PdfWriter writer =PdfWriter.getInstance(documento,ficheroPdf);
                documento.open();
                documento.add(new Paragraph(datos.getText().toString()+"\n\n"));



            }catch (DocumentException e){

            }catch (IOException e){

            }finally {
                documento.close();
            }
    }

    public File crearFichero(String nombre_docmento){
        File ruta=getRuta();
        File fichero=null;
        if(ruta!= null){
            fichero=new File(ruta,nombre_docmento);

        }
        return fichero;
    }
    public File getRuta(){
        File ruta=null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),nombre_directorio);
        if(ruta!= null){
            if(!ruta.mkdir()){
               if(!ruta.exists()){
                   return null;
               }
            }

        }

        }
        return ruta;
    }

   /* public void writeXml(View v){
        ArrayList<String> datos=new ArrayList<>();
        datos.add("Comercio:   5999");
        datos.add("Fecha: 15/3/2020");

        XmlSerializer serializer= Xml.newSerializer();
        StringWriter writer=new StringWriter();
        try{
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8",true);
            serializer.startTag("","datos");
            serializer.endTag("","datos");
            serializer.endDocument();
            String result=writer.toString();

            IOHelper.writeToFile(this,"datos.xml",result);
            File archivoXML = new File("cliente.xml");
            ticket.setText("From writeToXmlFile\n" + result);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public void readXMLDOM(View view) {

        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            db = dbf.newDocumentBuilder();
            FileInputStream fis = openFileInput("datos.xml");
            doc = db.parse(fis);
            //doc = db.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8)));
            NodeList cities = doc.getElementsByTagName("datos");
            String result = "";
            for (int i = 0; i < cities.getLength(); i++) {

                Node city = cities.item(i);
                /*
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
                    Node node = nodeList.item(0);
                    node.getNodeValue();
                  }
                  */
                /*NodeList cityInfo = city.getChildNodes();
                for (int j = 0; j < cityInfo.getLength(); j++) {
                    Node info = cityInfo.item(j);
                    result += "<" + info.getNodeName() + ">" + info.getTextContent() + "</" + info.getNodeName() + ">\n";
                }
            }

           ticket.setText(result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readXmlPull(View v){
        XmlPullParserFactory factory;
        FileInputStream fis =null;
        try{
            StringBuilder sb=new StringBuilder();
            factory=XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp=factory.newPullParser();
            fis=openFileInput("datos.xml");
            xpp.setInput(fis,null);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT)
                    sb.append("[START]");
                else if (eventType == XmlPullParser.START_TAG)
                    sb.append("\n<" + xpp.getName() + ">");
                else if (eventType == XmlPullParser.END_TAG)
                    sb.append("</" + xpp.getName() + ">");
                else if (eventType == XmlPullParser.TEXT)
                    sb.append(xpp.getText());

                eventType = xpp.next();
            }
            ticket.setText(sb.toString());
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }*/

}
