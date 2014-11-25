package com.ticktick.grouplist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class MainActivity extends Activity implements OnGroupClickListener,OnChildClickListener {

	private ExpandableListView mlistView;	
	private GroupListAdapter mAdapter;	
	
	private static final String[] mParentMenu = {
		"Book", "Video", "Audio"
	};
	
	private static final String[][] mChildMenu = {
		{ "book1", "book2", "book3", "book4" },
		{ "video1", "video2" },
		{ "audio1", "audio2", "audio3","audio4"}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		mlistView = new ExpandableListView(this);                        
        mlistView.setOnGroupClickListener(this);        
        mlistView.setOnChildClickListener(this);
        
        List<GroupList> groups = new ArrayList<GroupList>();
        for( int i=0; i<mParentMenu.length; i++) {        	
        	List<GroupList> childs = new ArrayList<GroupList>();
        	for( int j=0; j<mChildMenu[i].length; j++ ) {
        		childs.add(new GroupListChild(mChildMenu[i][j]));
        	}
        	groups.add(new GroupListParent(mParentMenu[i],childs));
        }

        mAdapter = new GroupListAdapter(this,groups);
        mlistView.setAdapter(mAdapter);
        
        setContentView(mlistView);		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
		
		return false;
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
		
		return false;
	}

}
