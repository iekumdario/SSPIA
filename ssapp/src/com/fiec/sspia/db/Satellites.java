package com.fiec.sspia.db;

public class Satellites extends Celestial{
	
	public Satellites(String n, String[] inf, int pid, int imgres, String extra) {
		super(n, inf, pid, imgres, extra);
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
}
