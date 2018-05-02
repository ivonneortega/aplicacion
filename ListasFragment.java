package com.example.germa.proyecto;

import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import java.util.ArrayList;

public class ListasFragment extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<String> selectItems = new ArrayList<>();
    ListView lista_asistencia;
    View vista;
    Cursor cursor;
    TextView fecha, hora, materia;
    Button guardar;
   static Bundle bundle;

    private ArrayAdapter adapter;
    private String getAllContactsURL = "http://130.100.25.69/consulta_lista.php";
    private String getAllAlumnosURL = "http://130.100.25.69/consulta_lista_alumnos.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_listas, container, false);

        lista_asistencia = (ListView) vista.findViewById(R.id.lista_asistencia_asis);
        guardar = (Button) vista.findViewById(R.id.btn_guardar_lista);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               seleccionarItems();
/*

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ReportesFragment reporte = new ReportesFragment();
                reporte.setArguments(bundle);
                fragmentTransaction.replace(R.id.Contenedor, reporte);
                fragmentTransaction.addToBackStack(null).commit();
                */
            }
        });
        //lista_asistencia.setOnItemClickListener(this);

        fecha = (TextView) vista.findViewById(R.id.fecha_asis);
        hora = (TextView) vista.findViewById(R.id.hora_asis);
        materia = (TextView) vista.findViewById(R.id.materia_asis);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        adapter = new ArrayAdapter(getContext(), R.layout.items_veri,R.id.checkboxItem);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item,R.id.checkboxItem,lista);
        lista_asistencia.setAdapter(adapter);
        lista_asistencia.setOnItemClickListener(this);
        lista_asistencia.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        webServiceRest(getAllContactsURL);
        webServiceLista(getAllAlumnosURL);



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
            llenarTextView(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void webServiceLista(String requestURL){
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
            llenar_lista(webServiceResult);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void llenarTextView(String jsonResult){
        JSONArray jsonArray = null;
        String fechas;
        String horas;
        String materias;

        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fechas = jsonObject.getString("fecha");
                horas = jsonObject.getString("hora");
                materias = jsonObject.getString("asignatura");

                fecha.setText(fechas);
                hora.setText(horas);
                materia.setText(materias);


            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public void llenar_lista(String jsonResult){
        //ArrayList<String> lista = new ArrayList<>();

        JSONArray jsonArray = null;
        String nombre, apellido_pat, apellido_mat;


        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            e.printStackTrace();
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nombre = jsonObject.getString("nombre");
                apellido_pat = jsonObject.getString("apellido_pat");
                apellido_mat = jsonObject.getString("apellido_mat");

                adapter.add(nombre + " " + apellido_pat + " " + apellido_mat);
                //ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item,R.id.checkboxItem,lista);

                //lista_asistencia.setAdapter(adapter);

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String selectItem = ((TextView)view).getText().toString();
            if (selectItems.contains(selectItem)) {

                selectItems.remove(selectItem);
        }
        else {
                selectItems.add(selectItem);
                //Toast.makeText(getContext(),"Algo no funciona", Toast.LENGTH_LONG).show();
            }
    }

    public void seleccionarItems(){
        String items = "";

        for (String item:selectItems){
            items+=""+item+"\n";

        }

        bundle = new Bundle();
        bundle.putString("texto",items);
    }
}
