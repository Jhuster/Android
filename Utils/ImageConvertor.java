/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2015, Jhuster <lujun.hust@gmail.com>
 *  https://github.com/Jhuster/Android
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    ImageConvertor.java
 *  @brief   Support NV21/YUY2 RGB JPEG Bitmap Conversion
 *  
 *  @version 1.0     
 *  @author  Jhuster
 *  @date    2015/11/20    
 */
package com.jhuster.imageprocessor;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;

public class ImageConvertor {

    public static byte[] nv21ToJpeg(byte[] nv21,int width, int height) {                
        YuvImage image = new YuvImage(nv21,ImageFormat.NV21,width,height,null);       
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0,0,width,height), 100, out);        
        return out.toByteArray();
    }   
    
    public static byte[] yuy2ToJpeg(byte[] yuy2,int width, int height) {                
        YuvImage image = new YuvImage(yuy2,ImageFormat.YUY2,width,height,null);       
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compressToJpeg(new Rect(0,0,width,height), 100, out);        
        return out.toByteArray();
    }   

    public static Bitmap rgb565ToBitmap(byte[] rgb565, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565); 
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(rgb565,0,rgb565.length));
        return bitmap;
    }
    
    public static Bitmap argb8888ToBitmap(byte[] argb, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);        
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(argb,0,argb.length));
        return bitmap;
    }
    
    public static Bitmap jpegToBitmap(byte[] jpeg) {
        return BitmapFactory.decodeByteArray(jpeg,0,jpeg.length);
    }
    
    public static Bitmap pngToBitmap(byte[] png) {
        return BitmapFactory.decodeByteArray(png,0,png.length);
    }
    
    public static byte[] bitmapToJpeg(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, out);
        return out.toByteArray();
    }
    
    public static byte[] bitmapToPng(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }
    
}
