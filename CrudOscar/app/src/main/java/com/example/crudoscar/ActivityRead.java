package com.example.crudoscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityRead extends AppCompatActivity {

    TextView etq_clients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        etq_clients = findViewById(R.id.etq_clientes);
        this.read();
    }
    public void read(){

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://192.168.1.5/APIenPHP/Obtener.php";

        JsonObjectRequest solicitud =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("El servidor responde OK");
                System.out.println(response.toString());

                getClients(response);
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
    public void getClients(JSONObject data){
        try {
            JSONArray arreglo = data.getJSONArray("registros");
            etq_clients.setText("");
            for (int i=0;i<arreglo.length();i++){
                JSONObject client = arreglo.getJSONObject(i);
                String document =  client.getString("cedula");
                String first_name =  client.getString("nombres");
                String last_name = client.getString("apellidos");

                etq_clients.append(document + " || " + first_name + " " + "\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}