package com.ticktick.jnicallback;

import android.util.Log;

public class Native {
	
	public native void nativeInitilize();
	
	public native void nativeThreadStart();
	public native void NativeThreadStop();
	
	//this function is callback by native code
	public void onNativeCallback( int count ) {
		Log.d("Native","onNativeCallback count="+count);
	}
	
	static {
    	System.loadLibrary("native");
    }
}
