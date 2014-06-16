package com.fiec.sspia.db;

import android.os.Bundle;
import android.os.Parcel;

public abstract class Celestial{
	
	protected String NAME;
	protected int PID;
	protected int IMGREG;
	protected String[] INFO;
	protected int SAT_COUNT;
	protected String SAT_EXTRA;
	protected SolarDb DB;
	
	public Celestial(String n, String[] inf, int pid, int sat, int imgres) {
		this.NAME = n;
		this.INFO = inf;
		this.PID = pid;
		this.SAT_COUNT = sat;
		this.IMGREG = imgres;
	}
	
	public Celestial(String n, String[] inf, int pid, int imgres, String extra) {
		this.NAME = n;
		this.INFO = inf;
		this.PID = pid;
		this.IMGREG = imgres;
		this.SAT_EXTRA = extra;
	} 
	
	public Celestial(Parcel in){
		Bundle bund = in.readBundle();
		this.NAME = bund.getString("namekey");
		this.INFO = bund.getStringArray("infokey");
		this.PID = bund.getInt("pidkey");
		this.IMGREG = bund.getInt("imgkey");
		this.SAT_COUNT = bund.getInt("satkey");
	}
	
	public abstract String getName();
	public abstract String[] getinf();
	public abstract int getPid();
	public abstract int getImgres();
	public abstract SolarDb getDb();
	
	public int getSat(){
		return SAT_COUNT;
	}
	
	public String getSatExtra(){
		return SAT_EXTRA;
	}
	
	public void setInfoMars(String max, String min){
		this.INFO[0] = max;
		this.INFO[2] = min;
	}
	
	public void setDb(SolarDb db){
		this.DB = db;
	}
	
	public void setName(String name){
		this.NAME = name;
	}
	
	public void setImg(int img){
		this.IMGREG = img;
	}
	
	public void setPid(int pid){
		this.PID = pid;
	}
	
	public void setSats(int sats) {
		this.SAT_COUNT = sats;
	}
	
	public void setInf(String[] inf){
		this.INFO = inf;
	}
}
