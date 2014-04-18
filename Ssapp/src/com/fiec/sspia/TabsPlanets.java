package com.fiec.sspia;

import com.fiec.ssapp.R;

import android.R.color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TabsPlanets{
	
	public static FragmentActivity act;
	private FragmentTabHost th;
	public static String pname;
	public static int pos;
	private View view;
	
	public TabsPlanets(FragmentActivity acti, Fragment frag, View parentview, String pname, int pos) {
		TabsPlanets.act = acti;
		TabsPlanets.pname = pname;
		TabsPlanets.pos = pos;
		th = (FragmentTabHost)parentview.findViewById(android.R.id.tabhost);
		th.setup(acti, frag.getChildFragmentManager(), R.id.realtabcontent);
		th.setBackgroundColor(color.transparent);
	}
	
	public boolean addTabs(){		
		th.addTab(th.newTabSpec("tab1").setIndicator(customTab("Information")),
				InformationTab.class, null);
		
		th.addTab(th.newTabSpec("tab2").setIndicator(customTab("Satellites")),
				SatellitesClass.class, null);
		return true;
	}
	
	private static View customTab(String arsg){
		View view = LayoutInflater.from(act).inflate(R.layout.customtab, null, false);
		TextView tv = (TextView)view.findViewById(R.id.tabedita1);
		tv.setText(arsg);
		return view;
	}

}
