package com.jason.base.utils;

import android.os.AsyncTask;

public class AsyncTaskUtils {

	public static void cancelTask(AsyncTask<?, ?, ?> task) {
		if(task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
			task.cancel(true);
		}

	}

	public static void cancelTask(AsyncTaskCompat<?, ?, ?> task) {
		if(task != null && task.getStatus() != AsyncTaskCompat.Status.FINISHED) {
			task.cancel(true);
		}

	}

	public static AsyncTaskCompat commitTask(Runnable runnable) {
		AsyncTaskCompat asyncTaskCompat = new AsyncTaskCompat() {
			protected Object doInBackground(Object[] params) {
				return null;
			}
		};
		return asyncTaskCompat;
	}
}
