package android.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class ProximityOverlay extends Overlay
{
    private ProximityPoint mProximityPoint;
    
    private Paint mProximityColor;
    private Paint mPointColor;
    private Drawable mMarker;
    
    public ProximityOverlay(ProximityPoint point, Paint pointColor, Paint proximityColor)
    {
        mProximityPoint = point;
        mPointColor = pointColor;
        mProximityColor = proximityColor;
    }
    
    public ProximityOverlay(ProximityPoint point, Drawable marker)
    {
        mProximityPoint = point;
        mMarker = marker;
    }
    
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
        Projection projection = mapView.getProjection();
        
        Point point = projection.toPixels(mProximityPoint.getGeoPoint(), null);
        float radius = projection.metersToEquatorPixels(mProximityPoint.getAccuracy());
        
        canvas.drawCircle(point.x, point.y, radius, mProximityColor);
//        canvas.drawPoint(point.x, point.y, mPointColor);
        
//        Bitmap pointIcon = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.point_b);
        
//        pointIcon.getHeight();
//        pointIcon.getWidth();
        
//        canvas.
//        canvas.drawBitmap(pointIcon, matrix, paint)

        super.draw(canvas, mapView, shadow);
    }
}
