package com.jason.base.utils;

/**
 * Created by yaping on 2016/1/12.
 */
public class CompatException extends Exception{

    public String mMessage="";
    public int mCode;
    public CompatException(String mMessage,int mCode){
        this.mMessage=mMessage;
        this.mCode=mCode;
    }
}
