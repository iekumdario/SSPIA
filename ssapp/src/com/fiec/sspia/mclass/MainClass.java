package com.fiec.sspia.mclass;

import java.util.Calendar;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.PlanetSource;
import com.fiec.sspia.buff.Tag;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.system.IRemoteService;
import com.fiec.sspia.system.SspiaService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainClass extends FragmentActivity{
	protected String _isChk = "false";
	protected SolarDb db;
	public IRemoteService serv;
	
	protected final static long _INTERVAL = AlarmManager.INTERVAL_HOUR*6;	
	protected Intent intent;
	protected Intent in;
	protected AlarmManager service;
	protected PendingIntent pintent;
	protected Calendar cal;
	
	public void start(){
		if(getIsAct().equals("off")){
			runAlarm();
			db.updateLogIsact("on");
			db.close();
		}		
		else db.close();
	}
	
	private String getIsAct(){
		db = new SolarDb(this);
		db.open();
		return db.getIsAct();
	}
	
	public void start2(Activity act){		
		act.getActionBar().show();
		if(getIsAct2(act).equals("off")){
			runAlarm2(act);
			db.updateLogIsact("on");
			db.close();
		}		
		else db.close();
		//checkUpdate(act);
	}
	
	private String getIsAct2(Activity act){
		db = new SolarDb(act);
		db.open();
		return db.getIsAct();
	}
	
	public boolean checkUpdate(Activity act){
		Log.i(Tag._TAG, "UPDATE!");
		db = new SolarDb(act);
		db.open();
		
		String aux = db.getUpdate();
		if(aux == null){
			String currentAct = db.getIsAct();
			String currentCheck = db.getIsCheck();
			db.dropTable(3);
			db.createTableLog();
			String[] aux2 = db.getMarsTemp(db.getIdbyPname("Mars"));
			db.createLog(currentAct, currentCheck, aux2[0], aux2[1],-1);
		}		
		int[] updates = act.getResources().getIntArray(R.array.updates);
		if(!db.getUpdate().equals("-2")){
			for(int i=0; i<updates.length; i++){
				if(updates[i] == 1)
					db.updateLogUpdate(i);
			}
			if(!(aux=db.getUpdate()).equals("-1")){
				int[] inits = act.getResources().getIntArray(R.array.planetscode);
				int[] inits2 = act.getResources().getIntArray(R.array.satcode);
				switch(Integer.parseInt(aux)){
					case 0: break;
					case 1:
						String[] dats;
						db.dropTable(1);
						db.createTableDetalle();
						for (int i = 0; i < inits.length; i++) {
							dats = act.getResources().getStringArray(inits[i]);
							db.create(dats);
						}
						for (int i = 0; i < inits2.length; i++) {
							dats = act.getResources().getStringArray(inits2[i]);
							db.create(dats);
						}
					break;
					
					case 2: break;
					case 3: break;
				}
				db.updateLogUpdate(-2);
				db.close();
				String[] mdats = act.getResources().getStringArray(R.array.fromcuriositi);
				new PlanetSource((FragmentActivity) act, mdats);
			}		
		}
		
		return true;
	}
	
	public void runAlarm(){
		intent = new Intent(this, SspiaService.class);
		bindService(intent, connect, Context.BIND_AUTO_CREATE);
		service = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		in = new Intent(this, SspiaService.class);
		pintent = PendingIntent.getService(this, 0, in, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		cal = Calendar.getInstance();
		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				_INTERVAL, pintent);
	}
	
	public void runAlarm2(Activity act){
		intent = new Intent(act, SspiaService.class);
		act.bindService(intent, connect, Context.BIND_AUTO_CREATE);
		service = (AlarmManager)act.getSystemService(Context.ALARM_SERVICE);
		in = new Intent(act, SspiaService.class);
		pintent = PendingIntent.getService(act, 0, in, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		cal = Calendar.getInstance();
		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				_INTERVAL, pintent);
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
}
