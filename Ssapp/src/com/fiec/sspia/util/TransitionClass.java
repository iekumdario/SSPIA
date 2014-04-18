package com.fiec.sspia.util;

import com.fiec.sspia.db.SolarDb;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class TransitionClass {
	private String[] arg1, arg3;
	private int[] aux, arg2, arg4;
	
	private SolarDb db;
	
	public TransitionClass(FragmentActivity act, String[] arg1, int[] arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		db = new SolarDb(act);
	}
	
	public void setParams(String pname, int pos){
		db.open();
		pos = db.getIdbyPname(pname);
		aux = db.getSatellitesByPlanetId(pos);
		db.close();
		
	}
	
	public Bundle getParams(){
		Bundle bundle = new Bundle();
		
		arg3 = new String[aux.length];
		arg4 = new int[aux.length];
		
		for(int i = 0; i<arg3.length; i++){
			arg3[i] = arg1[aux[i]-1];
			arg4[i] = arg2[aux[i]-1];
		}
		
		bundle.putStringArray("names", arg3);
		bundle.putIntArray("images", arg4);
		return bundle;
	}

}
