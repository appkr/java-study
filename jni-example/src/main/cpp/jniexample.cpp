#include<stdio.h>
#include<jni.h>
#include "jniexample.h"

JNIEXPORT void JNICALL Java_dev_appkr_jniExample_JNIExample_greet
  (JNIEnv *env, jobject obj) {
      printf("Hello JNI");
      return;
}
