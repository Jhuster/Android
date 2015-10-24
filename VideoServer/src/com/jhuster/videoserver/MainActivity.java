package com.jhuster.videoserver;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private static final String DEFAULT_FILE_PATH  = Environment.getExternalStorageDirectory() + "/movie.mp4";
    private static final int VIDEO_WIDTH  = 320; 
    private static final int VIDEO_HEIGHT = 240;
    
    private TextView mTipsTextView;
    private VideoServer mVideoServer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTipsTextView = (TextView)findViewById(R.id.TipsTextView);
        mVideoServer = new VideoServer(DEFAULT_FILE_PATH, VIDEO_WIDTH, VIDEO_HEIGHT, VideoServer.DEFAULT_SERVER_PORT);
        mTipsTextView.setText("请在远程浏览器中输入:\n\n"+getLocalIpStr(this)+":"+VideoServer.DEFAULT_SERVER_PORT);
        try {
            mVideoServer.start();
        } 
        catch (IOException e) {        
            e.printStackTrace();
            mTipsTextView.setText(e.getMessage());
        }
    }
    
    @Override
    protected void onDestroy() {
        mVideoServer.stop();
        super.onDestroy();
    }

    public static String getLocalIpStr(Context context) {        
        WifiManager wifiManager=(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();          
        return intToIpAddr(wifiInfo.getIpAddress());
    }
    
    private static String intToIpAddr(int ip) {
        return (ip & 0xff) + "." + ((ip>>8)&0xff) + "." + ((ip>>16)&0xff) + "." + ((ip>>24)&0xff);
    }
    
}
