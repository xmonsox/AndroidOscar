package com.example.preguntasv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Resumen extends AppCompatActivity {

    TextView user;
    TextView nombresitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        user = findViewById(R.id.text_Id);
        nombresitos = findViewById(R.id.text_name);

        Bundle datos = getIntent().getExtras();
        String id_user = datos.getString("id_usuario");
        String nombre = datos.getString("nombres");

        user.setText(id_user);
        nombresitos.setText(nombre);
    }
}