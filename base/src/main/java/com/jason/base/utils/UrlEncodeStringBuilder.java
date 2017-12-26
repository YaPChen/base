package com.jason.base.utils;

import java.net.URLEncoder;

/**
 * Created by yaping on 2016/1/13.
 */
public class UrlEncodeStringBuilder {
    private StringBuilder mSb;

    public UrlEncodeStringBuilder() {
        this.mSb = new StringBuilder();
    }

    public UrlEncodeStringBuilder(CharSequence charSequence) {
        this.mSb = new StringBuilder(charSequence);
    }

    public <T> UrlEncodeStringBuilder append(T param) {
        this.mSb.append(param);
        return this;
    }

    public UrlEncodeStringBuilder appendUrlEncodedString(String param) {
        this.mSb.append(URLEncoder.encode(param));
        return this;
    }

    public UrlEncodeStringBuilder appendUrlEncodedString(String param, String fallback) {
        if(param == null) {
            this.mSb.append(fallback);
        } else {
            this.mSb.append(URLEncoder.encode(param));
        }

        return this;
    }

    public UrlEncodeStringBuilder appendUrlEncodedStringNotNull(String param) {
        return this.appendUrlEncodedString(param, "");
    }

    public String toString() {
        return this.mSb.toString();
    }
}
