package com.jason.base.account;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;


import com.jason.base.MyApplication;
import com.jason.base.database.daoaccount.AccountDBDao;
import com.jason.base.utils.DebugUtils;

import java.util.LinkedList;
import java.util.List;

public class MyAccountManager {
	private static final String TAG = "MyAccountManager";
	private AccountObject mAccount;
	private Context mContext;
	SharedPreferences mSharedPreferences;
	private AccountDBDao mAccountDBDao;
	private static MyAccountManager mInstance;

    public static interface AccountChangeCallback {
        public void onAccountChanged(AccountObject accountObject);
    }

    private List<AccountChangeCallback> mAccountChangeCallbackList = new LinkedList<AccountChangeCallback>();
	
	private MyAccountManager() {}
	
	public static MyAccountManager getInstance() {
		if (mInstance == null) { // 首先判断是否已经创建实例，如果已经创建，直接返回，效率高
			synchronized (MyAccountManager.class) {// 如果没有创建，然后再同步，并在同步块内再初始化。注意要再次判断是否已实例化
				if (mInstance == null) {
					mInstance = new MyAccountManager();
					DebugUtils.logD(TAG, "getInstance newInstance " + mInstance);
				}
			}
		}
		return mInstance;
	}
	
	public synchronized void setContext(Context context) {
		mContext = context;
		mAccountDBDao=new AccountDBDao(context);
		if (mContext == null) {
			throw new RuntimeException("MyAccountManager.setContext(null), you must apply a Context object.");
		}
		mAccount = null;
		mSharedPreferences = mContext.getSharedPreferences(TAG, Context.MODE_PRIVATE);
		initAccountObject();
	}

    public synchronized void addAccountChangeCallback(AccountChangeCallback callback) {
        if (!mAccountChangeCallbackList.contains(callback)) {
            mAccountChangeCallbackList.add(callback);
        }
    }

    public synchronized void removeAccountChangeCallback(AccountChangeCallback callback) {
        if (mAccountChangeCallbackList.contains(callback)) {
            mAccountChangeCallbackList.add(callback);
        }
    }

    public synchronized void notifyAccountChange(final AccountObject accountObject) {

        MyApplication.getInstance().postAsync(new Runnable() {

            @Override
            public void run() {
                for(AccountChangeCallback callback : mAccountChangeCallbackList) {
                    callback.onAccountChanged(accountObject);
                }
            }
        });

    }
	
	public synchronized void initAccountObject() {
		//如果没有默认账户，我们使用Demo账户
		if (mAccount == null) {
			mAccount = mAccountDBDao.queryDefualAccount();
			if(mAccount!=null){
				notifyAccountChange(mAccount);
			}
		}
	}
	

	/**
	 * 删除指定uid的账户
	 * @param uid
	 */
	public void deleteAccountForUid(long uid) {
		mAccountDBDao.deleteData("userid = "+uid);
	}
	
	public synchronized AccountObject getAccountObject() {
		return mAccount;
	}

	public synchronized long getDefaultPhoneNumber() {
		return mAccount != null ? mAccount.getTel() : null;
	}
	
	public synchronized String getCurrentAccountUid() {
		return getCurrentAccountUid();
	}
	
	public synchronized String getCurrentAccountId() {
		return mAccount != null ? mAccount.getUserId() : null;
	}
	/**默认情况即使用户没有登录，系统会初始化一个演示账户，一旦用户登陆了，就会将演示账户删掉*/
	public synchronized boolean hasLoginned() {
		return mAccount != null && mAccount.getDbid() > 0;
	}

	/**
	 * 返回上一次登陆时候使用的用户名
	 * @return
	 */
	public synchronized String getLastUsrTel() {
		return mSharedPreferences.getString("lastUserTel", "");
	}
	
    public synchronized void saveLastUsrTel(String userName) {
    	mSharedPreferences.edit().putString("lastUserTel", (userName == null ? "" : userName)).commit();
	}
    
    /**
     * 更新账户，每当我们增删家和保修卡数据的时候，调用该方法可以同步当前账户信息.
     */
    public synchronized void updateAccount() {
		mAccount = null;
    	initAccountObject();
    }

}
