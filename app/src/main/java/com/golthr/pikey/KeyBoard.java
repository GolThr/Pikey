package com.golthr.pikey;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
 * 自定义的钢琴键盘  88个键  支持多指滑动
 */
public class KeyBoard extends View {

    private ArrayList<Key> list = new ArrayList<>();
    private ArrayList<Key> reverseList = new ArrayList<>();
    private HashMap<Integer, Key> keyMap = new HashMap<>();
    private float keyBoardWidth;
    private float keyBoardHeight;
    private float gridWidth;
    private float gridHeight;
    private int keyboardLines = 5;
    private float margin = 2;
    private int radius = 10;
    private int keyTextColor = Color.GRAY;
    private float keyTextDimension = 20;
    private float keyTextYRatio = 0.5f;
    private int keyColor = Color.parseColor("#dadada");;
    private int keyPressedColor = Color.parseColor("#c7c7c7");;
    private TextPaint keyTextPaint;
    private int min_size = 10;
    private KeyListener keyListener;

    public KeyBoard(Context context) {
        super(context);
        init(null, 0);
    }

    public KeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public KeyBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.KeyBoard, defStyle, 0);

        keyTextColor = a.getColor(R.styleable.KeyBoard_keyTextColor, keyTextColor);
        keyTextDimension = a.getDimension(R.styleable.KeyBoard_keyTextSize, keyTextDimension);
        keyColor = a.getColor(R.styleable.KeyBoard_keyColor, keyColor);
        keyPressedColor = a.getColor(R.styleable.KeyBoard_keyPressedColor, keyPressedColor);
        margin = a.getFloat(R.styleable.KeyBoard_keyMargin, margin);
        radius = a.getInt(R.styleable.KeyBoard_keyRadius, radius);

        a.recycle();
        keyTextPaint = new TextPaint();
        keyTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        keyTextPaint.setTextAlign(Paint.Align.LEFT);
        invalidatePaintAndMeasurements();
    }

    private void invalidatePaintAndMeasurements() {
        keyTextPaint.setTextSize(keyTextDimension);
        keyTextPaint.setColor(keyTextColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0, 0);
        if (list != null) {
            for (Key key : list) {
                key.drawKey(canvas);
            }
        }
    }

    public void setAllNots(HashMap<Integer, String> key2name) {
        HashMap<Character, Integer> rollCall = new HashMap<>();
        rollCall.put('C', 1);
        rollCall.put('D', 2);
        rollCall.put('E', 3);
        rollCall.put('F', 4);
        rollCall.put('G', 5);
        rollCall.put('A', 6);
        rollCall.put('B', 7);
        Collection<Integer> keys = key2name.keySet();
        for(int k : keys){
            Key key = getKeyByKeycode(k);
            String name = key2name.get(k);
            if(name == null) continue;
            if(key == null) continue;
            if(name.charAt(0) == 'b' || name.charAt(0) == '#'){
                key.setTone(String.valueOf(name.charAt(0)));
                key.setRollCall(String.valueOf(rollCall.get(name.charAt(1))));
                key.setSeries(Integer.parseInt(String.valueOf(name.charAt(2))));
            }else{
                key.setTone("");
                key.setRollCall(String.valueOf(rollCall.get(name.charAt(0))));
                key.setSeries(Integer.parseInt(String.valueOf(name.charAt(1))));
            }
        }
        invalidate();
    }

    public void noteUp(int kc) {
        Key currentKey = null;
        for (Key key : reverseList) {
            if (key.getKeyCode() == kc) {
                currentKey = key;
                break;
            }
        }
        currentKey.setPressed(false);
        invalidate();
    }

    public void noteDown(int kc) {
        Key currentKey = null;
        for (Key key : reverseList) {
            if (key.getKeyCode() == kc) {
                currentKey = key;
                break;
            }
        }
        currentKey.setPressed(true);
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onNewTouchEvent(event);
        return true;
    }


    private void onNewTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        int pointerIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(pointerIndex);
        float x = motionEvent.getX(pointerIndex);
        float y = motionEvent.getY(pointerIndex);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_POINTER_DOWN:
                onFingerDown(pointerId, x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                onFingerUp(pointerId, x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int j = motionEvent.getPointerCount();
                for (int i = 0; i < j; i++) {
                    onFingerMove(motionEvent.getPointerId(i), motionEvent.getX(i), motionEvent.getY(i));
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                onAllFingersUp();
                break;
        }
    }

    private void onFingerUp(int index, float x, float y) {
        Key key = this.keyMap.get(Integer.valueOf(index));
        if (key != null) {
            fireKeyUp(key);
            this.keyMap.remove(index);
        } else {
            Key key1 = pointerInWhichKey(x, y);
            fireKeyUp(key1);
        }
    }

    private void onFingerDown(int index, float x, float y) {
        Key key = pointerInWhichKey(x, y);
        fireKeyDown(key);
        this.keyMap.put(Integer.valueOf(index), key);
    }


    private void onAllFingersUp() {
        Iterator<Key> localIterator = this.keyMap.values().iterator();
        while (localIterator.hasNext()) {
            fireKeyUp(localIterator.next());
        }
        this.keyMap.clear();
    }

    private void onFingerMove(int index, float x, float y) {
        Key key = this.keyMap.get(Integer.valueOf(index));
        Key currentKey = pointerInWhichKey(x, y);
        if (key != null) {
            if ((currentKey != null) && (currentKey != key)) {
                fireKeyDown(currentKey);
                fireKeyUp(key);
                this.keyMap.put(index, currentKey);
            }
        }
    }


    private void fireKeyUp(Key key) {
        if (key != null) {
            if (keyListener != null) {
                keyListener.onKeyUp(key);
            }
            key.setPressed(false);
        }
        invalidate();
    }

    private void fireKeyDown(Key key) {
        if (key != null) {
            if (keyListener != null) {
                keyListener.onKeyPressed(key);
            }
            key.setPressed(true);
        }
        invalidate();
    }

    private Key pointerInWhichKey(float x, float y) {
        Key currentKey = null;
        for (Key key : reverseList) {
            if (key.getRectF().contains(x, y)) {
                currentKey = key;
                break;
            }
        }
        return currentKey;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyBoardWidth = w;
        keyBoardHeight = h;

        initKeys();
    }

    private void initKeys() {
        list.clear();
        gridWidth = keyBoardWidth / 43;
//        gridHeight = keyBoardHeight / 5;
        gridHeight = gridWidth * 2;
        list.addAll(EntityKey.generatorEntityKey(gridWidth, gridHeight, margin, radius, keyColor, keyPressedColor, keyTextPaint, gridHeight * keyTextYRatio));
        reverseList.clear();
        reverseList.addAll(list);
        Collections.reverse(reverseList);
        invalidate();
        keyListener.onLoad(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int grid = widthMeasureSpec;
        setMeasuredDimension(widthMeasureSpec, measureH(heightMeasureSpec, grid));
    }

    private int measureH(int measureSpec, int grid) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int sizew = MeasureSpec.getSize(grid) / 43;
        if (mode == MeasureSpec.EXACTLY) {
            return size;
        } else {
            result = Math.round(keyboardLines * 2 * sizew + 2 * keyboardLines * margin);
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(size, result);
            }
        }
        return result;
    }


    public void setKeyListener(KeyListener listener) {
        this.keyListener = listener;
    }


    /**
     * 设置按键字体颜色
     *
     * @param keyTextColor 颜色的值
     */
    public void setkeyTextColor(int keyTextColor) {
        this.keyTextColor = keyTextColor;
        invalidatePaintAndMeasurements();
        initKeys();
    }

    /**
     * 设置按键字体大小
     *
     * @param keyTextDimension 字体的像素大小
     */
    public void setKeyTextDimension(float keyTextDimension) {
        this.keyTextDimension = keyTextDimension;
        invalidatePaintAndMeasurements();
        initKeys();
    }

    /**
     * 设置按键默认颜色
     *
     * @param colorStr
     */
    public void setKeyColor(String colorStr) {
        this.keyColor = Color.parseColor(colorStr);
        initKeys();
    }

    /**
     * 设置按键被按下时的颜色
     *
     * @param colorStr
     */
    public void setKeyPressedColor(String colorStr) {
        this.keyPressedColor = Color.parseColor(colorStr);
        initKeys();
    }

    /**
     * 设置按键外边距
     *
     * @param margin
     */
    public void setKeyMargin(float margin) {
        this.margin = margin;
        initKeys();
    }

    /**
     * 设置按键圆角
     *
     * @param radius
     */
    public void setKeyRadius(int radius) {
        this.radius = radius;
        initKeys();
    }

    /**
     * 根据keycode 获取key
     *
     * @param keycode
     * @return Key may be null
     */
    public Key getKeyByKeycode(int keycode) {
        Key key = null;
        for (Key temp : list) {
            if (temp.getKeyCode() == keycode) {
                key = temp;
                break;
            }
        }
        return key;
    }

    /**
     * 键盘的监听
     */
    public interface KeyListener {
        /**
         * 键盘被按下的回调
         *
         * @param key 被按下的键
         */
        void onKeyPressed(Key key);

        /**
         * 键盘被按松开的回调
         *
         * @param key 被松开的键
         */
        void onKeyUp(Key key);

        /**
         * 键盘初始化成功后回调
         *
         * @param kb 自己本身
         */
        void onLoad(KeyBoard kb);
    }


}
