package com.fiec.ssapp.buff;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.fiec.ssapp.R;
import com.fiec.ssapp.util.CustomInfoAdapter;
import com.fiec.ssapp.util.JSONParser;

public class PlanetSource extends AsyncTask<Void,Void,Boolean>{
	
	public static final String _ACTUALURL = "http://marsweather.ingenology.com/v1/latest/";
	private boolean isGet = false; 
	private String[] dats, res;
	
	private static final String _TAG = "gmaTag";
	private Activity act;
	private JSONParser parser;
	private JSONObject json = null;
	
	private ListView info;
	private CustomInfoAdapter adapter;

	public PlanetSource(Activity act, ListView info, String[] dats) {
		parser = new JSONParser();
		this.dats = dats;
		this.act = act;
		this.info = info;
		
		this.execute();
	}
	
	@Override
	protected void onPreExecute() {
		res = new String[this.dats.length];
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if(getFromCuriosity() == true){
			getRes();
			return true;
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if (result == true){
			info.setAdapter(adapter);
		}
	}
	
	private boolean getFromCuriosity(){
		try{
			json = parser.makeHttpRequest(_ACTUALURL, "GET");
			if(json == null){
				Log.e(_TAG, "Error de JSON parser..!!");
				return false;
			}
			else{
				return true;
			}			
		}
		catch(Exception ex){ex.printStackTrace(); return false;}		
	}
	
	private void getRes(){
		try {
			JSONObject array = json.getJSONObject("report");
			for(int i=0; i<array.length(); i++){
				res[i] = array.getString(dats[i]);
				//res[i] = json.getString(dats[i]);
				Log.w(_TAG, "res = "+res[i]);
			}
			adapter = new CustomInfoAdapter(this.act, res, dats);
			isGet = true;
		} catch (Exception e) {
			Log.e("Tag", "ERROR: "+e.toString());
			isGet = false;
		}
	}
	
}
