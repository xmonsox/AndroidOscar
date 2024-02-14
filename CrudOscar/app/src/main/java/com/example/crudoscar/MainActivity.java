package com.example.crudoscar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createView(View vista){
        Intent intencion = new Intent(getApplicationContext(), Activity_Create.class);
        startActivity(intencion);
    }
    public void readView(View vista){
        Intent intencion = new Intent(getApplicationContext(), ActivityRead.class);
        startActivity(intencion);
    }
    public void updateView(View vista){
        Intent intencion = new Intent(getApplicationContext(), ActivityUpdate.class);
        startActivity(intencion);
    }
}