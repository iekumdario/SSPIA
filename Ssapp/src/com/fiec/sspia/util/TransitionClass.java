package com.fiec.sspia.util;

import com.fiec.sspia.db.SolarDb;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class TransitionClass {
	protected final static String[] _KEYS = {"titles","images","details"}; 
	private String[] arg1, arg3, arg5;
	private int[] aux, arg2, arg4;
	private int aux2;
	private SolarDb db;
	
	public TransitionClass(FragmentActivity act, String[] arg1, int[] arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
		db = new SolarDb(act);
	}
	
	public void setParams(String pname, int pos, int pos2){
		this.db.open();
		pos = db.getIdbyPname(pname);
		this.aux = db.getSatellitesByPlanetId(pos);
		this.aux2 = db.getRealSatBySatellitesId(aux[pos2]);
		this.arg5 = db.getSatDetails(aux2+1);
		this.db.close();
	}
	
	public void setParams(String pname, int pos){
		this.db.open();
		pos = db.getIdbyPname(pname);
		this.aux = db.getSatellitesByPlanetId(pos);
		this.db.close();
	}
	
	public Bundle getParams(){
		Bundle bundle = new Bundle();
		
		arg3 = new String[aux.length];
		arg4 = new int[aux.length];
		
		for(int i = 0; i<arg3.length; i++){
			arg3[i] = arg1[aux[i]-1];
			arg4[i] = arg2[aux[i]-1];
		}
		
		bundle.putStringArray(_KEYS[0], arg3);
		bundle.putIntArray(_KEYS[1], arg4);
		bundle.putStringArray(_KEYS[2], arg5);
		return bundle;
	}

}
