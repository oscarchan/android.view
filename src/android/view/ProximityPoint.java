package android.view;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;

public class ProximityPoint implements Parcelable
{
    private GeoPoint mPoint;
    private float mAccuracy;

    public ProximityPoint(GeoPoint point, float accuracy)
    {
        if(point==null)
            throw new IllegalArgumentException("missing GeoPoint");
        
        mPoint = point;
        mAccuracy = accuracy;
    }

    public GeoPoint getGeoPoint()
    {
        return mPoint;
    }
    
    public int getLatitudeE6()
    {
        return mPoint.getLatitudeE6();
    }

    public int getLongitudeE6()
    {
        return mPoint.getLongitudeE6();
    }
    
    public float getAccuracy()
    {
        return mAccuracy;
    }
 
    public String toString()
    {
        return "PrxPt(" + mPoint.getLatitudeE6() + ", " + mPoint.getLongitudeE6() + ": " + mAccuracy + ")";
    }
    
    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeInt(mPoint.getLatitudeE6());
        parcel.writeInt(mPoint.getLongitudeE6());
        parcel.writeFloat(mAccuracy);
        
    }
    
    public static final Parcelable.Creator<ProximityPoint> CREATOR =
        new Parcelable.Creator<ProximityPoint>() {
        public ProximityPoint createFromParcel(Parcel in) {
            int latE6 = in.readInt();
            int lngE6 = in.readInt();
            float accuracy = in.readFloat();
            
            return new ProximityPoint(new GeoPoint(latE6, lngE6), accuracy);
        }

        public ProximityPoint[] newArray(int size) {
            return new ProximityPoint[size];
        }
    };
    

}
