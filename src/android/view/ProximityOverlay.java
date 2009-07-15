package android.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class ProximityOverlay extends Overlay
{
    private GeoPoint mGeoPoint;
    private POICategory mCategory;
    
    public ProximityOverlay(GeoPoint geoPoint, POICategory category)
    {
        mGeoPoint = geoPoint;
        mCategory = category;
    }
    
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
        Projection projection = mapView.getProjection();
        
        Point point = projection.toPixels(mGeoPoint, null);
        
        Bitmap pointIcon = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.point_b);
        
        pointIcon.getHeight();
        pointIcon.getWidth();
        
//        canvas.drawBitmap(pointIcon, matrix, paint)
        
    }
}
