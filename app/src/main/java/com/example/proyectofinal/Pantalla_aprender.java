package com.example.proyectofinal;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;

public class Pantalla_aprender extends AppCompatActivity implements SensorEventListener {
    //integración de sensores
    private SensorManager sensorManager;
    private Sensor sensor_acelerometro;
    private static final float UMBRAL_SHAKE=15f; //sensibilidad del gesto de sacudir
    private long ultimoMov=0;
    private String categoria;

    NameBox namebox;
    InfoBox infobox;
    ImageView img;
    TextView txtTam, txtPeso, txtPeligrosidad, txtApariencia, txtHabitat, txtDieta, txtGeneralidades;
    private ArrayList<Animales_aprender> listAnimalesAgua=ListAnimalesAgua_aprender.listAnimalesAgua_aprender;
    private ArrayList<Animales_aprender> listAnimalesAire=ListAnimalesAire_aprender.listAnimalesAire_aprender;
    private ArrayList<Animales_aprender> listAnimalesTierra=ListAnimalesTierra_aprender.listAnimalesTierra_aprender;
    ArrayList<Animales_aprender> listRonda;
    private SoundPool soundPool;
    private int soundAcierto, soundError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pantalla_aprender);
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

        namebox=findViewById(R.id.nameBox);
        img=findViewById(R.id.img);

        infobox=findViewById(R.id.info);
        txtTam=infobox.findViewById(R.id.tamano);
        txtPeso=infobox.findViewById(R.id.peso);
        txtPeligrosidad=infobox.findViewById(R.id.peligro_texto);
        txtApariencia=infobox.findViewById(R.id.apariencia_texto);
        txtHabitat=infobox.findViewById(R.id.habitat_texto);
        txtDieta=infobox.findViewById(R.id.dieta_texto);
        txtGeneralidades=infobox.findViewById(R.id.generalidades_texto);

        cargarLista();
        mostrarAnimal();
        //configurando el sensor
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor_acelerometro=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //listener de toque
        View main=findViewById(R.id.main);
        main.setOnClickListener(v -> animalMemorizado());
    }

    private void aplicarAnimacion(View v){
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_fade));
    }

    private void mostrarAnimal(){
        if(listRonda.isEmpty()){
            //reiniciarl la lista por si el usuario quiere volver a aprender no esté vacía
            cargarLista();
            Toast.makeText(this, "Ronda de aprendizaje terminada!", Toast.LENGTH_SHORT).show();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                //quiere decir que el usuario concluyó la ronda de aprendizaje
                Intent intent = new Intent(this, Pantalla_ganar.class);
                startActivity(intent);
            }, 1200);
            return;
        }

        Animales_aprender a=listRonda.get(0);
        namebox.setNombre(a.getNombre());
        img.setImageResource(a.getImg());
        txtTam.setText("Tamaño: "+a.getTamano());
        txtPeso.setText("Peso: "+a.getPeso());
        txtPeligrosidad.setText(a.getPeligrosidad());
        txtApariencia.setText(a.getApariencia());
        txtHabitat.setText(a.getHabitat());
        txtDieta.setText(a.getDieta());
        txtGeneralidades.setText("Generalidades: "+a.getGeneralidades());
        aplicarAnimacion(namebox);
        aplicarAnimacion(img);
        aplicarAnimacion(infobox);
    }

    private void animalMemorizado(){
        soundPool.play(soundAcierto, 1, 1, 0, 0, 1f); //sonido de acierto
        listRonda.remove(0); //se elimina porque significa que ya lo memorizó
        mostrarAnimal();
    }

    private void cargarLista(){
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
        //mezclamos el arreglo pra no tener el mismo orden
        Collections.shuffle(listRonda);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x=event.values[0];
        float y=event.values[1];
        float z=event.values[2];
        float fuerza=(float) Math.sqrt(x*x+y*y+z*z);

        if(fuerza>UMBRAL_SHAKE){
            long ahora=System.currentTimeMillis();
            if(ahora-ultimoMov>800) {
                ultimoMov=ahora;
                //si sacude el dispositivo es porque desea mandar el animal al final de lista
                //para repasar, porque no lo memorizó
                soundPool.play(soundError, 1, 1, 0, 0, 1f); //sonido de error
                Collections.rotate(listRonda, -1);
                mostrarAnimal();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor_acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool=null;
    }
}