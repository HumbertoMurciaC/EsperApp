package com.example.humbertomariom.login;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityUserProfile extends AppCompatActivity {

    private TextView textView;

    private Spinner spinner;


    private List<Entidad> listaEntidades;


    private static String TAG = "ActivityUserProfile";

    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/PedirListaEntidades";


    public void cargarEntidades() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {


            public void onResponse(String response) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<Entidad>>() {
                }.getType();


                listaEntidades = gson.fromJson(response,type);

                Log.i(TAG, "Response data2:" + listaEntidades.toString());

                if (!listaEntidades.isEmpty()) {

                    for(Entidad c: listaEntidades ) {
                        Log.d(TAG, "Correo:"+c.getCorreoContaco()+" - Dirección:"+c.getDireccion()+" - Nit:"+c.getIdNit()+" - Nombre:"+c.getNombre());
                    }

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        spinner = (Spinner)findViewById(R.id.spinnerEntidades);

        textView = (TextView) findViewById(R.id.textViewUsername);

        Intent intent = getIntent();

        textView.setText("Welcome User " + intent.getStringExtra(LoginActivity.KEY_USERNAME));

        cargarEntidades();



    }


}