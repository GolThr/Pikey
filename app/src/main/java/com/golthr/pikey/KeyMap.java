package com.golthr.pikey;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class KeyMap {
    private HashMap<Integer, String> key2name = new HashMap<>();

    public KeyMap() {
    }

    public HashMap<Integer, Integer> parseKeymap(Context context, String type, String path) {
        HashMap<Integer, Integer> KEY2PITCH = new HashMap<>();
        HashMap<String, Integer> KNAME2KEY = new HashMap<>();
        HashMap<String, Integer> PNAME2PITCH = new HashMap<>();
        // auto
        KEY2PITCH.put(KeyEvent.KEYCODE_GRAVE, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_1, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_2, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_3, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_4, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_5, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_6, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_7, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_8, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_9, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_0, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_MINUS, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_EQUALS, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_DEL, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_TAB, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_Q, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_W, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_E, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_R, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_T, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_Y, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_U, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_I, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_O, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_P, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_LEFT_BRACKET, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_RIGHT_BRACKET, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_BACKSLASH, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_CAPS_LOCK, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_A, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_S, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_D, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_F, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_G, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_H, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_J, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_K, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_L, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_SEMICOLON, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_APOSTROPHE, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_ENTER, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_SHIFT_LEFT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_Z, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_X, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_C, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_V, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_B, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_N, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_M, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_COMMA, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_PERIOD, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_SLASH, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_SHIFT_RIGHT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_CTRL_LEFT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_ALT_LEFT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_SPACE, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_ALT_RIGHT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_CTRL_RIGHT, 21);

        KEY2PITCH.put(KeyEvent.KEYCODE_INSERT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_MOVE_HOME, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_PAGE_UP, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_FORWARD_DEL, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_MOVE_END, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_PAGE_DOWN, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_DPAD_UP, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_DPAD_LEFT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_DPAD_DOWN, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_DPAD_RIGHT, 21);

        KEY2PITCH.put(KeyEvent.KEYCODE_NUM_LOCK, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_0, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_1, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_2, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_3, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_4, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_5, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_6, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_7, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_8, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_9, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_DOT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_DIVIDE, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_MULTIPLY, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_SUBTRACT, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_ADD, 21);
        KEY2PITCH.put(KeyEvent.KEYCODE_NUMPAD_ENTER, 21);

        // KNAME2KEY
        KNAME2KEY.put("~", KeyEvent.KEYCODE_GRAVE);
        KNAME2KEY.put("1", KeyEvent.KEYCODE_1);
        KNAME2KEY.put("2", KeyEvent.KEYCODE_2);
        KNAME2KEY.put("3", KeyEvent.KEYCODE_3);
        KNAME2KEY.put("4", KeyEvent.KEYCODE_4);
        KNAME2KEY.put("5", KeyEvent.KEYCODE_5);
        KNAME2KEY.put("6", KeyEvent.KEYCODE_6);
        KNAME2KEY.put("7", KeyEvent.KEYCODE_7);
        KNAME2KEY.put("8", KeyEvent.KEYCODE_8);
        KNAME2KEY.put("9", KeyEvent.KEYCODE_9);
        KNAME2KEY.put("0", KeyEvent.KEYCODE_0);
        KNAME2KEY.put("-", KeyEvent.KEYCODE_MINUS);
        KNAME2KEY.put("=", KeyEvent.KEYCODE_EQUALS);
        KNAME2KEY.put("Backspace", KeyEvent.KEYCODE_DEL);
        KNAME2KEY.put("Tab", KeyEvent.KEYCODE_TAB);
        KNAME2KEY.put("Q", KeyEvent.KEYCODE_Q);
        KNAME2KEY.put("W", KeyEvent.KEYCODE_W);
        KNAME2KEY.put("E", KeyEvent.KEYCODE_E);
        KNAME2KEY.put("R", KeyEvent.KEYCODE_R);
        KNAME2KEY.put("T", KeyEvent.KEYCODE_T);
        KNAME2KEY.put("Y", KeyEvent.KEYCODE_Y);
        KNAME2KEY.put("U", KeyEvent.KEYCODE_U);
        KNAME2KEY.put("I", KeyEvent.KEYCODE_I);
        KNAME2KEY.put("O", KeyEvent.KEYCODE_O);
        KNAME2KEY.put("P", KeyEvent.KEYCODE_P);
        KNAME2KEY.put("[", KeyEvent.KEYCODE_LEFT_BRACKET);
        KNAME2KEY.put("]", KeyEvent.KEYCODE_RIGHT_BRACKET);
        KNAME2KEY.put("|", KeyEvent.KEYCODE_BACKSLASH);
        KNAME2KEY.put("CapsLock", KeyEvent.KEYCODE_CAPS_LOCK);
        KNAME2KEY.put("A", KeyEvent.KEYCODE_A);
        KNAME2KEY.put("S", KeyEvent.KEYCODE_S);
        KNAME2KEY.put("D", KeyEvent.KEYCODE_D);
        KNAME2KEY.put("F", KeyEvent.KEYCODE_F);
        KNAME2KEY.put("G", KeyEvent.KEYCODE_G);
        KNAME2KEY.put("H", KeyEvent.KEYCODE_H);
        KNAME2KEY.put("J", KeyEvent.KEYCODE_J);
        KNAME2KEY.put("K", KeyEvent.KEYCODE_K);
        KNAME2KEY.put("L", KeyEvent.KEYCODE_L);
        KNAME2KEY.put(";", KeyEvent.KEYCODE_SEMICOLON);
        KNAME2KEY.put("'", KeyEvent.KEYCODE_APOSTROPHE);
        KNAME2KEY.put("Enter", KeyEvent.KEYCODE_ENTER);
        KNAME2KEY.put("LShift", KeyEvent.KEYCODE_SHIFT_LEFT);
        KNAME2KEY.put("Z", KeyEvent.KEYCODE_Z);
        KNAME2KEY.put("X", KeyEvent.KEYCODE_X);
        KNAME2KEY.put("C", KeyEvent.KEYCODE_C);
        KNAME2KEY.put("V", KeyEvent.KEYCODE_V);
        KNAME2KEY.put("B", KeyEvent.KEYCODE_B);
        KNAME2KEY.put("N", KeyEvent.KEYCODE_N);
        KNAME2KEY.put("M", KeyEvent.KEYCODE_M);
        KNAME2KEY.put(",", KeyEvent.KEYCODE_COMMA);
        KNAME2KEY.put(".", KeyEvent.KEYCODE_PERIOD);
        KNAME2KEY.put("?", KeyEvent.KEYCODE_SLASH);
        KNAME2KEY.put("RShift", KeyEvent.KEYCODE_SHIFT_RIGHT);
        KNAME2KEY.put("LCtrl", KeyEvent.KEYCODE_CTRL_LEFT);
        KNAME2KEY.put("LAlt", KeyEvent.KEYCODE_ALT_LEFT);
        KNAME2KEY.put("Space", KeyEvent.KEYCODE_SPACE);
        KNAME2KEY.put("RAlt", KeyEvent.KEYCODE_ALT_RIGHT);
        KNAME2KEY.put("RCtrl", KeyEvent.KEYCODE_CTRL_RIGHT);
        KNAME2KEY.put("Ins", KeyEvent.KEYCODE_INSERT);
        KNAME2KEY.put("Home", KeyEvent.KEYCODE_MOVE_HOME);
        KNAME2KEY.put("PgUp", KeyEvent.KEYCODE_PAGE_UP);
        KNAME2KEY.put("Del", KeyEvent.KEYCODE_FORWARD_DEL);
        KNAME2KEY.put("End", KeyEvent.KEYCODE_MOVE_END);
        KNAME2KEY.put("PgDn", KeyEvent.KEYCODE_PAGE_DOWN);
        KNAME2KEY.put("Up", KeyEvent.KEYCODE_DPAD_UP);
        KNAME2KEY.put("Left", KeyEvent.KEYCODE_DPAD_LEFT);
        KNAME2KEY.put("Down", KeyEvent.KEYCODE_DPAD_DOWN);
        KNAME2KEY.put("Right", KeyEvent.KEYCODE_DPAD_RIGHT);
        KNAME2KEY.put("NumLock", KeyEvent.KEYCODE_NUM_LOCK);
        KNAME2KEY.put("Num0", KeyEvent.KEYCODE_NUMPAD_0);
        KNAME2KEY.put("Num1", KeyEvent.KEYCODE_NUMPAD_1);
        KNAME2KEY.put("Num2", KeyEvent.KEYCODE_NUMPAD_2);
        KNAME2KEY.put("Num3", KeyEvent.KEYCODE_NUMPAD_3);
        KNAME2KEY.put("Num4", KeyEvent.KEYCODE_NUMPAD_4);
        KNAME2KEY.put("Num5", KeyEvent.KEYCODE_NUMPAD_5);
        KNAME2KEY.put("Num6", KeyEvent.KEYCODE_NUMPAD_6);
        KNAME2KEY.put("Num7", KeyEvent.KEYCODE_NUMPAD_7);
        KNAME2KEY.put("Num8", KeyEvent.KEYCODE_NUMPAD_8);
        KNAME2KEY.put("Num9", KeyEvent.KEYCODE_NUMPAD_9);
        KNAME2KEY.put("Num.", KeyEvent.KEYCODE_NUMPAD_DOT);
        KNAME2KEY.put("Num/", KeyEvent.KEYCODE_NUMPAD_DIVIDE);
        KNAME2KEY.put("Num*", KeyEvent.KEYCODE_NUMPAD_MULTIPLY);
        KNAME2KEY.put("Num-", KeyEvent.KEYCODE_NUMPAD_SUBTRACT);
        KNAME2KEY.put("Num+", KeyEvent.KEYCODE_NUMPAD_ADD);
        KNAME2KEY.put("NumEnter", KeyEvent.KEYCODE_NUMPAD_ENTER);

        // PNAME2PITCH
        PNAME2PITCH.put("A0", 21);
        PNAME2PITCH.put("#A0", 22);
        PNAME2PITCH.put("bB0", 22);
        PNAME2PITCH.put("B0", 23);
        PNAME2PITCH.put("C1", 24);
        PNAME2PITCH.put("#C1", 25);
        PNAME2PITCH.put("bD1", 25);
        PNAME2PITCH.put("D1", 26);
        PNAME2PITCH.put("#D1", 27);
        PNAME2PITCH.put("bE1", 27);
        PNAME2PITCH.put("E1", 28);
        PNAME2PITCH.put("F1", 29);
        PNAME2PITCH.put("#F1", 30);
        PNAME2PITCH.put("bG1", 30);
        PNAME2PITCH.put("G1", 31);
        PNAME2PITCH.put("#G1", 32);
        PNAME2PITCH.put("bA1", 32);
        PNAME2PITCH.put("A1", 33);
        PNAME2PITCH.put("#A1", 34);
        PNAME2PITCH.put("bB1", 34);
        PNAME2PITCH.put("B1", 35);
        PNAME2PITCH.put("C2", 36);
        PNAME2PITCH.put("#C2", 37);
        PNAME2PITCH.put("bD2", 37);
        PNAME2PITCH.put("D2", 38);
        PNAME2PITCH.put("#D2", 39);
        PNAME2PITCH.put("bE2", 39);
        PNAME2PITCH.put("E2", 40);
        PNAME2PITCH.put("F2", 41);
        PNAME2PITCH.put("#F2", 42);
        PNAME2PITCH.put("bG2", 42);
        PNAME2PITCH.put("G2", 43);
        PNAME2PITCH.put("#G2", 44);
        PNAME2PITCH.put("bA2", 44);
        PNAME2PITCH.put("A2", 45);
        PNAME2PITCH.put("#A2", 46);
        PNAME2PITCH.put("bB2", 46);
        PNAME2PITCH.put("B2", 47);
        PNAME2PITCH.put("C3", 48);
        PNAME2PITCH.put("#C3", 49);
        PNAME2PITCH.put("bD3", 49);
        PNAME2PITCH.put("D3", 50);
        PNAME2PITCH.put("#D3", 51);
        PNAME2PITCH.put("bE3", 51);
        PNAME2PITCH.put("E3", 52);
        PNAME2PITCH.put("F3", 53);
        PNAME2PITCH.put("#F3", 54);
        PNAME2PITCH.put("bG3", 54);
        PNAME2PITCH.put("G3", 55);
        PNAME2PITCH.put("#G3", 56);
        PNAME2PITCH.put("bA3", 56);
        PNAME2PITCH.put("A3", 57);
        PNAME2PITCH.put("#A3", 58);
        PNAME2PITCH.put("bB3", 58);
        PNAME2PITCH.put("B3", 59);
        PNAME2PITCH.put("C4", 60);
        PNAME2PITCH.put("#C4", 61);
        PNAME2PITCH.put("bD4", 61);
        PNAME2PITCH.put("D4", 62);
        PNAME2PITCH.put("#D4", 63);
        PNAME2PITCH.put("bE4", 63);
        PNAME2PITCH.put("E4", 64);
        PNAME2PITCH.put("F4", 65);
        PNAME2PITCH.put("#F4", 66);
        PNAME2PITCH.put("bG4", 66);
        PNAME2PITCH.put("G4", 67);
        PNAME2PITCH.put("#G4", 68);
        PNAME2PITCH.put("bA4", 68);
        PNAME2PITCH.put("A4", 69);
        PNAME2PITCH.put("#A4", 70);
        PNAME2PITCH.put("bB4", 70);
        PNAME2PITCH.put("B4", 71);
        PNAME2PITCH.put("C5", 72);
        PNAME2PITCH.put("#C5", 73);
        PNAME2PITCH.put("bD5", 73);
        PNAME2PITCH.put("D5", 74);
        PNAME2PITCH.put("#D5", 75);
        PNAME2PITCH.put("bE5", 75);
        PNAME2PITCH.put("E5", 76);
        PNAME2PITCH.put("F5", 77);
        PNAME2PITCH.put("#F5", 78);
        PNAME2PITCH.put("bG5", 78);
        PNAME2PITCH.put("G5", 79);
        PNAME2PITCH.put("#G5", 80);
        PNAME2PITCH.put("bA5", 80);
        PNAME2PITCH.put("A5", 81);
        PNAME2PITCH.put("#A5", 82);
        PNAME2PITCH.put("bB5", 82);
        PNAME2PITCH.put("B5", 83);
        PNAME2PITCH.put("C6", 84);
        PNAME2PITCH.put("#C6", 85);
        PNAME2PITCH.put("bD6", 85);
        PNAME2PITCH.put("D6", 86);
        PNAME2PITCH.put("#D6", 87);
        PNAME2PITCH.put("bE6", 87);
        PNAME2PITCH.put("E6", 88);
        PNAME2PITCH.put("F6", 89);
        PNAME2PITCH.put("#F6", 90);
        PNAME2PITCH.put("bG6", 90);
        PNAME2PITCH.put("G6", 91);
        PNAME2PITCH.put("#G6", 92);
        PNAME2PITCH.put("bA6", 92);
        PNAME2PITCH.put("A6", 93);
        PNAME2PITCH.put("#A6", 94);
        PNAME2PITCH.put("bB6", 94);
        PNAME2PITCH.put("B6", 95);
        PNAME2PITCH.put("C7", 96);
        PNAME2PITCH.put("#C7", 97);
        PNAME2PITCH.put("bD7", 97);
        PNAME2PITCH.put("D7", 98);
        PNAME2PITCH.put("#D7", 99);
        PNAME2PITCH.put("bE7", 99);
        PNAME2PITCH.put("E7", 100);
        PNAME2PITCH.put("F7", 101);
        PNAME2PITCH.put("#F7", 102);
        PNAME2PITCH.put("bG7", 102);
        PNAME2PITCH.put("G7", 103);
        PNAME2PITCH.put("#G7", 104);
        PNAME2PITCH.put("bA7", 104);
        PNAME2PITCH.put("A7", 105);
        PNAME2PITCH.put("#A7", 106);
        PNAME2PITCH.put("bB7", 106);
        PNAME2PITCH.put("B7", 107);
        PNAME2PITCH.put("C8", 108);

        if(type.equals("assets")){
            //通过设备管理对象 获取Asset的资源路径
            AssetManager assetManager = context.getApplicationContext().getAssets();

            InputStream inputStream = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            try{
                inputStream = assetManager.open("Keymap/" + path);
                isr = new InputStreamReader(inputStream);
                br = new BufferedReader(isr);

                String line = null;
                key2name.clear();
                while((line = br.readLine()) != null){
                    String[] kp = line.split("\t");
                    Integer k = KNAME2KEY.get(kp[0]);
                    int p = PNAME2PITCH.get(kp[1]);
                    if(k != null && (p >= 21 && p <= 108)){
                        KEY2PITCH.put(k, p);
                        key2name.put(KNAME2KEY.get(kp[0]), kp[1]);
                    }
                }

                br.close();
                isr.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return KEY2PITCH;
        }else if(type.equals("external")){
            FileInputStream inputStream = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            try{
                File externalDir = context.getExternalFilesDir(null);
                File file = new File(externalDir + "/Keymap/" + path);
                inputStream = new FileInputStream(file);
                isr = new InputStreamReader(inputStream);
                br = new BufferedReader(isr);

                String line = null;
                key2name.clear();
                while((line = br.readLine()) != null){
                    String[] kp = line.split("\t");
                    Integer k = KNAME2KEY.get(kp[0]);
                    int p = PNAME2PITCH.get(kp[1]);
                    if(k != null && (p >= 21 && p <= 108)){
                        KEY2PITCH.put(k, p);
                        key2name.put(KNAME2KEY.get(kp[0]), kp[1]);
                    }
                }

                br.close();
                isr.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (isr != null) {
                        isr.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return KEY2PITCH;
        }else{
            return null;
        }
    }

    public HashMap<Integer, String> getKey2name() {
        return this.key2name;
    }
}
