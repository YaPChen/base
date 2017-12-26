package com.jason.base.bean;

import android.content.Context;
import android.text.TextUtils;

import com.jason.base.utils.ComPreferencesManager;
import com.jason.base.utils.CommTools;

import java.util.List;
import java.util.Set;

/**
 * Created by chenyp on 2017/12/25.
 */

public class AppVersionObject {
    public static final String defualVersionName = "1.0.0";
    public static final int defualVersionCode = 1;

    private int versionCode;
    private String versionName;
    private String updateUrl;
    private String bugInfo;
    private Set<String> bugInfos;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getBugInfo() {
        return bugInfo;
    }

    public void setBugInfo(String bugInfo) {
        this.bugInfo = bugInfo;
    }

    public Set<String> getBugInfos() {
        return bugInfos;
    }

    public void setBugInfos(Set<String> bugInfos) {
        this.bugInfos = bugInfos;
    }



    public void save() {
        ComPreferencesManager.getInstance().mPreferVersionManager.edit().putInt("versionCode", versionCode).commit();
        ComPreferencesManager.getInstance().mPreferVersionManager.edit().putString("versionName", versionName).commit();
        ComPreferencesManager.getInstance().mPreferVersionManager.edit().putString("updateUrl", updateUrl).commit();
        ComPreferencesManager.getInstance().mPreferVersionManager.edit().putString("bugInfo", bugInfo).commit();
        ComPreferencesManager.getInstance().mPreferVersionManager.edit().putStringSet("bugInfos", bugInfos).commit();
    }


    public static AppVersionObject getVersionInfo(){
        AppVersionObject vo=new AppVersionObject();
        vo.setVersionCode(ComPreferencesManager.getInstance().mPreferVersionManager.getInt("versionCode",defualVersionCode));
        vo.setVersionName(ComPreferencesManager.getInstance().mPreferVersionManager.getString("versionName", defualVersionName));
        vo.setUpdateUrl(ComPreferencesManager.getInstance().mPreferVersionManager.getString("updateUrl", ""));
        vo.setBugInfo(ComPreferencesManager.getInstance().mPreferVersionManager.getString("bugInfo", ""));
        vo.setBugInfos(ComPreferencesManager.getInstance().mPreferVersionManager.getStringSet("bugInfos",null));
        return vo;
    }

    public static int getNewVersionCode(){
        return ComPreferencesManager.getInstance().mPreferVersionManager.getInt("versionCode", defualVersionCode);
    }
    public static int getNewVersionName(){
        return ComPreferencesManager.getInstance().mPreferVersionManager.getInt("versionName", defualVersionCode);
    }

    public boolean isYiAZNewVersion(Context context){
        String oldVerCode = CommTools.getAppVersion(context);
        if(!TextUtils.isEmpty(oldVerCode)){
            if(oldVerCode.equals(versionCode)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
