package spawn.gps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GpsActivity extends MapActivity {

    private MapView mapView;
    private MapController mc;

    private ParticularItemizedOverlay itemizedoverlay;
    private Geocoder geoCoder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LocationManager milocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        milocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, new MiLocationListener());
        mapView = (MapView) findViewById(R.id.mapview);
        mc = mapView.getController();
        mapView.setBuiltInZoomControls(true);

        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_camion);
        itemizedoverlay = new ParticularItemizedOverlay(drawable,this);
        geoCoder = new Geocoder(this, Locale.getDefault());
    }

    protected boolean isRouteDisplayed() {
        return false;
    }

    public class MiLocationListener implements LocationListener{
        public void onLocationChanged(Location loc){
        	if (loc != null) {
		        double lat = loc.getLatitude();
		        double lon = loc.getLongitude();
		        String coordenadas = "Se ha movido a: " + "Latitud = " + lat + "Longitud = " + lon;
		        Toast.makeText( getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
                GeoPoint point = new GeoPoint((int) (lat),(int) (lon));
                mc.animateTo(point);
                String address="";
                
                //mc.setZoom(16); 
///////////////////////////
                try {
                	geoCoder.getFromLocation(40,  4, 1);
                    //List<Address> addresses = geoCoder.getFromLocation(lat/1E6,  lon/1E6, 1);
                    //if (addresses.size() > 0) {
                    //    for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++)
                    //       address += addresses.get(0).getAddressLine(i) + "\n";
                    //}
                 
                } catch (IOException e) {
                	System.out.println("oooo que penita");
                    e.printStackTrace();
                }
///////////////////////////
                //Saco un icono con dicha geolocalización
                List<Overlay> mapOverlays = mapView.getOverlays();
                //Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
                //GeoPoint point = new GeoPoint(-4443769,40115845);
                OverlayItem overlayitem = new OverlayItem(point, "Está aquí", address);
                itemizedoverlay.addOverlay(overlayitem);
                mapOverlays.add(itemizedoverlay);

                mapView.invalidate();
        	}
        }
        public void onProviderDisabled(String provider){
        	Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        public void onProviderEnabled(String provider){
        	Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
        }
        public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}