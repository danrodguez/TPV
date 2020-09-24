package com.diusframi.tpv.Fragments.MiCuenta;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MicrophoneInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.TotalizarCierreCaja.TotalizarCierreCajaFianzaFragment;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.MainActivity;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MiCuentaFragment extends Fragment {

    //Declaraciones
    private final int REQUEST_CODE_GALLERY = 999;
    private ImageView Imagen;
    private Cursor listaRegistros2;
    private Cursor listaRegistros3;
    private CheckBox check;
    private EditText cif;
    private EditText nombrecomercial;
    private EditText domiciliocomercial;
    private EditText localidadcomercial;
    private EditText codigopostalcomercial;
    private EditText provinciacomercial;
    private EditText telefonocomercial;
    private EditText nombrefiscal;
    private EditText domiciliofiscal;
    private EditText localidadfiscal;
    private EditText codigopostalfiscal;
    private EditText provinciafiscal;
    private EditText telefonofiscal;
    EditText proximonumeroticket;
    EditText proximonumerodevolucion;
    EditText serieticket;
    EditText seriedevolucion;
    ImageView borrarimagenview;
    ActionBarDrawerToggle Actionbar;
    Button imagenB;
    String serietickettexto = "";
    String seriedevoluciontexto = "";
    String idticketexto = "";
    String iddevoluciontexto = "";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mi_cuenta_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi cuenta");
        final BaseDatos sqLiteHelper = new BaseDatos(this.getActivity(), "BDUsuarios", null, 1);
        com.diusframi.tpv.BaseDatos baseDatos = new BaseDatos(this.getActivity(), "BDUsuarios", null, 1);
        final SQLiteDatabase bd = baseDatos.getReadableDatabase();

        //Declaraciones
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);
        proximonumeroticket = view.findViewById(R.id.proximonumeroventaedit);
        proximonumerodevolucion = view.findViewById(R.id.proximonumerodevolucionedit);
        borrarimagenview = view.findViewById(R.id.borrarfoto);
        Imagen = view.findViewById(R.id.imagen);
        check = view.findViewById(R.id.checkbox);
        imagenB = view.findViewById(R.id.IMAGENboton);
        cif = view.findViewById(R.id.cifedit);
        serieticket = view.findViewById(R.id.serieticketsventaedit);
        seriedevolucion = view.findViewById(R.id.seriedevolucionesedit);
        nombrecomercial = view.findViewById(R.id.nombrecomercialedit);
        domiciliocomercial = view.findViewById(R.id.domicilioedit);
        localidadcomercial = view.findViewById(R.id.localidadedit);
        codigopostalcomercial = view.findViewById(R.id.codigopostaledit);
        provinciacomercial = view.findViewById(R.id.provinciaedit);
        telefonocomercial = view.findViewById(R.id.telefonocontactoedit);
        nombrefiscal = view.findViewById(R.id.nombrefiscaledit);
        domiciliofiscal = view.findViewById(R.id.domiciliofiscaledit);
        localidadfiscal = view.findViewById(R.id.localidadfiscaledit);
        codigopostalfiscal = view.findViewById(R.id.codigopostalfiscaledit);
        provinciafiscal = view.findViewById(R.id.provinciafiscaledit);
        telefonofiscal = view.findViewById(R.id.telefonocontactofiscaledit);
        final Button guardarboton = view.findViewById(R.id.guardarboton);
        Button cancelarboton = view.findViewById(R.id.cancelarboton);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());


        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);
int idticket = 0;
int iddevolucion = 0;
        int idticketnumero = 0;
        int iddevolucionnumero = 0;
        guardarboton.setEnabled(false);

        if (!guardarboton.isEnabled()) {
            guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
        }

        BaseDatos resg2 = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();
        final Cursor cursor3 = bd2.rawQuery("SELECT id FROM Ordenes ORDER BY id DESC", null);

        if(cursor3.moveToFirst()){
             idticket = cursor3.getInt(0);
              idticketnumero = idticket + 1;
        }

        final Cursor cursor4 = bd2.rawQuery("SELECT id FROM Devoluciones ORDER BY id DESC", null);

        if(cursor4.moveToFirst()){
             iddevolucion= cursor4.getInt(0);
             iddevolucionnumero = iddevolucion + 1;
        }



        Cursor cursor5  = bd.rawQuery("SELECT  NumeroTicket FROM TextoTicketDevolucion", null);

        int NumeroTicket = 0;

        while (cursor5.moveToNext()){
            NumeroTicket = cursor5.getInt(0);

        }
        if(NumeroTicket != 0){
            idticketnumero = NumeroTicket;
        }

        Cursor cursor6  = bd.rawQuery("SELECT  NumeroDevolucion FROM TextoTicketDevolucion", null);

        int NumeroDevolucion = 0;

        while (cursor6.moveToNext()){
            NumeroDevolucion = cursor6.getInt(0);

        }
        if(NumeroDevolucion != 0){
            iddevolucionnumero = NumeroDevolucion;
        }




        if(idticketnumero == 0){
    idticketnumero = 1;
}

if (iddevolucionnumero == 0){
    iddevolucionnumero = 1;
}
        proximonumeroticket.setText(String.valueOf(idticketnumero));
        proximonumerodevolucion.setText(String.valueOf(iddevolucionnumero));
        serietickettexto = serieticket.getText().toString();
        seriedevoluciontexto = seriedevolucion.getText().toString();

        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        nombrecomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    guardarboton.setEnabled(true);
                } else {
                         guardarboton.setEnabled(false);
                }
                if (domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que domiciliocomercial tiene texto y vuelve el boton de enviar de gris a naranja
        domiciliocomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que domiciliocomercial tiene texto y vuelve el boton de enviar de gris a naranja
        localidadcomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que domiciliocomercial tiene texto y vuelve el boton de enviar de gris a naranja
        codigopostalcomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if ( domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        provinciacomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        telefonocomercial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if ( domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Funcion que comprueba que nombrefiscal tiene texto y vuelve el boton de enviar de gris a naranja
        nombrefiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if ( domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que domiciliofiscal tiene texto y vuelve el boton de enviar de gris a naranja
        domiciliofiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                ) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if ( domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que localidadfiscal tiene texto y vuelve el boton de enviar de gris a naranja
        localidadfiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que codigopostalfiscal tiene texto y vuelve el boton de enviar de gris a naranja
        codigopostalfiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {

                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (nombrecomercial.getText().toString().equals(nombrefiscal.getText().toString())
                        & domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que provinciafiscal tiene texto y vuelve el boton de enviar de gris a naranja
        provinciafiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & telefonofiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }
                if (nombrecomercial.getText().toString().equals(nombrefiscal.getText().toString())
                        & domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Funcion que comprueba que telefonofiscal tiene texto y vuelve el boton de enviar de gris a naranja
        telefonofiscal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & codigopostalcomercial.getText().toString().length() > 0
                        & localidadcomercial.getText().toString().length() > 0
                        & telefonocomercial.getText().toString().length() > 0
                        & provinciacomercial.getText().toString().length() > 0
                        & nombrecomercial.getText().toString().length() > 0
                        & domiciliocomercial.getText().toString().length() > 0
                        & codigopostalfiscal.getText().toString().length() > 0
                        & localidadfiscal.getText().toString().length() > 0
                        & provinciafiscal.getText().toString().length() > 0
                        & nombrefiscal.getText().toString().length() > 0
                        & domiciliofiscal.getText().toString().length() > 0) {


                    guardarboton.setEnabled(true);
                } else {

                    guardarboton.setEnabled(false);
                }

                if (nombrecomercial.getText().toString().equals(nombrefiscal.getText().toString())
                        & domiciliocomercial.getText().toString().equals(domiciliofiscal.getText().toString())
                        & localidadcomercial.getText().toString().equals(localidadfiscal.getText().toString())
                        & codigopostalcomercial.getText().toString().equals(codigopostalfiscal.getText().toString())
                        & provinciacomercial.getText().toString().equals(provinciafiscal.getText().toString())

                ) {
                    check.setChecked(true);
                    check.setEnabled(false);
                } else {
                    check.setChecked(false);
                    check.setEnabled(false);
                }
                if (!guardarboton.isEnabled()) {
                    guardarboton.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardarboton.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        imagenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_GALLERY
                    );

                }

        });

        cancelarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Venta.class);
                startActivity(i);
            }
        });

        borrarimagenview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Imagen = null;
               imagenB.setBackgroundResource(R.drawable.curz);
            }
        });


        final int finalIdticket = idticket;
        final int finalIddevolucion = iddevolucion;
        final int finalIdticket1 = idticket;


        guardarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = "";
                listaRegistros2 = bd.rawQuery("SELECT contrasena FROM Usuarios WHERE activo = 1", null);
                listaRegistros3 = bd.rawQuery("SELECT email FROM Usuarios WHERE activo = 1", null);

                if (listaRegistros3.moveToFirst()) {
                    email = listaRegistros3.getString(0);
                }

                if (listaRegistros2.moveToFirst()) {

                    if(Imagen !=null) {
                        sqLiteHelper.UpdateDataUsuarios(
                                email,
                                listaRegistros2.getString(0),
                                cif.getText().toString(),
                                nombrecomercial.getText().toString(),
                                nombrefiscal.getText().toString(),
                                domiciliocomercial.getText().toString(),
                                localidadcomercial.getText().toString(),
                                codigopostalcomercial.getText().toString(),
                                provinciacomercial.getText().toString(),
                                telefonocomercial.getText().toString(),
                                domiciliofiscal.getText().toString(),
                                localidadfiscal.getText().toString(),
                                codigopostalfiscal.getText().toString(),
                                provinciafiscal.getText().toString(),
                                telefonofiscal.getText().toString(),
                                imageViewToByte(Imagen),
                                1


                        );
                        idticketexto = proximonumeroticket.getText().toString();
                        iddevoluciontexto = proximonumerodevolucion.getText().toString();

                        if(!serietickettexto.equals("") ){
                            sqLiteHelper.inserttextoticket(serietickettexto);

                        }

if(!idticketexto.equals("")) {
    if (Integer.parseInt(idticketexto) >= finalIdticket1) {
        sqLiteHelper.insertnumeroticket(Integer.parseInt(idticketexto));

    }
}

                        if(!seriedevoluciontexto.equals("")){
                            sqLiteHelper.inserttextodevolucion(seriedevoluciontexto);

                        }
if(!iddevoluciontexto.equals("")) {
    if (Integer.parseInt(iddevoluciontexto) >= finalIddevolucion) {
        sqLiteHelper.insertnumerodevolucion(Integer.parseInt(iddevoluciontexto));

    }
}

                    }else{
                        sqLiteHelper.UpdateDataUsuariossinimagen(
                                email,
                                listaRegistros2.getString(0),
                                cif.getText().toString(),
                                nombrecomercial.getText().toString(),
                                nombrefiscal.getText().toString(),
                                domiciliocomercial.getText().toString(),
                                localidadcomercial.getText().toString(),
                                codigopostalcomercial.getText().toString(),
                                provinciacomercial.getText().toString(),
                                telefonocomercial.getText().toString(),
                                domiciliofiscal.getText().toString(),
                                localidadfiscal.getText().toString(),
                                codigopostalfiscal.getText().toString(),
                                provinciafiscal.getText().toString(),
                                telefonofiscal.getText().toString(),
                                1

                        );
                    }
                    Intent i = new Intent(getContext(), Venta.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Email no valido", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        return view;

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                MiCuentaFragment.this.startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                assert uri != null;
                InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                Imagen.setImageBitmap(bitmap);
                imagenB.setBackground(drawable);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



}
