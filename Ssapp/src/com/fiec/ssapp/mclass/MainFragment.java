package com.fiec.ssapp.mclass;

import com.fiec.ssapp.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends Fragment {
	
	private TextView planetname;
	private ImageView planetimage;
	private ListView planetinfo;
	private String[] plna;
	private Activity act;
	
	private String pname;
	private int pimg;
	
	private Animation animation;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_planets, null);
		plna = new String[]{"info","info","info","info","info","info","info","info"};
		this.planetname = (TextView)view.findViewById(R.id.main_planetname);
		this.planetimage = (ImageView)view.findViewById(R.id.main_planetimage);
		this.planetinfo = (ListView)view.findViewById(R.id.main_planetinfo);
		/*this.planetinfo.setAdapter(new ArrayAdapter<String>(act,
                R.layout.menu_lists, plna));*/
		this.planetname.setText(pname);
		this.planetimage.setImageResource(pimg);
		
		animation = AnimationUtils.loadAnimation(act, R.anim.x_rotation);
		this.planetimage.setAnimation(animation);
		
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
	
	public void setStrings(String pname, int pimg){
		this.pname = pname;
		this.pimg = pimg;
	}
	
}
