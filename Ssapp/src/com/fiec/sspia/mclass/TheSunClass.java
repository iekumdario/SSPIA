package com.fiec.sspia.mclass;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class TheSunClass{
	private ListView planetinfo;
	private String[] dats;
	private View view;
	private FragmentActivity act;
	
	public TheSunClass(FragmentActivity act, View view) {
		this.view = view;
		this.act = act;
		
		Log.w("gmaTag", "pasa6");
	}
	
	
	
}
