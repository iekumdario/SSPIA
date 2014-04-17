package com.fiec.sspia.mclass;

import com.fiec.ssapp.R;
import com.fiec.sspia.PlanetInfoActivity;
import com.fiec.sspia.SatelliteActivity;
import com.fiec.sspia.buff.PlanetSource;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainFragment extends Fragment{
	
	private TextView planetname, temp;
	private ImageView planetimage;
	private ListView planetinfo;
	private Activity act;
	
	private TabHost tabs;
	
	private String[] dats;
	
	private String pname;
	private int pimg, position;
	
	private Animation animation;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_planets, null);
		//this.planetname = (TextView)view.findViewById(R.id.main_planetnam);
		this.planetimage = (ImageView)view.findViewById(R.id.main_planetimage);
		this.planetinfo = (ListView)view.findViewById(R.id.main_planetinfo);
		this.dats = act.getResources().getStringArray(R.array.fromcuriositi);
		//this.planetname.setText(pname);
		this.planetimage.setImageResource(pimg);
		this.temp = (TextView)view.findViewById(R.id.tempmed);
		
		setinfo();
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	public void setact(Activity act){
		this.act = act;
	}
	
	public void setPosition(int pos){
		this.position = pos;
	}
	
	public void setStrings(String pname, int pimg){
		this.pname = pname;
		this.pimg = pimg;
	}
	
	private void setinfo(){		
		act.getActionBar().setTitle(pname);
		new PlanetSource(act, planetinfo, pname, temp);
		switch(this.position){
		case 3: new PlanetSource(act, planetinfo, dats); break;
		}
	}
}
