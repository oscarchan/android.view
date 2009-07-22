package android.view;

import com.google.android.maps.GeoPoint;

/**
 * simple linear weighted aggregation
 * 
 * 
 * @author ochan
 */
// TODO refactor to 
public class AggregatedProximityInfo
{
    private static final float MAX_ALLOWED_ACCURACY = 3000; // ignore anything that is over 3000 km

    private ProximityPoint mAggregatedPoint;
    private long mStartTime;
    private long mLastRecordedTime;
    
    public AggregatedProximityInfo(ProximityInfo proximityInfo)
    {
        mAggregatedPoint = proximityInfo.getProximityPoint();
        
        mStartTime = proximityInfo.getRecordedTime();
        mLastRecordedTime = proximityInfo.getRecordedTime();
    }
    
    public void addProximityInfo(ProximityInfo proximityInfo)
    {
        if(proximityInfo.getRecordedTime()<=mLastRecordedTime) 
            return;
        
        long recordTime = proximityInfo.getRecordedTime();
        ProximityPoint point = proximityInfo.getProximityPoint();

        long aggregatedWeight = mLastRecordedTime - mStartTime;
        long weight = recordTime - mLastRecordedTime; 

        float accuracy = getMidPoint(mAggregatedPoint.getAccuracy(), point.getAccuracy(), aggregatedWeight, weight);
        
        int latitudeE6 = (int)
            (getMidPoint(mAggregatedPoint.getLatitudeE6(), point.getLatitudeE6(), aggregatedWeight, weight)
            + getMidPoint(mAggregatedPoint.getLatitudeE6(), point.getLatitudeE6(), mAggregatedPoint.getAccuracy(), point.getAccuracy()) / 2);
        
        int longitudeE6 = (int) 
            (getMidPoint(mAggregatedPoint.getLongitudeE6(), point.getLongitudeE6(), aggregatedWeight, weight)
                + getMidPoint(mAggregatedPoint.getLongitudeE6(), point.getLongitudeE6(), mAggregatedPoint.getAccuracy(), point.getAccuracy()) / 2);
            
        mAggregatedPoint = new ProximityPoint(new GeoPoint(latitudeE6, longitudeE6), accuracy);
        mLastRecordedTime = recordTime;
    }
    
    private static float getMidPoint(float p1, float p2, float w1, float w2)
    {
        return (p1 * w1 + p2 * w2) / (w1 + w2); 
    }
    
    
}
