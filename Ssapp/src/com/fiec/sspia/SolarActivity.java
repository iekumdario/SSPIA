package com.fiec.sspia;

import java.lang.reflect.Field;
import java.util.Calendar;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.mclass.SSClass;
import com.fiec.sspia.mclass.SetttingsClass;
import com.fiec.sspia.system.IRemoteService;
import com.fiec.sspia.system.SspiaService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

public class SolarActivity extends FragmentActivity{
	private String _isChk = "false";
	private SolarDb db;
	private SSClass clase;
	public IRemoteService serv;
	private static int _POS = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solar);
		setSettings();		
		initialize();		
		
		clase = new SSClass(this);
		clase.selectItem(_POS);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(getIsAct().equals("off")){
			Intent intent = new Intent(this, SspiaService.class);
			bindService(intent, connect, Context.BIND_AUTO_CREATE);
			AlarmManager service = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			Intent in = new Intent(this, SspiaService.class);
			PendingIntent pintent = PendingIntent.getService(this, 0, in, 
					PendingIntent.FLAG_CANCEL_CURRENT);
			Calendar cal = Calendar.getInstance();
			service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
					AlarmManager.INTERVAL_HOUR*6, pintent);
			db.updateLogIsact("on");
			db.close();
		}		
		else db.close();
	}
	
	private String getIsAct(){
		db = new SolarDb(getApplicationContext());
		db.open();
		return db.getIsAct();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//unbindService(connect);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.solar, menu);

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        SSClass.drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.action_settings: new SetttingsClass(this).isNoty(_isChk); break;
    	case R.id.about: new SetttingsClass(this).isAbout();
    	}
        if (SSClass.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

	private boolean firstini() {
		db.open();
		String aux = db.getPlanet();
		if (aux == null) {
			db.close();
			return true;
		}
		db.close();
		return false;
	}
	
	private ServiceConnection connect = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			serv = IRemoteService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serv = null;
		}
	};
	
	private void initialize() {
		db = new SolarDb(getApplicationContext());
		
		if (firstini() == true) {
			String[] aux; 
			String[] planets = this.getResources().getStringArray(R.array.theplanets);
			int[] inits = this.getResources().getIntArray(R.array.planetscode);
			int[] inits2 = this.getResources().getIntArray(R.array.satcode);
			String[] dats;
			db.open();
			db.createPlanets(planets);
			// para crear satelites
			 String[] satellites = this.getResources().getStringArray(R.array.satellites);
			 int[] satplanet= this.getResources().getIntArray(R.array.satelliteplanet);
			 db.createSatellites(satellites,satplanet);
			
			for (int i = 0; i < inits.length; i++) {
				dats = this.getResources().getStringArray(inits[i]);
				db.create(dats);
			}
			for (int i = 0; i < inits2.length; i++) {
				dats = this.getResources().getStringArray(inits2[i]);
				db.create(dats);
			}
			
			 aux = db.getMarsTemp(db.getIdbyPname("Mars"));
			 db.createLog("off", "false", aux[0], aux[1]);
			 _isChk = db.getIsCheck();
			db.close();
		}
	}
    
	@Override
	protected void onStop() {
		super.onStop();
		this.finish();
	}
	
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	//this.finish();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	clase = null;
    }

	public void setSettings() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			Log.e("gmaTag", "ERROR: "+ex.toString());
		}
	}

}
