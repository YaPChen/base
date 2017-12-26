package com.jason.base.network;

import android.widget.ProgressBar;

import java.util.HashMap;

/**
 * 请求数据基类
 */
public class ServerRequest {
	/**
	 * @TODO：接口访问请求参数定义
	 * @param requestId：请求id用于返回解析
	 * @param url：请求url
	 * @param modth：GET或POST或文件上传
	 * @param jsonParam：请求json参数
	 * @param mapParam：请求map类型参数
	 * @param fileParam：请求file参数HashMap<文件名称或者其他，文件绝对地址>
	 */
	public static final int MODTH_GET=0;
	public static final int MODTH_POST=1;
	public static final int MODTH_FILE=2;
	public int requestId;
	public String url;
	public int modth;
	public String jsonParam;
	public HashMap<String, String> mapParam;
	public HashMap<String, String> fileParam;
}