package com.example.primeraplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Resultados extends AppCompatActivity {

    TextView preguntas_correctas;
    TextView preguntas_incorrectas;
    LinearLayout layoutPreguntas;
    int contador_correctas;
    int contador_incorrectas;
    ArrayList<Boolean> listaRespuestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        preguntas_correctas = findViewById(R.id.etq_respuestas_correctas);
        preguntas_incorrectas = findViewById(R.id.etq_respuestas_incorrectas);
        layoutPreguntas = findViewById(R.id.layout_preguntas);

        Intent intent = getIntent();
        listaRespuestas = (ArrayList<Boolean>) intent.getSerializableExtra("respuestas");

        if (listaRespuestas != null && !listaRespuestas.isEmpty()) {
            for (int i = 0; i < listaRespuestas.size(); i++) {
                Boolean respuesta = listaRespuestas.get(i);
                TextView textView = new TextView(this);
                textView.setText("Pregunta " + (i + 1) + ": " + (respuesta ? "Correcta" : "Incorrecta"));
                layoutPreguntas.addView(textView);

                if (respuesta) {
                    contador_correctas++;
                } else {
                    contador_incorrectas++;
                }
            }
            preguntas_correctas.setText("Preguntas Correctas: " + contador_correctas);
            preguntas_incorrectas.setText("Preguntas incorrectas: " + contador_incorrectas);
        } else {
            System.out.println("La lista de respuestas está vacía o es nula");
        }
    }
}