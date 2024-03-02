package com.example.preguntasv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.preguntasv1.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pregunta extends AppCompatActivity {
    Config config;
    String id_usuario;
    String names;
    LinearLayout linearDatos;
    TextView nombre_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        nombre_user = findViewById(R.id.nombre_user);
        linearDatos = findViewById(R.id.linearDatos);
        config = new Config(getApplicationContext());

        Bundle datos = getIntent().getExtras();
        id_usuario = datos.getString("id_user");
        names = datos.getString("name");

        //System.out.println("id " + id_usuario);
        nombre_user.setText(names);

        read();
    }
    public void read(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = config.getEndpoint("API-Preguntas/getFechaInicio.php?id_usuario="+id_usuario);
        JsonObjectRequest solicitud =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("El servidor responde OK");
                System.out.println(response.toString());

                getFechaInicio(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("El servidor responde con un error:");
                System.out.println(error.getMessage());
            }
        });

        queue.add(solicitud);
    }
    public void getFechaInicio(JSONObject data){
        try {
            JSONObject datosCuestionario = data.getJSONObject("datos_cuestionario");
            String id_usuario = datosCuestionario.getString("id_usuario");
            String fecha_inicio = datosCuestionario.getString("fecha_inicio");

            System.out.println("id_usuario: " + id_usuario);
            System.out.println("fecha_inicio: " + fecha_inicio);

            TextView fecha_i = new TextView(getApplicationContext());
            fecha_i.setText("Fecha Inicio: " + fecha_inicio);
            fecha_i.setTextColor(Color.rgb(0, 0, 0));
            linearDatos.addView(fecha_i);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}