package com.fiec.sspia.mclass;

import android.R.color;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.fiec.ssapp.R;

public class SetttingsClass {

	private Dialog dialog;

	public SetttingsClass(FragmentActivity act) {
		dialog = new Dialog(act);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
		dialog.setCanceledOnTouchOutside(true);		
	}
	
	public void isNoty(){
		dialog.setContentView(R.layout.customdialog);
		dialog.show();
	}
	
	public void isAbout(){
		dialog.setContentView(R.layout.about_dialog);
		dialog.show();
	}

}
