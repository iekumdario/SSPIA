package com.fiec.sspia.mclass;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.SolarDb;

public class SplashClass extends AsyncTask<Void, Integer, Boolean>{
	
	private SolarDb db;
	private Activity context;
	private ProgressBar progress;
	public String _isChk;
	private SSClass clase;
	private static int _POS = 0;
	int progreso = 0;
	
	public SplashClass(Activity context, ProgressBar progress){
		this.context = context;
		this.progress = progress;
	}
	
	private void initialize() {
		db = new SolarDb(context);
		
		String[] aux; 
		String[] planets = context.getResources().getStringArray(R.array.theplanets);
		int[] inits = context.getResources().getIntArray(R.array.planetscode);
		int[] inits2 = context.getResources().getIntArray(R.array.satcode);
	
		progress.setMax(inits.length + inits2.length);
		String[] dats;
		db.open();
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
		 //db.createLog("off", "true", aux[0], aux[1]);
		 
		 _isChk = db.getIsCheck();
		 publish();
		db.close();
		
		
	}
	
	private void publish(){
		progreso++;
		publishProgress(progreso);
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
			new MainClass().start2(context);
			clase = new SSClass(context);
			SSClass.drawerToggle.syncState();
			clase.selectItem(_POS);
			
		}
	}
}
