package com.diusframi.tpv.Fragments.EditarArticulo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.MisArticulos.MisarticulosFragment;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.InputDinero;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.StringTokenizer;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
import static android.text.InputType.TYPE_NUMBER_FLAG_SIGNED;

public class EditarArticuloFragment extends Fragment {

    //Declaraciones
    private Fragment fragment = null;
    CheckBox preciovariable;
    private EditText articulo;
    private Button categoria;
    private Button BotonIVA;
    private EditText importe;
    private int cursorint;
    String categorianombre = "";
    String importetexto = "";
    String articulonombre = "";
    ActionBarDrawerToggle Actionbar;
    DrawerLayout drawerLayout;
    int ivadoble = 0;
    double importeint = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_editar_articulo_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Editar Articulo");
        String nombreproducto = getArguments().getString("nombreproducto");
        String categoriaproducto = getArguments().getString("categoriaproducto");
        String ivaproducto = getArguments().getString("ivaproducto");
        String precioproducto = getArguments().getString("precioproducto");
        if (nombreproducto == null) {
            nombreproducto = "";
        }

        //Declaraciones


        drawerLayout = view.findViewById(R.id.drawer_layout);
        articulo = view.findViewById(R.id.articulonombre);
        final Button guardar = view.findViewById(R.id.guardarboton);
        Button cancelar = view.findViewById(R.id.cancelarboton);
        categoria = view.findViewById(R.id.categoriasboton);
        Button crearnueva = view.findViewById(R.id.crearnuevaboton);
        String mystring = "Crear nueva";
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        crearnueva.setText(content);
        importe = view.findViewById(R.id.importeedit);



        BotonIVA = view.findViewById(R.id.ivaboton);
        preciovariable = view.findViewById(R.id.preciovariablecheck);
        final BaseDatos sqLiteHelper = new BaseDatos(view.getContext(), "BDUsuarios", null, 1);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());


        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);

        BaseDatos resg2 = new BaseDatos(getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd2 = resg2.getReadableDatabase();
        Cursor cursor = bd2.rawQuery("SELECT Categorias,Nombre,Precio,IVA FROM Articulos WHERE Favorito LIKE '1'", null);

        guardar.setEnabled(false);




        if(!categoriaproducto.equals("")){
            categoria.setText(categoriaproducto);
        }

        if(!precioproducto.equals("")){
            importe.setText(precioproducto);
        }

        if(!ivaproducto.equals("")){
            BotonIVA.setText(ivaproducto);
        }

        if (!nombreproducto.equals("")) {
            articulo.setText(nombreproducto);
        }

        if (preciovariable.isChecked()) {
            importe.setEnabled(false);
            importe.setText("");
        }


        if(!categoriaproducto.equals("") && !precioproducto.equals("") && !ivaproducto.equals("") && !nombreproducto.equals("")){
            guardar.setEnabled(true);
            guardar.setBackgroundResource(R.drawable.botonnaranja);
        }


        categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogcategoria();
            }

        });


        BotonIVA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogarticuloiva();
            }
        });


        crearnueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogcrearnueva();

            }
        });


        importe.setFilters(new InputFilter[]{new InputDinero()});

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new MisarticulosFragment();
                drawerLayout.setVisibility(View.GONE);

                if (fragment != null) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_fragment, fragment);
                    ft.commit();
                }

            }
        });



        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        categoria.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                       ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });
        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        BotonIVA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        importe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {
                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                        & articulo.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });        //Funcion que comprueba que nombrecomercial tiene texto y vuelve el boton de enviar de gris a naranja
        articulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0
                        & BotonIVA.getText().toString().length() > 0
                        & importe.getText().toString().length() > 0
                        & categoria.getText().toString().length() > 0
                ) {

                    guardar.setEnabled(true);
                } else {
                    guardar.setEnabled(false);
                }

                if (!guardar.isEnabled()) {
                    guardar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {

                    guardar.setBackgroundResource(R.drawable.botonnaranja);
                }
            }
        });



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categorianombre = categoria.getText().toString();

                importetexto = importe.getText().toString().replace(",",".");
                articulonombre = articulo.getText().toString();
                 String finalIvatexto = BotonIVA.getText().toString();;
                    StringTokenizer token = new StringTokenizer(finalIvatexto, "%");
                    String ivasinporcentaje = token.nextToken();
                    ivadoble = Integer.parseInt(ivasinporcentaje);
                    DecimalFormat decim = new DecimalFormat("0.00");
                    String importetext = importe.getText().toString().replace(",",".");

                 importeint = Double.parseDouble(importetext);


                BaseDatos resg = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
                SQLiteDatabase bd = resg.getReadableDatabase();
                final Cursor cursortipos = bd.rawQuery("SELECT Nombre FROM Articulos WHERE Nombre = '" + articulonombre + "'", null);


                if (cursortipos.moveToNext()) {
                   sqLiteHelper.Updatearticulo(
                            categorianombre,
                            articulonombre,
                            importeint,
                            ivadoble
                    );

                    Intent i = new Intent(view.getContext(), Venta.class);
                    startActivity(i);
                    cursortipos.close();
                } else {
int id = 0;

                    final Cursor cursortipos2 = bd.rawQuery("SELECT id FROM Categoriastabla WHERE Categoria = '" + categorianombre + "'", null);
                    if (cursortipos2.moveToNext()) {
                        id = cursortipos2.getInt(0);
                    }

                    sqLiteHelper.Creararticulo(
                            id,
                            articulonombre,
                            0,
                            importeint,
                            ivadoble
                    );
                    Intent i = new Intent(view.getContext(), Venta.class);
                    startActivity(i);
                    cursortipos2.close();
                }







            }
        });

        return view;
    }


    public void openDialogcategoria() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_categoriaeditararticulodialog, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference

        Button btn_negative = (Button) view.findViewById(R.id.cancelarboton);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        BaseDatos resg = new BaseDatos(view.getContext(), "BaseDatos", null, 1);
        SQLiteDatabase bd = resg.getReadableDatabase();
        Cursor cursortipos = bd.rawQuery("SELECT DISTINCT Categoria FROM Categoriastabla", null);
        LinearLayout linearLayout = view.findViewById(R.id.botoneslinear);
        while (cursortipos.moveToNext()) {
            final Button btCategory = new Button(view.getContext());
            btCategory.setText(cursortipos.getString(0));
            btCategory.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            btCategory.setAllCaps(false);
            btCategory.setBackgroundResource(R.drawable.a);
            btCategory.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            btCategory.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_text));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(size.x / 3, size.x / 8);
            layoutParams2.setMargins(0, size.x / 50, size.x / 25, size.x / 25);
            btCategory.setLayoutParams(layoutParams2);
            btCategory.setPadding(size.x / 25, size.x / 50, size.x / 25, size.x / 50);
            linearLayout.addView(btCategory);

            btCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    categoria.setText(btCategory.getText().toString());
                }
            });
        }

        // Set negative/no button click listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss/cancel the alert dialog
                //dialog.cancel();
                dialog.dismiss();

            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

    public void openDialogcrearnueva() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_crearnuevaeditararticulodialog, null);

        final EditText categorianueva = view.findViewById(R.id.crearcategoria);
        Button salir = view.findViewById(R.id.cancelarboton);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference

        Button crearboton = view.findViewById(R.id.crearboton);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);


        // Set negative/no button click listener
        crearboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoriatexto = categorianueva.getText().toString();
                final BaseDatos sqLiteHelper = new BaseDatos(getContext(), "BDUsuarios", null, 1);


                sqLiteHelper.CrearCategoria(
                        categoriatexto
                );
                dialog.dismiss();

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }

    public void openDialogarticuloiva() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ivaeditararticulodialog, null);

        final Button iva21boton = view.findViewById(R.id.iva21);
        final Button iva10boton = view.findViewById(R.id.iva10);
        Button cancelar = view.findViewById(R.id.cancelarboton);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);


        // Set negative/no button click listener
        iva21boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = iva21boton.getText().toString();
                BotonIVA.setText(ivatexto);

                dialog.dismiss();

            }
        });
        iva10boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = iva10boton.getText().toString();
                BotonIVA.setText(ivatexto);

                dialog.dismiss();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ivatexto = "";
                BotonIVA.setText(ivatexto);
                dialog.dismiss();

            }
        });

        // Display the custom alert dialog on interface
        dialog.show();
    }


}
