package com.fiec.sspia.util;

import java.lang.reflect.Field;

import com.fiec.sspia.buff.Tag;

import android.app.Activity;
import android.util.Log;
import android.view.ViewConfiguration;

public class MenuSettings{
	private final static String _KEY = "sHasPermanentMenuKey";
	
	private ViewConfiguration _CONFIG;
	private Field _MENUKEY;

	public MenuSettings(Activity context) {
		this._CONFIG = ViewConfiguration.get(context);		
	}
	
	public boolean show(){
		try{
			this._MENUKEY = ViewConfiguration.class.getDeclaredField(_KEY);
			if(this._MENUKEY != null){
				this._MENUKEY.setAccessible(true);
				this._MENUKEY.setBoolean(this._CONFIG, false);
			}
			return true;
		}
		catch(Exception ex){Log.e(Tag._TAG, "Error:_ "+ex.toString()); return false;}
	}
}
