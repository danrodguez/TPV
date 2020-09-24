package com.diusframi.tpv.Fragments.TotalizarCierreCaja;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.diusframi.tpv.Fragments.Venta.Venta;
import com.diusframi.tpv.InputDinero;
import com.diusframi.tpv.R;
import com.google.android.material.navigation.NavigationView;

public class TotalizarCierreCajaFragment extends Fragment {

    //Declaraciones
    private Button continuar;
    EditText importe;
    ActionBarDrawerToggle Actionbar;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_totalizar_cierre_caja_fragment, container, false);


        //Declaraciones

        Button cancelar = view.findViewById(R.id.cancelarbotontotalizar);
        continuar = view.findViewById(R.id.continuarbotontotalizar);
        importe = view.findViewById(R.id.procimporteedit);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawer_layout);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

        Actionbar = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = view.findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getContext());
        continuar.setEnabled(false);

        //Hacer menu transparente
        drawerLayout.setDrawerElevation(0);

        InputDinero dinero = new InputDinero();
        importe.setFilters(new InputDinero[]{dinero});


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Venta.class);
                startActivity(i);
            }
        });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double recuentoefectivo;
                String importetexto = importe.getText().toString();
                recuentoefectivo = Double.parseDouble(importetexto);


                Fragment fragment = new TotalizarCierreCajaFianzaFragment();
                Bundle bundle = new Bundle();
                bundle.putDouble("recuentoefectivo", recuentoefectivo);
                fragment.setArguments(bundle);
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_fragment, fragment);
                ft.commit();
            }
        });

        importe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    continuar.setEnabled(true);

                } else {
                    continuar.setEnabled(false);

                }

                if (!continuar.isEnabled()) {
                    continuar.setBackgroundResource(R.drawable.botongrisclaro);
                } else {
                    continuar.setBackgroundResource(R.drawable.botonnaranja);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }


}
