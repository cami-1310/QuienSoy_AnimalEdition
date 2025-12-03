package com.example.proyectofinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class CaptionBox extends View {
    String caption="";

    public void setCaption(String caption){
        this.caption=caption;
        invalidate();
    }

    public CaptionBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CaptionBox(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Typeface font=getResources().getFont(R.font.shadowsintolighttwo_regular);

        Paint boxPaint=new Paint();
        boxPaint.setColor(0xCC333333);
        boxPaint.setAntiAlias(true);
        canvas.drawRoundRect(0, 0, getWidth(), 700, 40, 40, boxPaint);

        TextPaint textPaint=new TextPaint();
        textPaint.setColor(0xFFFFFFFF);
        textPaint.setTextSize(50f);
        textPaint.setAntiAlias(true);
        textPaint.setTypeface(font);

        int padding=60;
        int textWidth=getWidth()-padding*2; //margen izq, der
        StaticLayout textLayout=new StaticLayout(
                caption,
                textPaint,
                textWidth,
                Layout.Alignment.ALIGN_CENTER,
                1f,   //espacio entre líneas
                0f,
                false
        );

        float totalTextHeight=textLayout.getHeight();
        float startY=(700-totalTextHeight)/2;
        canvas.save();
        canvas.translate(padding, startY); //posicion inicial del texto
        textLayout.draw(canvas);
        canvas.restore();
    }
}