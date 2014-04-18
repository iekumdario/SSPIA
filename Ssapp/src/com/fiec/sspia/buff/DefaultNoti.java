package com.fiec.sspia.buff;

import com.fiec.ssapp.R;
import com.fiec.sspia.TabsPlanets;
import com.fiec.sspia.util.SSInterfaceNoti;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public abstract class DefaultNoti implements SSInterfaceNoti{
	
	protected Activity act;
	protected Context context;
	protected String _DEFAULT;
	protected String _CUSTOM;
	
	protected String _TYPE;
	protected String _CTITLE;
	protected String _CTEXT;
	protected String _TICKER;
	protected String[] _PARAMS;
	
	protected int _CTITLE2;
	protected int _CTEXT2;
	protected int _TICKER2;
	protected int _SICON;
	protected int[] _PARAMS2;
	
	private Intent notifi;
	private NotificationCompat.Builder rangenoti;
	private NotificationManager manager;
	private TaskStackBuilder tsb;
	private PendingIntent intent;
	protected Class<?> myclass;

	public DefaultNoti(Activity act, String _TYPE) {
		this.act = act;
		this._TYPE = _TYPE;
	}
	
	public DefaultNoti(Activity act, String _TYPE, String[] params) {
		this.act = act;
		this._TYPE = _TYPE;
		this._PARAMS = params;
	}
	
	public DefaultNoti(Activity act, String _TYPE, int[] params) {
		this.act = act;
		this._TYPE = _TYPE;
		this._PARAMS2 = params;
	}
	
	public void switching(){
		if(_TYPE.equals(_DEFAULT)){
			setDefaultNotifi();
		}
		else if(_TYPE.equals(_CUSTOM)){
			setCustomNotifi();
		}
	}
	
	private void setDefaultNotifi(){
		notifi = new Intent(context, myclass);	       
	       rangenoti = 
	    		   new NotificationCompat.Builder(act.getApplicationContext())
			       	.setContentTitle("Take a tour on Mars!")
			       	.setContentText("The Mars temperature is good for survival")
			       	.setSmallIcon(R.drawable.ic_launcher)
			       	.setTicker("Openfor details");
	       rangenoti.setAutoCancel(true);
	       manager = (NotificationManager)
	    		   act.getSystemService(Activity.NOTIFICATION_SERVICE);
	       
	       tsb = TaskStackBuilder.create(act.getApplicationContext());
	       tsb.addParentStack(TabsPlanets.class);
	       tsb.addNextIntent(notifi);
	       
	       intent = tsb.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
	       rangenoti.setContentIntent(intent);      
	}
	
	protected void shownotifi(){
		 manager.notify(0, rangenoti.build());
	}
	
	private void setCustomNotifi(){
		notifi = new Intent(context, myclass);	       
	       rangenoti = 
	    		   new NotificationCompat.Builder(act.getApplicationContext())
			       	.setContentTitle(_CTITLE)
			       	.setContentText(_CTEXT)
			       	.setSmallIcon(_SICON)
			       	.setTicker(_TICKER);
	       rangenoti.setAutoCancel(true);
	       manager = (NotificationManager)
	    		   act.getSystemService(Activity.NOTIFICATION_SERVICE);
	       
	       tsb = TaskStackBuilder.create(act.getApplicationContext());
	       tsb.addParentStack(TabsPlanets.class);
	       tsb.addNextIntent(notifi);
	       
	       intent = tsb.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
	       rangenoti.setContentIntent(intent);
	}
	
	@Override
	public void getRes() {
		context = act.getApplicationContext();
		_DEFAULT = act.getResources().getString(R.string.defaulttype);	
		_CUSTOM = act.getResources().getString(R.string.customtype);		
	}
	
	@Override
	public void setParams() {
		if(_TYPE.equals(_DEFAULT)){
			_CTITLE = _PARAMS[0];
			_CTEXT = _PARAMS[1];
			_TICKER = _PARAMS[2];
			try{
				_SICON = Integer.parseInt(_PARAMS[3]);
			} catch (NumberFormatException ex){
				ex.printStackTrace(); 
				_SICON = R.drawable.ic_launcher;}
		}
		else if(_TYPE.equals(_CUSTOM)){
			_CTITLE2 = _PARAMS2[0];
			_CTEXT2 = _PARAMS2[1];
			_TICKER2 = _PARAMS2[2];
			_SICON = _PARAMS2[3];
		}
	}
}
