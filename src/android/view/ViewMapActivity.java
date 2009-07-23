package android.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
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
        
        // custom control 
        ImageButton backButton = (ImageButton) findViewById(R.id.map_back_button);
        backButton.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                // TODO Auto-generated method stub

            }
        });
        
        ImageButton resetButton = (ImageButton) findViewById(R.id.map_reset_button);
        resetButton.setOnClickListener(new OnClickListener()
        {

            public void onClick(View v)
            {
                // TODO Auto-generated method stub

            }
        });
        
        
        // 
        Intent intent = getIntent();
        
        if(intent!=null)
        {
            ArrayList<Location> locations = intent.getParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS);
            Toast.makeText(this, "ViewMapActivity: received " + locations.size() +" locations", Toast.LENGTH_SHORT).show();
            
            mLocations = locations;
        }
    }

    private void populateLocation(int numLocation)
    {
        ArrayList<Location> locations = mLocations;
        
        if (locations == null)
            return;
        
        if(numLocation==mOverlays.size())
            return;
        
        

        if(numLocation > mOverlays.size()) {
            AggregatedProximityInfo proximityInfo;
            
            for (Location location : locations) {
                GeoPoint geoPoint = LocationUtils.getGeoPoint(location);

                ProximityPoint proximityPoint = new ProximityPoint(geoPoint, location.getAccuracy());
                
//                OverlayItem overlayItem = new OverlayItem(geoPoint, "" + index++, "");
                ProximityOverlay overlay = new ProximityOverlay(proximityPoint, mDrawable);

//                itemizedOverlay.addOverlay(overlayItem);
                mOverlays.add(overlay);
            }
        
            
        } else {
            // need to recalculate the average
            while(mOverlays.size()>numLocation)
                mOverlays.remove(mOverlays.size() - 1);
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
