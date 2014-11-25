package com.ticktick.grouplist;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;

public class GroupListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<GroupList> mGroups;
	
	public GroupListAdapter(Context context, List<GroupList> groups) {
            this.mContext = context;
            this.mGroups  = groups;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
	    List<GroupList> chList = mGroups.get(groupPosition).getChild();
            if( chList == null) {
                return null;
            }
	    
	    return chList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,ViewGroup parent) {
					    	    		
	    GroupList child = (GroupList)getChild(groupPosition, childPosition);
	    if( child == null) {
	        return null;
	    }
	       
	    if (view == null) {		
	    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
	    	view = (RelativeLayout)inflater.inflate(child.getResource(), null);
            }
	        
	    child.buildView(view);
		
            return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
	    List<GroupList> chList = mGroups.get(groupPosition).getChild();
	    if( chList == null) {
	        return 0;
	    }
		
            return chList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
            return mGroups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
            return mGroups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
            return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view,ViewGroup parent) {

	    GroupList group = (GroupList)getGroup(groupPosition);
	    
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
	    	view = (RelativeLayout)inflater.inflate(group.getResource(), null);	   
            }

            group.buildView(view);
		
            return view;
	}

	@Override
	public boolean hasStableIds() {
            return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
            return true;
	}
}
