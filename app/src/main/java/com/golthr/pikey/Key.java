package com.golthr.pikey;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by issuser on 2018/3/8 0008.
 */

public abstract class Key {

    private RectF rectF;
    private boolean isPressed;
    private Paint keyPaint;
    private int keyCode;
    private String rollCall = "";
    private int series = 0;
    private String tone = "";
    private PointF rollCallTextPointF;


    public Key(float left, float top, float right, float bottom) {
        rectF = new RectF(left, top, right, bottom);
        keyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        keyPaint.setFilterBitmap(true);
        keyPaint.setDither(true);
    }

    public final void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public final void drawKey(Canvas canvas) {
        int color;
        int tColor = getKeyTextPaint().getColor();
        if (canvas == null) {
            return;
        }
        if (isPressed) {
            color = getPressedColor();
        } else {
            color = getUnPressedColor();
        }
        keyPaint.setColor(color);
        canvas.drawRoundRect(rectF, getRadius(), getRadius(), keyPaint);

        String textToDraw = getTextToDraw();
        PointF textPoint = getTextPoint();
        if (!TextUtils.isEmpty(textToDraw) && getKeyTextPaint() != null && textPoint != null) {
            getKeyTextPaint().setTextAlign(Paint.Align.LEFT);
            getKeyTextPaint().setColor(Color.parseColor("#808080"));
            canvas.drawText(textToDraw, textPoint.x, textPoint.y, getKeyTextPaint());
        }
        drawRollCall(canvas, tColor);
    }

    private void drawRollCall(Canvas canvas, int tColor) {
        Paint ktp = getKeyTextPaint();
        ktp.setTextAlign(Paint.Align.CENTER);
        ktp.setColor(tColor);
        int n = series == 0 ? 0 : series - 4;
        float ny;
        if(n == 0) {
            ny = rollCallTextPointF.y;
            canvas.drawText(rollCall, rollCallTextPointF.x, ny, ktp);
        }else if(n > 0) {
            ny = rollCallTextPointF.y + 0.7f * ktp.getTextSize();
            float y = ny - ktp.getTextSize();
            canvas.drawText(rollCall, rollCallTextPointF.x, ny, ktp);
            for (int i = 0; i < Math.abs(n); i++){
                canvas.drawText(".", rollCallTextPointF.x, y, ktp);
                y -= ktp.getTextSize() * 0.3f;
            }
        }else{
            ny = rollCallTextPointF.y;
            float y = ny + 0.5f * ktp.getTextSize();
            canvas.drawText(rollCall, rollCallTextPointF.x, ny, ktp);
            for (int i = 0; i < Math.abs(n); i++){
                canvas.drawText(".", rollCallTextPointF.x, y, ktp);
                y += ktp.getTextSize() * 0.3f;
            }
        }
        canvas.drawText(tone, rollCallTextPointF.x - ktp.getTextSize() / 2, ny, ktp);
    }

    protected abstract Paint getKeyTextPaint();

    protected abstract int getUnPressedColor();

    protected abstract int getPressedColor();

    protected abstract String getTextToDraw();

    protected abstract PointF getTextPoint();

    protected abstract int getRadius();


    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public void setRollCall(String rollCall) {
        this.rollCall = rollCall;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public void setRollCallTextPointF(float x, float y) {
        this.rollCallTextPointF = new PointF(x, y);;
    }

    public RectF getRectF() {
        return rectF;
    }
}
