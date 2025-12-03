package com.example.proyectofinal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;

public class OpcionesTierra extends GridLayout {
    private int idCorrecto;
    private ListenerOpciones listener;
    private ImageButton opc1, opc2, opc3, opc4;
    private ArrayList<AnimalesTierra_jugar> listAnimales=ListAnimalesTierra_jugar.listAnimalesTierra_jugar;

    public interface ListenerOpciones {
        void onOpcionSeleccionada(int idAnimalSeleccionado);
    }

    public OpcionesTierra(Context context){
        super(context);
        inicializar(context);
    }

    public OpcionesTierra(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar(context);
    }

    public OpcionesTierra(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inicializar(context);
    }

    public void inicializar(Context context){
        inflate(context, R.layout.opciones_layout, this);
        opc1=findViewById(R.id.opc1);
        opc2=findViewById(R.id.opc2);
        opc3=findViewById(R.id.opc3);
        opc4=findViewById(R.id.opc4);
    }

    public void setOpciones(Context context, int idCorrecto, ArrayList<AnimalesTierra_jugar> listAnimales, ListenerOpciones listener) {
        this.idCorrecto=idCorrecto;
        this.listener=listener;
        this.listAnimales=listAnimales;
        generarOpciones(context);
    }

    //obtener imagen para cada opc
    private int getResId(Context context, int num){
        String name="op_tierra"+num;
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    private void generarOpciones(Context context) {
        AnimalesTierra_jugar correcto=null;
        for(AnimalesTierra_jugar a:listAnimales) {
            if (a.getId() == idCorrecto) {
                correcto=a;
                break;
            }
        }

        ArrayList<AnimalesTierra_jugar> opciones=new ArrayList<>();
        opciones.add(correcto);

        //para generar 4 opciones incorrectas + 1 correcta
        while (opciones.size()<4){
            AnimalesTierra_jugar random=listAnimales.get((int)(Math.random()*listAnimales.size()));
            //evitamos repetir la opcion correcta
            if(random.getId()==correcto.getId()) continue;

            //no repetir opciones
            boolean repetido=false;
            for(AnimalesTierra_jugar op:opciones) {
                if (op.getId()==random.getId()) {
                    repetido=true;
                    break;
                }
            }
            if(repetido) continue;
            opciones.add(random);
        }

        //mezclar las 4 opciones
        Collections.shuffle(opciones);

        opc1.setImageResource(getResId(context, opciones.get(0).getId()));
        opc2.setImageResource(getResId(context, opciones.get(1).getId()));
        opc3.setImageResource(getResId(context, opciones.get(2).getId()));
        opc4.setImageResource(getResId(context, opciones.get(3).getId()));

        opc1.setOnClickListener(v -> listener.onOpcionSeleccionada(opciones.get(0).getId()));
        opc2.setOnClickListener(v -> listener.onOpcionSeleccionada(opciones.get(1).getId()));
        opc3.setOnClickListener(v -> listener.onOpcionSeleccionada(opciones.get(2).getId()));
        opc4.setOnClickListener(v -> listener.onOpcionSeleccionada(opciones.get(3).getId()));
    }
}