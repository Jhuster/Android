#include <stdio.h>
#include <stdlib.h>
#include <jni.h>
#include <android/log.h>

#define LOG(...) __android_log_print(ANDROID_LOG_DEBUG,"Native",__VA_ARGS__)

#define TEST_BUFFER_SIZE 128

/*
 * Class:     com_ticktick_jnibuffer_Native
 * Method:    nativeSetBuffer1
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_com_ticktick_jnibuffer_Native_nativeSetBuffer1(JNIEnv *env, jobject thiz, jbyteArray buffer, jint len)
{
    unsigned char array[TEST_BUFFER_SIZE];

    //直接将Java端的数组拷贝到本地的数据中，建议使用这种方式，更加安全
    (*env)->GetByteArrayRegion(env, buffer, 0, len, array);

    //可以通过array来访问这段数组的值了,注意，修改的只是本地的值，Java端不会同时改变数组的值
    int i=0;
    for( i=0; i<TEST_BUFFER_SIZE; i++ ) {
        array[i] = i;
    }
}

/*
 * Class:     com_ticktick_jnibuffer_Native
 * Method:    nativeSetBuffer2
 * Signature: ([BI)V
 */
JNIEXPORT void JNICALL Java_com_ticktick_jnibuffer_Native_nativeSetBuffer2(JNIEnv *env, jobject thiz, jbyteArray buffer, jint len)
{
    //将本地指针指向含有Java端数组的内存地址，依赖Jvm的具体实现，可能是锁住Java端的那段数组不被回收（增加引用计数），
    //也可能所Jvm在堆上对该数组的一份拷贝
    //速度和效率比GetByteArrayRegion方法要高很多
    unsigned char * pBuffer = (*env)->GetByteArrayElements(env,buffer,NULL);
    if( pBuffer == NULL ) {
	LOG("GetByteArrayElements Failed!");
	return;
    }

    //可以通过pBuffer指针来访问这段数组的值了,注意，修改的是堆上的值，Java端可能会同步改变，依赖于Jvm的具体实现,不建议通过本方法改变Java端的数组值
    int i=0;
    for( i=0; i<TEST_BUFFER_SIZE; i++ ) {
	pBuffer[i] = i;
    }

    //最后不要忘记释放指针（减小引用计数）
    (*env)->ReleaseByteArrayElements(env,buffer,pBuffer,0);
}

/*
 * Class:     com_ticktick_jnibuffer_Native
 * Method:    nativeSetDirectBuffer
 * Signature: (Ljava/lang/Object;I)V
 */
JNIEXPORT void JNICALL Java_com_ticktick_jnibuffer_Native_nativeSetDirectBuffer(JNIEnv *env, jobject thiz, jobject buffer, jint len)
{
    //无需拷贝，直接获取与Java端共享的直接内存地址(效率比较高，但object的构造析构开销大，建议长期使用的大型buffer采用这种方式)
    unsigned char * pBuffer = (unsigned char *)(*env)->GetDirectBufferAddress(env,buffer);
    if( pBuffer == NULL ) {
	LOG("GetDirectBufferAddress Failed!");
	return;
    }

    //可以通过pBuffer指针来访问这段数组的值了,注意，修改数组的值后，Java端同时变化
    int i=0;
    for( i=0; i<TEST_BUFFER_SIZE; i++ ) {
	pBuffer[i] = i;
    }
}

/*
 * Class:     com_ticktick_jnibuffer_Native
 * Method:    nativeGetByteArray
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_ticktick_jnibuffer_Native_nativeGetByteArray(JNIEnv *env, jobject thiz)
{
    //传递JNI层的数组数据到Java端，有两种方法，一种是本例所示的通过返回值来传递
    //另一种是通过回调Java端的函数来传递(多用于jni线程中回调java层)
    unsigned char buffer[TEST_BUFFER_SIZE];

    int i=0;
    for( i=0; i<TEST_BUFFER_SIZE; i++ ) {
	buffer[i] = i;
    }

    //分配ByteArray
    jbyteArray array = (*env)->NewByteArray(env,TEST_BUFFER_SIZE);

    //将传递数据拷贝到java端
    (*env)->SetByteArrayRegion(env, array, 0, TEST_BUFFER_SIZE, buffer);

    return array;
}
