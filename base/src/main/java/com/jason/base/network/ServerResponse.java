package com.jason.base.network;

public class ServerResponse{
	/**
	 * @TODO：接口访问返回定义
	 * @param requestId：请求id用于返回解析
	 * @param success：是否成功
	 * @param errorMsg：异常信息
	 * @param serverRetrunString：返回的字符串
	 * @param serverObject：返回的对象
	 */
	public int requestId;
	public boolean success;
	public String responseMsg;
	public String serverRetrunString;
	public Object serverObject;


	public boolean isSuccessfully(){
		return success;
	}
}