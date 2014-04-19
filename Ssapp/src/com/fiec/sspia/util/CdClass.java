package com.fiec.sspia.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class CdClass extends AbstrCd implements OnClickListener{

	public CdClass(FragmentActivity act, View content) {
		super(act, content);
	}
	
	public void setAttr(Bundle bundle, int pos){	
		super.setDialogContent(true, true);
		super.setDefaultDialog(bundle, pos);
		this._WIDTH = (int) (metr.widthPixels * 0.85);
		this._HEIGHT = (int) (metr.heightPixels * 0.60);
		this.params.width = _WIDTH;
		
		//this.dialog.getWindow().setLayout(_WIDTH, _HEIGHT);
		this.close.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		this.dialog.dismiss();		
	}

	@Override
	public void showdialog() {
		this.dialog.show();		
	}

}
