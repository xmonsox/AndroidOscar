package com.example.preguntasv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetalleCuestionario extends AppCompatActivity {
    TextView mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuestionario);
        Bundle datos = getIntent().getExtras();
        String id = datos.getString("id");

        mostrar = findViewById(R.id.mostrar);
        mostrar.setText(id);
    }
}