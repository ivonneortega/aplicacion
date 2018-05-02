package com.example.germa.proyecto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DocentesFragment extends Fragment {

    ListView lista_docentes;
    View vista;
    Cursor cursor;
    private ArrayAdapter adapter;
    private String getAllContactsURL = "http://130.100.25.69/materia_docente.php";
    //  DBHelper base_datos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_docentes, container, false);

        lista_docentes = (ListView) vista.findViewById(R.id.lista_docentes);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
        lista_docentes.setAdapter(adapter);

        webServiceRest(getAllContactsURL);

        //   base_datos = new DBHelper(getContext());

        // llenar_lista();
        return vista;
    }

    private void webServiceRest(String requestURL) {
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while ((line = bufferedReader.readLine()) != null) {
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parseInformation(String jsonResult) {
        JSONArray jsonArray = null;
        String id_docente;
        String nombre;
        String apellido_pat;
        String apellido_mat;
        String asignatura;

        try {
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_docente = jsonObject.getString("id_docente");
                nombre = jsonObject.getString("nombre");
                apellido_pat = jsonObject.getString("apellido_pat");
                apellido_mat = jsonObject.getString("apellido_mat");
                asignatura = jsonObject.getString("asignatura");
                adapter.add(id_docente + ": " + nombre + " " + apellido_pat + " " + apellido_mat + " -- " + asignatura);
            } catch (JSONException e) {
                e.printStackTrace();
            }

/*
    public void llenar_lista(){

        ArrayList<String>lista = new ArrayList<>();

        cursor = base_datos.consulta_docentes();
        while (cursor.moveToNext()){

            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String apellido_pat = cursor.getString(cursor.getColumnIndex("apellido_pat"));
            String apellido_mat = cursor.getString(cursor.getColumnIndex("apellido_mat"));

            lista.add(nombre + " " + apellido_pat + " " + apellido_mat);

            ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lista);

            lista_docentes.setAdapter(adaptador);
        }
    }
    */

        }
    }
}