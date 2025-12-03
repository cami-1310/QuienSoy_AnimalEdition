package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Categorias_activity extends AppCompatActivity {
    ImageButton btnAgua, btnAire, btnTierra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.categorias_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent=getIntent();
        Boolean modoJuego=intent.getBooleanExtra("modoJuego", true);

        btnAgua=findViewById(R.id.btnAgua);
        btnAire=findViewById(R.id.btnAire);
        btnTierra=findViewById(R.id.btnTierra);

        if(modoJuego){
            //intent de llamar a la actividad que mostrará las preguntas
            btnAgua.setOnClickListener(v -> {
                //actividad preguntas animales acuaticos
            });
            btnAire.setOnClickListener(v -> {
                //actividad preguntas animales aereos
            });
            btnTierra.setOnClickListener(v -> {
                //actividad preguntas animales terrestres
                Intent intentJuego=new Intent(this, Pantalla_juego.class);
                intentJuego.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentJuego);
                finish();
            });
        } else {
            //intent de llamar a la actividad que mostrará las caracteristicas
            btnAgua.setOnClickListener(v -> {
                //actividad aprender animales acuaticos
            });
            btnAire.setOnClickListener(v -> {
                //actividad aprender animales aereos
            });
            btnTierra.setOnClickListener(v -> {
                //actividad aprender animales terrestres
                Intent intentAprender=new Intent(this, Pantalla_aprender.class);
                startActivity(intentAprender);
            });
        }
    }
}