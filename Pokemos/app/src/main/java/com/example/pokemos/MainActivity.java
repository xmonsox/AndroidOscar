package com.example.pokemos;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    ImageView loading;
    Integer suma = 0;
    LinearLayout LinearMayor;
    AdaptadorNombres adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearMayor = findViewById(R.id.LinearMayor);
        recycler = findViewById(R.id.recycler_pokemones);
        loading = findViewById(R.id.loading_pokemon);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading_pokeball)
                .into(loading);

        adaptador = new AdaptadorNombres(this);
        recycler.setAdapter(adaptador);
        recycler.setLayoutManager(new LinearLayoutManager(this));


        cargarDatos();
    }

    private void cargarDatos() {
        loading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://pokeapi.co/api/v2/pokemon/?offset="+suma+"&limit=20";

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.setVisibility(View.GONE);
                        try {
                            JSONArray resultados = response.getJSONArray("results");
                            for (int i = 0; i < resultados.length(); i++) {
                                JSONObject pokemon = resultados.getJSONObject(i);
                                String nombre = pokemon.getString("name");
                                String url = pokemon.getString("url");
                                System.out.println("Url Maurico " + url);

                                adaptador.agregarNombrePokemon(nombre,url);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        error.printStackTrace();
                    }
                });

        queue.add(solicitud);
    }
    public void siguiente(View vista){
        suma += 20; // Incrementar el offset para cargar los siguientes 20 pokémon
        adaptador.clear();
        cargarDatos();
        loading.setVisibility(View.VISIBLE);
    }

    public void anterior(View vista){
        if (suma >= 20) { // Verificar que el offset no sea menor que 20 para evitar valores negativos
            suma -= 20; // Decrementar el offset para cargar los 20 pokémon anteriores
            adaptador.clear();
            cargarDatos();
            loading.setVisibility(View.VISIBLE);
        }
    }

}