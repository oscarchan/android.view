package android.view;

import java.util.Locale;

public class ProximityLocation
{
    private int mLatitudeE6;
    private int mLongitudeE6;
    private int mAccuracy; // in kilometer
    
    private String mAddress1;
    private String mAddress2;
    private String mAddressRemaining;
    
    private String mLocality;
    private String mPostalCode;
    private String mCountryName;
    
    private Locale mLocale;

    public int getLatitudeE6()
    {
        return mLatitudeE6;
    }

    public void setLatitudeE6(int latitudeE6)
    {
        mLatitudeE6 = latitudeE6;
    }

    public int getLongitudeE6()
    {
        return mLongitudeE6;
    }

    public void setLongitudeE6(int longitudeE6)
    {
        mLongitudeE6 = longitudeE6;
    }

    public String getAddress1()
    {
        return mAddress1;
    }

    public void setAddress1(String address1)
    {
        mAddress1 = address1;
    }

    public String getAddress2()
    {
        return mAddress2;
    }

    public void setAddress2(String address2)
    {
        mAddress2 = address2;
    }
    

    public String getAddressRemaining()
    {
        return mAddressRemaining;
    }

    public void setAddressRemaining(String addressRemaining)
    {
        mAddressRemaining = addressRemaining;
    }

    public String getLocality()
    {
        return mLocality;
    }

    public void setLocality(String locality)
    {
        mLocality = locality;
    }

    public String getPostalCode()
    {
        return mPostalCode;
    }

    public void setPostalCode(String postalCode)
    {
        mPostalCode = postalCode;
    }

    public String getCountryName()
    {
        return mCountryName;
    }

    public void setCountryName(String countryName)
    {
        mCountryName = countryName;
    }

    public Locale getLocale()
    {
        return mLocale;
    }

    public void setLocale(Locale locale)
    {
        mLocale = locale;
    }
    
    
    
    
    
}
