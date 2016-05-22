package com.example.humbertomariom.Mapas;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.humbertomariom.login.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.util.logging.Handler;


public class MapsActivity extends FragmentActivity {

    private LatLng ORIGEN = new LatLng(4.627155, -74.063881);
    private LatLng DESTINO = new LatLng(4.641726, -74.074601);
    private LatLng DESTINO2 = new LatLng(4.615201, -74.087762);
    private GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private Button bNavigation;
    private TextView textView;
    private Polyline newPolyline;
    private boolean ViajandoA = false;
    private int width, height;
    public Criteria criteria;
    public String bestProvider;
    LocationManager locationManagerR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        textView = (TextView) findViewById(R.id.Distancia);
        //handle.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) this);


        getSreenDimanstions();
        fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = fragment.getMap();

        bNavigation = (Button) findViewById(R.id.bNavegacion);
        bNavigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //--------------------------------------------------------------------
                //if (!ViajandoA) {
                ViajandoA = true;
                findDirections(ORIGEN.latitude, ORIGEN.longitude, DESTINO.latitude, DESTINO.longitude, GMapV2Direction.MODE_DRIVING);

                /*} else {
                    ViajandoA = false;
                    findDirections(ORIGEN.latitude, ORIGEN.longitude, DESTINO2.latitude, DESTINO2.longitude, GMapV2Direction.MODE_DRIVING);

                }*/
            }
        });
    }




    @Override
    protected void onResume() {

        super.onResume();
        latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

    }


    public void Actualizar() {

        MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        criteria = new Criteria();
        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        Location loc = locationManager.getLastKnownLocation(bestProvider);

        //ACTUALIZAR ORIGEN
        if (loc != null) {
            ORIGEN = new LatLng(loc.getLatitude(), loc.getLongitude());
        }


    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null) {
            newPolyline.remove();
        }
        newPolyline = map.addPolyline(rectLine);

        //if (ViajandoA) {
        latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
        map.addMarker(new MarkerOptions().position(new LatLng(ORIGEN.latitude, ORIGEN.longitude)).title("Estoy Aqui"));
        map.addMarker(new MarkerOptions().position(new LatLng(DESTINO.latitude, DESTINO.longitude)).title("Sucursal BANCOLOMBIA"));
        float[] dist = new float[1];
        Location.distanceBetween(ORIGEN.latitude, ORIGEN.longitude, DESTINO.latitude, DESTINO.longitude, dist);
        float distancia = (dist[0]);
        String CadenaDist = String.valueOf(distancia);
        textView.setText("DISTANCIA: Usted esta a "+CadenaDist + "m");


        Actualizar();
        /*} else {
            latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO2);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
            map.addMarker(new MarkerOptions().position(new LatLng(ORIGEN.latitude, ORIGEN.longitude)).title("Estoy Aqui"));
            map.addMarker(new MarkerOptions().position(new LatLng(DESTINO2.latitude, DESTINO2.longitude)).title("Sucursal DAVIVIENDA"));
            float[] dist = new float[1];
            Location.distanceBetween(ORIGEN.latitude, ORIGEN.longitude, DESTINO2.latitude, DESTINO2.longitude, dist);
            float distancia = (dist[0]);
            String CadenaDist = String.valueOf(distancia);
            textView.setText("DISTANCIA: Usted esta a "+CadenaDist + "m");
            //getDireccion(DESTINO.latitude, DESTINO.longitude);
            Actualizar();
        }*/

    }

    /*
        private void getDireccion(double lat, double lng){

            final String Url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+4.627184+","+-74.063800+"&key=AIzaSyBWseSjaR9VYYMnYIXpOpOkzWI5FMCSLwg";
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            Button button = (Button) findViewById(R.id.bNavegacion);
            textView = (TextView) findViewById(R.id.Direccion);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,Url
                            ,null,new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject responde) {

                            try {
                                String address = responde.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                textView.setText("DIRECCIÓN: "+address);
                                //editText.setText(address);

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

        }*/
    private void getSreenDimanstions() {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }


    public class MyCurrentLoctionListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if(location == null) return;
            location.getLatitude();
            location.getLongitude();

            String myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //I make a log to see the results
            Log.e("MY CURRENT LOCATION", myLocation);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }




}
