package android.view;

import android.app.Activity;
import android.os.Bundle;

public class ViewActivity extends Activity
{
	public static final String LAYOUT_ID = "layoutId";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		int layoutId = getIntent().getExtras().getInt(LAYOUT_ID);
		setContentView(layoutId);
	}
}
