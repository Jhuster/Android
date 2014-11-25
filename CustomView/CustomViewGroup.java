/*
 *  COPYRIGHT NOTICE  
 *  Copyright (C) 2014, ticktick <lujun.hust@gmail.com>
 *  http://ticktick.blog.51cto.com/
 *   
 *  @license under the Apache License, Version 2.0 
 *
 *  @file    CustomViewGroup.java 
 *  @brief   定义了一个行优先布局的Layout
 *  
 *  @version 1.0     
 *  @author  ticktick
 *  @date    2014/08/19  
 * 
 */

package com.ticktick.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {

    private static final String TAG = CustomViewGroup.class.getSimpleName(); 
 
    private static final int DEFAULT_VIEW_WIDTH  = 100;
    private static final int DEFAULT_VIEW_HEIGHT = 100;
	
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
	    super(c, attrs);			
        }		
    }

    public CustomViewGroup(Context context) {
        super(context);		
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);		
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override  
    public LayoutParams generateLayoutParams(AttributeSet attrs) {  
        return new CustomViewGroup.LayoutParams(getContext(), attrs);  
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	
	int mViewGroupWidth  = getMeasuredWidth();  //当前ViewGroup的总宽度
	int mViewGroupHeight = getMeasuredHeight(); //当前ViewGroup的总高度
	
	Log.d(TAG,"Group width:" + mViewGroupWidth + ",height:" + mViewGroupHeight);

	int mPainterPosX = left; //当前绘图光标横坐标位置
	int mPainterPosY = top;  //当前绘图光标纵坐标位置  
		
        int childCount = getChildCount();        
        for ( int i = 0; i < childCount; i++ ) {
            
            View childView = getChildAt(i);
 
            int width  = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();             
            
            Log.d(TAG,"child width:" + width + ",height:" + height);
 
            CustomViewGroup.LayoutParams margins = (CustomViewGroup.LayoutParams)(childView.getLayoutParams());
            Log.d(TAG,"Margin:"+margins.leftMargin+","+margins.topMargin+ ","+margins.rightMargin+","+margins.bottomMargin);
            
            //ChildView占用的width  = width+leftMargin+rightMargin
            //ChildView占用的height = height+topMargin+bottomMargin
            //如果剩余的空间不够，则移到下一行开始位置
            if( mPainterPosX + width + margins.leftMargin + margins.rightMargin > mViewGroupWidth ) {            	
            	mPainterPosX = left; 
            	mPainterPosY += height + margins.topMargin + margins.bottomMargin;
            }                    
            
            //执行ChildView的绘制
            childView.layout(mPainterPosX+margins.leftMargin, mPainterPosY+margins.topMargin, 
            		mPainterPosX+margins.leftMargin+width, mPainterPosY+margins.topMargin+height);
            
            mPainterPosX += width + margins.leftMargin + margins.rightMargin;
        }		
    }
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	
	int width  = measureDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
	int height = measureDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);
    
	//计算子控件的尺寸
	measureChildren(widthMeasureSpec, heightMeasureSpec);
	
	setMeasuredDimension(width, height);	    
	
        Log.d(TAG, "onMeasure " + width + "," + height );		
    }
	
    protected int measureDimension( int defaultSize, int measureSpec ) {
	
	int result = defaultSize;
	
	int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
    	    
        if (specMode == MeasureSpec.EXACTLY) { 	    
    	    result = specSize; 
        } 
        else if (specMode == MeasureSpec.AT_MOST) { 	        
    	    result = Math.min(defaultSize, specSize);
        } 
        else {	    
    	    result = defaultSize; 
        }
    
        return result;
    }

}
