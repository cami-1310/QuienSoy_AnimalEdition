package com.example.proyectofinal;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;

public class InfoBox extends TableLayout {

    public InfoBox(Context context) {
        super(context);
        inicializar(context);
    }

    public InfoBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        inicializar(context);
    }

    public void inicializar(Context context){
        inflate(context, R.layout.info_layout, this);
    }
}
