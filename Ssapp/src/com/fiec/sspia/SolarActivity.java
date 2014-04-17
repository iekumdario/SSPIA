package com.fiec.sspia;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.mclass.SSClass;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SolarActivity extends Activity {
	SolarDb db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solar);
		
		db = new SolarDb(getApplicationContext());
		
		if(firstini() == true){
			String[] planets = this.getResources().getStringArray(R.array.theplanets);
			int[] inits = this.getResources().getIntArray(R.array.planetscode);
			int[] inits2 = this.getResources().getIntArray(R.array.satcode);
			String[] dats;
			db.open();
			db.createPlanets(planets);
			for(int i=0; i<inits.length; i++){
				dats = this.getResources().getStringArray(inits[i]);
				db.create(dats);
			}
			for(int i=0; i<inits2.length; i++){
				dats = this.getResources().getStringArray(inits2[i]);
				db.create(dats);
			}
			
			db.close();
		}
		
		new SSClass(this);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SSClass.drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SSClass.drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (SSClass.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private boolean firstini(){
		db.open();
		String aux = db.getPlanet();
		if(aux == null){
			db.close();
			return true;
		}
		db.close();
		return false;		
    }

}
