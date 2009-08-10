package android.view;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity
{
    private DateFormat formatter = new SimpleDateFormat("kk:mm:ss");
    private GestureDetector mGestureDetector;
    private Handler mHandler;

    
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

  	setContentView(R.layout.main);

    setUpViewButton();  	
  	setUpLocationButton();
  	setUpOverlayButton();
    setUpMovementButton();
  	setUpProximityButton();
    setUpGestureDetector();

//  	startService(service)
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
	    /** IMPORTANT: we need to feed touch event to detector */
	    return mGestureDetector.onTouchEvent(event);
	}

    private void setUpGestureDetector()
    {
        OnGestureListener listener = getToastProxy(OnGestureListener.class, new GestureDetector.SimpleOnGestureListener());
        
        mGestureDetector = new GestureDetector(MainActivity.this, listener);
    }

    private void setUpLocationButton()
    {
        View locationButton = this.findViewById(R.id.sample_locations);
        
        locationButton.setOnClickListener(getToastProxy(OnClickListener.class, new OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent intent = new Intent(MainActivity.this, ViewLocationActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void setUpViewButton()
    {
        Button viewButton = (Button) this.findViewById(R.id.sample_views);
        
        viewButton.setOnClickListener(getToastProxy(OnClickListener.class, new OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ViewSelectorActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void setUpProximityButton()
    {
        Button proximityButton = (Button) this.findViewById(R.id.sample_proximity);
        
        proximityButton.setOnClickListener(getToastProxy(OnClickListener.class, new OnClickListener()
        {
            public void onClick(View v)
            {
                // nothing
                Intent intent = new Intent(MainActivity.this, ViewProximityActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void setUpMovementButton()
    {
        Button movementButton = (Button) this.findViewById(R.id.test_movement_map_overlay);
         
        movementButton.setOnClickListener(getToastProxy(OnClickListener.class, new OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent intent = new Intent(MainActivity.this, ViewMapActivity.class);
                ArrayList<Location> list = new ArrayList<Location>(Arrays.asList(mockLocation("07:06:17", 37.429681, -121.907170, 1901), mockLocation("07:06:59", 37.424589, -121.915846, 1965),
                    mockLocation("07:07:15", 37.425376, -121.914608, 1521), mockLocation("07:07:28", 37.426636, -121.917310, 1521), mockLocation("07:07:33", 37.426479, -121.915996, 1521),
                    mockLocation("07:08:59", 37.421055, -121.922358, 1768), mockLocation("07:09:26", 37.420251, -121.939335, 2187), mockLocation("07:10:50", 37.418237, -121.951347, 1184),
                    mockLocation("07:10:55", 37.417588, -121.956692, 1184), mockLocation("07:11:25", 37.418874, -121.957873, 2069), mockLocation("07:11:32", 37.416328, -121.971568, 2069),
                    mockLocation("07:12:08", 37.413821, -121.983176, 2312), mockLocation("07:12:16", 37.413802, -121.984220, 2312), mockLocation("07:12:28", 37.412750, -121.987662, 2266),
                    mockLocation("07:13:23", 37.407458, -122.005904, 1289), mockLocation("07:13:40", 37.405388, -122.003737, 1289), mockLocation("07:13:57", 37.401032, -122.016000, 1888),
                    mockLocation("07:14:21", 37.400462, -122.019362, 1888), mockLocation("07:14:56", 37.402135, -122.038585, 1894), mockLocation("07:15:08", 37.400441, -122.034507, 1888),
                    mockLocation("07:15:50", 37.401006, -122.035866, 1888), mockLocation("07:16:16", 37.403605, -122.044684, 1815), mockLocation("07:16:19", 37.403115, -122.042651, 1815),
                    mockLocation("07:16:29", 37.404095, -122.046716, 1815), mockLocation("07:17:12", 37.404427, -122.057646, 2184), mockLocation("07:17:23", 37.403418, -122.062668, 1460),
                    mockLocation("07:17:31", 37.405071, -122.065474, 1460), mockLocation("07:18:18", 37.410900, -122.076672, 2059), mockLocation("07:19:48", 37.412988, -122.068843, 2934),
                    mockLocation("07:20:53", 37.411944, -122.072758, 2059), mockLocation("07:20:56", 37.411596, -122.074062, 2059), mockLocation("07:25:06", 37.420618, -122.075779, 2194),
                    mockLocation("07:37:53", 37.416803, -122.072311, 2194)));

                intent.putParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS, list);
                startActivity(intent);
            }
        }));
    }

    private void setUpOverlayButton()
    {
        Button overlayButton = (Button) this.findViewById(R.id.test_map_overlay);
        
        overlayButton.setOnClickListener(getToastProxy(OnClickListener.class, new OnClickListener()
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
        }));
    }
	
	private <T> T getToastProxy(Class<T> clazz, T object)
	{
	    T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ToastInvocationHandler(object));
	    return proxy;
	}
	
	private long getTime(String dateString)
	{
	    try {
            Date time = formatter.parse(dateString);
            return time.getTime();
        } catch (ParseException e) {
            Toast.makeText(this, "unable to parse text: " +  e, Toast.LENGTH_SHORT);
            return System.currentTimeMillis();
        }
	}

    private Location mockLocation(String date, double lat, double lng, float accuracy)
    {
        Location location = new Location("network");
        location.setTime(getTime(date));
        location.setAccuracy(accuracy);
        location.setLatitude(lat);
        location.setLongitude(lng);
        
        return location;
    }
	
    final class ToastInvocationHandler implements InvocationHandler
    {
        private Object mWrappedObj;
        
        public ToastInvocationHandler()
        {
            this(null);
        }
        
        public ToastInvocationHandler(Object wrappedObj)
        {
            mWrappedObj = wrappedObj;
        }
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            Toast.makeText(MainActivity.this, method.getName() + "(" + Arrays.asList(args) + ")", Toast.LENGTH_SHORT).show();
            
            Object returned = null;
            
            if(mWrappedObj!=null)
                returned = method.invoke(mWrappedObj, args);
            
            return returned;
        }
    }	
	




	
}