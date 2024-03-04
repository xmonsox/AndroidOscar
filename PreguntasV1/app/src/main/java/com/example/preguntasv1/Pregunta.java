package com.example.preguntasv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Random;

public class Pregunta extends AppCompatActivity {
    Config config;
    String id_usuario;
    String names;
    LinearLayout linearDatos;
    TextView pregunt;
    Random rand = new Random();
    Integer [] arreglo = new Integer[10];
    LinearLayout linearPregunt;
    TextView nombre_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta);

        nombre_user = findViewById(R.id.nombre_user);
        linearDatos = findViewById(R.id.linearDatos);
        linearPregunt = findViewById(R.id.linearPregunt);
        pregunt = findViewById(R.id.pregunt);

        config = new Config(getApplicationContext());

        mostrarPreguntas(rand.nextInt(10) + 1);

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
            String id_cuest = datosCuestionario.getString("id");

            System.out.println("id_usuario: " + id_usuario);
            System.out.println("fecha_inicio: " + fecha_inicio);
            System.out.println("id_cuest " +  id_cuest );

            TextView fecha_i = new TextView(getApplicationContext());
            fecha_i.setText("Fecha Inicio: " + fecha_inicio);
            fecha_i.setTextColor(Color.rgb(0, 0, 0));
            fecha_i.setTextSize(20); // Tamaño del texto de la pregunta*/
            linearDatos.addView(fecha_i);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void generarRandom(){

        int numeroAleatorio = rand.nextInt(10) + 1;

        // Imprimir el número aleatorio
        //System.out.println("Número aleatorio: " + numeroAleatorio);
    }
    public void mostrarPreguntas(Integer numero){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = config.getEndpoint("API-Preguntas/getOtherCuestionario.php?id_pregunta="+ numero);
        JsonObjectRequest solicitud =  new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("El servidor responde OK");
                System.out.println(response.toString());
                getPreguntas(response);

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
    public void getPreguntas(JSONObject data){
        //System.out.println("data " + data);
        //JSONObject respuesta = null;
        try {
            JSONObject respuesta = data.getJSONObject("respuesta");
            JSONObject datosPregunta = respuesta.getJSONObject("pregunta");

            JSONArray opci = respuesta.optJSONArray("opciones");

            String descr = datosPregunta.getString("descripcion");
            String id_pregunta = datosPregunta.getString("id");
            String id_correcta = datosPregunta.getString("id_correcta");
            String url = datosPregunta.getString("url_imagen");

            /*TextView preguntas = new TextView(getApplicationContext());
            preguntas.setTextColor(Color.rgb(0,0,0));
            preguntas.setTextSize(18); // Tamaño del texto de la pregunta*/
            pregunt.setText(descr);


            RadioGroup radioGroup = findViewById(R.id.radio);

            for (int j = 0; j < opci.length(); j++) {
                JSONObject obje = opci.getJSONObject(j);

                String descripcion_opcion = obje.getString("descripcion");
                String id_opcion = obje.getString("id");

                RadioButton radioButton = new RadioButton(getApplicationContext());
                radioButton.setId(Integer.parseInt(id_opcion)); // Establecer un ID para el RadioButton

                radioButton.setText(descripcion_opcion);
                radioButton.setTextColor(Color.rgb(0, 0, 0));
                radioButton.setTextSize(15);
                radioButton.setTypeface(null, Typeface.ITALIC);
                radioButton.setPadding(0, 0, 0, 8);

                // Agregar el RadioButton al RadioGroup
                radioGroup.addView(radioButton);

                // Opcional: Seleccionar el primer RadioButton
                if (j == 0) {
                    radioButton.setChecked(true);
                }
            }



            System.out.println("Pregunta " + descr );
            System.out.println("Id_pregunta " + id_pregunta);
            System.out.println("id_correcta " + id_correcta);
            System.out.println("Url " + url);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}