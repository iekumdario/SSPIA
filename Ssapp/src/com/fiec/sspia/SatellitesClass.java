package com.fiec.sspia;

import com.fiec.ssapp.R;
import com.fiec.sspia.util.CdClass;
import com.fiec.sspia.util.SatelliteGridAdapterClass;
import com.fiec.sspia.util.TransitionClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SatellitesClass extends Fragment implements OnItemClickListener{
	protected final static String[] _KEYS = {"titles","images","details"}; 
	private final static int _RVIEW = R.layout.custom_moon_dialog;
	
	private GridView grid;
	private FragmentActivity context;
	private String pname;
	private int ppos;
	private String[]satellites, z_xinfo;
	private int[]images;	
	private View content;
	private CdClass cd;
	private LayoutInflater inflater;
	private TransitionClass transition;
	private Bundle bundle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_infosat, container, false);
		this.inflater = inflater;
		this.context = TabsPlanets.act;
		this.pname = TabsPlanets.pname;
		this.ppos = TabsPlanets.pos;
		this.satellites = context.getResources().getStringArray(R.array.satellites);
		this.images = context.getResources().getIntArray(R.array.satellitesimg);
		this.z_xinfo = context.getResources().getStringArray(R.array.z_xinfo);
		this.bundle = new Bundle();
		this.transition = new TransitionClass(context, satellites, images);
		this.transition.setParams(pname, ppos);
		this.bundle = this.transition.getParams();
		
		
		grid = (GridView)view.findViewById(R.id.infotab_grid);
		grid.setAdapter(new SatelliteGridAdapterClass(
				context, this.bundle.getStringArray(_KEYS[0]), 
				this.bundle.getIntArray(_KEYS[1])));
		grid.setOnItemClickListener(this);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int[] pos;
		content = inflater.inflate(_RVIEW, null);
		pos = this.transition.setParams(pname, ppos, position);
		this.bundle = this.transition.getParams();
		cd = new CdClass(context, content);
		cd.setAttr(bundle, z_xinfo, position, pos[position]-1);
		cd.showdialog();
	}
}
