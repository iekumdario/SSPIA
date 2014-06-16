package com.fiec.sspia.mclass;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.Tag;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.util.MenuSettings;

public class SplashClass extends AsyncTask<Void, Integer, Boolean>{
	
	private SolarDb db;
	private Activity context;
	private ProgressBar progress;
	public String _isChk;
	private static int _POS = 0;
	int progreso = 0;
	
	boolean isdone = false;
	
	public SplashClass(Activity context, ProgressBar progress, SolarDb db){
		this.context = context;
		this.progress = progress;
		this.db = db;
	}
	
	public void initialize() {		
		String[] aux; 
		String[] planets = context.getResources().getStringArray(R.array.theplanets);
		int[] inits = context.getResources().getIntArray(R.array.planetscode);
		int[] inits2 = context.getResources().getIntArray(R.array.satcode);
	
		//progress.setMax(inits.length + inits2.length);
		String[] dats;
		db.createPlanets(planets);
		//publish();
		 String[] satellites = context.getResources().getStringArray(R.array.satellites);
		 int[] satplanet= context.getResources().getIntArray(R.array.satelliteplanet);
		 db.createSatellites(satellites,satplanet);
		 //publish();
		for (int i = 0; i < inits.length; i++) {
			dats = context.getResources().getStringArray(inits[i]);
			db.create(dats);
			//publish();
		}
		for (int i = 0; i < inits2.length; i++) {
			dats = context.getResources().getStringArray(inits2[i]);
			db.create(dats);
			//publish();
		}
			
		 aux = db.getMarsTemp(db.getIdbyPname("Mars"));
		 db.createLog("off", "true", aux[0], aux[1],0);
		 
		 _isChk = db.getIsCheck();
		 //publish();
		context.getActionBar().show();
		//context.onBackPressed();
	}
	
	private void initialize2() {		
		String[] aux; 
		String[] planets = context.getResources().getStringArray(R.array.theplanets);
		int[] inits = context.getResources().getIntArray(R.array.planetscode);
		int[] inits2 = context.getResources().getIntArray(R.array.satcode);
	
		progress.setMax(inits.length + inits2.length);
		String[] dats;
		db.createPlanets(planets);
		publish();
		 String[] satellites = context.getResources().getStringArray(R.array.satellites);
		 int[] satplanet= context.getResources().getIntArray(R.array.satelliteplanet);
		 db.createSatellites(satellites,satplanet);
		 publish();
		for (int i = 0; i < inits.length; i++) {
			dats = context.getResources().getStringArray(inits[i]);
			db.create(dats);
			publish();
		}
		for (int i = 0; i < inits2.length; i++) {
			dats = context.getResources().getStringArray(inits2[i]);
			db.create(dats);
			publish();
		}
			
		 aux = db.getMarsTemp(db.getIdbyPname("Mars"));
		 db.createLog("off", "true", aux[0], aux[1],0);
		 
		 _isChk = db.getIsCheck();
		 publish();
	}
	
	private void publish(){
		progreso++;
		publishProgress(progreso);
	}
	
	public synchronized boolean isDone() throws InterruptedException{
		while(isdone == false){
			wait();
		}
		if(isdone == true) return true;
		else return false;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		initialize();
		return true;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		this.progress.setProgress(values[0]);
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if(result == true){
			this.isdone = true;
			notify();
			context.getActionBar().show();
			context.onBackPressed();			
		}
	}
}
