package com.ticktick.popdialog;

import com.ticktick.popdailog.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickButton(View v) {
		
		final CustomDialog dailog = new CustomDialog(this,getRootLayout());
		dailog.setTitle("Warning");
		dailog.setMessage("This is ticktick's blog!");
		dailog.setPositiveButton("OK", new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dailog.dismiss();			
			}
		});
		dailog.setNegativeButton("Cancel", new OnClickListener() {			
			@Override
			public void onClick(View v) {
				dailog.dismiss();			
			}
		});
		dailog.show();
	}
	
	public View getRootLayout() {
		 return ((ViewGroup)this.findViewById(android.R.id.content)).getChildAt(0);
	}

}
