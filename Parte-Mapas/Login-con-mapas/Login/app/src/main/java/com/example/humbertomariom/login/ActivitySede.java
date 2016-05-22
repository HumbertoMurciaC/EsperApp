package com.example.humbertomariom.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.humbertomariom.Mapas.MapsActivity;

/**
 * Created by Juancho on 21/05/16.
 */
public class ActivitySede extends AppCompatActivity implements View.OnClickListener {

    private Button botonmapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        botonmapa = (Button) findViewById(R.id.Mapa);
        botonmapa.setOnClickListener(this);
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
