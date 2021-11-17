#include <jni.h>
#include <string>
#include<android/log.h>

#define TAG "Gusi" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

int myfree(int *p) {
    free(p);
    p = NULL;
    return 0;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_gusi_jni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int i = 0;
    LOGD("##########Ylw_ i = %d", i);
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_gusi_jni_MainActivity_testFromJNI(JNIEnv *env, jobject thiz) {
    const char *hello = "Hello from C++";
    size_t i = strlen(hello);
    LOGD("##########Ylw_ i = %d", i);
    std::string hello1 = nullptr;
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_gusi_jni_MainActivity_getStr(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
//    std::string hello = nullptr;
    unsigned int length = hello.length();
    LOGD("##########Ylw_ i = %d", length);
    int *ptr = (int *) malloc(sizeof(int) * 3);

    myfree(ptr);
    myfree(ptr);

    return env->NewStringUTF(hello.c_str());
}

