package com.fiec.sspia;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.PlanetSource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InformationTab extends Fragment {
	public FragmentActivity act;
	public String[] dats;
	private ListView planetinfo;
	public String pname;
	public int position;
	
	public InformationTab(){
		setParams();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_infotab, container, false);
		
		this.planetinfo = (ListView)view.findViewById(R.id.main_planetinfo);
		
		setinfo();
		return view;
	}
	
	public void setParams(){
		act = TabsPlanets.act;
		pname = TabsPlanets.pname;
		position = TabsPlanets.pos;
	}
	
	public void setinfo(){
		act.getActionBar().setTitle(pname);
		new PlanetSource(act, planetinfo, pname);
		switch(position){
		case 3:
			this.dats = act.getResources().getStringArray(R.array.fromcuriositi);
			new PlanetSource(act, dats); break;
		}
	}
}
