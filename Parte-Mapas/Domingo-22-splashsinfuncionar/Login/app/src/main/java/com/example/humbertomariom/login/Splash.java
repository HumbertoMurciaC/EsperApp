package com.example.humbertomariom.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by Juancho on 22/05/16.
 */
public class Splash extends ActionBarActivity {

    public static final int segundos = 0;
    public static final int milisegundos = segundos * 1000;
    public static final int delay = 2;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setMax(maximo_progreso());
        empezarAnimacion();
    }

    public void empezarAnimacion(){
        new CountDownTimer(milisegundos,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(establecer_progreso(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent nuevafrom = new Intent(Splash.this,LoginActivity.class);
                startActivity(nuevafrom);
                finish();

            }
        }.start();
    }

    public int establecer_progreso(long miliseconds){
        return (int)((milisegundos-miliseconds)/1000);

    }

    public int maximo_progreso(){

        return segundos-delay;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
