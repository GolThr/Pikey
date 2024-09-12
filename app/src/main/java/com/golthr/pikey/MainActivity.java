package com.golthr.pikey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midisynthesizer.MidiSynthesizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private MidiSynthesizer synthesizer;
    private PianoKeyBoard pianoKeyboard;
    private KeyBoard keyboard;
    private TextView tv_tonality;
    private TextView tv_kmp_name;
    private int num = 0;
    private final static int START_KEY = 17;
    private int chan = 0;
    private Properties props;
    private final static String SFNAME = "Grand Piano";
    private boolean isMute = true;
    private boolean isDrum = false;
    private int CurTonality = 0;
    KeyMap kmp = new KeyMap();
    private HashMap<Integer, Integer> KEY2PITCH_C = new HashMap<>();
    private HashMap<Integer, Integer> KEY2PITCH = new HashMap<>();
    private HashMap<Integer, Integer> Tonality = new HashMap<>();
    private String[] TONE2NAME = {"C", "bD", "D", "bE", "E", "F", "#F", "G", "bA", "A", "bB", "B"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView6 = getWindow().getDecorView();
        int uiOptions6 = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView6.setSystemUiVisibility(uiOptions6);
        getSupportActionBar().hide();

        pianoKeyboard = (PianoKeyBoard) findViewById(R.id.piano_keyboard);
        keyboard = (KeyBoard) findViewById(R.id.keyboard);
        tv_kmp_name = (TextView) findViewById(R.id.tv_kmp_name);
        tv_tonality = (TextView) findViewById(R.id.tv_tonality);

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int width = dm.widthPixels;  // 屏幕宽度（像素）
//        int height = dm.heightPixels;  // 屏幕高度（像素）
//        int dpi = dm.densityDpi;
//        double p = Math.sqrt((width / dpi) * (width / dpi) + (height / dpi) * (height / dpi));
//        if(p < 6){
//            pianoKeyboard.setMinimumHeight(100);
//        }

        // 初始化文件
        try {
            Utils.doCopy(this, "Keymap");
            Utils.doCopy(this, "SoundFonts");
        } catch (IOException e) {
            e.printStackTrace();
        }

        initKeymap();


        Tonality.put(0, 0);      // C
        Tonality.put(1, 1);      // bD
        Tonality.put(2, 2);      // D
        Tonality.put(3, 3);      // bE
        Tonality.put(4, 4);      // E
        Tonality.put(5, 5);      // F
        Tonality.put(6, 6);      // #F
        Tonality.put(7, 7);      // G
        Tonality.put(8, -4);     // bA
        Tonality.put(9, -3);     // A
        Tonality.put(10, -2);    // bB
        Tonality.put(11, -1);    // B


        // 初始化乐器
        try {
            if (synthesizer == null) {
                synthesizer = new MidiSynthesizer(getApplicationContext(), SFNAME + ".sf2", 1.0f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        pianoKeyboard.setKeyListener(new PianoKeyBoard.KeyListener() {
            @Override
            public void onKeyPressed(PianoKey key) {
                if (synthesizer != null) {
                    synthesizer.noteon(chan, key.getKeyCode(), chan == 9 ? 120 : 75);
                }
            }

            @Override
            public void onKeyUp(PianoKey key) {
                if (synthesizer != null) {
                    synthesizer.noteoff(chan, key.getKeyCode());
                }
            }

            @Override
            public void currentFirstKeyPosition(int i) {
//                if (seekBar.getProgress() != i) {
//                    seekBar.setProgress(i);
//                }
            }
        });

        keyboard.setKeyListener(new KeyBoard.KeyListener() {
            @Override
            public void onKeyPressed(Key key) {
                int pitch = KEY2PITCH.get(key.getKeyCode());
                if (synthesizer != null) {
                    synthesizer.noteon(chan, pitch, chan == 9 ? 120 : 75);
                    if(pitch >= 21 && pitch <= 109){
                        pianoKeyboard.noteDown(pitch);
                    }
                }
            }

            @Override
            public void onKeyUp(Key key) {
                int pitch = KEY2PITCH.get(key.getKeyCode());
                if (synthesizer != null) {
                    synthesizer.noteoff(chan, pitch);
                    if(pitch >= 21 && pitch <= 109){
                        pianoKeyboard.noteUp(pitch);
                    }
                }
            }

            @Override
            public void onLoad(KeyBoard kb) {
                kb.setAllNots(kmp.getKey2name());
            }
        });


        // 配置延音开关
        LinearLayout sw = (LinearLayout) findViewById(R.id.switch_sound);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t = (TextView) view.findViewById(R.id.switch_sound_text);
                if (!isMute) {
                    isMute = true;
                    Resources resources = getApplicationContext().getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.shape_on);
                    view.setBackground(drawable);
                    t.setText("on");
                } else {
                    isMute = false;
                    Resources resources = getApplicationContext().getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.shape);
                    view.setBackground(drawable);
                    t.setText("off");
                }
            }
        });

        // 配置鼓组开关
        sw = findViewById(R.id.switch_drum);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t = (TextView) view.findViewById(R.id.switch_drum_text);
                if (synthesizer != null) {
                    synthesizer.mute(chan);
                }
                chan = !isDrum ? 9 : 0;
                if(!isDrum){
                    isDrum = true;
                    Resources resources = getApplicationContext().getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.shape_on);
                    view.setBackground(drawable);
                    t.setText("on");
                }else{
                    isDrum = false;
                    Resources resources = getApplicationContext().getResources();
                    Drawable drawable = resources.getDrawable(R.drawable.shape);
                    view.setBackground(drawable);
                    t.setText("off");
                }
            }
        });

        // 单独处理配置选择乐器的按钮
        setupSelectProgramBtn();

        setupSelectKmpBtn();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int pitch = 21;
        if(isMute == true && keyCode == KeyEvent.KEYCODE_BACK) synthesizer.mute(-1);
        if(KEY2PITCH.containsKey(keyCode)){
            pitch = KEY2PITCH.get(keyCode);
        }else{
            return false;
        }
        if (synthesizer != null) {
            if(event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0){
                synthesizer.noteon(chan, pitch, chan == 9 ? 120 : 75);
                keyboard.noteDown(keyCode);
                if(pitch >= 21 && pitch <= 109){
                    pianoKeyboard.noteDown(pitch);
                }
            }else if(event.getAction() == KeyEvent.ACTION_UP){
                keyboard.noteUp(keyCode);
                if (isMute == false) {
                    synthesizer.noteoff(chan, pitch);
                }
                if (pitch >= 21 && pitch <= 109) {
                    pianoKeyboard.noteUp(pitch);
                }
            }
        }
        return true;
    }

    private void setupSelectKmpBtn() {
        File externalDir = this.getExternalFilesDir(null);
        File files = new File(externalDir + "/Keymap/");
        String[] someFiles = files.list();
        final List<String> fitems = new ArrayList<String>();
        final List<String> fixNames = new ArrayList<String>();
        for (String str : someFiles){
            String s = str.toLowerCase();
            if(s.endsWith(".kmp")){
                fitems.add(str.substring(0, s.length() - 4));
                fixNames.add(str);
            }
        }
        RelativeLayout btn = (RelativeLayout) findViewById(R.id.btn_select_kmp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final String[] items = fitems.toArray(new String[fitems.size()]);
                final String[] lname = fixNames.toArray(new String[fixNames.size()]);
                builder.setTitle("选择键位")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectKmp(lname[which], items[which]);
                            }
                        });
                builder.show();
            }
        });
    }

    private void selectKmp(final String name, String shortName) {
        KEY2PITCH_C = kmp.parseKeymap(this, "external", name);
        KEY2PITCH.putAll(KEY2PITCH_C);
        // Move to C
        CurTonality = 0;
        tv_tonality.setText(TONE2NAME[CurTonality]);
        tv_kmp_name.setText(shortName);
        keyboard.setAllNots(kmp.getKey2name());
    }

    private void setupSelectProgramBtn() {
        props = getInstrumentsProperties();

        RelativeLayout btn = (RelativeLayout) findViewById(R.id.button_select_program);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final String[] items = props.stringPropertyNames().toArray(new String[props.size()]);
                builder.setTitle("选择音源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                selectProgram(items[which]);
                            }
                        });
                builder.show();
            }
        });
    }

    private void selectProgram(final String name) {
        if (synthesizer == null) return;
        String val = props.getProperty(name);
        String[] vals = val.split(",");
        synthesizer.selectProgram(0, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
//        ((Button) findViewById(R.id.button_select_program)).setText(name);
    }

    private Properties getInstrumentsProperties() {
        Properties props = new Properties();
        try {
            props.load(getAssets().open(SFNAME + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public void initKeymap() {
        KEY2PITCH_C = kmp.parseKeymap(this, "external", "default.kmp");
        KEY2PITCH.putAll(KEY2PITCH_C);
    }

    public void onRisingTone(View view){
        CurTonality++;
        if(CurTonality > 11) CurTonality = 0;
        Collection<Integer> keys = KEY2PITCH.keySet();
        for(int k : keys){
            KEY2PITCH.put(k, KEY2PITCH_C.get(k) + Tonality.get(CurTonality));
        }
        tv_tonality.setText(TONE2NAME[CurTonality]);
    }

    public void onFallingTone(View view){
        CurTonality--;
        if(CurTonality < 0) CurTonality = 11;
        Collection<Integer> keys = KEY2PITCH.keySet();
        for(int k : keys){
            KEY2PITCH.put(k, KEY2PITCH_C.get(k) + Tonality.get(CurTonality));
        }
        tv_tonality.setText(TONE2NAME[CurTonality]);
    }

    public void finishApp(View view) {
        finish();
    }
}
