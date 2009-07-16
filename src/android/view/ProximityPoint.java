package android.view;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.maps.GeoPoint;

public class ProximityPoint implements Parcelable
{
    private GeoPoint mPoint;
    private int mAccuracy;

    public ProximityPoint(GeoPoint point)
    {
        if(point==null)
            throw new IllegalArgumentException("missing GeoPoint");
        
        mPoint = point;
    }

    public int getLatitudeE6()
    {
        return mPoint.getLatitudeE6();
    }

    public int getLongitudeE6()
    {
        return mPoint.getLongitudeE6();
    }
    
    public int getAccuracy()
    {
        return mAccuracy;
    }
 
    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeInt(mPoint.getLatitudeE6());
        parcel.writeInt(mPoint.getLongitudeE6());
        parcel.writeInt(mAccuracy);
        
    }
    
    public static final Parcelable.Creator<ProximityPoint> CREATOR =
        new Parcelable.Creator<ProximityPoint>() {
        public ProximityPoint createFromParcel(Parcel in) {
            int latE6 = in.readInt();
            int lngE6 = in.readInt();
            int accuracy = in.readInt();
            
            return new ProximityPoint(new GeoPoint(latE6, lngE6));
        }

        public ProximityPoint[] newArray(int size) {
            return new ProximityPoint[size];
        }
    };
    

}
