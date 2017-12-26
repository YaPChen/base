package com.jason.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zhangcuicui on 2016/4/20.
 */
public class ComPreferencesManager {
    private static final ComPreferencesManager INSTANCE = new ComPreferencesManager();
    private android.content.Context Context;
    public SharedPreferences mFirstPreferManager;
    public SharedPreferences mPreferManager;
    public SharedPreferences mPreferVersionManager;
    public SharedPreferences mPreferServerManager;
    public static final String KEY_LATEST_VERSION = "preferences_latest_version";
    public static final String KEY_LATEST_VERSION_CODE_NAME = "preferences_latest_version_code_name";
    public static final String KEY_LATEST_VERSION_INSTALL = "preferences_latest_version_install";
    public static final String KEY_LATEST_VERSION_LEVEL = "preferences_latest_version_level";

    private ComPreferencesManager() {
    }

    public static ComPreferencesManager getInstance() {
        return INSTANCE;
    }

    public void setContext(Context context) {
        this.Context = context;
        Context var10003 = this.Context;
        this.mFirstPreferManager = context.getSharedPreferences("first_launch", 0);
        this.mPreferManager = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPreferVersionManager = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPreferServerManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isFirstLaunch(String key, boolean defaultValues) {
        return this.mFirstPreferManager.getBoolean(key, defaultValues);
    }

    public boolean setFirstLaunch(String key, boolean defaultValues) {
        return this.mFirstPreferManager.edit().putBoolean(key, defaultValues).commit();
    }

    public boolean removeFirstLaunch(String key) {
        return this.mFirstPreferManager.edit().remove(key).commit();
    }

    public void resetFirsetLaunch() {
        this.mFirstPreferManager.edit().clear().commit();
    }

}
