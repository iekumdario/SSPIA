package com.fiec.sspia.mclass;

import android.app.Activity;

import com.fiec.sspia.buff.DefaultNoti;

public class SSNotific extends DefaultNoti{

	public SSNotific(Activity act, String type) {
		super(act, type);
	}
	
	@Override
	public void initialize(Class<?> myclass) {
		super.myclass = myclass;
		getRes();
		setParams();
		switching();
	}

	@Override
	public void start() {
		super.shownotifi();		
	}	
}
