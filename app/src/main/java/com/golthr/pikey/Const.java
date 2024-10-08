package com.golthr.pikey;

/**
 * Created by issuser on 2018/3/21 0021.
 */

public class Const {
    public static final String[] RANGE = {"A0", "B0",
            "C1", "D1", "E1", "F1", "G1", "A1", "B1",
            "C2", "D2", "E2", "F2", "G2", "A2", "B2",
            "C3", "D3", "E3", "F3", "G3", "A3", "B3",
            "C4", "D4", "E4", "F4", "G4", "A4", "B4",
            "C5", "D5", "E5", "F5", "G5", "A5", "B5",
            "C6", "D6", "E6", "F6", "G6", "A6", "B6",
            "C7", "D7", "E7", "F7", "G7", "A7", "B7",
            "C8"};
    public static final String[] PRONUNCIATION = {"la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do", "re", "mi", "fa", "sol", "la", "si",
            "do"};
    public static final int[] GAPS = {0,
            2, 3, 5, 6, 7,
            9, 10, 12, 13, 14,
            16, 17, 19, 20, 21,
            23, 24, 26, 27, 28,
            30, 31, 33, 34, 35,
            37, 38, 40, 41, 42,
            44, 45, 47, 48, 49};
    public static final int[] WHITEKEY_CODE = {21, 23,
            24, 26, 28, 29, 31, 33, 35,
            36, 38, 40, 41, 43, 45, 47,
            48, 50, 52, 53, 55, 57, 59,
            60, 62, 64, 65, 67, 69, 71,
            72, 74, 76, 77, 79, 81, 83,
            84, 86, 88, 89, 91, 93, 95,
            96, 98, 100, 101, 103, 105, 107,
            108,};

    public static final int[] BLACKKEY_CODE = {22,
            25, 27, 30, 32, 34,
            37, 39, 42, 44, 46,
            49, 51, 54, 56, 58,
            61, 63 ,66 ,68 ,70,
            73 ,75 ,78 ,80 ,82,
            85 ,87 ,90 ,92 ,94,
            97 ,99 ,102 ,104 ,106};

    public static final String[][] KEYBOARD_RANGE = {
            {"~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace", "Ins", "Home", "PgUp", "NumLock", "Num/", "Num*", "Num-"},
            {"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "|", "Del", "End", "PgDn", "Num7", "Num8", "Num9", "Num+"},
            {"CapsLock", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "Enter", "Num4", "Num5", "Num6"},
            {"LShift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "?", "RShift", "Up", "Num1", "Num2", "Num3", "NumEnter"},
            {"LCtrl", "Win", "LAlt", "Space", "RAlt", "Win", "Menu", "RCtrl", "Left", "Down", "Right", "Num0", "Num."}};

    public static final int[][] KEYBOARD_CODE = {
            {68, 8, 9, 10, 11, 12, 13, 14, 15, 16, 7, 69, 70, 67, 124, 122, 92, 143, 154, 155, 156},
            {61, 45, 51, 33, 46, 48, 53, 49, 37, 43, 44, 71, 72, 73, 112, 123, 93, 151, 152, 153, 157},
            {115, 29, 47, 32, 34, 35, 36, 38, 39, 40, 74, 75, 66, 148, 149, 150},
            {59, 54, 52, 31, 50, 30, 42, 41, 55, 56, 76, 60, 19, 145, 146, 147, 160},
            {113, 113, 57, 62, 58, 58, 58, 114, 21, 20, 22, 144, 158}};

    public static final int[][] KEYBOARD_KEY_GRID = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2},
            {3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 2, 2},
            {5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2},
            {2, 2, 2, 15, 2, 2, 2, 2, 2, 2, 2, 4, 2}};
}
