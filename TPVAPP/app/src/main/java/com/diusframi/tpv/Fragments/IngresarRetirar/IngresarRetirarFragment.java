package com.diusframi.tpv.Fragments.IngresarRetirar;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.diusframi.tpv.BaseDatos;
import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.InputDinero;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.Objects;

public class IngresarRetirarFragment extends Fragment {
    ActionBarDrawerToggle Actionbar;
    private Fragment fragment = null;
    private Button aceptar;
    private EditText importeedit;
    private EditText motivoedit;
    RadioButton ingresar;
    RadioButton retirar;
    DrawerLayout drawerLayout;
    Double dineroingresado;
    Double dineroretirado;
    String motivo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ingresar_retirar_fragment, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Mis arqueos");

        //Declaraciones
        ingresar = view.findViewById(R.id.ingresar);
        retirar = view.findViewById(R.id.retirar);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        motivoedit = view.findViewById(R.id.motivoedit);
        aceptar = view.findViewById(R.id.aceptarboton);
        importeedit = view.findViewById(R.id.importeedit);
        Button cancelar = view.findViewById(R.id.cancelar);
        final RadioGroup radiog = view.findViewById(R.id.radiogroup);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());


        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);


        aceptar.setEnabled(false);


        if (!aceptar.isEnabled()) {
            aceptar.setBackgroundResource(R.drawable.botongrisclaro);
        }

        importeedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0 && ingresar.isChecked() || s.toString().length() > 0 && retirar.isChecked()) {
                                     aceptar.setEnabled(true);
                    aceptar.setBackgroundResource(R.drawable.botonnaranja);
                } else {
                                aceptar.setEnabled(false);
                    aceptar.setBackgroundResource(R.drawable.botongrisclaro);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        InputDinero dinero = new InputDinero();
        importeedit.setFilters(new InputDinero[]{dinero});


        aceptar.setOnClickListener(view1 -> {
            if (ingresar.getId() == radiog.getCheckedRadioButtonId()) {

                openDialogIngresar();

            } else if (retirar.getId() == radiog.getCheckedRadioButtonId()) {
                openDialogRetirar();
            }
        });


        cancelar.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), Venta.class);
            startActivity(i);
        });


        return view;
    }


    @SuppressLint("SetTextI18n")
    public void openDialogIngresar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ingresardialog, null);
        final BaseDatos sqLiteHelper = new BaseDatos(getContext(), null);
        DecimalFormat decim = new DecimalFormat("#.00");
        dineroingresado = Double.valueOf(importeedit.getText().toString());
        motivo = motivoedit.getText().toString();
        Button aceptarboton = view.findViewById(R.id.aceptar);
        TextView dinerotext = view.findViewById(R.id.dinerotexto);


        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        aceptarboton.setOnClickListener(v -> {

            sqLiteHelper.actualizarcajaingresar(
                    dineroingresado
            );
            fragment = new IngresarRetirarFragment();
            drawerLayout.setVisibility(View.GONE);

            if (fragment != null) {


                Fragment fragment = new IngresarRetirarFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_fragment, fragment);
                ft.commit();
                drawerLayout.setVisibility(View.GONE);

            }
            dialog.dismiss();
        });


        dinerotext.setText("+ " + decim.format(dineroingresado) + "€");

        // Display the custom alert dialog on interface
        dialog.show();
    }


    @SuppressLint("SetTextI18n")
    public void openDialogRetirar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        final BaseDatos sqLiteHelper = new BaseDatos(getContext(), null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_retirardialog, null);
        DecimalFormat decim = new DecimalFormat("#.00");


        dineroretirado = Double.valueOf(importeedit.getText().toString());


        motivo = motivoedit.getText().toString();
        Button aceptarboton = view.findViewById(R.id.aceptar);
        TextView dinerotext = view.findViewById(R.id.dinerotexto);
        TextView motivotext = view.findViewById(R.id.motivotexto);
        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(view);

        // Get the custom alert dialog view widgets reference


        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();

        display.getSize(size);

        aceptarboton.setOnClickListener(v -> {
            sqLiteHelper.actualizarcajaretirar(
                    dineroretirado
            );

            fragment = new IngresarRetirarFragment();
            drawerLayout.setVisibility(View.GONE);

            if (fragment != null) {


                Fragment fragment = new IngresarRetirarFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_fragment, fragment);
                ft.commit();
                drawerLayout.setVisibility(View.GONE);

            }
                       dialog.dismiss();
        });


        dinerotext.setText("- " + decim.format(dineroretirado) + "€");
        motivotext.setText(motivo);
        // Display the custom alert dialog on interface
        dialog.show();
    }


}
