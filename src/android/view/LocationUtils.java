package android.view;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.maps.GeoPoint;

public class LocationUtils
{
    public static GeoPoint getGeoPoint(Location location)
    {
        Double geoLat = location.getLatitude() * 1E6;
        Double geoLng = location.getLongitude() * 1E6;
        GeoPoint geoPoint = new GeoPoint(geoLat.intValue(), geoLng.intValue());
        
        return geoPoint;
    }
 
    public static String getLowPowerProvider(LocationManager locationManager)
    {
        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
     
        return provider;
    }
    
    public static double getDistance(GeoPoint p1, GeoPoint p2)
    {
        float[] results = new float[2];
        
        Location.distanceBetween(p1.getLatitudeE6() / 1.0E6, p1.getLongitudeE6() / 1.0E6, p2.getLatitudeE6() / 1.0E6, p2.getLongitudeE6() / 1.0E6, results);
        
        return results[0];
    }
}
