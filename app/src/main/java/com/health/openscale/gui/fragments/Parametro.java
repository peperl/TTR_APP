package com.health.openscale.gui.fragments;

/**
 * Created by JuanGB on 31/07/2017.
 */

public class Parametro {

    private String nombre;
    private String valor;


    public Parametro(String nombre, String valor){
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
