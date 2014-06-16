package com.fiec.sspia.main;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.Planets;

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
	
	public static Planets planet;
	public static String[] tags;
	
	public TabsPlanets(FragmentActivity act, Fragment frag, View parentview, Planets plan, int pos, String[] tag) {
		TabsPlanets.act = act;
		TabsPlanets.pname = plan.getName();
		TabsPlanets.pos = pos;
		planet = plan;
		tags = tag;
		th = (FragmentTabHost)parentview.findViewById(android.R.id.tabhost);
		th.setup(act, frag.getChildFragmentManager(), R.id.realtabcontent);
	}
	
	public boolean addTabs2(){		
		//EDITAR INFORMATION TAB PARA MANDARLE PARAMETRO PLANET!!
		th.addTab(th.newTabSpec("tab1").setIndicator("Information"),null, null);
	    
		return true;
	}
	
	public boolean addTabs(){
		//EDITAR INFORMATION TAB PARA MANDARLE PARAMETRO PLANET!!
		th.addTab(th.newTabSpec("tab1").setIndicator("Information"),InformationTab.class, null);
		
		if(planet.getSat() >= 1)
			th.addTab(th.newTabSpec("tab2").setIndicator("Satellites"),SatellitesClass.class, null);
		
	    for(int i=0;i<th.getTabWidget().getChildCount();i++) 
	    {
	        TextView tv = (TextView) th.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
	        tv.setTextColor(Color.parseColor(_C));
	    } 
	    
		return true;
	}
}
