package com.jason.base.network;

import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.jason.base.utils.AsyncTaskCompat;
import com.jason.base.utils.NetworkConnectUtils;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenyp on 2017/12/22.
 */

public class AsyncTask extends AsyncTaskCompat<ServerRequest, Integer, ServerResponse> {
    private InputStream inputStream;
    private ProgressBar progressBar;

    public AsyncTask(ServerResponseDoLinister linister, ProgressBar progressBar){
        if(progressBar!=null) {
            this.progressBar = progressBar;
        }
        mServerResponseDoLinisterList.clear();
        if(linister!=null){
            mServerResponseDoLinisterList.add(linister);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(null!=progressBar){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(null!=progressBar){
            progressBar.setProgress(values[0]);//设置进度值
        }
    }

    @Override
    protected ServerResponse doInBackground(ServerRequest... params) {
        ServerResponse response = new ServerResponse();
        if (params == null || params.length == 0) {
            response.success = false;
            response.responseMsg = "请求错误";
            return response;
        } else {
            ServerRequest request = params[0];
            if (null != request.url) {
                if (request.modth == ServerRequest.MODTH_GET) {
                    if (request.jsonParam != null) {
                        try {
                            inputStream = NetworkConnectUtils.openContectionLockedGet(request.url, "param", request.jsonParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.requestId = request.requestId;
                            response.success = false;
                            response.responseMsg = "访问网络异常";
                        } finally {
                            NetworkConnectUtils.closeInputStream(inputStream);
                        }
                    } else if (request.mapParam != null) {
                        try {
                            inputStream = NetworkConnectUtils.openContectionLockedGet(request.url, request.mapParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.requestId = request.requestId;
                            response.success = false;
                            response.responseMsg = "访问网络异常";
                        } finally {
                            NetworkConnectUtils.closeInputStream(inputStream);
                        }
                    } else {
                        try {
                            inputStream = NetworkConnectUtils.openContectionLockedGet(request.url);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.requestId = request.requestId;
                            response.success = false;
                            response.responseMsg = "访问网络异常";
                        } finally {
                            NetworkConnectUtils.closeInputStream(inputStream);
                        }
                    }
                } else if (request.modth == ServerRequest.MODTH_POST) {
                    if (request.jsonParam != null) {
                        try {
                            inputStream = NetworkConnectUtils.openContectionLockedPost(request.url, request.jsonParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.requestId = request.requestId;
                            response.success = false;
                            response.responseMsg = "访问网络异常";
                        } finally {
                            NetworkConnectUtils.closeInputStream(inputStream);
                        }
                    } else if (request.mapParam != null) {
                        try {
                            inputStream = NetworkConnectUtils.openContectionLockedPost(request.url, request.mapParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.requestId = request.requestId;
                            response.success = false;
                            response.responseMsg = "访问网络异常";
                        } finally {
                            NetworkConnectUtils.closeInputStream(inputStream);
                        }
                    }
                } else if (request.modth == ServerRequest.MODTH_FILE) {
                    try {
                        inputStream = NetworkConnectUtils.openContectionUpLoadFiles(request.url, request.mapParam, request.fileParam);
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.requestId = request.requestId;
                        response.success = false;
                        response.responseMsg = "附件上传：访问网络异常";
                    } finally {
                        NetworkConnectUtils.closeInputStream(inputStream);
                    }
                } else {
                    response.requestId = request.requestId;
                    response.success = false;
                    response.responseMsg = "请指定访问方式GET或POST，eg:request.modth=ServerRequest.MODTH_GET";
                }

                if (inputStream != null) {
                    String resultContent = NetworkConnectUtils.getContentFromInput(inputStream);
                    if (!TextUtils.isEmpty(resultContent)) {
                        response.requestId = request.requestId;
                        response.success = false;
                        response.responseMsg = "网络中断";
                    } else {
                        response.serverRetrunString = resultContent;
                        for (ServerResponseDoLinister linister : mServerResponseDoLinisterList) {

                            Object object = null;
                            try{
                                object = linister.paseContent(resultContent);
                                response.requestId = request.requestId;
                                response.success = true;
                                response.responseMsg = "访问成功";
                                response.serverObject = object;
                            }catch (Exception e){
                                e.printStackTrace();
                                response.requestId = request.requestId;
                                response.success = false;
                                response.responseMsg = "访问成功,数据解析失败";
                            }
                            if (object != null) {
                                try{
                                    linister.insertDataBase(object);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    response.requestId = request.requestId;
                                    response.success = true;
                                    response.responseMsg = "访问成功，数据缓存数据库失败";
                                }
                            }
                        }
                    }
                }
                NetworkConnectUtils.closeInputStream(inputStream);
            } else {
                response.requestId = request.requestId;
                response.success = false;
                response.responseMsg = "URL错误";
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(ServerResponse result) {
        for (ServerResponseDoLinister linister : mServerResponseDoLinisterList) {
            linister.onResultSuccess(result);
        }
        super.onPostExecute(result);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        NetworkConnectUtils.closeInputStream(inputStream);
        progressBar.setVisibility(View.GONE);
    }

    public void cancelTask(boolean cancel) {
        super.cancel(cancel);
        NetworkConnectUtils.closeInputStream(inputStream);
        progressBar.setVisibility(View.GONE);
    }


    public interface ServerResponseDoLinister {
        Object paseContent(String stringContent) throws Exception;

        void insertDataBase(Object object) throws Exception;

        void onResultSuccess(ServerResponse response);
    }

    private List<ServerResponseDoLinister> mServerResponseDoLinisterList = new LinkedList<ServerResponseDoLinister>();

    public synchronized void addLinister(ServerResponseDoLinister linister) {
        if (!mServerResponseDoLinisterList.contains(linister)) {
            mServerResponseDoLinisterList.add(linister);
        }
    }

    public synchronized void removeLinister(ServerResponseDoLinister linister) {
        if (mServerResponseDoLinisterList.contains(linister)) {
            mServerResponseDoLinisterList.remove(linister);
        }
    }

    public synchronized void removeLinister() {
         mServerResponseDoLinisterList.clear();
    }

}
