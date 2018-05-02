package com.example.germa.proyecto;

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


public class MainActivity extends AppCompatActivity {
    EditText etUsuario, etContraseña;
    Button btnInicio;
    Cursor cursor;
    //private String getAllContactsURL = "http://130.100.25.69/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*BHelper BD = new DBHelper(this);
        SQLiteDatabase BASE_DATOS = BD.abrirBaseDatos();
        helper = new DBHelper(this);*/

        btnInicio = (Button) findViewById(R.id.btn_iniciar);
        etUsuario = (EditText) findViewById(R.id.tv_usuario);
        etContraseña = (EditText) findViewById(R.id.tv_contraseña);

        btnInicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentReg= new Intent(MainActivity.this, Menu_Principal.class);
                startActivity(intentReg);

              //  validarUsuario();
                   }
        });
    }
/*
    public void validarUsuario(){

        String usuario = etUsuario.getText().toString();
        String contraseña = etContraseña.getText().toString();

        cursor = helper.consulta_loggin(usuario, contraseña);

        if(cursor.getCount()==1){

            Intent actPrin = new Intent(MainActivity.this, Menu_Principal.class);
            startActivity(actPrin);

        }
    }
    */



}