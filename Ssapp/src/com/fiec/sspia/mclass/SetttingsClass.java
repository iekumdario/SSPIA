package com.fiec.sspia.mclass;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;

import com.fiec.ssapp.R;

public class SetttingsClass {

	private Dialog dialog;

	public SetttingsClass(FragmentActivity act) {
		dialog = new Dialog(act);
		dialog.setContentView(R.layout.customdialog);
		dialog.setTitle("Notifications");
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

}
