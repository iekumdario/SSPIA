package com.fiec.sspia.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartingNotis extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, SspiaService.class);
		context.startService(service);	
	}

}
