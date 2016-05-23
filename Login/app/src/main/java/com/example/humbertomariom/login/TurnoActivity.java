package com.example.humbertomariom.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by Humberto Mario M on 23/05/2016.
 */
public class TurnoActivity extends AppCompatActivity {

    private static String TAG = "ActivityTurno";
    private Turno turno;
    private String correo1="";
    private String IdSede="";
    private String IdServicio="";




    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/PedirTurno";

    public void pedirTurno(String correo, String IdSede, String IdServicio) {

        correo.trim();
        IdSede.trim();
        IdServicio.trim();


        String data = REGISTER_URL+"?"+"idCorreo="+correo+"&idSede="+IdSede+"&idServicio="+IdServicio;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Gson gson = new Gson();

                turno = gson.fromJson(response,Turno.class);


                if (turno!=null) {

                    Log.d(TAG, "Fecha: "+turno.getFecha().toString()+" Estado: "+turno.getAtendido());

                } else {
                    //Toast.makeText(getApplicationContext(), "La lista esta vacia", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_turno);


        Intent intent = getIntent();
        IdSede=intent.getExtras().getString("IDSEDE");
        IdServicio=intent.getExtras().getString("IDSERVICIO");
        correo1 = intent.getExtras().getString("CORREO1");
//

        Log.e(TAG, "Correo: "+correo1+" Idsede: "+IdSede+" IdServicio: "+IdServicio);

        pedirTurno(correo1,IdSede,IdServicio);


    }
}
