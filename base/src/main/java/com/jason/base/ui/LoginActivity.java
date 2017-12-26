package com.jason.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jason.base.MyApplication;
import com.jason.base.R;
import com.jason.base.account.AccountObject;
import com.jason.base.account.MyAccountManager;
import com.jason.base.utils.ComConnectivityManager;
import com.jason.base.utils.DebugUtils;

/**
 * Created by zhangcuicui on 2016/4/18.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
	private static final String TAG = "LoginActivity";

	private TextView mRegisterButton, mFindPwdBuuton;
	private static final int REQUEST_LOGIN = 1;
	private Button mLoginBtn;
	private EditText mTelInput, mPasswordInput;
	public static AccountObject mAccountObject;
	private Bundle mBundles;
    private Context mContext;


	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DebugUtils.logD(TAG, "onCreate()");
		if (isFinishing()) {
			return ;
		}
		mContext=this;
		mBundles = getIntent().getExtras();
		setContentView(R.layout.activity_login);
		initViews();
	}
	
	public void onResume() {
		super.onResume();
		//每次进来我们都要先清空一下mAccountObject，这个值作为静态变量在各个Activity中传递
		mAccountObject = null;
	}
	
	
	private void initViews() {
		mRegisterButton = (TextView) findViewById(R.id.button_register);
		mRegisterButton.setOnClickListener(this);

		mFindPwdBuuton = (TextView) findViewById(R.id.button_find_password);
		mFindPwdBuuton.setOnClickListener(this);

		mLoginBtn = (Button) findViewById(R.id.button_login);
		mLoginBtn.setOnClickListener(this);

		mTelInput = (EditText) findViewById(R.id.tel);
		//显示上一次输入的用户号码
		mTelInput.setText(MyAccountManager.getInstance().getLastUsrTel());
		if (mBundles != null) {
			String tel = mBundles.getString("tel");
			if (!TextUtils.isEmpty(tel)) {
				mTelInput.setText(tel);
			}
		}

		mPasswordInput = (EditText) findViewById(R.id.pwd);
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
		 return false;
	 }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.button_register:
//				SettingActivity.startActivity(this,null);
				break;
			case R.id.button_find_password:
				//如果电话号码为空，提示用户先输入号码，在找回密码
				String tell = mTelInput.getText().toString().trim().replaceAll("[- +]", "");
				Bundle bundle=new Bundle();
				bundle.putString("tel",tell);
//                UpdatePasswordActivity.startActivity(this,bundle);
				break;
			case R.id.button_login:
				if (!ComConnectivityManager.getInstance().isConnected()) {
					//没有联网，这里提示用户
					MyApplication.getInstance().onCreateNoNetworkDialog(mContext).show();
					return;
				}
				String tel = mTelInput.getText().toString().trim().replaceAll("[- +]", "");
				String pwd = mPasswordInput.getText().toString().trim();
				if (TextUtils.isEmpty(tel)) {
					MyApplication.getInstance().showMessage(R.string.msg_input_usrtel_invalid);
					return;
				}
				if (TextUtils.isEmpty(pwd)) {
					MyApplication.getInstance().showMessage(R.string.msg_input_usrpwd_invalid);
					return;
				}
				MyAccountManager.getInstance().saveLastUsrTel(tel);
				startActivityForResult(LoginOrUpdateAccountDialog.createLoginOrUpdate(this, true, tel, pwd), REQUEST_LOGIN);
				break;
		}
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_LOGIN) {
			if (resultCode == RESULT_OK) {
				MainActivity.startActivityForTop(mContext);
				finish();
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	
	
	public static void startIntent(Context context, Bundle modelBundle) {
		Intent intent = new Intent(context, LoginActivity.class);
		if (context instanceof Activity) {
			
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (modelBundle == null) {
			modelBundle = new Bundle();
		}
		intent.putExtras(modelBundle);
		context.startActivity(intent);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
