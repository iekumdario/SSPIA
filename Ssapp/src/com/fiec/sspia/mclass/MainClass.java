package com.fiec.sspia.mclass;

import java.util.Calendar;

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
	}
	
	private String getIsAct2(Activity act){
		db = new SolarDb(act);
		db.open();
		return db.getIsAct();
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
	
	protected void onStop() {
		super.onStop();
		//activ.unbindService(connect);
		this.finish();		
	};
	
}
