package android.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class ViewMapActivity extends MapActivity
{
    private List<Overlay> mOverlays;
    private Drawable mDrawable;
    private MapController mMapController;
    private Handler mHandler;
    private Runnable mNavViewDisolver;
    
    private ArrayList<Location> mLocations;
    
    private int mNumReplayLocation;
    
    private Paint mPointPaint = new Paint();
    private Paint mProximityPaint = new Paint();
    private Paint mAggrPointPaint = new Paint();
    private Paint mAggrProximityPaint = new Paint();
    private GestureDetector mGestureDetector;

    
    @Override
    protected void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        
        setContentView(R.layout.map_layout);
        
        // storing references
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.zoom_view);
        final View mapNavView = findViewById(R.id.map_nav_view);
        mapNavView.setVisibility(View.INVISIBLE);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setSatellite(false);
        mapView.setStreetView(true);
        mapView.displayZoomControls(true);
        
        MapController mapController = mapView.getController();
        mapController.setZoom(15);

        // my current location
        Location currentLocation = getCurrentLocation();
        GeoPoint currentGeoPoint = new GeoPoint((int)(37.425376 * 1E6), (int)(-121.914608 * 1E6));
        if(currentLocation!=null) {
            currentGeoPoint = LocationUtils.getGeoPoint(currentLocation);
        }
        mapController.animateTo(currentGeoPoint);
        
        List<Overlay> overlays = mapView.getOverlays();
        MyLocationOverlay currentLocationOverlay = new MyLocationOverlay(this, mapView);
        overlays.add(currentLocationOverlay);
        currentLocationOverlay.enableMyLocation();
        
        ZoomControls zoomControls = (ZoomControls) mapView.getZoomControls();
        
        linearLayout.addView(zoomControls);
        
        mOverlays = mapView.getOverlays();
        mDrawable = this.getResources().getDrawable(R.drawable.point_b);
        
        mMapController = mapController;
        
        mHandler = new Handler();

        // color
        mPointPaint.setColor(this.getResources().getColor(R.color.point_color));
        mProximityPaint.setColor(this.getResources().getColor(R.color.proximity_color));
        
        mapView.setOnTouchListener(new OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                return mGestureDetector.onTouchEvent(event);
            }
        });
        // custom control
        OnGestureListener gestureListener = ProxyUtil.getToastProxy(ViewMapActivity.this, OnGestureListener.class, new SimpleOnGestureListener()
        {
            public boolean onDown(MotionEvent e)
            {
                show(mapNavView);
                return false;
            }

            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                show(mapNavView);
                return false;
            }

            public boolean onSingleTapUp(MotionEvent e)
            {
                show(mapNavView);
                return false;
            }
        });
        
        
        mGestureDetector = new GestureDetector(mapView.getContext(), 
            gestureListener, mHandler);

        mGestureDetector.setIsLongpressEnabled(true);
        
        final TextView currentPointView = (TextView) findViewById(R.id.current_point);
        
        View backButton = findViewById(R.id.map_back_button);
        backButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if(mNumReplayLocation>0)
                    populateLocation(--mNumReplayLocation);
              
                currentPointView.setText(String.valueOf(mNumReplayLocation));
            }
        });
        
        View resetButton = findViewById(R.id.map_reset_button);
        resetButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                mNumReplayLocation = 0;
                populateLocation(mNumReplayLocation);
                
                currentPointView.setText(String.valueOf(mNumReplayLocation));
            }
        });
        
        View forwardButton = findViewById(R.id.map_forward_button);

        forwardButton.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if(mNumReplayLocation>mLocations.size() - 1)
                    populateLocation(++mNumReplayLocation);
                
                currentPointView.setText(String.valueOf(mNumReplayLocation));
            }
        });
        
        TextView totalPointView = (TextView) findViewById(R.id.total_points);
        final EditText editText = (EditText) findViewById(R.id.current_point);
        editText.setText("0");
        editText.setOnKeyListener(new OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(KeyEvent.ACTION_DOWN==event.getAction()
                    && KeyEvent.KEYCODE_ENTER==keyCode) {
                    String positionString = editText.getText().toString();
                    try {
                        int position = Integer.parseInt(positionString);
                        
                        if(0<=position && position<mLocations.size())
                            populateLocation(position);
                        else
                            Toast.makeText(ViewMapActivity.this, "invalid point position: " + positionString, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(ViewMapActivity.this, "invalid input for point position: " + positionString, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        } );
        
        editText.setOnFocusChangeListener(new OnFocusChangeListener() 
        {
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus==false) {
                    mHandler.removeCallbacks(mNavViewDisolver);
                    mHandler.postDelayed(mNavViewDisolver, ViewConfiguration.getZoomControlsTimeout());                    
                }
            }
        });

        // 
        Intent intent = getIntent();
        
        if(intent!=null)
        {
            ArrayList<Location> locations = intent.getParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS);
            Toast.makeText(this, "ViewMapActivity: received " + locations.size() +" locations", Toast.LENGTH_SHORT).show();
            
            totalPointView.setText("/" + locations.size());
            
            mLocations = locations;
            
            populateLocation(mLocations.size());
        }
        
        mNavViewDisolver = new Runnable() 
        {
            public void run()
            {
                if(editText.isFocused()==false)
                    hide(mapNavView);
            }
        };
    }

    public void show(View view) {
        fade(view, View.VISIBLE, 0.0f, 1.0f, 0);
        
        mHandler.removeCallbacks(mNavViewDisolver);
        mHandler.postDelayed(mNavViewDisolver, ViewConfiguration.getZoomControlsTimeout());
    }
    
    public static void hide(View view) {
        fade(view, View.GONE, 1.0f, 0.0f, 5000);
    }
    
    private static void fade(View view, int visibility, float startAlpha, float endAlpha, int startDelay) {
        AlphaAnimation anim = new AlphaAnimation(startAlpha, endAlpha);
        anim.setStartTime(AnimationUtils.currentAnimationTimeMillis() + startDelay);
        anim.setDuration(500);
        view.startAnimation(anim);
        view.setVisibility(visibility);
    }
    
    private void populateLocation(int numLocation)
    {
        ArrayList<Location> locations = mLocations;
        
        if (locations == null)
            return;
        
        if(numLocation==mOverlays.size())
            return;
        
        
        mOverlays.clear();

        
        AggregatedProximityInfo aggregatedInfo = new AggregatedProximityInfo();
        
        for(int i=0;i<numLocation;i++) {
            Location location = locations.get(i);
            
            GeoPoint geoPoint = LocationUtils.getGeoPoint(location);

            ProximityPoint proximityPoint = new ProximityPoint(geoPoint, location.getAccuracy());
            ProximityInfo proximityInfo = new ProximityInfo(proximityPoint, location.getTime());
            
            aggregatedInfo.addProximityInfo(proximityInfo);
            
            ProximityOverlay overlay = new ProximityOverlay(proximityPoint, mPointPaint, mProximityPaint);
            
            mOverlays.add(overlay);
            
            
            
        }
        
        ProximityPoint aggregatedPoint = aggregatedInfo.getAggregatedPoint();
        
        if(aggregatedPoint!=null)
            mOverlays.add(new ProximityOverlay(aggregatedPoint, mAggrPointPaint, mAggrProximityPaint));
    }
    
    private Location getCurrentLocation()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationUtils.getLowPowerProvider(locationManager);
        Location currentLocation = locationManager.getLastKnownLocation(provider);
        
        return currentLocation;
    }
    
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

}
