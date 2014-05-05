package com.fiec.sspia.mclass;

import com.fiec.sspia.R;
import com.fiec.sspia.main.TabsPlanets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment{
	
	private ImageView planetimage;
	private FragmentActivity act;
	public String pname;
	public int position;
	private int pimg;
		
	private TabsPlanets tp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_planets, null);
		this.planetimage = (ImageView)view.findViewById(R.id.main_planetimage);		
		this.planetimage.setImageResource(pimg);	
		
		tp = new TabsPlanets(act, this, view, pname, position);
		tp.addTabs();
		
		return view;
	}
	
	public void setact(FragmentActivity act){
		this.act = act;
	}
	
	public void setPosition(int pos){
		this.position = pos;
	}
	
	public void setStrings(String pname, int pimg){
		this.pname = pname;
		this.pimg = pimg;
	}	
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		tp = null;
	}
}
