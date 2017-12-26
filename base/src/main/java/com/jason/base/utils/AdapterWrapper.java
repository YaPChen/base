package com.jason.base.utils;

import android.database.Cursor;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;

import java.util.List;

public class AdapterWrapper <T>
{
	  private static final String TAG = "AdapterWrapper";
	  private T mAdapter;

	  public AdapterWrapper(T adpapter)
	  {
	    this.mAdapter = adpapter;
	  }

	  public T getAdapter() {
	    return this.mAdapter;
	  }

	  public Cursor getCursor() {
	    if ((this.mAdapter instanceof CursorAdapter)) {
	      return ((CursorAdapter)this.mAdapter).getCursor();
	    }
	    return null;
	  }

	  public void changeCursor(Cursor cursor) {
	    if (this.mAdapter == null) {
	      DebugUtils.logE("AdapterWrapper", "changeCursor failed, mAdapter is null");
	      return;
	    }
	    if ((this.mAdapter instanceof CursorAdapter))
	      ((CursorAdapter)this.mAdapter).changeCursor(cursor);
	  }

	  public void notifyDataSetChanged()
	  {
	    if ((this.mAdapter instanceof CursorAdapter))
	      ((CursorAdapter)this.mAdapter).notifyDataSetChanged();
	    else if ((this.mAdapter instanceof BaseAdapter))
	      ((BaseAdapter)this.mAdapter).notifyDataSetChanged();
	  }

	  public void releaseAdapter()
	  {
	    if (this.mAdapter == null) return;
	    if ((this.mAdapter instanceof CursorAdapter)) {
	      ((CursorAdapter)this.mAdapter).changeCursor(null);
	    }

	    this.mAdapter = null;
	  }

	  public int getCount() {
	    if ((this.mAdapter instanceof CursorAdapter))
	      return ((CursorAdapter)this.mAdapter).getCount();
	    if ((this.mAdapter instanceof BaseAdapter)) {
	      ((BaseAdapter)this.mAdapter).getCount();
	    }
	    return 0;
	  }
}