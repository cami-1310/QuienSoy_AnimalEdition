package com.example.proyectofinal;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class Pantalla_juego extends AppCompatActivity {
    public int score=0;
    private CaptionBox captionBox;
    private ScoreBox scoreBox;
    private OpcionesTierra opciones;
    private CountDownTimer timer;
    private static final long TIMEPOXPREG=10000;
    private ProgressBar progressBar;
    private ArrayList<AnimalesTierra_jugar> listAnimales=ListAnimalesTierra_jugar.listAnimalesTierra_jugar;
    private ArrayList<AnimalesTierra_jugar> listRonda;
    private SoundPool soundPool;
    private int soundAcierto, soundError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_juego);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //inicializando para reproducir sonido segun SDK
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP){
            soundPool=new SoundPool.Builder().setMaxStreams(1).build();
        } else {
            soundPool=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        soundAcierto=soundPool.load(this, R.raw.acierto, 1);
        soundError=soundPool.load(this, R.raw.error, 1);

        captionBox=findViewById(R.id.captionBox);
        scoreBox=findViewById(R.id.scoreBox);
        opciones=findViewById(R.id.opciones);
        progressBar=findViewById(R.id.progressTiempo);
        cargarPreguntas();
        mostrarPregunta();
    }

    private void aplicarAnimacion(View v){
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_fade));
    }

    private void mostrarPregunta(){
        if(listRonda.isEmpty()){
            //reiniciamos lista para que si el usuario desea volver a jugar, no esté vacía
            cargarPreguntas();
            Toast.makeText(this, "Ronda terminada!", Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                //quiere decir que el usuario ya acertó todos los animales
                Intent intent = new Intent(this, Pantalla_ganar.class);
                startActivity(intent);
            }, 1200);
            return;
        }

        AnimalesTierra_jugar animal=listRonda.get(0);
        captionBox.setCaption(animal.getCaption());
        scoreBox.setScore(score);
        aplicarAnimacion(captionBox);

        if(timer!=null){ timer.cancel(); }
        progressBar.setMax((int)TIMEPOXPREG);
        progressBar.setProgress((int)TIMEPOXPREG);
        //iniciar timer
        timer=new CountDownTimer(TIMEPOXPREG, 50){
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                //si se acaba el tiempo, pasa a la sig pregunta
                soundPool.play(soundError, 1, 1, 0, 0, 1f); //sonido de error
                Collections.rotate(listRonda, -1);
                mostrarPregunta();
            }
        }.start();

        opciones.setOpciones(this, animal.getId(), listAnimales, idSeleccionado -> {
            timer.cancel();
            progressBar.setProgress(0);

            if (idSeleccionado==animal.getId()) {
                score++;
                scoreBox.setScore(score);
                soundPool.play(soundAcierto, 1, 1, 0, 0, 1f); //sonido de acierto
                listRonda.remove(0); //se quita el que acertó
                mostrarPregunta();
            } else {
                // incorrecto
                soundPool.play(soundError, 1, 1, 0, 0, 1f); //sonido de error
                Collections.rotate(listRonda, -1);
                mostrarPregunta();
            }
        });
        aplicarAnimacion(opciones);
    }

    private void cargarPreguntas(){
        listRonda=new ArrayList<>(listAnimales);
        //mezclando la lista de animales antes para no tener el mismo orden siempre
        Collections.shuffle(listRonda);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool=null;
    }
}