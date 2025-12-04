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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pantalla_juego extends AppCompatActivity {
    public int score=0;
    private CaptionBox captionBox;
    private ScoreBox scoreBox;
    private Opciones opciones;
    private CountDownTimer timer;
    private static final long TIMEPOXPREG=30000;
    private ProgressBar progressBar;
    private ArrayList<Animales_jugar> listAnimalesAgua=ListAnimalesAgua_jugar.listAnimalesAgua_jugar;
    private ArrayList<Animales_jugar> listAnimalesAire=ListAnimalesAire_jugar.listAnimalesAire_jugar;
    private ArrayList<Animales_jugar> listAnimalesTierra=ListAnimalesTierra_jugar.listAnimalesTierra_jugar;
    private ArrayList<Animales_jugar> listRonda;
    private ArrayList<Animales_jugar> listAnimales; //lista a enviar para generar las opciones
    private SoundPool soundPool;
    private int soundAcierto, soundError;
    private String categoria;

    //para sqlite
    private String nombreUsuario;
    private JugadorDao jugadorDao; // DAO
    private ExecutorService executorService; // para manejar la inserción asincrona (con hilo secundario)

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

        Intent intent=getIntent();
        categoria=intent.getStringExtra("categoria");

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
        //para sqlite
        nombreUsuario=intent.getStringExtra("username");
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        jugadorDao = db.jugadorDao();
        // inicializar el servicio de ejecución de hilos
        executorService = Executors.newSingleThreadExecutor();
        //----------------------------------------------------------sqlite

        cargarPreguntas();
        mostrarPregunta();
    }

    private void aplicarAnimacion(View v){
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_fade));
    }

    private void mostrarPregunta(){
        if(listRonda.isEmpty()){
            long fechaActual = System.currentTimeMillis(); // La marca de tiempo actual
            Jugador jugador = new Jugador(nombreUsuario, score, fechaActual);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    jugadorDao.insertar(jugador);
                }
            });

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

        Animales_jugar animal=listRonda.get(0);
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
                listRonda.remove(0);
                mostrarPregunta();
            }
        }.start();

        switch (categoria){
            case "agua":
                listAnimales=new ArrayList<>(listAnimalesAgua);
            break;
            case "aire":
                listAnimales=new ArrayList<>(listAnimalesAire);
                break;
            case "tierra":
                listAnimales=new ArrayList<>(listAnimalesTierra);
                break;
        }

        opciones.setOpciones(this, animal.getId(), categoria, listAnimales, idSeleccionado -> {
            timer.cancel();
            progressBar.setProgress(0);

            listRonda.remove(0); //se quita para pasar al siguiente animal

            if (idSeleccionado==animal.getId()) {
                //correcto
                score++;
                scoreBox.setScore(score);
                soundPool.play(soundAcierto, 1, 1, 0, 0, 1f); //sonido de acierto
            } else {
                //incorrecto
                soundPool.play(soundError, 1, 1, 0, 0, 1f); //sonido de error
            }
            mostrarPregunta();
        });
        aplicarAnimacion(opciones);
    }

    private void cargarPreguntas(){
        switch (categoria){
            case "agua":
                listRonda=new ArrayList<>(listAnimalesAgua);
                break;
            case "aire":
                listRonda=new ArrayList<>(listAnimalesAire);
                break;
            case "tierra":
                listRonda=new ArrayList<>(listAnimalesTierra);
                break;
        }
        //mezclando la lista de animales antes para no tener el mismo orden siempre
        Collections.shuffle(listRonda);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool=null;
        //executor del hilo de guardar score
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}