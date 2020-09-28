package com.diusframi.tpv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
public class MostrarPdf extends AsyncTask<String, String, Boolean> {
    public Boolean doInBackground(String... params) {
        boolean resul = true;
        Application application = null;

        String respStr;
        String fecha = "";
        String hora = "";
        HttpClient httpClient = new DefaultHttpClient();
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Factura_" + fecha + "&" + hora + ".pdf");
        HttpPost post = new HttpPost("https://cr289fri24.execute-api.eu-west-1.amazonaws.com/test/uploadToS3");
        post.setHeader("content-type", "application/json");
        try {
            JSONObject dato = new JSONObject();
            dato.put("username", "0320002");
            dato.put("menu_desc", "/Factura_" + fecha + "&" + hora);
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


    }

}