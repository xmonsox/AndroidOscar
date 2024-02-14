package com.example.crudoscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Create extends AppCompatActivity {

    EditText campo_cedula;
    EditText campo_nombres;
    EditText campo_apellidos;
    EditText campo_direccion;
    EditText campo_email;
    EditText campo_telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        campo_cedula = findViewById( R.id.campo_cedula );
        campo_nombres = findViewById( R.id.campo_nombres );
        campo_apellidos = findViewById( R.id.campo_apellidos );
        campo_telefono = findViewById( R.id.campo_telefono );
        campo_direccion = findViewById( R.id.campo_direccion );
        campo_email = findViewById( R.id.campo_email );
    }
    public void insert(View vista){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.5/APIenPHP/Insert.php";

        StringRequest solicitud =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("El servidor POST responde OK");
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(response);
                    Intent intencion = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intencion);
                } catch (JSONException e) {
                    System.out.println("El servidor POST responde con un error:");
                    System.out.println(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("El servidor POST responde con un error:");
                System.out.println(error.getMessage());
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("cedula", campo_cedula.getText().toString());
                params.put("nombres", campo_nombres.getText().toString());
                params.put("apellidos", campo_apellidos.getText().toString());
                params.put("telefono", campo_telefono.getText().toString());
                params.put("direccion", campo_direccion.getText().toString());
                params.put("email", campo_email.getText().toString());

                return params;
            }
        };
        queue.add(solicitud);
    }
}