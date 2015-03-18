package com.jhuster.videoplayer;

import java.io.File;

import com.jhuster.videoplayer.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                
        //startPlayerExplicitlly();
        startPlayerImplicitlly();        
        finish();
    }
    
    public void startPlayerExplicitlly() {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.setDataAndType(Uri.fromFile(new File("/sdcard/test.3gp")),"video/*");     
        startActivity(intent);  
    }
    
    public void startPlayerImplicitlly() {
        Intent intent = new Intent(Intent.ACTION_VIEW);        
        intent.setDataAndType(Uri.parse("rtsp://192.168.1.102/test.mp4"),"video/mp4");        
        startActivity(intent);  
    }
}
