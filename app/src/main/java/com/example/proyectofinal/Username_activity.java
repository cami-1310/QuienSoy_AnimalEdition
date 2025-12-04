package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Username_activity extends AppCompatActivity {
    ImageButton btnSig;
    EditText usernameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.username_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //se obtiene modoJuego desde MainActivity
        Intent intent=getIntent();
        Boolean modoJuego=intent.getBooleanExtra("modoJuego", true);

        //se obtiene username desde la vista
        usernameTxt=findViewById(R.id.username);

        btnSig=findViewById(R.id.btnSig);
        btnSig.setOnClickListener(v -> {
            if(usernameTxt.getText().toString().isEmpty()){
                usernameTxt.setError("Ingresa un nombre de usuario");
                return;
            }
            String username=usernameTxt.getText().toString();
            //se manda a Categorias_activity el modo de juego y el username
            Intent intent1=new Intent(this, Categorias_activity.class);
            intent1.putExtra("modoJuego", modoJuego);
            intent1.putExtra("username", username);
            startActivity(intent1);
        });
    }
}