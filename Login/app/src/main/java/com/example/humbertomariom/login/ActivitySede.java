package com.example.humbertomariom.login;

/**
 * Created by Humberto Mario M on 21/05/2016.
 */
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ActivitySede extends AppCompatActivity{


    private Button botonmapa;
    private Button botonturno;
    private Spinner spinnerSedes;
    private Spinner spinnerServicios;
    private List<Sede> listaSedes;
    private List<Servicio> listaServicios;
    ArrayAdapter adapter;
    ArrayAdapter adapter2;
    private TextView textViewNomEntidad;
    public static String DirSede = "";
    public static StringTokenizer tokens;
    public static double lat=0;
    public static double lng=0;
    public static String latstr="";
    public static String lngstr2="";



    public static String NomEntidad;
    public static String IDSEDE="";
    public static String NomServicio;
    public static String IDSERVICIO="";
    public static String CORREO1="";
    public float distancia;

    String nit;
    String correo;

    private static String TAG = "ActivitySede";

    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/PedirSedesXEntidad";

    public static final String REGISTER_URL2 = "http://192.168.40.1:8080/WebApplication1/PedirServicioDeSede";



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

    public void cargarServicios(String IdSede) {

        IdSede.trim();

        String data = REGISTER_URL2+"?"+"idSede="+IdSede;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Gson gson = new Gson();

                Type type = new TypeToken<List<Servicio>>() {
                }.getType();


                listaServicios = gson.fromJson(response,type);

                if (!listaServicios.isEmpty()) {

                    for(Servicio c: listaServicios ) {
                        Log.d(TAG, "Servicio:"+c.getTipo());
                    }

                    CargarSpinner2(listaServicios);

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

    public void CargarSpinner2(List<Servicio> listaServicios){
        // Create an ArrayAdapter using the string array and a default spinner layout

        List<String> Arreglo = new ArrayList<String>();
        int i=1;
        for(Servicio c: listaServicios ) {
            Arreglo.add(c.getTipo());
        }
        Log.d(TAG,"tam: "+Arreglo.size());

        adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Arreglo);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServicios.setAdapter(adapter2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);

        botonmapa = (Button) findViewById(R.id.Mapa);
        botonmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Mapa:
                        if(distancia <= 3000){
                            Intent intent  = new Intent(ActivitySede.this,MapsActivity.class);
                            intent.putExtra("La",lat);
                            intent.putExtra("Lg",lng);
                            intent.putExtra("NomEntidad",NomEntidad);
                            Toast.makeText(ActivitySede.this,"Puede solicitar turno",Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }else{
                            String noPuede = "No puede accesar al mapa porque esta a: "+distancia+" m";
                            Toast.makeText(ActivitySede.this,noPuede,Toast.LENGTH_LONG).show();
                        }


                        break;
                }
            }
        });


        botonturno=(Button) findViewById(R.id.Turno);
        botonturno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {



                    case R.id.Turno:


                        Intent intent2  = new Intent(ActivitySede.this,TurnoActivity.class);

                        String IDSE=DevolverIdSede(NomEntidad);
                        String IDS=IdServicio(NomServicio);


                        intent2.putExtra("IDSEDE",IDSE);
                        intent2.putExtra("IDSERVICIO",IDS);
                        intent2.putExtra("CORREO1",correo);
                        startActivity(intent2);


                        break;

                }

            }
        });

        Intent intent = getIntent();

        textViewNomEntidad = (TextView) findViewById(R.id.textViewNomEntidad);

        textViewNomEntidad.setText(intent.getStringExtra(ActivityUserProfile.NomEntidad));

        nit=intent.getStringExtra(ActivityUserProfile.Nit);
        correo=intent.getStringExtra(ActivityUserProfile.CORREO);

        spinnerSedes = (Spinner)findViewById(R.id.spinnerSedes);
        spinnerServicios  = (Spinner)findViewById(R.id.spinnerServicios);





        cargarSedes(nit);

        spinnerSedes.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            public  void  onItemSelected(AdapterView<?> parent, View view, int  position, long  i) {

                NomEntidad=spinnerSedes.getSelectedItem().toString();

                String IdSede= DevolverIdSede(NomEntidad);
                DirSede = DevolverDir(NomEntidad);
                tokens= new StringTokenizer(DirSede,",");
                int nDatos = tokens.countTokens();
                Log.e(TAG, "DireccionSede error"+DirSede);
                latstr = tokens.nextToken();
                lat=Double.valueOf(latstr).doubleValue();
                lngstr2 = tokens.nextToken();
                lng=Double.valueOf(lngstr2).doubleValue();
                float[] dist = new float[1];
                Location.distanceBetween(4.627155, -74.063881,lat,lng,dist);
                distancia = (dist[0]);
                Log.e(TAG, "DireccionSede error:"+latstr+","+lngstr2);
                cargarServicios(IdSede);




            }
            public  void  onNothingSelected(AdapterView<?> parent) {
                // We don't need to worry about nothing being selected, since Spinners don't allow
                // this.
            }
        });

        spinnerServicios.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
            public  void  onItemSelected(AdapterView<?> parent, View view, int  position, long  i) {

                NomServicio=spinnerServicios.getSelectedItem().toString();


            }
            public  void  onNothingSelected(AdapterView<?> parent) {
                // We don't need to worry about nothing being selected, since Spinners don't allow
                // this.
            }
        });



    }




    public String getDirSede(){
        return DirSede;
    }
    String DevolverIdSede(String nom){

        for(Sede c: listaSedes ) {
            if(nom.equalsIgnoreCase(c.getNombre())){
                return c.getIdSede();
            }
        }

        return "";

    }

    String DevolverDir(String nom){

        for(Sede c: listaSedes ) {
            if(nom.equalsIgnoreCase(c.getNombre())){
                return c.getDireccion();
            }
        }

        return "";

    }

    String IdServicio(String nom){

        for(Servicio c: listaServicios ) {
            if(nom.equalsIgnoreCase(c.getTipo())){
                return c.getIdServicio();
            }
        }

        return "";

    }






}
