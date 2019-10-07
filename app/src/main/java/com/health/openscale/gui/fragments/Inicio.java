package com.health.openscale.gui.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.health.openscale.R;
import com.health.openscale.constants.Constantes;
import com.health.openscale.gui.MainActivity;

import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Inicio extends Activity {
    private ZXingScannerView escanerView;
    Context t = this;
    private static final int REQUEST_CAMERA = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied! F*** u", Toast.LENGTH_SHORT).show();
            }


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
            //After this function call,it will ask for permission and whether it granted or not,this response is handle in onRequestPermissionsResult() which we overrided.
        }
    }

    public void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        ModelServices services = new ModelServices(getApplicationContext());
        EditText editText7, editText8;
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);
        services.setInicio(this);
        services.inicioSesion(editText7.getText().toString(),editText8.getText().toString());
    }

    public void result(JSONObject a) {
        if (a != null) {
            Utils.saveDataSharePreference(this, Constantes.paciente,a.toString());
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        } else {
            showMessage("correo y/o contraseña incorrectos");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void escanerQR(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            escanerView = new ZXingScannerView(this);
            escanerView.setResultHandler(new zxingscanner());
            setContentView(escanerView);
            escanerView.startCamera();
        }else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        //Intent intent = new Intent(getApplicationContext(),newPass.class);
        //startActivity(intent);
    }

    class zxingscanner implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(Result result) {
            Intent intent = new Intent(t, newPass.class);
            intent.putExtra("qr", result.getText());
            startActivity(intent);
        }
    }

}
