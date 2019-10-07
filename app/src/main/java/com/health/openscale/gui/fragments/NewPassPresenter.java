package com.health.openscale.gui.fragments;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPassPresenter {
    private newPass viewPass;
    private ModelServices service;
    private JSONObject jsonObject;

    public NewPassPresenter(Context applicationContext, String qr, newPass newPass) {
        viewPass = newPass;
        service = new ModelServices(applicationContext);
        service.setNewPassPresenter(this);
        service.getPaciente(qr);
    }

    public void setName(JSONObject jsonObject) {
        if (jsonObject == null) {
            viewPass.showMessage("El objeto es nulo");
        }
        try {
            this.jsonObject = jsonObject;
            viewPass.setNamePaciente(((String) jsonObject.get("nombre") )+ " "+ ((String) jsonObject.get("apellidos") ));
        } catch (Exception e) {
            viewPass.showMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendPassWord(String password) {
        try {
            service.changePassword(jsonObject.getInt("idpaciente"), password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void changePass() {
        viewPass.showMessage("Contraseña actualizada");
        viewPass.returnToInicio();
    }
}
