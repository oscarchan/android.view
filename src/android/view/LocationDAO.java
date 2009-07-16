package android.view;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

public class LocationDAO
{
    private static final String LOCATION_TABLE = "location";
    private static final String LOCATION_TABLE_CREATE
        = "create table Location ( id integer primary key autoincrement, "
            + " latitudeE6 integer, "
            + " longitudeE6 integer, "
            
            + " address1 text null, "
            + " address2 text null, "
            + " address_remaining text null, "
            
            + " locality text null, "
            + " postal_code text null, "
            + " country text null ); ";
    
    
    
    private SampleDatabaseHelper mDbHelper;
    
    LocationDAO(SampleDatabaseHelper dbHelper)
    {
        mDbHelper = dbHelper;
    }
    
    public void addLocation(ProximityLocation location)
    {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        
        values.put("latitudeE6", location.getLatitudeE6());
        values.put("longitudeE6", location.getLongitudeE6());
        
        values.put("address1", location.getAddress1());
        values.put("address2", location.getAddress2());
        values.put("address_remaining", location.getAddressRemaining());
        
        values.put("locality", location.getLocality());
        values.put("postal_code", location.getPostalCode());
        values.put("country", location.getCountryName());
        
        database.insert(LOCATION_TABLE, null, values);
    }
    
    public ProximityLocation getByLocation(int latE6, int lngE6)
    {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        
//        database.qu
        return null;
    }
    
    public ProximityLocation getByID(int id)
    {
        return null;
    }
    
    public static void onCreate(SQLiteDatabase db)
    {
        db.execSQL(LOCATION_TABLE_CREATE);
    }
    

}
