package com.example.proyectofinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class ScoreBox extends View {
    int score=0;

    public void setScore(int score){
        this.score=score;
        invalidate();
    }

    public ScoreBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ScoreBox(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Typeface font=getResources().getFont(R.font.slackey_regular);

        Paint scoreBox=new Paint();
        scoreBox.setColor(0x88FFFFFF);
        scoreBox.setAntiAlias(true);

        TextPaint scorePaint=new TextPaint();
        scorePaint.setColor(0xFF1E3051);
        scorePaint.setTextSize(60f);
        scorePaint.setAntiAlias(true);
        scorePaint.setTypeface(font);
        String texto="Score: "+score;
        float textWidth=scorePaint.measureText(texto);
        float textHeight=scorePaint.getTextSize();
        float padding=50f;

        canvas.drawRoundRect(
                0,
                0,
                textWidth + padding * 2,
                textHeight + padding * 2,
                20, 20,
                scoreBox
        );

        scorePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(
                texto,
                210,
                padding + textHeight * 0.85f,
                scorePaint
        );
    }
}