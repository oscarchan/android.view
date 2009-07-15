package android.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

  	setContentView(R.layout.main);
  	this.findViewById(R.id.sample_locations);
  	Button viewButton = (Button) this.findViewById(R.id.sample_views);
  	
  	viewButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Intent intent = new Intent(MainActivity.this, ViewSelectorActivity.class);
				startActivity(intent);
			}
		});
  	
  	Button locationButton = (Button) this.findViewById(R.id.sample_locations);
  	
  	locationButton.setOnClickListener(new OnClickListener()
		{
			public void onClick(View arg0)
			{
				Intent intent = new Intent(MainActivity.this, ViewLocationActivity.class);
				startActivity(intent);
			}
		});
  	
  	Button proximityButton = (Button) this.findViewById(R.id.sample_proximity);
  	
  	proximityButton.setOnClickListener(
  	    new OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ViewProximityActivity.class);
                startActivity(intent);
            }
        });
  	
//  	startService(service)
	}
	
	
	
	




	
}