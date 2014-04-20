package com.fiec.sspia.util;

import com.fiec.ssapp.R;

import android.R.color;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class AbstrCd{
	protected FragmentActivity context;
	protected int _WIDTH;
	protected int _HEIGHT;
	protected final static String[] _KEYS = {"titles","images","details"}; 
	private final String[] _TAG = {"Max. Temperature","Min. Temperature", "Ice Cover","Surface"}; 
	
	protected Dialog dialog; 
	
	protected DisplayMetrics metr;
	protected WindowManager.LayoutParams params;
	
	protected ImageView close;
	protected TextView sattitle;
	protected ListView listinfo;
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
		this.satinfo =  (TextView)content.findViewById(R.id.cmoon_discovered);
		this.satimage = (ImageView)content.findViewById(R.id.cmoon_image);
		this.listinfo = (ListView)content.findViewById(R.id.cmon_listinfo);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(color.transparent));
		dialog.setCancelable(cancel);
		dialog.setCanceledOnTouchOutside(cancelout);
		dialog.setContentView(content);
	}
	
	public void setDefaultDialog(Bundle bundle, String[] info, int pos, int realpos){	
		String[] res ={bundle.getStringArray(_KEYS[2])[0], bundle.getStringArray(_KEYS[2])[2],
				bundle.getStringArray(_KEYS[2])[3], bundle.getStringArray(_KEYS[2])[4]};
		sattitle.setText(bundle.getStringArray(_KEYS[0])[pos]);		
		satimage.setImageResource(bundle.getIntArray(_KEYS[1])[pos]);
		listinfo.setAdapter(new CustomInfoAdapter(context, res, _TAG));
		satinfo.setText(info[realpos]);
	}
	
	public abstract void showdialog();

}