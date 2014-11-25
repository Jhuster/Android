package com.ticktick.jnicallback;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	private Native mNative = new Native();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNative.nativeInitilize();
		mNative.nativeThreadStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mNative.NativeThreadStop();
	}
}
