package com.fiec.sspia.main;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.PlanetSource;
import com.fiec.sspia.buff.Tag;
import com.fiec.sspia.db.Planets;

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
	
	private Planets planet;
	private String[] tags;
	
	public InformationTab(){
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saved) {
		View view = inflater.inflate(R.layout.fragment_infotab, container, false);
		Log.w(Tag._TAG,"entra aqui");
		this.planetinfo = (ListView)view.findViewById(R.id.main_planetinfo);
		
		/*if(saved!=null){
			planet = saved.getParcelable("planetkey");
			position = saved.getInt("poskey");
			tags = saved.getStringArray("tagskey");
		}
		else{
			setParams();
		}*/
		setParams();
		setinfo();
		return view;
	}
	
	public void setParams(){
		Log.w(Tag._TAG,"entra qui");
		act = TabsPlanets.act;
		planet = TabsPlanets.planet;
		position = TabsPlanets.pos;
		tags = TabsPlanets.tags;
		Log.w(Tag._TAG,"entra qui");
	}
	
	public void setinfo(){
		act.getActionBar().setTitle(this.planet.getName());
		new PlanetSource(act, planetinfo, planet, tags);
		switch(position){
		case 3:
			this.dats = act.getResources().getStringArray(R.array.fromcuriositi);
			new PlanetSource(act, dats, planet); break;
		}
	}
	
	/*@Override
	public void onSaveInstanceState(Bundle out) {
		out.putParcelable("planetkey", planet);
		out.putInt("poskey",position);
		out.putStringArray("tagskey", tags);
		super.onSaveInstanceState(out);
		Log.w(Tag._TAG,"entra a guardar estado");
	}*/
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
