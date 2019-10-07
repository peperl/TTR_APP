package com.health.openscale.gui.fragments;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.CloseableHttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.client.BasicCookieStore;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;


public class PostRunner extends AsyncTask<Post, Void, Response> {
    private Service service;

    @Override
    protected Response doInBackground(Post... params) {
//        Utils.showLog(">PostRunner", "iniciando hilo");
        //obtenemos el obtejto post
        if (params.length < 1) {
            Response r = new Response();
            r.setStatus(Response.ERROR_PARAM);
            r.setResponse("No se han recibido parametros de envio");
            return r;
        }

//        Utils.showLog(">PostRunner", "tenemos parametros");

        //obtenemos la unica peticion
        Post p = params[0];

        if (p == null) {
            Response r = new Response();
            r.setStatus(Response.ERROR_POST_NULL);
            r.setResponse("");
            return r;
        }

//        Utils.showLog(">PostRunner", "obtenemos parametro");

        //obtenemos la url que se enviara
        String urlPeticion = p.getUrl();

        if (urlPeticion == null || urlPeticion.isEmpty()) {
            Response r = new Response();
            r.setStatus(Response.ERROR_INVALID_URL);
            r.setResponse("");
            return r;
        }

//        Utils.showLog(">PostRunner", "Tenemos url valida " + urlPeticion);

        List<Parametro> parametros = p.getParametros();

        if(parametros!= null && parametros.size()>=1){
            p.setParametros(parametros);
        }

        return  sendRequest(p);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onCancelled() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    /**
     * Envia peticiones post al servidor
     *
     * @param p
     * @return
     */
    private Response sendRequest(Post p) {
//        Log.e(">>>", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        Log.e(">>>", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Nuevo Reques");
        Utils.showLog(">PostRunner>sendRequest", "request a>" + p.getUrl());
        //respuesta que se enviara
        Response response = new Response();

        //store de cookies
        BasicCookieStore cookieStore = new BasicCookieStore();

        //creamos un httpclient y le adjuntamos el store de cookies
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //creamos una peticion post
        HttpPost httpPost = new HttpPost(p.getUrl());
//        Log.e(">>>", "pegando cookie");
        if (p.getCookie() != null && !p.getCookie().isEmpty()) {
            httpPost.setHeader("Cookie", "JSESSIONID=" + p.getCookie());
            Log.e(">>>", "Cokkie pegada" + p.getCookie());
        }

        //creamos una repuesta http
        CloseableHttpResponse closeableHttpClient;
        try {

//            Utils.showLog(">PostRunner","parametros");
            if (p.getParametros() != null && p.getParametros().size() > 0) {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                for (Parametro parametro : p.getParametros()) {
                    nameValuePairs.add(new BasicNameValuePair(parametro.getNombre(), parametro.getValor()));
                    Utils.showLog(">>",""+parametro.getNombre()+parametro.getValor());
                }

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
//            Utils.showLog(">PostRunner", "termino parametros");

            //ejecutamos la peticion
            closeableHttpClient = httpClient.execute(httpPost);

            //obtenemos el response body
            String responseBody = EntityUtils.toString(closeableHttpClient.getEntity());
            response.setResponse(responseBody);

            //adjuntamos el status (codigo de respuesta)
            int status = closeableHttpClient.getStatusLine().getStatusCode();
//            Utils.showLog(">PostRunner", "estatus " + status);
            response.setStatus(status);

            //obtenemos las cookies
            List<Cookie> cookies = cookieStore.getCookies();

            for (Cookie cookie : cookies) {
                //obtenemos la cookie de sesion
                if (cookie.getName().equals("JSESSIONID")) {
                    //guardamos en la respuesta la cookie
                    response.setCookie(cookie.getValue());
                    response.setCookineName(cookie.getName());
                    break;
                }
            }

            //cerramos la conexion
            httpClient.close();
        } catch (IOException e) {
            response = new Response();
            response.setStatus(Response.ERROR_IOEXCEPTION);
            response.setCookie("");
            response.setResponse("Error en la conexion: " + e.getMessage());
//            Utils.showLog("Error en la conexion");
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response result) {
        service.processResponse(result);
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
