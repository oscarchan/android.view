package android.view;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ViewMapItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
    private ArrayList<OverlayItem> mItems = new ArrayList<OverlayItem>();
//    private GeoPoint mGeoPoint;
//    private float mAccuracy;
    
    public ViewMapItemizedOverlay(Drawable defaultMarker)
    {
        super(boundCenter(defaultMarker));
        
//        mGeoPoint = geoPoint;
//        mAccuracy = accuracy;
    }
    
    public void addOverlay(OverlayItem overlay)
    {
        mItems.add(overlay);
        populate();
    }
    
//    @Override
//    public void draw(Canvas canvas, MapView mapView, boolean shadow)
//    {
//        super.draw(canvas, mapView, shadow);
//        
//        Projection projection = mapView.getProjection();
//        
//        Point point = projection.toPixels(mGeoPoint, null);
//        
////        canvas.draw
//        
//    }

    @Override
    public int size()
    {
        return mItems.size();
    }
    
    @Override
    protected OverlayItem createItem(int index)
    {
        return mItems.get(index);
    }
    
    

}
