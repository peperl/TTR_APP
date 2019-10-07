package com.health.openscale.gui.fragments;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by JuanGB on 31/07/2017.
 */

public class Post {
    private String url;
    private String cookie;
    private List<Parametro> parametros;

    public Post(String url) {
        this.setUrl(url);
        parametros = new ArrayList<>();
    }

    public void addParametro(Parametro p) {
        getParametros().add(p);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }
}

