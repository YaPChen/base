package com.jason.base.utils;


import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

import static android.R.attr.targetSdkVersion;


public class PermissionUtils {

	public boolean selfPermissionGranted(Context mContext,String permission) {
		boolean result = true;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (targetSdkVersion >= Build.VERSION_CODES.M) {
				result = mContext.checkSelfPermission(permission)
						== PackageManager.PERMISSION_GRANTED;
			} else {
				result = PermissionChecker.checkSelfPermission(mContext, permission)
						== PermissionChecker.PERMISSION_GRANTED;
			}
		}
		return result;
	}
}
