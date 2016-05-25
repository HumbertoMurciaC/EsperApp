package com.example.humbertomariom.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.StringTokenizer;

/**
 * Created by Humberto Mario M on 23/05/2016.
 */
public class TurnoActivity extends AppCompatActivity {


    private TextView textViewTurno;
    private Button botoncancelar;
    private static String TAG = "ActivityTurno";
    private String IdSede="";
    private String IdServicio="";
    private String nomEntidad="";
    private String nomServicio="";
    private String nomSede="";
    private String TurnOOO="";
    private String QRcode="";



    ImageView qrCodeImageview;

    String IDT="";
    public final static int WIDTH=500;




    public static final String REGISTER_URL = "http://192.168.40.1:8080/WebApplication1/PedirTurno";

    public static final String REGISTER_URL2 = "http://192.168.40.1:8080/WebApplication1/CancelarTurno";

    public void pedirTurno(String correo, String IdSede, String IdServicio) {

        correo.trim();
        IdSede.trim();
        IdServicio.trim();



        String data = REGISTER_URL+"?"+"idCorreo="+correo+"&idSede="+IdSede+"&idServicio="+IdServicio;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Log.i(TAG, "Response ok");
                if(response != null){
                    Log.i(TAG, "Response data");
                }

                //Casteo a Gson
                Gson gson = new Gson();

                login obj = gson.fromJson(response,login.class);

                if(!obj.getResponse().equalsIgnoreCase("")){

                    Log.e(TAG,"Turno: "+obj.getResponse());

                   // QRcode=obj.getResponse();
                    textViewTurno.setText(obj.getResponse());

                    QRcode=textViewTurno.getText().toString();

                    Log.e(TAG,"Turno333: "+QRcode);

                }else{
                    Toast.makeText(getApplicationContext(), "Usuario ya existe!, por favor ingrese otro", Toast.LENGTH_LONG).show();
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

    public void CancelarTurno() {


        String data = REGISTER_URL2+"?"+"idTurno="+QRcode;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, data, new Response.Listener<String>() {


            public void onResponse(String response) {

                Log.i(TAG, "Response ok");
                if(response != null){
                    Log.i(TAG, "Response data");
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

        GlobalVariables appState = ((GlobalVariables)getApplicationContext());

        textViewTurno = (TextView) findViewById(R.id.TextViewTurno);

        botoncancelar = (Button) findViewById(R.id.Cancelar);


        Intent intent = getIntent();
        IdSede=intent.getExtras().getString("IDSEDE");
        IdServicio=intent.getExtras().getString("IDSERVICIO");
        nomEntidad = intent.getExtras().getString("NomEntity");
        nomSede = intent.getExtras().getString("NomSede");
        nomServicio = intent.getExtras().getString("NomServicio");


//
        Log.e(TAG, "Correo: "+appState.getUserId()+" Idsede: "+IdSede+" IdServicio: "+IdServicio);

        pedirTurno(appState.getUserId(),IdSede,IdServicio);

        Log.e(TAG, "TURNOOOO: "+appState.getUserId());



        botoncancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Cancelar:

                        CancelarTurno();
                        Intent intent  = new Intent(TurnoActivity.this,ActivityUserProfile.class);
                        startActivity(intent);

                        break;
                }
            }
        });

        getID();

        // create thread to avoid ANR Exception
        Thread t = new Thread(new Runnable() {
            public void run() {
// this is the msg which will be encode in QRcode
// http://smartandroiddeveloper.com/2016/01/29/how-to-generate-qrcode-in-10-minutes-using-zxing-library-in-android-studio/

                TurnOOO = "Entidad:"+ nomEntidad + " Sede:"+nomSede+" Servicio:"+nomServicio;


                try {
                    synchronized (this) {
                       wait(10);
// runOnUiThread method used to do UI task in main thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = null;

                                    bitmap = encodeAsBitmap(TurnOOO);
                                    qrCodeImageview.setImageBitmap(bitmap);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } // end of catch block

                            } // end of run method
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }
        });
        t.start();



    }

    private void getID() {
        qrCodeImageview=(ImageView) findViewById(R.id.img_qr_code_image);
    }

    // this is method call from on create and return bitmap image of QRCode.
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    } /// end of this method


}
