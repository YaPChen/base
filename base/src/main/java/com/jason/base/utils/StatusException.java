package com.jason.base.utils;

import java.io.IOException;

/**
 * Created by yaping on 2016/1/12.
 */
public class StatusException extends IOException {
    private String mStatusCode;

    public StatusException(String detailMessage, String statusCode) {
        super(detailMessage);
        this.mStatusCode = statusCode;
    }

    public String getStatusCode() {
        return this.mStatusCode;
    }
}
