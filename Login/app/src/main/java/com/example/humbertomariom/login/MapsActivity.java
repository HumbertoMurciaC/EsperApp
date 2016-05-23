package com.example.humbertomariom.login;

import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import java.util.*;
import java.util.StringTokenizer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static LatLng ORIGEN = new LatLng(4.627155, -74.063881);
    private static LatLng DESTINO = new LatLng(4.641726, -74.074601);
    private static final LatLng DESTINO2 = new LatLng(4.615201, -74.087762);
    private GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private Button bNavigation;
    private Button bRegresar;
    private Polyline newPolyline;
    private boolean isTravelingToParis = false;
    private int width, height;
    private TextView textView;
    private TextView direccion;
    public Criteria criteria;
    public String bestProvider;
    public static String DirSede="";
    public static StringTokenizer tokens;
    public static String NombreSed = "";
    public static String nombreMDestino = "";
    public double lat=0;
    public double lng=0;

    private static String TAG = "ActivityMapa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        textView = (TextView) findViewById(R.id.Distancia);
        direccion = (TextView) findViewById(R.id.Direccion);
        Intent intent = getIntent();
        DirSede=intent.getStringExtra(ActivitySede.DirSede);
        NombreSed = intent.getStringExtra(ActivitySede.NomEntidad);
        //String str1 = intent.getStringExtra(ActivitySede.latstr.trim());
        //String str2 = intent.getStringExtra(ActivitySede.lngstr2.trim());
        //Log.e(TAG, "DirMapa er****:"+str1+"*"+str2);
        lat = intent.getDoubleExtra("La",ActivitySede.lat);
        lng = intent.getDoubleExtra("Lg",ActivitySede.lng);
        Log.e(TAG, "DireccionMapa er****:"+lat+"*"+lng);
        if(lat != 0&& lng != 0){
            DESTINO = new LatLng(lat,lng);
        }
        getSreenDimanstions();
        fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = fragment.getMap();

        bRegresar = (Button) findViewById(R.id.RegresarSede);
        bRegresar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.RegresarSede:
                        Intent Next = new Intent(MapsActivity.this, ActivitySede.class);
                        startActivity(Next);
                        break;
                }
            }
        });

        bNavigation = (Button) findViewById(R.id.bNavigation);
        bNavigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if (!isTravelingToParis) {
                isTravelingToParis = true;
                findDirections( ORIGEN.latitude, ORIGEN.longitude, DESTINO.latitude, DESTINO.longitude, GMapV2Direction.MODE_DRIVING );
                /*}
                else
                {
                    isTravelingToParis = false;
                    findDirections( ORIGEN.latitude, ORIGEN.longitude, DESTINO2.latitude, DESTINO2.longitude, GMapV2Direction.MODE_DRIVING );
                }*/
            }
        });

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
    public class MyCurrentLoctionListener implements android.location.LocationListener {

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

    @Override
    protected void onResume() {

        super.onResume();
        latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO);
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

    }



    public String CalcularDireccion(double latitude, double longitude){



        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        String Direcc;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            Direcc = address+", "+ city +", "+ state +", "+ country +", "+ postalCode;
            return Direcc;
        } catch (IOException e) {
            e.printStackTrace();

        }


        return null;

    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null)
        {
            newPolyline.remove();
        }
        newPolyline = map.addPolyline(rectLine);
        //if (isTravelingToParis) {
        latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO);
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
        map.addMarker(new MarkerOptions().position(new LatLng(ORIGEN.latitude, ORIGEN.longitude)).title("Estoy Aqui"));
        nombreMDestino = NombreSed;
        if(nombreMDestino != null){
            map.addMarker(new MarkerOptions().position(new LatLng(DESTINO.latitude, DESTINO.longitude)).title("Sucursal "+nombreMDestino));
        }
        else {
            map.addMarker(new MarkerOptions().position(new LatLng(DESTINO.latitude, DESTINO.longitude)).title("Sucursal BANCOLOMBIA"));
        }
        float[] dist = new float[1];
        Location.distanceBetween(ORIGEN.latitude, ORIGEN.longitude, DESTINO.latitude, DESTINO.longitude, dist);
        float distancia = (dist[0]);
        String CadenaDist = String.valueOf(distancia);
        textView.setText("DISTANCIA: Usted esta a "+CadenaDist + "m");
        String dirDestino = CalcularDireccion(DESTINO.latitude, DESTINO.longitude);
        if(dirDestino != null) {
            direccion.setText("DIRECCIÓN: "+dirDestino);

        }else{
            direccion.setText("DIRECCIÓN DESCONOCIDA ");
        }

        Actualizar();

        /*}
        else
        {
            latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO2);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
            map.addMarker(new MarkerOptions().position(new LatLng(ORIGEN.latitude, ORIGEN.longitude)).title("Estoy Aqui"));
            map.addMarker(new MarkerOptions().position(new LatLng(DESTINO2.latitude, DESTINO2.longitude)).title("Sucursal DAVIVIENDA"));
            float[] dist = new float[1];
            Location.distanceBetween(ORIGEN.latitude, ORIGEN.longitude, DESTINO2.latitude, DESTINO2.longitude, dist);
            float distancia = (dist[0]);
            String CadenaDist = String.valueOf(distancia);
            textView.setText("DISTANCIA: Usted esta a "+CadenaDist + "m");
        }*/

    }

    private void getSreenDimanstions()
    {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation)
    {
        if (firstLocation != null && secondLocation != null)
        {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(4.598079, -74.075994);
        mMap.addMarker(new MarkerOptions().position(sydney).title("BOGOTÁ"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
