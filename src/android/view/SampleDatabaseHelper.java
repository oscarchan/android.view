package android.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SampleDatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 0;
    
	private static final String EVENT_TABLE = "event";
	private static final String EVENT_TABLE_CREATE 
		= "CREATE TABLE " + EVENT_TABLE + " ("
				+ " id INTEGER PRIMARY KEY, "
				+ " created_at INTEGER, "
				+ " action TEXT, "
				+ " category TEXT, "
				+ " data TEXT "
				+ ");";


	
	
	public SampleDatabaseHelper(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		LocationDAO.onCreate(db);
		db.execSQL(EVENT_TABLE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	    // do nothing
	}

}
