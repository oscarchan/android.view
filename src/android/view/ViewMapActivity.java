package android.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
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
        mapView.setSatellite(true);
        mapView.setStreetView(true);
        mapView.displayZoomControls(false);
        
        MapController mapController = mapView.getController();
        mapController.setZoom(17);
        
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        
        Location currentLocation = locationManager.getLastKnownLocation(provider);
        GeoPoint currentGeoPoint = getGeoPointLocation(currentLocation);
        mapController.animateTo(currentGeoPoint);
        
        
        ZoomControls zoomControls = (ZoomControls) mapView.getZoomControls();
        
        
        linearLayout.addView(zoomControls);
        
        mOverlays = mapView.getOverlays();
        mDrawable = this.getResources().getDrawable(R.drawable.screen18x18);
        mItemizedOverlay = new ViewMapItemizedOverylay(mDrawable);
        mOverlays.add(mItemizedOverlay);
        mMapController = mapController;
        
        // 
        
        ArrayList<Location> locations = icicle.getParcelableArrayList(ViewActivityConstants.INTENT_LOCATIONS);

        for (Location location : locations) {
            Double lat = location.getLatitude() * 1E6;
            Double lng = location.getLongitude() * 1E6;
            
            GeoPoint point = new GeoPoint(lat.intValue(), lng.intValue());
            
            OverlayItem overlayItem = new OverlayItem(point, "", "");
            mItemizedOverlay.addOverlay(overlayItem);
            mOverlays.add(mItemizedOverlay);
            
        }
        
    }
    
    private GeoPoint getGeoPointLocation(Location location)
    {
        Double geoLat = location.getLatitude() * 1E6;
        Double geoLng = location.getLongitude() * 1E6;
        GeoPoint geoPoint = new GeoPoint(geoLat.intValue(), geoLng.intValue());
        
        return geoPoint;
    }
        
    
    
    
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

}
