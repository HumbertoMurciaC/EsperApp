package com.example.humbertomariom.login;

/**
 * Created by Humberto Mario M on 21/05/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ActivitySede extends AppCompatActivity implements View.OnClickListener {

    private Button botonmapa;
    private Spinner spinnerSedes;
    private List<Sede> listaSedes;
    ArrayAdapter adapter;
    private TextView textViewNomEntidad;

    String NomEntidad;

    String nit;

    private static String TAG = "ActivitySede";

    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/PedirSedesXEntidad";



    public void cargarSedes(String nit) {


        String data = REGISTER_URL+"?"+"Nit="+nit;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<Sede>>() {
                }.getType();


                listaSedes = gson.fromJson(response,type);

                Log.i(TAG, "Response data2:" + listaSedes.toString());

                if (!listaSedes.isEmpty()) {

                    for(Sede c: listaSedes ) {
                       Log.d(TAG, "Correo:"+c.getCorreoContacto()+" - Dirección:"+c.getDireccion()+" - Id:"+c.getIdSede()+" - Nombre:"+c.getNombre());
                    }

                    CargarSpinner(listaSedes);

                } else {
                    Toast.makeText(getApplicationContext(), "La lista esta vacia", Toast.LENGTH_LONG).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(TAG, "Response error");
                        if (error != null) {
                            Log.e("Volley", "Error. HTTP Status Code:"+error.toString());
                        }
                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "NoConnectionError");
                        }else if(error instanceof NoConnectionError){
                            Log.e("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("Volley", "ParseError");
                        }
                    }
                })

        {


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void CargarSpinner(List<Sede> listaSedes){
        // Create an ArrayAdapter using the string array and a default spinner layout

        List<String> Arreglo = new ArrayList<String>();
        int i=1;
        for(Sede c: listaSedes ) {
            Arreglo.add(c.getNombre());
        }
        Log.d(TAG,"tam: "+Arreglo.size());

        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arreglo);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSedes.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);

        botonmapa = (Button) findViewById(R.id.Mapa);
        botonmapa.setOnClickListener(this);

        Intent intent = getIntent();


        NomEntidad=intent.getStringExtra(ActivityUserProfile.NomEntidad);

        nit=intent.getStringExtra(ActivityUserProfile.Nit);

        spinnerSedes = (Spinner)findViewById(R.id.spinnerSedes);

        textViewNomEntidad = (TextView) findViewById(R.id.textViewNomEntidad);

        textViewNomEntidad.setText(intent.getStringExtra(ActivityUserProfile.NomEntidad));

        cargarSedes(nit);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.Mapa:

                startActivity(new Intent(this, MapsActivity.class));


                break;

        }
    }




}
