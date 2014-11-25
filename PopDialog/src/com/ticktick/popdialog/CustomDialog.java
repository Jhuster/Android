package com.ticktick.popdialog;

import com.ticktick.popdailog.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CustomDialog {
    
    private View mParent;
    private PopupWindow mPopupWindow; 
    private LinearLayout mRootLayout;    
    private LayoutParams mLayoutParams;   
    
    //PopupWindow必须有一个ParentView，所以必须添加这个参数
    public CustomDialog(Context context, View parent) {
        
        mParent = parent;
        
        LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
        
        //加载布局文件
        mRootLayout = (LinearLayout)mInflater.inflate(R.layout.custom_dailog, null);    
               
        mLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }    
    
    //设置Dailog的标题
    public void setTitle(String title) {
        TextView mTitle = (TextView)mRootLayout.findViewById(R.id.CustomDlgTitle);
        mTitle.setText(title);
    }
    
    //设置Dailog的主体内容
    public void setMessage(String message) {
    	TextView mMessage = (TextView)mRootLayout.findViewById(R.id.CustomDlgContentText);
    	mMessage.setText(message);
    }
    
    //设置Dailog的“确定”按钮
    public void setPositiveButton(String text,OnClickListener listener ) {
        final Button buttonOK = (Button)mRootLayout.findViewById(R.id.CustomDlgButtonOK); 
        buttonOK.setText(text);
        buttonOK.setOnClickListener(listener);
        buttonOK.setVisibility(View.VISIBLE);
    }
    
    //设置Dailog的“取消”按钮
    public void setNegativeButton(String text,OnClickListener listener ) {
        final Button buttonCancel = (Button)mRootLayout.findViewById(R.id.CustomDlgButtonCancel);
        buttonCancel.setText(text);
        buttonCancel.setOnClickListener(listener);
        buttonCancel.setVisibility(View.VISIBLE);
    }
    
    //替换Dailog的“主体”布局
    public void setContentLayout(View layout) {
    	
    	TextView mMessage = (TextView)mRootLayout.findViewById(R.id.CustomDlgContentText);
        mMessage.setVisibility(View.GONE);
        
        LinearLayout contentLayout = (LinearLayout)mRootLayout.findViewById(R.id.CustomDlgContentView);        
        contentLayout.addView(layout);                
    }
    
    //设置Dailog的长宽
    public void setLayoutParams(int width, int height) {
        mLayoutParams.width  = width;
        mLayoutParams.height = height; 
    }
    
    //显示Dailog
    public void show() {
    
        if(mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mRootLayout, mLayoutParams.width,mLayoutParams.height);
            mPopupWindow.setFocusable(true);
        }
        
        mPopupWindow.showAtLocation(mParent, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
    }
    
    //取消Dailog的显示
    public void dismiss() {
        
        if(mPopupWindow == null) {
            return;
        }
        
        mPopupWindow.dismiss();
    }
}
