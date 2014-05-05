package com.fiec.sspia.mclass;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.fiec.sspia.R;
import com.fiec.sspia.db.SolarDb;

public class SetttingsClass implements OnCheckedChangeListener, OnDismissListener, OnShowListener{
	
	private Activity act;
	private Dialog dialog;
	private LayoutInflater inflater;
	private View view;
	private EditText min, max;
	private CheckBox cheq;
	private double _MIN = -82, _MAX = -22;
	private SolarDb db;

	public SetttingsClass(FragmentActivity act) {
		this.act = act;
		dialog = new Dialog(act);
		db = new SolarDb(act);
		inflater = (LayoutInflater)act.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.customdialog, null);
		cheq = (CheckBox)view.findViewById(R.id.notify_checkstatus);
		min = (EditText)view.findViewById(R.id.notify_mintemp);
		max = (EditText)view.findViewById(R.id.notify_maxtemp);
		cheq.setOnCheckedChangeListener(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setOnShowListener(this);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
		dialog.setOnShowListener(this);
		dialog.setCanceledOnTouchOutside(true);		
		dialog.setOnDismissListener(this);
	}
	
	public void isNoty(String ischk){		
		
		if(ischk.equals("true")) cheq.setChecked(true);
		else cheq.setChecked(false);
		
		dialog.setContentView(view);
		dialog.show();
	}
	
	public boolean notiStatus(){
		return cheq.isChecked() ? true:false;
	}
	
	public double getMin(){
		if(!min.getText().toString().equals(""))
			this._MIN = getTemp(min.getText().toString());
		return this._MIN;
	}
	public double getMax(){
		if(!max.getText().toString().equals(""))
			this._MAX = getTemp(max.getText().toString());
		return this._MAX;
	}
	
	private double getTemp(String arg){
		try{
			return Double.parseDouble(arg);
		} catch(NumberFormatException ex){Toast.makeText(act, "Number Format Exception, try again",
				Toast.LENGTH_SHORT).show();
		return 0;
		}
	}
	
	public void isAbout(){
		dialog.setContentView(R.layout.about_dialog);
		dialog.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.i("gmaTag", "entra0 = "+isChecked);
		db.open();
		db.updateLogIscheck(String.valueOf(isChecked));
		db.close();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		getMin(); getMax();
		db.open();
		db.updateLogTemp(String.valueOf(_MIN), String.valueOf(_MAX));
		db.close();
	}

	@Override
	public void onShow(DialogInterface dialog) {
		String[]aux;
		String aux2;
		db.open();
		aux = db.getUserTemp();
		min.setText(aux[0]);
		max.setText(aux[1]);
		aux2 = db.getIsCheck();
		if(aux2.equals("true")) cheq.setChecked(true);
		else cheq.setChecked(false);
		
		
		db.close();
	}

}
