package com.example.germa.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Reporte1Fragment extends Fragment {
    Bundle bundle;
    TextView datos;
    View vista;
    String valor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_reporte1, container, false);

        datos = (TextView) vista.findViewById(R.id.reporte1);
        bundle = getArguments();
        valor = bundle.getString("texto");
        datos.setText(valor);

        return vista;
    }
}
