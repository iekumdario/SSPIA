package com.fiec.sspia.system;

import com.fiec.sspia.SolarActivity;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.mclass.SSNotific;
import com.fiec.sspia.util.TempClass;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class SspiaService extends Service{
	private NotificationManager nm;
	private TempClass temp;
	private int NOTI = 0;
	private SSNotific notification;
	private SolarDb db;
	
	public SspiaService() {
	}
	
	@Override
	public void onCreate() {		
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notification = new SSNotific(this.getApplicationContext(), nm, "default");
		db = new SolarDb(this);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("gmaTag", "Recibiendo servicio en: "+startId+" : "+intent);	
		//runAlarm();
		show();
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		nm.cancel(NOTI);
		Toast.makeText(this, "SSPIA service stopped!", Toast.LENGTH_SHORT).show();
	}
	
	private final IRemoteService.Stub sbin = new IRemoteService.Stub() {		
		@Override
		public int getPid() throws RemoteException {
			return android.os.Process.myPid();
		}
	};
	
	@Override
	public IBinder onBind(Intent intent) {
		return sbin;
	}
	
	public class SBinder extends Binder{
		public SspiaService getService(){
			return SspiaService.this;
		}
	}
	
	private void show(){
		String[] aux;
		db.open();
		if(db.getIsCheck().equals("true")){
			aux = db.getUserTemp();
			temp = new TempClass(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));		
			notification.initialize(SolarActivity.class, temp);
			notification.start();
		}
		db.close();		
	}

}
