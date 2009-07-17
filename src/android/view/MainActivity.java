package android.view;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
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
  	

     Button overlayButton = (Button) this.findViewById(R.id.test_map_overlay);
    
     overlayButton.setOnClickListener(
         new OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent intent = new Intent(MainActivity.this, ViewMapActivity.class);
                Location location1 = new Location("network");
                location1.setTime(System.currentTimeMillis());
                location1.setAccuracy(908);
                location1.setLatitude(37.408459);
                location1.setLongitude(-122.064309);

                Location location2 = new Location("network");
                location2.setTime(System.currentTimeMillis());
                location2.setAccuracy(1860);
                location2.setLatitude(37.421201);
                location2.setLongitude(-122.066186);

                Location location3 = new Location("network");
                location3.setTime(System.currentTimeMillis());
                location3.setAccuracy(635);
                location3.setLatitude(37.422163);
                location3.setLongitude(-122.078076);

                Location location4 = new Location("network");
                location4.setTime(System.currentTimeMillis());
                location4.setAccuracy(974);
                location4.setLatitude(37.415311);
                location4.setLongitude(-122.071192);

                Location location5 = new Location("network");
                location5.setTime(System.currentTimeMillis());
                location5.setAccuracy(908);
                location5.setLatitude(37.453032);
                location5.setLongitude(-122.116572);
                
                ArrayList<Location> list = new ArrayList<Location>(Arrays.asList(location1, location2, location3, location4, location5));
                intent.putParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS, list);
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