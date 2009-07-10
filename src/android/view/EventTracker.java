package android.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

// TODO HIGH need to make sure the tracker doesn't die
public class EventTracker extends BroadcastReceiver
{
	private static final String DB_FILE = "event_db";
	
	private SQLiteDatabase _database;
	
	public EventTracker()
	{
//		_database = SQLiteDatabase.openDatabase(DB_FILE, null, flags);
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
//		_database
//		Uri data = intent.getData();
		
		
	}

}
