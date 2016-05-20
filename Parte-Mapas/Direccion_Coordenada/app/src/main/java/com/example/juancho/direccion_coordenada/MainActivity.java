package com.example.juancho.direccion_coordenada;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private RequestQueue requestQueue;
    private EditText editText;
    private EditText La;
    private EditText Lo;
    private String Url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.editText);
        editText = (EditText) findViewById(R.id.editText2);
        La = (EditText) findViewById(R.id.Latt);
        Lo = (EditText) findViewById(R.id.Lott);
        Url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+4.627184+","+-74.063800+"&key=AIzaSyCUlLy9E791tmntuTiFoB4R72OxF2KkCoI";
        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,Url
                        ,null,new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject responde) {

                                try {
                                    String address = responde.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                    textView.setText(address);
                                    editText.setText(address);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(request);
            }
        });


    }
}

