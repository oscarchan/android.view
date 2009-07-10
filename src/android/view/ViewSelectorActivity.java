package android.view;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ViewSelectorActivity extends ListActivity
{
	private static final int INVISIBLE_ID = 0;
	private static final int GONE_ID = 1;
	
	private static final List<Integer> MENU_ITEM_TITLE_IDS
	  = Arrays.asList(new Integer[] {R.string.mm_item1, R.string.mm_item2, R.string.mm_item3, R.string.mm_item4});

	private int mMainMenuItemCount = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		List<String> fieldNameList = getPublicFieldNames(R.layout.class);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fieldNameList);

		setListAdapter(adapter);

		getListView().setTextFilterEnabled(true);
		
		registerForContextMenu(getListView());
		// setContentView(R.layout.main_menu);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);

		String item = (String) l.getSelectedItem();

		Integer layoutValue = getPublicFieldValue(R.layout.class, item);
		
		if(layoutValue!=null) { 
			
			Toast.makeText(this, "selected: " + item + ": value=" + layoutValue, Toast.LENGTH_SHORT);
			
			Intent intent = new Intent(this, ViewActivity.class);
			intent.putExtra(ViewActivity.LAYOUT_ID, layoutValue);
			startActivity(intent);
		} else {
			Toast.makeText(this, "bug: selected unrecognized item: " + item, Toast.LENGTH_SHORT);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.simple_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		
		int delta;
		do {
			int nextItemIndex = menu.size() - 2;
			int lastItemIndex = nextItemIndex - 1; 
			delta = mMainMenuItemCount - nextItemIndex;
			
			if(delta > 0)
				menu.add(0, nextItemIndex, 0, MENU_ITEM_TITLE_IDS.get(nextItemIndex));
			else if (delta < 0)
				menu.removeItem(lastItemIndex);

			delta = mMainMenuItemCount - nextItemIndex;
		} while (delta !=0);
		
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.mm_add_id:
			{
				if(mMainMenuItemCount<6) {
					mMainMenuItemCount++;
					String msg = getResources().getString(R.string.mm_item_added, mMainMenuItemCount);
				
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.mm_item_max, Toast.LENGTH_LONG).show();
				}
				break;
			}
			case R.id.mm_remove_id:
			{
				if(mMainMenuItemCount>0) {
					mMainMenuItemCount--;
				
					String msg = getResources().getString(R.string.mm_item_removed, mMainMenuItemCount + 1);
				
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, R.string.mm_item_min, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	
		// TODO how to refresh menu list??
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		
		menu.add(0, INVISIBLE_ID, 0, "Hide");
		menu.add(0, GONE_ID, 0, "Delete");
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo  menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		long id = menuInfo.id;
		
		switch(item.getItemId())
		{
			case INVISIBLE_ID:
			{
				getListView().getSelectedView().setVisibility(View.INVISIBLE);
				
				break;
			}
			case GONE_ID:
			{
				getListView().getSelectedView().setVisibility(View.GONE);
				break;

				
			}
		}
		return true;
	}

	public static List<String> getPublicFieldNames(Object target)
	{
		Class<?> clazz;
		if (target instanceof Class)
			clazz = (Class<?>) target;
		else
			clazz = target.getClass();

		List<String> nameList = new ArrayList<String>();

		for (Field field : clazz.getFields()) {
			if (Modifier.isPublic(field.getModifiers()))
				nameList.add(field.getName());
		}

		return nameList;
	}
	
	public static Integer getPublicFieldValue(Object target, String fieldName)
	{
		Class<?> clazz;
		if (target instanceof Class)
			clazz = (Class<?>) target;
		else
			clazz = target.getClass();

		Map<String, Integer> nameList = new HashMap<String, Integer>();

		for (Field field : clazz.getFields()) {
			if (Modifier.isPublic(field.getModifiers()))
				try {
					nameList.put(field.getName(), field.getInt(target));
				} catch (IllegalArgumentException e) {
					// ignore
				} catch (IllegalAccessException e) {
					// ignore
				}
		}

		return nameList.get(fieldName);
	}
	
	
}