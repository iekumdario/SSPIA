package com.fiec.sspia.util;

import com.fiec.ssapp.R;

import android.R.color;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class AbstrCd{
	protected FragmentActivity context;
	protected int _WIDTH;
	protected int _HEIGHT;
	protected final static String[] _KEYS = {"titles","images","details"}; 
	
	protected Dialog dialog; 
	
	protected DisplayMetrics metr;
	protected WindowManager.LayoutParams params;
	
	protected ImageView close;
	protected TextView sattitle;
	protected TextView satmaxtemp;
	protected TextView satmintemp;
	protected TextView saticecov;
	protected TextView satsurf;
	protected TextView satinfo;
	protected ImageView satimage;
	private View content;

	public AbstrCd(FragmentActivity context, View content) {
		this.context = context;
		this.content = content;		
		
		this.metr =  new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metr);
		this.params = context.getWindow().getAttributes();
		
		this.dialog = new Dialog(context);
	}
	
	public void setDialogContent(boolean cancel, boolean cancelout){
		this.close = (ImageView)content.findViewById(R.id.cmoon_close);
		this.sattitle = (TextView)content.findViewById(R.id.cmoon_title);
		this.satmaxtemp =  (TextView)content.findViewById(R.id.cmoon_maxtmp);
		this.satmintemp =  (TextView)content.findViewById(R.id.cmoon_mintmp);
		this.saticecov =  (TextView)content.findViewById(R.id.cmoon_icecov);
		this.satsurf =  (TextView)content.findViewById(R.id.cmoon_surf);
		this.satinfo =  (TextView)content.findViewById(R.id.cmoon_discovered);
		this.satimage = (ImageView)content.findViewById(R.id.cmoon_image);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
		dialog.setCancelable(cancel);
		dialog.setCanceledOnTouchOutside(cancelout);
		dialog.setContentView(content);
	}
	
	public void setDefaultDialog(Bundle bundle, int pos){	
		for(int i=0; i<bundle.getStringArray(_KEYS[2]).length; i++){
			Log.i("gmaTag", "bund = "+bundle.getStringArray(_KEYS[2])[i]);
		}
		sattitle.setText(bundle.getStringArray(_KEYS[0])[pos]);		
		satimage.setImageResource(bundle.getIntArray(_KEYS[1])[pos]);
		satmaxtemp.setText(bundle.getStringArray(_KEYS[2])[0]);
		satmintemp.setText(bundle.getStringArray(_KEYS[2])[2]);
		saticecov.setText(bundle.getStringArray(_KEYS[2])[3]);
		satsurf.setText(bundle.getStringArray(_KEYS[2])[4]);
		satinfo.setText("Discovered by Galileo Galilei on 7 January, 1610, using a 20x-power, refracting telescope at the University of Padua.");
		//satinfo.setText(bundle.getStringArray(_KEYS[2])[4]);
	}
	
	public abstract void showdialog();

}