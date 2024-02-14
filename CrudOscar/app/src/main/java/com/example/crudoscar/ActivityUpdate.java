package com.example.crudoscar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityUpdate extends AppCompatActivity {
    EditText campo_cedula;
    EditText campo_nombre;
    EditText campo_apellido;
    EditText campo_telefono;
    EditText campo_direccion;
    EditText campo_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        campo_cedula = findViewById(R.id.campo_cedula);
        campo_nombre = findViewById(R.id.campo_nombres);
        campo_apellido = findViewById(R.id.campo_apellidos);
        campo_telefono = findViewById(R.id.campo_telefono);
        campo_direccion = findViewById(R.id.campo_direccion);
        campo_email = findViewById(R.id.campo_email);

    }
    public void buscaCedula(View vista){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.5/APIenPHP/Consulta.php";

        StringRequest solicitud =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("El servidor POST responde OK");
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(response);
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

                return params;
            }
        };

        queue.add(solicitud);
    }

        public void getClients(JSONObject data){
            try {
                JSONArray arreglo = data.getJSONArray("registros");
                    JSONObject client = arreglo.getJSONObject(0);
                    String document =  client.getString("cedula");
                    String first_name =  client.getString("nombres");
                    String last_name = client.getString("apellidos");

                    campo_nombre.setText(first_name);
                    campo_apellido.setText(last_name);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

}