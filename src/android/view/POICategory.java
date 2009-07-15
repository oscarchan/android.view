package android.view;

public enum POICategory
{
    // TODO this should be decoupled
    airport(R.drawable.airportblue2_16x16),
    auto_mechanic(R.drawable.carrepairblue2_16x16),
    golf_court(R.drawable.golfclubgreen2_16x16),
    hospital(R.drawable.hospitalred2_16x16),
    restaurant(R.drawable.restaurantblue2_16x16),
    theater(R.drawable.theateryellow2_16x16)
    ;
    
    private int mDrawableId;
    
    private POICategory(int drawableId)
    {
        mDrawableId = drawableId;
    }
    
    public int getDrawableId()
    {
        return mDrawableId;
    }
}
