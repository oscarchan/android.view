package android.view;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ViewMapItemizedOverylay extends ItemizedOverlay
{

    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

    public ViewMapItemizedOverylay(Drawable defaultMarker)
    {
        super(boundCenter(defaultMarker));
    }
    public void addOverlay(OverlayItem overlay)
    {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    public int size()
    {
        return mOverlays.size();
    }
    
    @Override
    protected OverlayItem createItem(int index)
    {
        return mOverlays.get(index);
    }
    
    

}
