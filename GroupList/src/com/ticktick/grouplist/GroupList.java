package com.ticktick.grouplist;

import java.util.List;

import android.view.View;

public abstract class GroupList {

    private final String mTitle; 
    
    public GroupList(String title) {
        mTitle = title;
    }
    
    public String getTitle() {
        return mTitle;
    }
    
    public abstract List<GroupList> getChild();
    
    public abstract int getResource();       

    public abstract void buildView(View v);
}
