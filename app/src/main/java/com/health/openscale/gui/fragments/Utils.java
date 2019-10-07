package com.health.openscale.gui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    public static final String NOMBRE_PREFERENCIAS = "";
    public static boolean validaURL(String s) {

        String regex = "^(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)$";

        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();

        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean isNetWorkAvailable(Activity ap) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ap.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getSharePreference(Activity ap, String nombre) {
        try {
            SharedPreferences pref = ap.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);

            return pref.getString(nombre, "");
        } catch (Exception e) {
            return "";
        }
    }

    public static String getLocalCookie(Activity ap) {
//        SharedPreferences prefs = ap.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
//        String cookie = prefs.getString(Constantes.NOMBRE_COOKIE,"");
//        showLog(">>><<11>>","obtieniendo cookie>"+cookie+"<<");
//        return cookie;
        //Utils.showLog("XOOOOOOOOOOOO>" + getSharePreference(ap, Constantes.NOMBRE_COOKIE) + "<");
        //return getSharePreference(ap, Constantes.NOMBRE_COOKIE);
        return null;
    }

    public static String getUrlServicio(Activity ap) {

        /**
         * Comentar esta linea para "Dejar la configuracion como default para que no se mofique"
         */
//        return getSharePreference(ap, Constantes.NOMBRE_URL_ANANDA);
        //return Constantes.URL_SERVICIO;
        return null;
    }

    public static void showLog(String r) {
        showLog(null, r);
    }

    public static void showLog(String r, String p) {
        String rr = ">><<";
        if (r != null) {
            rr += r;
        }

        String pp = "";
        if (p != null) {
            pp += p;
        }
        Log.e(rr, pp);
    }

    public static void saveDataSharePreference(Activity view, String nombre, String valor) {
        Utils.showLog("GUARDANDO " + nombre + ">>" + valor + "<<<");
        if (nombre == null) {
            Log.e(">>Coo", "valor no guardado!!!!!");
            return;
        } else {
            if (valor == null) {
                valor = "";
            }
            SharedPreferences prefs = view.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(nombre, valor);
            editor.commit();
            Log.e(">>Coo", "valor guardadp");
        }
    }



    public static String getStringNotNull(String s){
        return s==null?"":s.isEmpty()?"":s.equals("null")?"":s;
    }

    public static String getAtribute(JSONObject producto, String name) {
        try {
            String att =producto.getString(name);
            if(att== null || att .isEmpty() || att.equals("null")){
                return "";
            }else{
                return att;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
