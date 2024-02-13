package com.example.primeraplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SegundaActividad extends AppCompatActivity {

    Button btn_registrar;
    RadioGroup radioGroup;
    RadioButton radioButtonOption1;
    RadioButton radioButtonOption2;
    ArrayList<Boolean> listaRespuestas;
    int indice = 0;
    ArrayList<Class<? extends Activity>> listaSerializada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_segunda_actividad);


        btn_registrar = findViewById(R.id.registrar2);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonOption1 = findViewById(R.id.radioButtonOption1);
        radioButtonOption2 = findViewById(R.id.radioButtonOption2);
        Intent intent = getIntent();
        listaRespuestas = (ArrayList<Boolean>) intent.getSerializableExtra("respuestas");
        listaSerializada = (ArrayList<Class<? extends Activity>>) getIntent().getSerializableExtra("actividades");
        indice = (int) intent.getSerializableExtra("indice");
        TextView titulo = findViewById(R.id.titulo);
        titulo.setText("Pregunta: "+(indice+1));
        indice++;

    }



    public void registrarPersona(View vista){
        Intent intencion = new Intent(this, listaSerializada.get(indice));

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            if(selectedRadioButtonId == radioButtonOption1.getId()){
                listaRespuestas.add(true);
            }else{
                listaRespuestas.add(false);
            }
            intencion.putExtra("respuestas", listaRespuestas);
            intencion.putExtra("indice", indice);
            intencion.putExtra("actividades", listaSerializada);
            startActivity(intencion);
        } else {
            Log.d("Texto seleccionado", "Ningún RadioButton seleccionado");
        }

    }
}