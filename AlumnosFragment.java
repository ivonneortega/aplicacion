package com.example.germa.proyecto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


import java.util.ArrayList;

public class AlumnosFragment extends Fragment {

    ListView lista_alumnos;
    View vista;
    Cursor cursor;
    private ArrayAdapter adapter;
    private String getAllContactsURL = "http://130.100.25.69/conexion.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       vista=inflater.inflate(R.layout.fragment_alumnos, container, false);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lista_alumnos = (ListView)vista.findViewById(R.id.lista_alumnos);
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);
        lista_alumnos.setAdapter(adapter);

        webServiceRest(getAllContactsURL);


        lista_alumnos=(ListView)vista.findViewById(R.id.lista_alumnos);


      //  llenar_lista();
        return vista;


    }

    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_alumno;
        String nombre;
        String apellido_pat;
        String apellido_mat;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_alumno = jsonObject.getString("id_alumno");
                nombre = jsonObject.getString("nombre");
                apellido_pat = jsonObject.getString("apellido_pat");
                apellido_mat = jsonObject.getString("apellido_mat");
                adapter.add(id_alumno + ": " + nombre + " " + apellido_pat + " " + apellido_mat);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    /*
    public void llenar_lista(){

        ArrayList<String>lista=new ArrayList<>();

        cursor = base_datos.consulta_alumnos();
        while (cursor.moveToNext()){

            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String apellido_pat = cursor.getString(cursor.getColumnIndex("apellido_pat"));
            String apellido_mat = cursor.getString(cursor.getColumnIndex("apellido_mat"));

            lista.add(nombre + " " + apellido_pat + " " + apellido_mat);

            ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lista);

            lista_alumnos.setAdapter(adaptador);


        }


    }

    */
}
