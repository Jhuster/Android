package com.ticktick.grouplist;

import java.util.List;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class GroupListParent extends GroupList {

    private List<GroupList> mPopListChilds;
    
    public GroupListParent(String title,List<GroupList> childs) {
        super(title);        
        mPopListChilds = childs;
    }

    @Override
    public int getResource() {
        return R.layout.grouplist_parent;
    }

    @Override
    public List<GroupList> getChild() {
        return mPopListChilds;
    }
    
    @Override
    public void buildView(View v) {
                
        TextView textView = (TextView)v.findViewById(R.id.GroupListParent);                
        textView.setText(getTitle());
    }

}
