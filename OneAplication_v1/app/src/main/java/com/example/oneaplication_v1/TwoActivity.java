package com.example.oneaplication_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TwoActivity extends AppCompatActivity {
    TextView etq_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);


        Bundle datos = getIntent().getExtras();
        String nombres = datos.getString("nombres");
        String apellidos = datos.getString("apellidos");
        String telefono = datos.getString("telefono");
        String direccion = datos.getString("direccion");
        String correo = datos.getString("correo");

        etq_info = findViewById(R.id.etq_info);

        etq_info.setText("Nombre: "+nombres + "\n" + "Apellidos: " + apellidos + "\n" + "Telefono: " +  telefono + "\n" + "Direccion: " + direccion + "\n" + "Correo: " + correo + "\n" );
    }
}