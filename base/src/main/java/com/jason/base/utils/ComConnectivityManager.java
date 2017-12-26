package com.jason.base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.LinkedList;
import java.util.List;

public class ComConnectivityManager 
{
	  private static final String TAG = "ConnectivityManager";
	  private static final ComConnectivityManager INSTANCE = new ComConnectivityManager();
	  private Context mContext;
	  private ConnectivityManager mCm;
	  private List<ConnCallback> mConnCallbackList = new LinkedList();

	  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
	  {
	    public void onReceive(Context context, Intent intent)
	    {
	      if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
	        NetworkInfo mobileInfo = ComConnectivityManager.this.mCm.getNetworkInfo(0);
	        NetworkInfo wifiInfo = ComConnectivityManager.this.mCm.getNetworkInfo(1);
	        NetworkInfo activeInfo = ComConnectivityManager.this.mCm.getActiveNetworkInfo();
	        DebugUtils.logD("ConnectivityManager", new StringBuilder().append("mobile:").append(mobileInfo != null ? Boolean.valueOf(mobileInfo.isConnected()) : "unsupport").append("\n").append("wifi:").append(wifiInfo != null ? Boolean.valueOf(wifiInfo.isConnected()) : "unsupport").append("\n").append("active:").append(activeInfo != null ? activeInfo.getTypeName() : "none").toString());

	        synchronized (ComConnectivityManager.this.mConnCallbackList) {
	          for (ConnCallback callback : ComConnectivityManager.this.mConnCallbackList)
	            callback.onConnChanged(ComConnectivityManager.INSTANCE);
	        }
	      }
	    }
	  };

	  public void setContext(Context context)
	  {
	    if (this.mContext == null) {
	      this.mContext = context;
	      this.mCm = ((ConnectivityManager)context.getSystemService("connectivity"));
	      startConnectivityMonitor();
	    }
	  }

	  public static ComConnectivityManager getInstance() {
	    return INSTANCE;
	  }

	  public void addConnCallback(ConnCallback callback) {
	    if (this.mContext == null) {
	      throw new RuntimeException("You must call ComConnectivityManager.getInstance().setContext() in Application.onCreate()");
	    }
	    synchronized (this.mConnCallbackList) {
	      if (!this.mConnCallbackList.contains(callback))
	        this.mConnCallbackList.add(callback);
	    }
	  }

	  public void removeConnCallback(ConnCallback callback)
	  {
	    synchronized (this.mConnCallbackList) {
	      if (this.mConnCallbackList.contains(callback))
	        this.mConnCallbackList.remove(callback);
	    }
	  }

	  public boolean isWifiConnected()
	  {
	    NetworkInfo activeInfo = this.mCm.getActiveNetworkInfo();
	    return (activeInfo != null) && (activeInfo.isConnected()) && (activeInfo.getType() == 1);
	  }

	  public boolean isMobileConnected() {
	    NetworkInfo activeInfo = this.mCm.getActiveNetworkInfo();
	    return (activeInfo != null) && (activeInfo.isConnected()) && (activeInfo.getType() == 0);
	  }

	  public boolean isConnected() {
	    NetworkInfo activeInfo = this.mCm.getActiveNetworkInfo();
	    return (activeInfo != null) && (activeInfo.isConnected());
	  }

	  public void startConnectivityMonitor() {
	    IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
	    this.mContext.registerReceiver(this.mBroadcastReceiver, filter);
	  }
	  public void endConnectivityMonitor() {
	    if (this.mContext != null)
	      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
	  }

	  public static abstract interface ConnCallback
	  {
	    public abstract void onConnChanged(ComConnectivityManager paramComConnectivityManager);
	  }
}