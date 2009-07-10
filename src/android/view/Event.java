package android.view;

import java.util.Date;

import android.net.Uri;

public class Event
{
	private String mAction;
	private String mCategory;
	private Uri mData;
	
	private Date mCreatedAt;
	
	public Event(String action, String category, Uri data, Date createdAt)
	{
		mAction = action;
		mCategory = category;
		mData = data;
		mCreatedAt = createdAt;
	}

	public String getAction()
	{
		return mAction;
	}

	public String getCategory()
	{
		return mCategory;
	}

	public Uri getData()
	{
		return mData;
	}

	public Date getCreatedAt()
	{
		return mCreatedAt;
	}
	
	@Override
	public String toString()
	{
		return "Event[" + mAction + ": " + mCategory + ": " + mData+ "]";
	}
}
