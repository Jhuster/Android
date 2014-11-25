package com.ticktick.grouplist;

import java.util.List;

import android.view.View;
import android.widget.TextView;

public class GroupListChild extends GroupList {
    
    public GroupListChild(String title) {
        super(title);        
    }

    @Override
    public int getResource() {
        return R.layout.grouplist_child;
    }

    @Override
    public List<GroupList> getChild() {       
        return null;
    }

    @Override
    public void buildView(View v) {
        
        TextView textView  = (TextView)v.findViewById(R.id.GroupListChild);                     
        textView.setText(getTitle());  
    }
  
}
