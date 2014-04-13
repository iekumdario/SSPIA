package com.fiec.ssapp.util;

import com.fiec.ssapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomInfoAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private String[] res, tag;
	
	public CustomInfoAdapter(Context context, String[] res, String[] tag) {
		this.res = res;
		this.tag = tag;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return res.length;
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
		View view = convertView;
		TextView tags, info;
		
		view = inflater.inflate(R.layout.info_list, null);
		tags = (TextView)view.findViewById(R.id.menu_info_tags);
		info = (TextView)view.findViewById(R.id.menu_info_res);
		
		tags.setText(tag[position]);
		info.setText(res[position]);
		
		return view;
	}

}
