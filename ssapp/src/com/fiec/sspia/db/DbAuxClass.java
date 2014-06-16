package com.fiec.sspia.db;

import com.fiec.ssapp.R;

import android.app.Activity;

public class DbAuxClass {
	
	private Planets[] planets;
	private Satellites[] satellites;
	private SolarDb db;
	private Activity act;
	
	private String[] pname;
	private String[] pinfo;
	private String[] exinfo;
	private int[] pimg;
	private int satcount;
	private int pid;

	public DbAuxClass(Activity act, SolarDb db) {
		this.db = db;
		this.act = act;	
	}
	
	public boolean setPlanets(){
		//CARGAR NOMBRES E IMAGENES DE LOS PLANETAS, 　　ESTO DEBERIA HACERSE POR BASE DE DATOS!!!!
		this.pname = act.getResources().getStringArray(R.array.theplanets);
        this.pimg = act.getResources().getIntArray(R.array.theimages);
        
        int cant = this.pname.length;
      
    	this.planets = new Planets[cant];
        
        for(int i=0; i<cant; i++){
        	this.pid = db.getIdbyPname(pname[i]);
        	//CARGAR CANTIDAD DE SATELITES DEL PLANETA
            this.satcount = db.getSatellitesByPlanetId(this.pid).length;
            
            //CARGAR INFO DEL PLANETA
            this.pinfo = db.getDetails(this.pid);
            
        	this.planets[i] = new Planets(this.pname[i], this.pinfo, this.pid, this.satcount, this.pimg[i]);
        	this.planets[i].setDb(db);
        }       
        
		return true;
	}
	
	public boolean setSatellites(){
		//CARGAR NOMBRES E IMAGENES DE LOS SATELLITES, 　　ESTO DEBERIA HACERSE POR BASE DE DATOS!!!!
		this.pname = act.getResources().getStringArray(R.array.satellites);
        this.pimg = act.getResources().getIntArray(R.array.satellitesimg);
		this.exinfo = act.getResources().getStringArray(R.array.z_xinfo);
        
        int cant = this.pname.length;
        int aux;
      
    	this.satellites = new Satellites[cant];
        
        for(int i=0; i<cant; i++){
        	this.pid = db.getSatellitesIdByName(pname[i]);   		
            
            //CARGAR INFO DEL PLANETA
        	aux = db.getSatDetailId(this.pid);
    		this.pinfo = db.getSatDetails(aux);
            
        	this.satellites[i] = new Satellites(this.pname[i], this.pinfo, this.pid, this.pimg[i], this.exinfo[i]);
        	this.satellites[i].setDb(db);
        }       
        
		return true;
	}
	
	public Planets[] getPlanets(){
		return this.planets;
	}
	
	public Satellites[] getSatelites(){
		return this.satellites;
	}

}
