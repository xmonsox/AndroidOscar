package com.example.consumoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView etq_respuesta;
    ImageView etq_imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etq_respuesta = findViewById(R.id.etq_respuesta);
        etq_imagen = findViewById(R.id.etq_imagen);
    }
    public void consumoGet(View vista){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://10.199.144.128/APIenPHP/Obtener.php";

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
            etq_respuesta.setText("");
            for (int i=0;i<arreglo.length();i++){
                JSONObject client = arreglo.getJSONObject(i);
                String document =  client.getString("cedula");
                String first_name =  client.getString("nombres");
                String last_name = client.getString("apellidos");

                etq_respuesta.append(document + " - " + first_name + " " + last_name + "\n");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void consumoPostJson(View vista){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://10.199.144.128/APIenPHP/Insert.php";

        StringRequest solicitud =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    System.out.println("El servidor POST responde OK");
                    System.out.println(response);
                    etq_respuesta.setText(response);
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
                params.put("cedula", "108817");
                params.put("nombres", "Juliana");
                params.put("apellidos", "Gonzalez");
                params.put("telefono", "5484784");
                params.put("direccion", "Calle 20");
                params.put("email", "juli@mail.com");
                return params;
            }
        };

        queue.add(solicitud);
    }

    public void consumoImagen(View vista){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png";

        ImageRequest solicitud = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        etq_imagen.setImageBitmap(bitmap);

                    }
                },
                0, 0, null, // maxWidth, maxHeight, decodeConfig;
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("El servidor responde con un error:");
                        System.out.println(error.getMessage());
                    }
                });

        queue.add(solicitud);
    }
}