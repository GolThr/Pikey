package com.midisynthesizer;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Build;

import java.io.IOException;

public class MidiSynthesizer implements AutoCloseable {
    // 原生对象指针
    private long _nativePtr;
    private static final String TAG = "JMidiSynthesizer";

    static {
        System.loadLibrary(BuildConfig.LIB_NAME);
    }

    @Override
    public void close() {
        if (_nativePtr != 0) {
            releaseNativePtr(_nativePtr);
            _nativePtr = 0;
        }
    }

    /**
     * @param soundFont 在 assets 中音频字体文件的路径
     * @param gain      音量增益。有效值为 [0, 5]
     * @throws IllegalArgumentException
     */
    public MidiSynthesizer(final Context context, final String soundFont, float gain) throws Exception {
        if (gain < 0 || gain > 5) throw new IllegalArgumentException("gain 的值无效");
        AssetManager am = context.getAssets();
        try {
            if (am.list(soundFont) == null) throw new IllegalArgumentException("指定 sound font 不存在");
        } catch (IOException e) {
            throw e;
        }
        _nativePtr = createNativePtr(am, soundFont, getBestSampleRate(context), gain);
        if (_nativePtr == 0) throw new IllegalArgumentException("创建 MidiSynthesizer 的时候出现参数异常");
    }

    /**
     * 按下按键
     *
     * @param chan  midi 通道。0 - 15
     * @param pitch 音高
     * @param vel   力度 0 - 127
     */
    public void noteon(int chan, int pitch, int vel) {
        synth((byte) (0x90 | chan), (byte) pitch, (byte) vel);
    }

    /**
     * 抬起按键。可以理解为停止某个音的发声
     *
     * @param chan  midi 通道
     * @param pitch 音高
     */
    public void noteoff(int chan, int pitch) {
        noteon(chan, pitch, 0);
    }

    /**
     * 停止指定 midi 通道的所以发声
     *
     * @param chan 如果通道是 -1 则停止所有通道的发声
     */
    public void mute(int chan) {
        if (_nativePtr == 0) return;
        nativeMute(_nativePtr, chan);
    }

    /**
     * 配置通道的音色
     *
     * @param chan       要配置的通道
     * @param bankNum    音色的 bank id
     * @param programNum 音色的 program id
     */
    public void selectProgram(int chan, int bankNum, int programNum) {
        if (_nativePtr == 0) return;
        nativeSelectProgram(_nativePtr, chan, bankNum, programNum);
    }

    /**
     * 渲染原始的 midi event。如果不了解 midi event 结构，请不要直接使用这个接口
     */
    public void synth(final byte... event) {
        if (_nativePtr == 0) return;
        nativeSynth(_nativePtr, event);
    }

    private static int getBestSampleRate(final Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            String sampleRateString = am.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
            return (sampleRateString == null) ? 44100 : Integer.parseInt(sampleRateString);
        } else {
            return 44100;
        }
    }

    private static native long createNativePtr(AssetManager am, final String sf, int sampleRate, float gain);

    private static native void releaseNativePtr(long ptr);

    private static native void nativeSynth(long ptr, final byte event[]);

    private static native void nativeMute(long ptr, int chan);

    private static native void nativeSelectProgram(long ptr, int chan, int bank, int program);
}
