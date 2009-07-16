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
}
