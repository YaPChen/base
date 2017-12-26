package com.jason.base;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.jason.base.ui.LoginActivity;
import com.jason.base.utils.AppCompatDialogUtils;
import com.jason.base.utils.ComPreferencesManager;
import com.tencent.smtt.sdk.QbSdk;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    public static MyApplication context; // 提供一个全局上下文对象
    public static SharedPreferences sp;
    public static int mNetWorkState; // 网络状态

    public Handler mHandler;
    private Toast mLongToast;
    private Toast mShortToast;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        context = this;
        sp = getSharedPreferences("Configer",Context.MODE_PRIVATE);
        // 设置debug模式
        this.mHandler = new Handler();
        this.mLongToast = Toast.makeText(this, "Test", Toast.LENGTH_LONG);
        this.mShortToast = Toast.makeText(this, "Test", Toast.LENGTH_SHORT);
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.d("app", " onViewInitFinished is " + arg0);
			}
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(),  cb);
        ComPreferencesManager.getInstance().setContext(this);
    }

    public static synchronized MyApplication getInstance() {
        return context;
    }


    public void showMessage(int resId) {
        this.showMessage(resId, 1);
    }

    public void showMessage(String msg) {
        this.showMessage(msg, 1);
    }

    public void showMessage(final String msg, final int length) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    MyApplication.this.showMessageInternal(msg, length, false);
                }
            });
        } else {
            this.showMessageInternal(msg, length, true);
        }

    }

    public void showMessage(final int resId, final int length) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    MyApplication.this.showMessageInternal(resId, length, false);
                }
            });
        } else {
            this.showMessageInternal(resId, length, true);
        }

    }

    private void showMessageInternal(String msg, int length, boolean uiThread) {
        if (length == 0) {
            this.mShortToast.setText(msg);
            this.mShortToast.show();
        } else {
            this.mLongToast.setText(msg);
            this.mLongToast.show();
        }

    }

    private void showMessageInternal(int resId, int length, boolean uiThread) {
        if (length == 0) {
            this.mShortToast.setText(resId);
            this.mShortToast.show();
        } else {
            this.mLongToast.setText(resId);
            this.mLongToast.show();
        }

    }

    public void showShortMessage(int resId) {
        this.showMessage(resId, 0);
    }

    public void showShortMessage(String message) {
        this.showMessage(message, 0);
    }

    public void postAsync(Runnable runnable) {
        this.mHandler.post(runnable);
    }

    public void postDelay(Runnable runnable, long delayMillis) {
        this.mHandler.postDelayed(runnable, delayMillis);
    }

    public void removeRunnable(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public AlertDialog onCreateNoNetworkDialog(Context context) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_no_network_title)
                .setMessage(R.string.dialog_no_network_message)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

    /**显示需要先登录提示信息*/
    public void showNeedLoginMessage(Context context,String str) {
        AppCompatDialogUtils.createSimpleConfirmAlertDialog(context, str, getString(android.R.string.ok), getString(android.R.string.cancel), new AppCompatDialogUtils.DialogCallback() {

            @Override
            public void onDismiss(DialogInterface dialog) {

            }

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        LoginActivity.startIntent(MyApplication.this, null);
                        break;
                }
            }

            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }
}
