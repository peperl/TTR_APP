package com.health.openscale.gui.fragments;

/**
 * Created by JuanGB on 01/08/2017.
 * intrfaz que nos permite recibir y procesar peticiones post
 */

public interface Service {
    void postEvent(int type);
    void postEvent(int type, String errorMessage);
    void saveCookie(String cookie);
    void processResponse(Response response);
}
