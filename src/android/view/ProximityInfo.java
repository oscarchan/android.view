package android.view;

public class ProximityInfo
{
    private ProximityPoint mProximityPoint;
    private long mTime;
    
    public ProximityPoint getProximityPoint()
    {
        return mProximityPoint;
    }
    
    public long getRecordedTime()
    {
        return mTime;
    }
}
