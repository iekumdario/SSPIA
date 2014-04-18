package com.fiec.sspia;

import com.fiec.ssapp.R;
import com.fiec.sspia.mclass.MainFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;

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
	}
	
	public View addTabs(){
		/*Log.w("gmaTag", "pasa0");
		InformationTab itab = new InformationTab();
		itab.setAct(act);
		Log.w("gmaTag", "pasa0.1");
		TabSpec ts = th.newTabSpec("Information");
		ts.setIndicator("tab1");
		Intent intent = new Intent(act, itab.getClass());
		intent.putExtra("planetname", pname);
		intent.putExtra("planetposition", position);
		ts.setContent(intent);
		Log.w("gmaTag", "pasa1");
		TabSpec ts2 = th.newTabSpec("Satellites");
		ts.setIndicator("tab2");
		Intent intent2 = new Intent(act, SatellitesClass.class);
		ts2.setContent(intent2);
		Log.w("gmaTag", "pasa3");*/
		//InformationTab itab = new InformationTab();
		th.addTab(th.newTabSpec("tab1").setIndicator("Information"),
				InformationTab.class, null);
		
		th.addTab(th.newTabSpec("tab2").setIndicator("Satellites"),
				SatellitesClass.class, null);		
		return this.view;
	}

}
