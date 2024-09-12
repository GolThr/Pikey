#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include <android/log.h>
#include <jni.h>

extern "C" {

#include "include/fluidsynth.h"
static AAssetManager *_assetManager = nullptr;
static const char *TAG = "libjmidisynthesizer";

static void fluidLogFunc(int level, const char *message, void *data) {
    switch (level) {
        case FLUID_PANIC:
            __android_log_print(ANDROID_LOG_FATAL, TAG, "%s", message);
            break;

        case FLUID_ERR:
            __android_log_print(ANDROID_LOG_ERROR, TAG, "%s", message);
            break;

        case FLUID_WARN:
            __android_log_print(ANDROID_LOG_WARN, TAG, "%s", message);
            break;

        case FLUID_INFO:
            __android_log_print(ANDROID_LOG_INFO, TAG, "%s", message);
            break;

        case FLUID_DBG:
            __android_log_print(ANDROID_LOG_DEBUG, TAG, "%s", message);
            break;

        default:
            __android_log_print(ANDROID_LOG_VERBOSE, TAG, "%s", message);
            break;
    }
}

static void *assetOpen(const char *path) {
    auto ret = AAssetManager_open(_assetManager, path, AASSET_MODE_RANDOM);
    return ret;
}

static int assetClose(void *handle) {
    AAsset *asset;

    asset = (AAsset *)handle;
    AAsset_close(asset);
    return FLUID_OK;
}

static long assetTell(void *handle) {
    AAsset *asset;

    asset = (AAsset *)handle;
    auto a = AAsset_getLength(asset);
    auto b = AAsset_getRemainingLength(asset);
    return a - b;
}

static int assetSeek(void *handle, long offset, int origin) {
    AAsset *asset;

    asset = (AAsset *)handle;
    auto ret = AAsset_seek(asset, (off_t)offset, origin);
    if (ret == -1) return FLUID_FAILED;
    return FLUID_OK;
}

static int assetRead(void *buf, int count, void *handle) {
    if (!handle) return FLUID_FAILED;
    AAsset *asset;

    asset = (AAsset *)handle;
    int ret = AAsset_read(asset, buf, (size_t)count);
    if (ret < 0) return FLUID_FAILED;
    return FLUID_OK;
}

fluid_sfloader_t *newAndroidSFLoader(fluid_settings_t *settings) {
    fluid_sfloader_t *loader = new_fluid_defsfloader(settings);
    if (!loader) return nullptr;

    fluid_sfloader_set_callbacks(loader, assetOpen, assetRead, assetSeek, assetTell, assetClose);

    return loader;
}
}

#define SF_NONE 0

struct MidiSynthesizer {
    fluid_synth_t *synth;
    fluid_settings_t *settings;
    fluid_audio_driver_t *adriver;
    int sf;

    MidiSynthesizer(const char *sf, int sampleRate, float gain)
        : synth(nullptr), settings(nullptr), adriver(nullptr), sf(SF_NONE) {
        // config settings
        settings = new_fluid_settings();
        if (!settings) return;

        fluid_settings_setnum(settings, "synth.sample-rate", (float)sampleRate);
        fluid_settings_setnum(settings, "synth.gain", gain);

        synth = new_fluid_synth(settings);
        if (!synth) return;

        adriver = new_fluid_audio_driver(settings, synth);
        if (!adriver) return;

        // 配置 asset 加载的 callbacks
        auto loader = newAndroidSFLoader(settings);
        if (!loader) return;
        fluid_synth_add_sfloader(synth, loader);

        // 配置 sound font
        this->sf = fluid_synth_sfload(synth, sf, 1);
    }

    ~MidiSynthesizer() {
        if (sf != SF_NONE) {
            fluid_synth_sfunload(synth, sf, 1);
            sf = SF_NONE;
        }
        if (adriver) {
            delete_fluid_audio_driver(adriver);
            adriver = nullptr;
        }
        if (synth) {
            delete_fluid_synth(synth);
            synth = nullptr;
        }
        if (settings) {
            delete_fluid_settings(settings);
            settings = nullptr;
        }
    }
};

extern "C" {
static bool loginited = false;

JNIEXPORT jlong JNICALL Java_com_midisynthesizer_MidiSynthesizer_createNativePtr(JNIEnv *env, jclass clazz, jobject am,
                                                                                  jstring sf, jint sampleRate,
                                                                                  jfloat gain) {
    if (!_assetManager) _assetManager = AAssetManager_fromJava(env, am);
    if (!_assetManager) return 0;

    if (!loginited) {
        loginited = true;
        fluid_set_log_function(FLUID_ERR, fluidLogFunc, NULL);
    }

    const char *psf = env->GetStringUTFChars(sf, nullptr);
    if (!psf) return 0;

    auto ret = new MidiSynthesizer(psf, sampleRate, gain);
    env->ReleaseStringUTFChars(sf, psf);

    return (jlong)ret;
}

JNIEXPORT void JNICALL Java_com_midisynthesizer_MidiSynthesizer_releaseNativePtr(JNIEnv *env, jclass clazz,
                                                                                  jlong ptr) {
    if (ptr) delete (MidiSynthesizer *)ptr;
}

JNIEXPORT void JNICALL Java_com_midisynthesizer_MidiSynthesizer_nativeSynth(JNIEnv *env, jclass clazz, jlong ptr,
                                                                             jbyteArray event) {
    auto ms = (MidiSynthesizer *)ptr;
    if (!ms || !event) return;
    // 转换 event
    auto data = env->GetByteArrayElements(event, 0);
    if (!data) return;
    auto arrLen = env->GetArrayLength(event);

    int status = data[0] & 0xf0;
    int channel = data[0] & 0xf;

    // 只支持部分 mid 文件可以保存的信息
    switch (status) {
        case 0x90:
            fluid_synth_noteon(ms->synth, channel, data[1], data[2]);
            break;
        case 0x80:
            fluid_synth_noteoff(ms->synth, channel, data[1]);
            break;
        case 0xb0:  // control change
            fluid_synth_cc(ms->synth, channel, data[1], data[2]);
            break;
        case 0xc0:  // 音色选择
            fluid_synth_program_change(ms->synth, channel, data[1]);
            break;
        case 0xe0:  // 滑音
            fluid_synth_pitch_bend(ms->synth, channel, data[1]);
            break;
        default:
            break;
    }

    // 释放 event
    env->ReleaseByteArrayElements(event, data, JNI_ABORT);
}

JNIEXPORT void JNICALL Java_com_midisynthesizer_MidiSynthesizer_nativeMute(JNIEnv *env, jclass clazz, jlong ptr,
                                                                            jint chan) {
    auto ms = (MidiSynthesizer *)ptr;
    if (!ms) return;
    fluid_synth_all_sounds_off(ms->synth, chan);
}

JNIEXPORT void JNICALL Java_com_midisynthesizer_MidiSynthesizer_nativeSelectProgram(JNIEnv *env, jclass clazz,
                                                                                     jlong ptr, jint chan, jint bank,
                                                                                     jint program) {
    auto ms = (MidiSynthesizer *)ptr;
    if (!ms) return;
    fluid_synth_program_select(ms->synth, chan, ms->sf, bank, program);
}
}
