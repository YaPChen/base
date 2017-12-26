package com.jason.base.utils;

import android.content.ContentResolver;
import android.content.ContentValues;

/**
 *
 */
public interface InfoInterface {

	public boolean saveInDatebase(ContentResolver cr, ContentValues addtion);
	
}
