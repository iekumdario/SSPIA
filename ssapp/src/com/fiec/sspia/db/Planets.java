package com.fiec.sspia.db;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Planets extends Celestial implements Parcelable{
	
	public Planets(String n, String[] inf, int pid, int sat, int imgres) {
		super(n, inf, pid, sat, imgres);
	}

	public Planets(Parcel in) {
		super(in);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String[] getinf() {
		return INFO;
	}

	@Override
	public int getPid() {
		return PID;
	}

	@Override
	public int getImgres() {
		return IMGREG;
	}

	@Override
	public SolarDb getDb() {
		return DB;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Bundle bund = new Bundle();
		bund.putString("namekey", getName());
		bund.putStringArray("infokey", getinf());
		bund.putInt("pidkey", getPid());
		bund.putInt("imgkey", getImgres());
		bund.putInt("satkey", getSat());
		dest.writeBundle(bund);
	}
	
	public static final Parcelable.Creator<Planets> CREATOR = 
			new Parcelable.Creator<Planets>(){
				public Planets createFromParcel(Parcel in){
					return new Planets(in);
				}	
				
				public Planets[] newArray(int size){
					return new Planets[size];
				}
			};
}
