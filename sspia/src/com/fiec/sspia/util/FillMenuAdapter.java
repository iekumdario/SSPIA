package com.fiec.sspia.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.fiec.sspia.buff.PlanetClass;

public class FillMenuAdapter {
	
	private Context context;
	private CustomMenuAdapter adapter;
	private String[] items;
	private int[] ints;
	private List<PlanetClass> listmenu;
	private int cont, i;
	
	public FillMenuAdapter(Context context, String[] items, int[] ints){
		this.context = context; 
		this.items = items; 
		this.ints = ints;
		
		this.listmenu = new ArrayList<PlanetClass>();
	}
	
	public CustomMenuAdapter getItemAdapter(){
		listmenu.clear();
		if(items.length >= 1){
			cont = 0;
			for(i=0; i<items.length; i++){
				listmenu.add(new PlanetClass(){
					{
						_p0 = items[cont];
						_img0 = ints[cont];
						cont++;
						
					}
				});	
				
			}
		}
		adapter = new CustomMenuAdapter(context, listmenu);
		return adapter;
	}
}
