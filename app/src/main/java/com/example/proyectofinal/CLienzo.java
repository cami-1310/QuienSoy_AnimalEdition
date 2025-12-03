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

public class CLienzo extends View {
    //agregando constructores
    public CLienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CLienzo(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p=new Paint();
        p.setShader(new LinearGradient(0,0,0,getHeight(),
                0xFFA9C45B, 0xFFEA8439, Shader.TileMode.CLAMP));
        canvas.drawRect(0,0,getWidth(),getHeight(), p);
    }
}