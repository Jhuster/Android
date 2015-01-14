/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2015, ticktick <lujun.hust@gmail.com>
 *  http://ticktick.blog.51cto.com/
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    BitmapHelper.java
 *  @brief   Bitmap processor, include load,save,rotate,crop etc.
 *  
 *  @version 1.0     
 *  @author  ticktick
 *  @date    2015/01/14    
 */
package com.ticktick.testbitmap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;

public class BitmapHelper {

    public static Bitmap load( String filepath ) {

        Bitmap bitmap = null;
        try {
            FileInputStream fin = new FileInputStream(filepath);
            bitmap = BitmapFactory.decodeStream(fin);
            fin.close();
        }
        catch (FileNotFoundException e) {
            
        } 
        catch (IOException e) {
                
        }
        return bitmap;
    }
    
    public static void save( Bitmap bitmap, String filepath ) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            bitmap.compress(CompressFormat.JPEG, 100, fos);              
            bitmap.recycle();            
            fos.close();            
        }
        catch (FileNotFoundException e) {
            
        } 
        catch (IOException e) {      
            
        }   
    }
    
    public static Bitmap crop( Bitmap bitmap, Rect cropRect ) {
        return Bitmap.createBitmap(bitmap,cropRect.left,cropRect.top,cropRect.width(),cropRect.height());
    }
    
    public static Bitmap cropWithCanvas( Bitmap bitmap, Rect cropRect ) {
        Rect destRect = new Rect(0,0,cropRect.width(),cropRect.height());
        Bitmap cropped = Bitmap.createBitmap(cropRect.width(),cropRect.height(),Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(cropped);        
        canvas.drawBitmap(bitmap,cropRect,destRect,null);
        return cropped;
    }
    
    public static Bitmap rotate( Bitmap bitmap, int degrees  ) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);            
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
    
    public static Bitmap rotateWithCanvas( Bitmap bitmap, int degrees  ) {
        
        int destWidth,destHeight;
        
        float centerX = bitmap.getWidth()/2;
        float centerY = bitmap.getHeight()/2;        
        
        // We want to do the rotation at origin, but since the bounding
        // rectangle will be changed after rotation, so the delta values
        // are based on old & new width/height respectively.
        Matrix matrix = new Matrix();        
        matrix.preTranslate(-centerX,-centerY);
        matrix.postRotate(degrees);        
        if( degrees/90%2 == 0 ) { 
            destWidth  = bitmap.getWidth();
            destHeight = bitmap.getHeight();
            matrix.postTranslate(centerX,centerY);
        }        
        else {            
            destWidth  = bitmap.getHeight();
            destHeight = bitmap.getWidth();
            matrix.postTranslate(centerY,centerX);            
        }
        Bitmap cropped = Bitmap.createBitmap(destWidth,destHeight,Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(cropped);       
        canvas.drawBitmap(bitmap, matrix, null);
        return cropped;
    }

}
