package com.health.openscale.gui.fragments;

/**
 * Created by JuanGB on 31/07/2017.
 */

public class Response {
    public static final int ERROR_PARAM = -1;
    public static final int ERROR_POST_NULL = -2;
    public static final int ERROR_INVALID_URL = -3;
    public static final int ERROR_IOEXCEPTION = -4;

    ///////////
    public static final int ERROR_NOT_SEND= -4;
    public static final int LOGIN_SUCCESS= 200;
    public static final int SUCCESS_RESPONSE = -200;

    public static final int SERVICE_NOT_FOUND_REQUEST= 404;
    public static final int SERVICE_NOT_FOUND_RESPONSE= -404;

    public static final int INCORRECT_CREDENTIALS_REQUEST = 302;
    public static final int INCORRECT_CREDENTIALS_RESPONSE = -302;

    private String cookie;
    private String cookineName;
    private String response;//BODY
    private String header;//header
    private int status;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCookineName() {
        return cookineName;
    }

    public void setCookineName(String cookineName) {
        this.cookineName = cookineName;
    }
}
