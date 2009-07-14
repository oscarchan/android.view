package android.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class EventLogViewActivity extends ListActivity implements LocationListener
{
	private final static int MIN_DISTANCE = 25;
	
	private final static int SHOW_ADDRESS = 0;
	private final static int LOCATE_MAP = 1;
	private final static int DELETE_LOC = 2;

	private List<LocationView> mLocationList;
	private ArrayAdapter<LocationView> mAdapter;
	private Geocoder mGeocoder;

	private int mListSelectedItemPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		List<LocationView> locationList = new ArrayList<LocationView>();

		ArrayAdapter<LocationView> adapter = new ArrayAdapter<LocationView>(this, android.R.layout.simple_list_item_1, locationList);

		setListAdapter(adapter);

		getListView().setTextFilterEnabled(true);

		mLocationList = locationList;
		mAdapter = adapter;
		mGeocoder = new Geocoder(this);

		registerForContextMenu(getListView());

		// haven't figured out how to get it working
		// ListView listView = (ListView) findViewById(R.id.event_log_view);
		//
		// mLocationAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, locationList);
		// listView.setAdapter(mLocationAdapter); 
		//
		// setContentView(R.layout.event_log);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		mListSelectedItemPosition = position;

		showDialog(SHOW_ADDRESS);
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
			case SHOW_ADDRESS:
			{
				Dialog dialog = new Dialog(this);

				dialog.setContentView(R.layout.location_info);
				dialog.setTitle("Location Info");

				return dialog;
			}
			case DELETE_LOC:
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Delete Location Info").setPositiveButton("Delete", new OnClickListener()
				{
					final int position = mListSelectedItemPosition;

					public void onClick(DialogInterface dialog, int which)
					{
						if (position < mLocationList.size()) {
							mLocationList.remove(position);
							Toast.makeText(EventLogViewActivity.this, "Deleted location position " + position, Toast.LENGTH_SHORT);
						} else {
							Toast.makeText(EventLogViewActivity.this, "Unable to delete location position " + position, Toast.LENGTH_SHORT);
						}
					}
				}).setNegativeButton("Cancel", new OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
			}
		}

		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog)
	{
		super.onPrepareDialog(id, dialog);

		switch (id)
		{
			case SHOW_ADDRESS:
			{
				int position = mListSelectedItemPosition;

				LocationView location = null;
				if (0 <= position && position < mLocationList.size()) {
					location = mLocationList.get(position);
				}

				String locationAddress = "invalid address";
				String locationInfo = "invalid info at position " + position;

				if (location != null) {
					locationInfo = "position " + position + ": " + location;
					locationAddress = getGeoAddress(location.getLocation());

				}

				TextView text = (TextView) dialog.findViewById(R.id.location_info_address);
				text.setText(locationAddress);

				TextView locationText = (TextView) dialog.findViewById(R.id.location_info_loc);
				locationText.setText(locationInfo);
				break;
			}
			case DELETE_LOC:
			{
				AlertDialog alertDialog = (AlertDialog) dialog;

				final int position = mListSelectedItemPosition;

				alertDialog.setMessage("location " + position + " to be deleted?");
			}
		}
	}

	private String getGeoAddress(Location location)
	{
		String reverseAddress = "address not found";

		try {
			List<Address> addresses = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

			if (addresses.isEmpty() == false) {
				Address address = addresses.get(0);

				StringBuffer sb = new StringBuffer();

				for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
					sb.append(address.getAddressLine(i)).append("\n");

				sb.append(address.getLocality()).append("\n").append(address.getPostalCode()).append("\n").append(address.getCountryName());

				reverseAddress = sb.toString();
			}

		} catch (IOException e) {
			// ignore
			Toast.makeText(EventLogViewActivity.this, "Unable to get address: " + e, Toast.LENGTH_SHORT);
		}

		return reverseAddress;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.event_log_menu, menu);

		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.add(0, LOCATE_MAP, 0, "Locate");
		menu.add(0, SHOW_ADDRESS, 0, "Show Address");
		menu.add(0, DELETE_LOC, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();

		mListSelectedItemPosition = menuInfo.position;

		switch (item.getItemId())
		{
			case SHOW_ADDRESS:
			{
				showDialog(SHOW_ADDRESS);
				break;
			}
			case LOCATE_MAP:
			{
              Intent intent = new Intent(EventLogViewActivity.this, ViewSelectorActivity.class);
              
              ArrayList<Location> locationList = new ArrayList<Location>();
              for (LocationView location : mLocationList)
              {
                locationList.add(location.mLocation);
              }

              // very interesting that it picked a specific List implementation
              intent.putParcelableArrayListExtra(ViewActivityConstants.INTENT_LOCATIONS, locationList);
              startActivity(intent);
              
				break;
			}
			case DELETE_LOC:
			{
				showDialog(DELETE_LOC);
				break;

			}
		}
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{

		if (item.getItemId() == R.id.record_loc) {
			LocationManager locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria = new Criteria();
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			criteria.setCostAllowed(false);

			String providerName = locationMgr.getBestProvider(criteria, true);
			Toast.makeText(this, "recording with provider: " + providerName, Toast.LENGTH_SHORT).show();

			if (providerName != null) {
				locationMgr.requestLocationUpdates(providerName, 1000, 0, this);

				Location location = locationMgr.getLastKnownLocation(providerName);

				mLocationList.add(new LocationView(location));

				mAdapter.notifyDataSetChanged();
			}
		} else if (item.getItemId() == R.id.track_loc) {
			LocationManager locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

			Criteria criteria = new Criteria();
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			criteria.setCostAllowed(false);

			String providerName = locationMgr.getBestProvider(criteria, true);
			Toast.makeText(this, "recording with provider: " + providerName, Toast.LENGTH_SHORT).show();

			if (providerName != null) {
				locationMgr.requestLocationUpdates(providerName, -1, 25, this);
			}
		} else if (item.getItemId() == R.id.cleare_all_loc) {
			mLocationList.clear();

			mAdapter.notifyDataSetChanged();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	static class LocationView
	{
		private static final String template = "%1$tF %1$tT (%2$f %3$f)";

		private Location mLocation;

		public LocationView(Location location)
		{
			if (location == null)
				throw new IllegalArgumentException("missing location");

			mLocation = location;
		}

		public Location getLocation()
		{
			return mLocation;
		}

		@Override
		public String toString()
		{
			return String.format(template, mLocation.getTime(), mLocation.getLatitude(), mLocation.getLongitude());
		}

	}

	public void onLocationChanged(Location location)
	{
		LocationManager locationMgr = (LocationManager) getSystemService(LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setCostAllowed(false);

		String providerName = locationMgr.getBestProvider(criteria, true);
		Toast.makeText(this, "recording with provider: " + providerName, Toast.LENGTH_SHORT).show();

		if (providerName != null) {
			locationMgr.requestLocationUpdates(providerName, 0, MIN_DISTANCE, this);
		}

		mLocationList.add(new LocationView(location));
		mAdapter.notifyDataSetChanged();
	}

	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub

	}

}