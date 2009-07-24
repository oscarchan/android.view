package android.view;

public class ProximityInfo
{
    private ProximityPoint mProximityPoint;
    private long mTime;
    
    public ProximityInfo(ProximityPoint point, long time)
    {
        mProximityPoint = point;
        mTime = time;
    }
    public ProximityPoint getProximityPoint()
    {
        return mProximityPoint;
    }
    
    public long getRecordedTime()
    {
        return mTime;
    }
}
