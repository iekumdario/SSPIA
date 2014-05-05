package com.fiec.sspia.util;

import com.fiec.sspia.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGridAdapterClass extends BaseAdapter{
	private String[] satellites;
	private int[] images;
	private LayoutInflater inflater;
	
	public MainGridAdapterClass(FragmentActivity context, String[]sname, int[] simage){
		super();
		this.satellites = sname;
		this.images = simage;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}	
	
	@Override
	public int getCount() {
		return satellites.length;
	}

	@Override
	public Object getItem(int pos) {		
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return 0;
	}

	@Override
	public View getView(int pos, View inflate, ViewGroup parent) {
		View vinflate = inflate;
		TextView satname;
		ImageView img;
		vinflate = inflater.inflate(R.layout.satellite_item, null);
		satname = (TextView) vinflate.findViewById(R.id.satellitename);
		satname.setText(satellites[pos]);
		img = (ImageView) vinflate.findViewById(R.id.satelliteimage);
		img.setImageResource(images[pos]);
		img.setScaleType(ImageView.ScaleType.CENTER_CROP);		
		return vinflate;
	}
}
