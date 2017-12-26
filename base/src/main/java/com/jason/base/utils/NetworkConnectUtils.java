package com.jason.base.utils;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yaping on 2016/1/8.
 */
public class NetworkConnectUtils {
    private static final String TAG = "NetworkConnectUtils";
    private static final int HTTPNETTIMEOUT_CONNECT = 5 * 1000;
    private static final int HTTPNETTIMEOUT_READ = 5 * 1000;
    private static HttpURLConnection urlConnection;

    public NetworkConnectUtils() {
    }

    public static InputStream openContectionLockedPost(String uri, HashMap<String, String> param) throws IOException {
        if(uri == null) {
            return null;
        } else {
            String data=getStringParaFromMap(param);
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Length",  String.valueOf(data.getBytes().length));
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }
    }
    public static InputStream openContectionLockedPost(String uri,String jsonPara) throws IOException {
        if(uri == null) {
            return null;
        } else {
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);
            urlConnection.setUseCaches(false);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Length",  String.valueOf(jsonPara.getBytes().length));
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonPara.getBytes());
            os.flush();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }
    }



    public static InputStream openContectionLockedGet(String uri, HashMap<String, String> param) throws IOException {
        if(uri == null) {
            return null;
        } else {
            String data=getStringParaFromMap(param);
            uri=uri+"?"+data;
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);;
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }
    }
    public static InputStream openContectionLockedGet(String uri, String  param,String json) throws IOException {
        if(uri == null) {
            return null;
        } else {
            String data=getStringParaFromStringGet(json,param);
            uri=uri+"?"+data;
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);;
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }
    }
    public static InputStream openContectionLockedGet(String uri) throws IOException {
        if(uri == null) {
            return null;
        } else {
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }
    }


    public static InputStream openContectionLockedV2(String uri) throws IOException {
        if(uri == null) {
            return null;
        } else {
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setConnectTimeout(HTTPNETTIMEOUT_CONNECT);
            urlConnection.setReadTimeout(HTTPNETTIMEOUT_READ);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                // 获取响应的输入流对象
                return urlConnection.getInputStream();
            }else{
                throw new StatusException("StatusCode!=200", String.valueOf(urlConnection.getResponseCode()));
            }
        }

    }




    public static InputStream openContectionUpLoadFiles(String uri, HashMap<String, String> textparam,HashMap<String, String> fileparam)throws IOException{
        if(uri == null) {
            return null;
        } else {
            String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
            urlConnection = (HttpURLConnection) new URL(uri).openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(30000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(urlConnection.getOutputStream());
            // text
            if (textparam != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textparam.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            // file
            if (fileparam != null) {
                Iterator<Map.Entry<String, String>> iter = fileparam.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = "application/octet-stream";
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            return urlConnection.getInputStream();
        }
    }

    public static boolean httpStatusOk(int statusCode) {
        return statusCode == 200 || statusCode == 302;
    }


    public static StringBuilder addURLStringBuilder(StringBuilder sb,String para) {
        return sb.append(URLEncoder.encode(para));
    }



    public static String getStringParaFromMap(HashMap<String,String> map,String para){
        StringBuilder sb = new StringBuilder();
        sb.append(para).append("=");
        Iterator<String> keys = map.keySet().iterator();
        while(keys.hasNext()) {
            String key= keys.next();
            if(!TextUtils.isEmpty(map.get(key))) {
                sb.append(key).append("=").append(map.get(key)).append('&');
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String getStringParaFromStringGet(String json,String para){
        StringBuilder sb = new StringBuilder();
        sb.append(para).append("=");
        return addURLStringBuilder(sb,json).toString();
    }

    public static String getStringParaFromMap(HashMap<String,String> map){
        StringBuilder sb = new StringBuilder();
        Iterator<String> keys = map.keySet().iterator();
        while(keys.hasNext()) {
            String key= keys.next();
            if(!TextUtils.isEmpty(map.get(key))) {
                sb.append(key).append("=").append(map.get(key)).append('&');
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static String getContentFromInput(InputStream is) {
        if(is == null) {
            DebugUtils.logNetworkOp("NetworkConnectUtils", "getContentFromInput passed null InputStream in");
            return null;
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];

            try {
                for(int size = is.read(buffer); size >= 0; size = is.read(buffer)) {
                    out.write(buffer, 0, size);
                }

                out.flush();
                buffer = out.toByteArray();
                out.close();
                String e = new String(buffer, "UTF-8");
                DebugUtils.logD("NetworkConnectUtils", "getContentFromInput return " + e);
                return e;
            } catch (IOException var5) {
                var5.printStackTrace();
                return null;
            }
        }
    }

    public static void closeInputStream(InputStream is) {
        if(is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
            urlConnection.disconnect();
        }
    }

    public static void closeOutStream(OutputStream out) {
        if(out != null) {
            try {
                out.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }
}
