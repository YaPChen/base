package com.jason.base;


import com.jason.base.utils.UrlEncodeStringBuilder;

public class ServiceManager {
    private static final String TAG = "ServiceManager";
    private static String BASE_SERVER_URL="http://jtgm.lanex.cn/";
    private static ServiceManager Instance;
    private ServiceManager(){}
    public static ServiceManager getInstance(){
        if(Instance==null){
            Instance =new ServiceManager();
        }
        return Instance;
    }



    public static String buildPageQuery(String url, int pageIndex, int pageSize) {
        StringBuilder sb = new StringBuilder(url).append('?').append('&');
        sb.append("pageindex=").append(pageIndex).append('&');
        sb.append("pagesize=").append(pageSize);
        return sb.toString();
    }

    public static String getVersionUrl() {
        StringBuilder sb = new StringBuilder(BASE_SERVER_URL);
        sb.append("version");
        return sb.toString();
    }


    public static String getLoginUrl() {
        StringBuilder sb = new StringBuilder(BASE_SERVER_URL);
        sb.append("login");
        return sb.toString();
    }
}
