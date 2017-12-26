package com.jason.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.google.gson.Gson;
import com.jason.base.MyApplication;
import com.jason.base.R;
import com.jason.base.ServiceManager;
import com.jason.base.account.AccountObject;
import com.jason.base.account.MyAccountManager;
import com.jason.base.database.daoaccount.AccountDBDao;
import com.jason.base.network.AsyncTask;
import com.jason.base.network.DataProvider;
import com.jason.base.network.ServerRequest;
import com.jason.base.network.ServerResponse;

import java.util.HashMap;

/**
 * 这个类用来更新和登录账户使用。
 *
 * @author chenkai
 *
 */
public class LoginOrUpdateAccountDialog extends Activity implements AsyncTask.ServerResponseDoLinister,MyAccountManager.AccountChangeCallback{

	private static final String TAG = "LoginOrUpdateAccountDialog";
	private AccountObject mAccountObject;
	private String mTel, mPwd;
	private boolean mIsLogin = false;
	private TextView mStatusView;
	private Context mContext;
	private AccountDBDao mAccountDBDao;
	private DataProvider mDataProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_login_or_update_layout);
		mAccountDBDao=new AccountDBDao(this);
		mDataProvider=new DataProvider();
		mStatusView = (TextView) findViewById(R.id.title);
		Intent intent = getIntent();
		mIsLogin = intent.getBooleanExtra("islogin", true);
		mTel = intent.getStringExtra("tel");
		mPwd = intent.getStringExtra("password");
		loginAsync();
	}

	private void loginAsync() {
		mStatusView.setText(mIsLogin ? R.string.msg_login_dialog_title_wait
				: R.string.msg_update_dialog_title_wait);
		mDataProvider.cancel(1);
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
	public void onBackPressed() {
		super.onBackPressed();
		mDataProvider.cancel();
	}

	@Override
	public Object paseContent(String stringContent) {
		Gson gson=new Gson();
		AccountObject accountObject =gson.fromJson(stringContent,
				AccountObject.class);
		return accountObject;
	}

	@Override
	public void insertDataBase(Object object) {
		mAccountDBDao.insertData(object);
	}

	@Override
	public void onResultSuccess(ServerResponse response) {
		if (!response.isSuccessfully()) {
			MyApplication.getInstance().showMessage(response.responseMsg);
			setResult(Activity.RESULT_CANCELED);
		} else {
			if(response.serverObject!=null) {
				setResult(Activity.RESULT_OK);
			}else{
				MyApplication.getInstance().showMessage("登录成功获取的用户信息解析错误");
				setResult(Activity.RESULT_CANCELED);
			}
		}
		finish();
	}

	@Override
	public void onAccountChanged(AccountObject accountObject) {

	}

	public static Intent createLoginOrUpdate(Context context, boolean login,
											 String tel, String pwd) {
		Intent intent = new Intent(context, LoginOrUpdateAccountDialog.class);
		intent.putExtra("islogin", login);
		intent.putExtra("tel", tel);
		intent.putExtra("password", pwd);
		return intent;
	}

	public static void startActivity(Context context, boolean login,
			String tel, String pwd) {
		Intent intent = new Intent(context, LoginOrUpdateAccountDialog.class);
		intent.putExtra("islogin", login);
		intent.putExtra("tel", tel);
		intent.putExtra("password", pwd);
		context.startActivity(intent);
	}
}
