package com.example.proyectofinal;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageButton btnPlay, btnLearn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //iniciar servicio para musica de fondo
        Intent intentMusic=new Intent(this, MusicPlayer.class);
        startService(intentMusic);

        btnPlay=findViewById(R.id.btnPlay);
        btnLearn=findViewById(R.id.btnLearn);

        btnPlay.setOnClickListener(v -> {
            Intent intent=new Intent(this, Username_activity.class);
            intent.putExtra("modoJuego", true); //true significa va a jugar
            startActivity(intent);
        });
        btnLearn.setOnClickListener(v -> {
            Intent intent=new Intent(this, Username_activity.class);
            intent.putExtra("modoJuego", false); //false significa va a aprender
            startActivity(intent);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //parar el servicio de musica de fondo
        stopService(new Intent(this, MusicPlayer.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicPlayer.class));
    }
}