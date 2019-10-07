package com.health.openscale.gui.fragments;

/**
 * Created by JuanGB on 25/09/2017.
 */

public class Events {

    public static final int getPaciente = 1;
    public static final int changePassword = 2;
    public static final int inicioSesion = 3;
    private String message;
    private int typeEvent;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

}
