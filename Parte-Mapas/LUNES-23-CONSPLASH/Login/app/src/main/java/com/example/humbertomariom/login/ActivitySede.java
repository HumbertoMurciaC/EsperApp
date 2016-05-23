package com.example.humbertomariom.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Juancho on 21/05/16.
 */
public class ActivitySede extends AppCompatActivity  {

    private Button botonmapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);

        botonmapa = (Button) findViewById(R.id.BotonMapa);
        botonmapa.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.BotonMapa:
                        Intent Next = new Intent(ActivitySede.this, MapsActivity.class);
                        startActivity(Next);
                        break;
                }
            }
        });
    }






}
