package com.example.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;

public class Broadcaster {
        
    private Context mContext;
    private int mDestPort = 0;
    private DatagramSocket mSocket;
    
    public Broadcaster(Context context)  {
        mContext = context;        
    }
    
    public boolean open(int localport,int destport) {
        mDestPort = destport;        
        try {
            mSocket = new DatagramSocket(localport);
            mSocket.setBroadcast(true);
            mSocket.setReuseAddress(true);
            return true;
        } 
        catch (SocketException e) {         
            e.printStackTrace();
        }        
        return false;
    }
    
    public boolean close() {
        if(mSocket != null && !mSocket.isClosed()) {
            mSocket.close();
        }    
        return true;
    }
    
    public boolean sendPacket(byte[] buffer) {
        try {
            InetAddress addr = getBroadcastAddress(mContext);
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length); 
            packet.setAddress(addr);
            packet.setPort(mDestPort);
            mSocket.send(packet);
            return true;
        } 
        catch (UnknownHostException e1) {
            e1.printStackTrace();
        } 
        catch (IOException e) {          
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean recvPacket(byte[] buffer) {
        DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
        try {
            mSocket.receive(packet);
            return true;
        } 
        catch (IOException e) {        
            e.printStackTrace();
        } 
        return false;
    }
    
    public static InetAddress getBroadcastAddress(Context context) throws UnknownHostException {
        if(isWifiApEnabled(context)) {
            return InetAddress.getByName("192.168.43.255");            
        }
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if(dhcp==null) {
            return InetAddress.getByName("255.255.255.255");
        }
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++) {
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        }          
        return InetAddress.getByAddress(quads);
    }  
    
    protected static Boolean isWifiApEnabled(Context context) {        
        try {
            WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getMethod("isWifiApEnabled");
            return (Boolean)method.invoke(manager);
        } 
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {         
            e.printStackTrace();
        }
        return false;
    }
}
