package com.health.openscale.gui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.health.openscale.R;
import com.health.openscale.gui.MainActivity;

public class newPass extends AppCompatActivity {

    private TextView namePaciente;
    private EditText editText2, editText3;
    private Button button;
    private NewPassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        setTitle("Crear Contraseña");
        Intent intent = getIntent();
        String qr = intent.getStringExtra("qr");

        namePaciente = findViewById(R.id.qrContent);
        button = findViewById(R.id.button7);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        presenter = new NewPassPresenter(this.getApplicationContext(), qr, this);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (editText2.getText().toString().equals(editText3.getText().toString())) {
                    presenter.sendPassWord(editText2.getText().toString());
                } else {
                    showMessage("Las contraseñas no coinciden, ingresar de nuevo");
                }
            }
        });

    }

    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public String getNamePaciente() {
        return (String) namePaciente.getText();
    }

    public void setNamePaciente(String string) {
        namePaciente.setText(string);
    }

    public void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    public void returnToInicio() {
        Intent intent = new Intent(getApplicationContext(),Inicio.class);
        startActivity(intent);
    }
}
