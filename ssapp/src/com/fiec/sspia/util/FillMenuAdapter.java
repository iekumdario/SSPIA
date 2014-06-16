package com.fiec.sspia.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.fiec.sspia.buff.PlanetClass;
import com.fiec.sspia.db.Planets;

public class FillMenuAdapter {
	
	private Context context;
	private CustomMenuAdapter adapter;
	private List<PlanetClass> listmenu;
	private int cont, i;
	
	private Planets[] planets;
	
	public FillMenuAdapter(Context context, Planets[] planets){
		this.context = context;
		this.planets = planets;
		
		this.listmenu = new ArrayList<PlanetClass>();
	}
	
	public CustomMenuAdapter getItemAdapter(){
		listmenu.clear();
		int length = this.planets.length;
		
		if(length >= 1){
			cont = 0;
			for(i=0; i<length; i++){
				listmenu.add(new PlanetClass(){
					{
						_p0 = planets[cont].getName();
						_img0 = planets[cont].getImgres();
						cont++;
						
					}
				});	
				
			}
		}
		adapter = new CustomMenuAdapter(context, listmenu);
		return adapter;
	}
}
