package com.example.oneaplication_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView etq_datos;
    EditText campo_nombre;
    EditText campo_apellidos;
    EditText campo_telefono;
    EditText campo_direccion;
    EditText campo_correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etq_datos = findViewById( R.id.etq_datos );
        campo_nombre = findViewById( R.id.campo_nombre );
        campo_apellidos = findViewById( R.id.campo_apellidos );
        campo_telefono = findViewById( R.id.campo_telefono );
        campo_direccion = findViewById( R.id.campo_direccion );
        campo_correo = findViewById( R.id.campo_correo );
    }

    public void imprimirDatos(View vista){
        String nombre = campo_nombre.getText().toString();
        String apellidos = campo_apellidos.getText().toString();
        String telefono = campo_telefono.getText().toString();
        String direccion = campo_direccion.getText().toString();
        String correo = campo_correo.getText().toString();

        etq_datos.setText("Nombre: "+nombre + "\n" + "Apellidos: " + apellidos + "\n" + "Telefono: " +  telefono + "\n" + "Direccion: " + direccion + "\n" + "Correo: " + correo + "\n" );


        Intent intencion = new Intent(getApplicationContext(), TwoActivity.class);
        intencion.putExtra("nombres", nombre);
        intencion.putExtra("apellidos", apellidos);
        intencion.putExtra("telefono", telefono);
        intencion.putExtra("direccion", direccion);
        intencion.putExtra("correo", correo);
        startActivity(intencion);
    }

}