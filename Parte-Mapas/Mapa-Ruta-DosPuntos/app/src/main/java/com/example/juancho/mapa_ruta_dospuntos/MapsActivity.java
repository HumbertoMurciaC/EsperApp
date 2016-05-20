package com.example.juancho.mapa_ruta_dospuntos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity {




    private static final LatLng ORIGEN = new LatLng(4.725338, -74.121072);
    private static final LatLng DESTINO = new LatLng(4.597986, -74.075957);
    //private static final LatLng JAVERIANA = new LatLng(4.626819, -74.064164);
    private GoogleMap map;
    private SupportMapFragment fragment;
    private LatLngBounds latlngBounds;
    private Button bNavigation;
    private Polyline newPolyline;
    private boolean isTravelingToParis = false;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getSreenDimanstions();
        fragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        map = fragment.getMap();

        bNavigation = (Button) findViewById(R.id.bNavigation);
        bNavigation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isTravelingToParis)
                {
                    isTravelingToParis = true;
                    findDirections( ORIGEN.latitude, ORIGEN.longitude, DESTINO.latitude, DESTINO.longitude, GMapV2Direction.MODE_DRIVING );
                }
                /*else
                {
                    isTravelingToParis = false;
                    findDirections( CARULLA.latitude, CARULLA.longitude, JAVERIANA.latitude, JAVERIANA.longitude, GMapV2Direction.MODE_DRIVING );
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
        if (isTravelingToParis)
        {
            latlngBounds = createLatLngBoundsObject(ORIGEN, DESTINO);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
        }
        /*else
        {
            latlngBounds = createLatLngBoundsObject(CARULLA, JAVERIANA);
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));
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



}
