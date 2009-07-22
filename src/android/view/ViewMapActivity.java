package android.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class ViewMapActivity extends MapActivity
{
    private List<Overlay> mOverlays;
    private Drawable mDrawable;
    private MapController mMapController;
    
    private ArrayList<Location> mLocations;
    
    @Override
    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        
        setContentView(R.layout.map_layout);
        
        // storing references
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.zoom_view);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setSatellite(false);
        mapView.setStreetView(true);
        mapView.displayZoomControls(true);
        
        MapController mapController = mapView.getController();
        mapController.setZoom(10);
        
        // my current location
        Location currentLocation = getCurrentLocation();
        GeoPoint currentGeoPoint = LocationUtils.getGeoPoint(currentLocation);
        mapController.animateTo(currentGeoPoint);
        
        List<Overlay> overlays = mapView.getOverlays();
        MyLocationOverlay currentLocationOverlay = new MyLocationOverlay(this, mapView);
        overlays.add(currentLocationOverlay);
        currentLocationOverlay.enableMyLocation();
        
        ZoomControls zoomControls = (ZoomControls) mapView.getZoomControls();
        
        
        linearLayout.addView(zoomControls);
        
        mOverlays = mapView.getOverlays();
        mDrawable = this.getResources().getDrawable(R.drawable.point_b);
        
        mMapController = mapController;
        
        // 
        Intent intent = getIntent();
        
        if(intent!=null)
        {
            ArrayList<Location> locations = intent.getParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS);
            mLocations = locations;
    
            if (locations != null) {

                Toast.makeText(this, "ViewMapActivity: received " + locations.size() +" locations", Toast.LENGTH_SHORT).show();
                int index = 0;
                AggregatedProximityInfo proximityInfo;
                
                for (Location location : locations) {
                    GeoPoint geoPoint = LocationUtils.getGeoPoint(location);

                    ProximityPoint proximityPoint = new ProximityPoint(geoPoint, location.getAccuracy());
                    
//                    OverlayItem overlayItem = new OverlayItem(geoPoint, "" + index++, "");
                    ProximityOverlay overlay = new ProximityOverlay(proximityPoint, mDrawable);

//                    itemizedOverlay.addOverlay(overlayItem);
                    mOverlays.add(overlay);
                }
            }
        }
    }

    
    private Location getCurrentLocation()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationUtils.getLowPowerProvider(locationManager);
        Location currentLocation = locationManager.getLastKnownLocation(provider);
        
        return currentLocation;
    }
    
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

}
