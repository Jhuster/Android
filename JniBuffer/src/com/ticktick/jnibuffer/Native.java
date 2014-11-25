package com.ticktick.jnibuffer;

import java.nio.ByteBuffer;

import android.util.Log;

public class Native {
	
    public static final int TEST_BUFFER_SIZE = 128;

    private byte[] mTestBuffer1;
    private byte[] mTestBuffer2;
    private ByteBuffer mDirectBuffer;

    private native void nativeSetBuffer1(byte[] buffer,int len);
    private native void nativeSetBuffer2(byte[] buffer,int len);
    private native void nativeSetDirectBuffer(Object buffer, int len);
    private native byte[] nativeGetByteArray();

    public Native() {
        mTestBuffer1 = new byte[TEST_BUFFER_SIZE];
        mTestBuffer2 = new byte[TEST_BUFFER_SIZE];
        mDirectBuffer = ByteBuffer.allocateDirect(TEST_BUFFER_SIZE);
    }

    public void test() {

        nativeSetBuffer1(mTestBuffer1,TEST_BUFFER_SIZE);
        printBuffer("nativeInitNormalBuffer",mTestBuffer1);
        
        nativeSetBuffer2(mTestBuffer2,TEST_BUFFER_SIZE);
        printBuffer("nativeInitNormalBuffer2",mTestBuffer2);

        nativeSetDirectBuffer(mDirectBuffer,TEST_BUFFER_SIZE);
        printBuffer("nativeInitDirectBuffer",mDirectBuffer.array());

        byte[] buffer = nativeGetByteArray();        
        printBuffer("nativeGetByteArray",buffer);
    }

    private void printBuffer( String tag, byte[] buffer ) {
        StringBuffer sBuffer = new StringBuffer();
        for( int i=0; i<buffer.length; i++ ) {
            sBuffer.append(buffer[i]);
            sBuffer.append(" ");	
        }
        Log.d("Native", tag + sBuffer.toString());
    }
	
    static {
        System.loadLibrary("native");
    }
}
