package android.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EventDatabaseHelper extends SQLiteOpenHelper
{
	private static final String EVENT_TABLE = "event";
	private static final String TABLE_EVENT_CREATE 
		= "CREATE TABLE " + EVENT_TABLE + " ("
				+ " id INTEGER PRIMARY KEY, "
				+ " created_at INTEGER, "
				+ " action TEXT, "
				+ " category TEXT, "
				+ " data TEXT "
				+ ");";
	
	
	
	public EventDatabaseHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(TABLE_EVENT_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{


	}

}
