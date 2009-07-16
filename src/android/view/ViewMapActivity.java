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
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ViewMapActivity extends MapActivity
{
    private List<Overlay> mOverlays;
    private Drawable mDrawable;
    private ViewMapItemizedOverylay mItemizedOverlay;
    private MapController mMapController;
    
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
        mapController.setZoom(17);
        
        // location
        Location currentLocation = getCurrentLocation();
        GeoPoint currentGeoPoint = LocationUtils.getGeoPoint(currentLocation);
        mapController.animateTo(currentGeoPoint);
        
        
        ZoomControls zoomControls = (ZoomControls) mapView.getZoomControls();
        
        
        linearLayout.addView(zoomControls);
        
        mOverlays = mapView.getOverlays();
        mDrawable = this.getResources().getDrawable(R.drawable.point_b);
        
        mItemizedOverlay = new ViewMapItemizedOverylay(mDrawable);
        mMapController = mapController;
        
        // 
        Intent intent = getIntent();
        
        if(intent!=null)
        {
            ArrayList<Location> locations = intent.getParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS);
    
            int index = 0;
            for (Location location : locations) {
                GeoPoint geoPoint = LocationUtils.getGeoPoint(location);
                
                OverlayItem overlayItem = new OverlayItem(geoPoint, "" + index++, "");
                mItemizedOverlay.addOverlay(overlayItem);
//                mOverlays.add(mItemizedOverlay);
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
