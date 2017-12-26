package com.jason.base.network;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.jason.base.utils.AsyncTaskUtils;

import java.util.HashMap;

/**
 * 请求数据提供类
 * 
 * @author Administrator
 * 
 */
public class DataProvider{
	private static final String TAG = DataProvider.class.getSimpleName();
	private static final boolean DBG = true;
	private static DataProvider mMe;
	public HashMap<String, AsyncTask> queue = new HashMap<String, AsyncTask>();

	public static synchronized DataProvider getInstance(Context ct) {
		if (mMe == null) {
			if (DBG)
				Log.d(TAG, "create DataProvider instance.");
			mMe = new DataProvider();
		}
		return mMe;
	}

	public boolean getServerRequest(ServerRequest request,AsyncTask.ServerResponseDoLinister listener,ProgressBar progressBar) {
		if(null!=queue.get(String.valueOf(request.requestId))){
			AsyncTask asyncTask=new AsyncTask(listener,progressBar);
			queue.put(String.valueOf(request.requestId),asyncTask);
			asyncTask.execute(request);
			return true;
		}else{
			return false;
		}
	}
	public boolean getServerRequest(ServerRequest request,AsyncTask.ServerResponseDoLinister listener) {
		if(null!=queue.get(String.valueOf(request.requestId))){
			AsyncTask asyncTask=new AsyncTask(listener,null);
			queue.put(String.valueOf(request.requestId),asyncTask);
			asyncTask.execute(request);
			return true;
		}else{
			return false;
		}
	}
	public void cancel(int requestId){
		AsyncTask asyncTask =queue.get(String.valueOf(requestId));
		if(asyncTask!=null) {
			asyncTask.removeLinister();
			AsyncTaskUtils.cancelTask(asyncTask);
		}
	}
	public void cancel(){
		for (String key : queue.keySet()) {
			AsyncTask asyncTask =queue.get(String.valueOf(queue.get(key)));
			if(asyncTask!=null) {
				asyncTask.removeLinister();
				AsyncTaskUtils.cancelTask(asyncTask);
			}
		}
	}
}
