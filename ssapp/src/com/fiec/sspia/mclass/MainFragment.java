package com.fiec.sspia.mclass;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.Tag;
import com.fiec.sspia.db.Planets;
import com.fiec.sspia.main.TabsPlanets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainFragment extends Fragment{
	
	private ImageView planetimage;
	private FragmentActivity act;
	public String pname;
	public int position;
		
	private TabsPlanets tp;
	private Planets planet;
	private String[] tags;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle saved) {
		View view = inflater.inflate(R.layout.activity_planets, null);
		this.planetimage = (ImageView)view.findViewById(R.id.main_planetimage);	
		
		/*if(saved != null){
			Log.i(Tag._TAG, "saved no nulo");
			this.planet=saved.getParcelable("planetkey");
			this.tags = saved.getStringArray("tagskey");
			this.position = saved.getInt("poskey");
			
			Log.w(Tag._TAG,"pname no nulo "+planet.getName());
			Log.w(Tag._TAG,"pos "+position);
			Log.w(Tag._TAG,"inf "+planet.getinf()[0]);
		}*/
		
		this.planetimage.setImageResource(this.planet.getImgres());
		tp = new TabsPlanets(act, this, view, planet, position, tags);
		tp.addTabs();		
		return view;
	}
	
	/*@Override
	public void onSaveInstanceState(Bundle out) {
		out.putParcelable("planetkey", planet);
		out.putStringArray("tagskey", this.tags);
		out.putInt("poskey", position);
		super.onSaveInstanceState(out);
		tp=null;
	}*/
	
	public void setact(FragmentActivity act){
		this.act = act;
	}
	
	public void setPosition(int pos){
		this.position = pos;
	}
	
	public void setPlanet(Planets planet, String[] tags){
		this.planet = planet;
		this.tags = tags;
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		tp = null;
	}
}
