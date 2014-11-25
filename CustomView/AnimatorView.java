package com.ticktick.testanimatorview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class AnimatorView extends View {

	private static final int RECT_WIDTH = 60; //每个矩形块的宽度
	private static final int RECT_DISTANCE = 40; //矩形块之间的间距
	private static final int TOTAL_PAINT_TIMES = 100; //控制绘制速度,分100次完成绘制	

	//待绘制的矩形块矩阵，left为高度，right为颜色
	private static final int[][] RECT_ARRAY = { 
		{380,Color.GRAY},
		{600,Color.YELLOW},
		{200,Color.GREEN},
		{450,Color.RED},
		{300,Color.BLUE}
	};
	
	private Paint mPaint;	
	private int mPaintTimes = 0;  //当前已经绘制的次数
	private boolean mIsAnimaionRun = false;

	public AnimatorView(Context context) {
		super(context);	
		initialize();
	}

	public AnimatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	
	public AnimatorView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initialize();
        }
	
	protected void initialize() {
            mPaint = new Paint(); 
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL);              
	}	

	@Override
	protected void onDraw(Canvas canvas) {
		
	    if( !mIsAnimaionRun ) {
		return;
	    }
	
	    mPaintTimes++;
	
	    for( int i=0; i<RECT_ARRAY.length; i++ ) {
		
		mPaint.setColor(RECT_ARRAY[i][1]);
		
		int paintXPos = i*(RECT_WIDTH+RECT_DISTANCE) + RECT_DISTANCE;			
		int paintYPos = RECT_ARRAY[i][0]/TOTAL_PAINT_TIMES*mPaintTimes;
		
		canvas.drawRect(paintXPos, getHeight(), paintXPos+RECT_WIDTH,getHeight()-paintYPos, mPaint);
	    }		
	
	    if( mPaintTimes < TOTAL_PAINT_TIMES ) {
		invalidate();			
	    }				
	}
	
	public void startAnimation() {
		mIsAnimaionRun = true;
		invalidate();
	}
	
}
