package com.golthr.pikey;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextPaint;

import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 白键
 */

public class EntityKey extends Key {

    private TextPaint textPaint;
    private int unPressedColor;
    private int pressedColor;
    private PointF pointF;
    private String textToDraw;
    private int radius;

    public EntityKey(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    public static List<EntityKey> generatorEntityKey(float gridWidth, float gridHeight, float margin, int radius, int unPressedColor, int pressedColor, TextPaint tp, float textYCoordinate) {
        ArrayList<EntityKey> list = new ArrayList<>();
        Paint kp = new Paint(Paint.ANTI_ALIAS_FLAG);
        kp.setFilterBitmap(true);
        kp.setDither(true);

        float l = 0, t = 0, r = 0, b = 0;
        for(int line = 0; line < 5; line++){
            int len = Const.KEYBOARD_CODE[line].length;
            t = line * gridHeight;
            b = t + gridHeight;
            for (int i = 0; i < len; i++) {
                if(line == 2 && i == 13){
                    l += Const.KEYBOARD_KEY_GRID[line - 1][14] * gridWidth * 3;
                }else if(line == 3 && (i == 12 || i == 13)){
                    l += Const.KEYBOARD_KEY_GRID[line][i] * gridWidth;
                }
                if((line == 1 && i == len - 1) || (line == 3 && i == len - 1)){
                    // Draw Num= & NumEnter
                    b = t + 2 * gridHeight;
                }
                r = l + Const.KEYBOARD_KEY_GRID[line][i] * gridWidth;
                EntityKey key = new EntityKey(l + margin, t + margin, r - margin, b - margin);
                key.setUnPressedBitmap(unPressedColor);
                key.setPressedBitmap(pressedColor);
                key.setTextPaint(tp);
                key.setDrawTextCoordinate(l + 0.3f * gridWidth, t + 0.3f * gridHeight);
                key.setRollCallTextPointF((l + r) / 2, tp.getTextSize() / 2 + (b + t) / 2);
                key.setTextToDraw(Const.KEYBOARD_RANGE[line][i]);
                key.setKeyCode(Const.KEYBOARD_CODE[line][i]);
                key.setRadius(radius);
                list.add(key);
                l = r;
            }
            l = 0;
        }
        return list;
    }


    @Override
    protected Paint getKeyTextPaint() {
        return textPaint;
    }

    @Override
    protected int getUnPressedColor() {
        return unPressedColor;
    }

    public void setUnPressedBitmap(int unPressedColor) {
        this.unPressedColor = unPressedColor;
    }

    @Override
    protected int getPressedColor() {
        return pressedColor;
    }

    public void setPressedBitmap(int pressedColor) {
        this.pressedColor = pressedColor;
    }

    @Override
    protected String getTextToDraw() {
        return textToDraw;
    }

    public void setTextToDraw(String textToDraw) {
        this.textToDraw = textToDraw;
    }

    @Override
    protected PointF getTextPoint() {
        return pointF;
    }

    public void setDrawTextCoordinate(float x, float y) {
        this.pointF = new PointF(x, y);
    }


    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    @Override
    protected int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
