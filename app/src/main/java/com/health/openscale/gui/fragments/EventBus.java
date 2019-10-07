package com.health.openscale.gui.fragments;


/**
 * Created by juangb on 30/07/17.
 */
public interface EventBus {
    void register(Object suscriber);
    void unregister(Object suscriber);
    void post(Object event);
}
