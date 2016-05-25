package com.example.humbertomariom.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by Juancho on 24/05/16.
 */



public class HistorialActivity extends Activity {

    private List<Turno> lista;

    private static String TAG = "ActivitySede";

    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/HistorialTurnos";

    ArrayList<String> listaFinal = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        CargarHistorial();


    }
    public String Devolvertipo(String aux){

        if(aux.equalsIgnoreCase("0")){
            return "En espera";
        }

        if(aux.equalsIgnoreCase("1")){
            return "Atendido";
        }

        if(aux.equalsIgnoreCase("2")){
            return "Cancelado";
        }

        return "";

    }

    public String concatenarDatos(Turno t){
        String retorno = "Turno:"+t.getNumTurno()+" - Sede: "+t.getSede().getNombre()+" - Estado: "+Devolvertipo(t.getAtendido())+" - Nombre de usuario: "+t.getUsuario().getNombre();
        return retorno;
    }

    public void cargar(List<Turno> lista){
        for(int i = 0;i<lista.size();i++){
            listaFinal.add(concatenarDatos(lista.get(i)));

        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaFinal);
        ListView myList = (ListView)
                findViewById(R.id.HistorialTurnos);
        myList.setAdapter(myAdapter);
    }

    public  void CargarHistorial(){

        GlobalVariables appState = ((GlobalVariables)getApplicationContext());

        String data = REGISTER_URL+"?"+"idCorreo="+appState.getUserId();;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<Turno>>() {
                }.getType();


                lista = gson.fromJson(response,type);


                try {

                    if (!lista.isEmpty()) {

                        for (Turno c : lista) {
                            Log.d(TAG, "Estado:" + c.getAtendido());
                        }

                        cargar(lista);



                    } else {
                        Toast.makeText(getApplicationContext(), "No ha solicitado un turno aun", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "No ha solicitado un turno aun", Toast.LENGTH_LONG).show();
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



}


