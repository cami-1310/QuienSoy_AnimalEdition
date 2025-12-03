package com.example.proyectofinal;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayer extends Service {
    private MediaPlayer mediaPlayer;
    private Boolean isPaused=false;
    //para exponer los métodos públicos
    private final IBinder binder=new MusicPlayerBinder();

    public class MusicPlayerBinder extends Binder {
        public MusicPlayer getService(){
            return MusicPlayer.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //inicializando reproductor
        mediaPlayer=MediaPlayer.create(this, R.raw.musica_fondo);
        mediaPlayer.setLooping(true); //repetir indefinidamente
        mediaPlayer.setVolume(100, 100); //volumen
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mediaPlayer.isPlaying() && !isPaused){
            //si no esta reproduciendo musica, que reproduzca
            mediaPlayer.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        if(mediaPlayer!=null){
            //liberar recursos
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void pauseMusic() {
        if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPaused=true;
        }
    }

    public void resumeMusic() {
        if (mediaPlayer!=null && isPaused) {
            mediaPlayer.start();
            isPaused=false;
        }
    }

    public boolean isPaused(){
        return mediaPlayer!=null && isPaused;
    }

    public boolean isPlaying(){
        return mediaPlayer!=null && mediaPlayer.isPlaying();
    }
}