package com.fiec.sspia.mclass;

import android.app.NotificationManager;
import android.content.Context;

import com.fiec.sspia.buff.DefaultNoti;
import com.fiec.sspia.util.TempClass;

public class SSNotific extends DefaultNoti{

	public SSNotific(Context act, NotificationManager nm, String type) {
		super(act, nm, type);
	}
	
	@Override
	public void initialize(Class<?> myclass, TempClass temp) {
		super.myclass = myclass;
		super.temp = temp;
		getRes();
		//setParams();
		switching();
	}

	@Override
	public void start() {
		super.shownotifi();		
	}	
}
