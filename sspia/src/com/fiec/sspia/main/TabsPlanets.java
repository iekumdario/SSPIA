package com.fiec.sspia.main;

import com.fiec.sspia.R;
import com.fiec.sspia.db.SolarDb;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TextView;

public class TabsPlanets{
	
	public static FragmentActivity act;
	private FragmentTabHost th;
	public static String pname;
	public static int pos;
	private static String _C= "#33B5E5";
	
	public TabsPlanets(FragmentActivity acti, Fragment frag, View parentview, String pname, int pos) {
		TabsPlanets.act = acti;
		TabsPlanets.pname = pname;
		TabsPlanets.pos = pos;
		th = (FragmentTabHost)parentview.findViewById(android.R.id.tabhost);
		th.setup(acti, frag.getChildFragmentManager(), R.id.realtabcontent);
	}
	
	public boolean addTabs(){	
		
		th.addTab(th.newTabSpec("tab1").setIndicator("Information"),InformationTab.class, null);
		
		if(getSatCount() >= 1)
			th.addTab(th.newTabSpec("tab2").setIndicator("Satellites"),SatellitesClass.class, null);
		
	    for(int i=0;i<th.getTabWidget().getChildCount();i++) 
	    {
	        TextView tv = (TextView) th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	        tv.setTextColor(Color.parseColor(_C));
	    } 
	    
		return true;
	}
	
	private int getSatCount(){
		SolarDb db = new SolarDb(act);
		int a;
		db.open();
		a = db.getSatellitesByPlanetId(db.getIdbyPname(pname)).length;
		db.close();
		return a;
	}

}