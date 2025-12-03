package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla_ganar extends AppCompatActivity {
    ImageButton btnAgain, btnLearn, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ganador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAgain=findViewById(R.id.btnAgain);
        btnLearn=findViewById(R.id.btnLearn);
        btnSalir=findViewById(R.id.btnSalir);

        btnAgain.setOnClickListener(v -> {
            Intent intent=new Intent(this, Categorias_activity.class);
            intent.putExtra("modoJuego", true);
            startActivity(intent);
            finish();
        });
        btnLearn.setOnClickListener(v -> {
            Intent intent=new Intent(this, Categorias_activity.class);
            intent.putExtra("modoJuego", false);
            startActivity(intent);
            finish();
        });
        btnSalir.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
    }
}