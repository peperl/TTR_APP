package com.health.openscale.gui.fragments;

import android.content.Context;
import android.content.SharedPreferences;


import com.health.openscale.constants.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Logger;

import static android.content.Context.MODE_PRIVATE;

public class ModelServices implements Service{

    private Context context;
    private NewPassPresenter newPassPresenter;
    private Inicio inicio;
    private int peticion = 0;

    public ModelServices(Context applicationContext) {
        context = applicationContext;
    }

    public void changePassword(int idPaciente, String newPassword){
        String url = Constantes.SERVER_PATH + "ChangePassword?idPaciente="+idPaciente+"&newPassword="+newPassword;

        Post p = new Post(url);
        PostRunner pr = new PostRunner();
        pr.setService(this);
        pr.execute(p);
        peticion = Events.changePassword;
        Logger.getGlobal().info(url);

    }
    public void inicioSesion(String user, String pass){
        String url = Constantes.SERVER_PATH + "InicioSesion?user="+user+"&pass="+pass;
        Post p = new Post(url);
        PostRunner pr = new PostRunner();
        pr.setService(this);
        pr.execute(p);
        peticion = Events.inicioSesion;
        Logger.getGlobal().info(url);
    }
    public JSONArray muestraPlan(String idPaciente){
        String url = Constantes.SERVER_PATH + "MuestraPlan?idPaciente="+idPaciente;
        final JSONArray[] result = new JSONArray[1];
        return result[0];
    }
    public JSONObject registrarComida(String idComida, String cumplimiento){
        String url = Constantes.SERVER_PATH + "RegistrarComida?idComida="+idComida+"&cumplimiento="+cumplimiento;
        Logger.getGlobal().info(url);
        final JSONObject[] result = new JSONObject[1];
        return result[0];
    }
    public void getPaciente(String qr){
        String url = Constantes.SERVER_PATH + "getPaciente?qr="+qr;
        Post p = new Post(url);
        Logger.getGlobal().info(url);

        PostRunner pr = new PostRunner();
        pr.setService(this);
        pr.execute(p);
        peticion = Events.getPaciente;
    }
    public JSONObject registraPeso(String idPaciente, String peso){
        String url = Constantes.SERVER_PATH + "registraPeso?idPaciente="+idPaciente+"&peso="+peso;
        Logger.getGlobal().info(url);
        final JSONObject[] result = new JSONObject[1];
        return result[0];
    }

    @Override
    public void postEvent(int type) {

    }

    @Override
    public void postEvent(int type, String message) {
        peticion = -1;
        Events event = new Events();
        event.setTypeEvent(type);
        if (message != null) {
            event.setMessage(message);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }

    @Override
    public void saveCookie(String cookie) {

    }

    @Override
    public void processResponse(Response response) {
        switch (peticion) {
            case Events.getPaciente:
                try {
                    newPassPresenter.setName(new JSONObject(response.getResponse()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Events.changePassword:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.getResponse());
                    newPassPresenter.changePass();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Events.inicioSesion:
                try {
                    inicio.result(new JSONObject(response.getResponse()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    inicio.result(null);
                }
                break;
        }


    }

    public NewPassPresenter getNewPassPresenter() {
        return newPassPresenter;
    }

    public void setNewPassPresenter(NewPassPresenter newPassPresenter) {
        this.newPassPresenter = newPassPresenter;
    }

    public Inicio getInicio() {
        return inicio;
    }

    public void setInicio(Inicio inicio) {
        this.inicio = inicio;
    }
}
