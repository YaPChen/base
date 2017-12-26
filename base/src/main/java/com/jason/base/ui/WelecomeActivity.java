package com.jason.base.ui;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.jason.base.ServiceManager;
import com.jason.base.account.MyAccountManager;
import com.jason.base.bean.AppVersionObject;
import com.jason.base.network.AsyncTask;
import com.jason.base.network.DataProvider;
import com.jason.base.network.ServerRequest;
import com.jason.base.network.ServerResponse;

import java.util.HashMap;


public class WelecomeActivity extends Activity  implements AsyncTask.ServerResponseDoLinister{
	private static final String TAG=WelecomeActivity.class.getSimpleName();
	private DataProvider mDataProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mDataProvider=new DataProvider();
		getVersion();
	}

	private void getVersion(){
		ServerRequest sr = new ServerRequest();
		sr.url= ServiceManager.getLoginUrl();
		sr.modth=ServerRequest.MODTH_GET;
		HashMap<String ,String> params=new HashMap<String ,String>();
		params.put("username","");
		params.put("password","");
		sr.mapParam=params;
		sr.requestId=1;
		mDataProvider.getServerRequest(sr,this);
	}

	@Override
	public Object paseContent(String stringContent) {
		Gson gson=new Gson();
		AppVersionObject mAppVersionObject=gson.fromJson(stringContent,AppVersionObject.class);
		return mAppVersionObject;
	}

	@Override
	public void insertDataBase(Object object) {
		if(object!=null&&object instanceof AppVersionObject){
			((AppVersionObject)object).save();
		}
	}

	@Override
	public void onResultSuccess(ServerResponse response) {
		if(MyAccountManager.getInstance().hasLoginned()){
			MainActivity.startActivityForTop(WelecomeActivity.this);
		}else{
			LoginActivity.startIntent(WelecomeActivity.this,null);
		}
	}
}
