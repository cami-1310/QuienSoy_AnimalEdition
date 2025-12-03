package com.example.proyectofinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class NameBox extends View {
    String nombre="";

    public void setNombre(String nombre){
        this.nombre=nombre;
        invalidate();
    }

    public NameBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NameBox(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Typeface font=getResources().getFont(R.font.slackey_regular);

        Paint nameBox=new Paint();
        nameBox.setColor(0xFFFFFFFF);
        nameBox.setAntiAlias(true);

        TextPaint namePaint=new TextPaint();
        namePaint.setColor(0xFF1E3051);
        namePaint.setTextSize(60f);
        namePaint.setAntiAlias(true);
        namePaint.setTypeface(font);

        float textHeight=namePaint.getTextSize();
        float padding=40f;

        canvas.drawRoundRect(
                0,
                0,
                getWidth(),
                textHeight + padding * 2,
                0, 0,
                nameBox
        );

        namePaint.setTextAlign(Paint.Align.LEFT);
        float marginLeft=60f;
        canvas.drawText(
                nombre,
                marginLeft,
                padding + textHeight * 0.85f,
                namePaint
        );
    }
}