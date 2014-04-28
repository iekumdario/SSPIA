package com.fiec.sspia.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastRec extends BroadcastReceiver{
	
	protected final static String _BOOT = "android.intent.action.BOOT_COMPLETED";
	protected Intent push;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(_BOOT.equals(intent.getAction())){
			/*AlarmManager service = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			Intent in = new Intent(context, StartingNotis.class);
			PendingIntent pintent = PendingIntent.getBroadcast(context, 0, in, 
					PendingIntent.FLAG_UPDATE_CURRENT);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, 10);
			service.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
					AlarmManager.INTERVAL_HOUR*6, pintent);*/
			//context.startService(in);
		}
	}

}
