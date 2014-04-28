package com.fiec.sspia.util;

import java.util.List;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.PlanetClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMenuAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private List<PlanetClass> list;
	
	public CustomMenuAdapter(Context context, List<PlanetClass> list) {
		this.list = list;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PlanetClass planet = list.get(position);
		View inflate = convertView;
		TextView text;
		ImageView image;
		
		inflate = inflater.inflate(R.layout.menu_lists, null);
		text = (TextView)inflate.findViewById(R.id.menu_planetname);
		image = (ImageView)inflate.findViewById(R.id.menu_planetimage);
		
		text.setText(planet._p0);
		image.setImageResource(planet._img0);
		
		return inflate;
	}

}
