package com.example.humbertomariom.login;

/**
 * Created by Humberto Mario M on 21/05/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.humbertomariom.Mapas.MapsActivity;

public class ActivitySede extends AppCompatActivity implements View.OnClickListener {

    private Button botonmapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedes);

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
