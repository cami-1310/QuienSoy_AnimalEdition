package com.example.proyectofinal;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Controles extends LinearLayout {
    private ImageButton btnSoundToggle, btnAyuda;
    private MusicPlayer musicService;
    private boolean bound=false, ismuted=false;

    public Controles(Context context) {
        super(context);
        inicializar(context);
    }
    public Controles(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar(context);
    }
    public Controles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicializar(context);
    }

    public void inicializar(Context context){
        inflate(getContext(), R.layout.controles, this);
        btnSoundToggle=findViewById(R.id.btnSoundToggle);
        btnAyuda=findViewById(R.id.btnAyuda);

        btnAyuda.setOnClickListener(v -> mostrarModal(context));

        //conectar con el servicio de MusicPlayer
        Intent intent=new Intent(context, MusicPlayer.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);

        //listeners para los botones para que reproduzcan o detengan la musica
        btnSoundToggle.setOnClickListener(v -> {
            if(!bound) return;

            if(ismuted){
                //boton sonido
                btnSoundToggle.setImageResource(R.drawable.btn_sound);
                musicService.resumeMusic();
                ismuted=false;
            } else{
                //boton mute
                btnSoundToggle.setImageResource(R.drawable.btn_mute);
                musicService.pauseMusic();
                ismuted=true;
            }
        });
    }

    private void mostrarModal(Context context){
        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(context);
        builder.setCancelable(true);

        //inflar layout del modal
        LinearLayout modal=(LinearLayout) inflate(context, R.layout.modal_menu, null);
        ImageView btnSalir=modal.findViewById(R.id.btnSalir);
        builder.setView(modal);
        AlertDialog dialog=builder.create();
        btnSalir.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    //connection para el servicio ligado
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicPlayer.MusicPlayerBinder binder=(MusicPlayer.MusicPlayerBinder) service;
            musicService=binder.getService();
            bound=true;

            //sincronizando el estado del servicio con el icono del boton
            if(musicService.isPaused()){
                btnSoundToggle.setImageResource(R.drawable.btn_mute);
                ismuted=true;
            } else {
                btnSoundToggle.setImageResource(R.drawable.btn_sound);
                ismuted=false;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }
    };
}