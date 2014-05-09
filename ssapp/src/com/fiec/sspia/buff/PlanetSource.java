package com.fiec.sspia.buff;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.util.CustomInfoAdapter;
import com.fiec.sspia.util.JSONParser;

public class PlanetSource extends AsyncTask<Void,Void,Boolean>{
	
	public static final String _ACTUALURL = "http://marsweather.ingenology.com/v1/latest/";
	
	protected boolean isGet = false; 
	private String[] dats, res;
	
	private static final String _TAG = "gmaTag";
	private FragmentActivity act;
	private JSONParser parser;
	private JSONObject json = null;
	
	private CustomInfoAdapter adapter;
	
	private SolarDb db;
	
	public PlanetSource(FragmentActivity act, String[] dats) {
		parser = new JSONParser();
		this.dats = dats;
		this.act = act;
		this.execute();
	}
	
	public PlanetSource(FragmentActivity act, ListView info, String pname){
		db = new SolarDb(act.getApplicationContext());
		int id,i=0,j=0, nulos=0;
		db.open();
		id = db.getIdbyPname(pname);
		String[] res = db.getDetails(id);
		dats = act.getResources().getStringArray(R.array.fields);
		db.updatetemp(id, res[0], res[1], res[2]);
		res = db.getDetails(id);
		
		for(i=0;i<res.length;i++){
			if(res[i].compareTo("null") == 0)
				nulos++;
			else
				continue;
			}
		String[] resf = new String[res.length-nulos];
		String[] datsf = new String[res.length-nulos];
		j=i=0;
		while(j<res.length){
			if(res[j].compareTo("null") == 0){
				j++;
				continue;
				}
			else{
				resf[i]=res[j];
				datsf[i]=dats[j];
				j++;
				i++;
				}
			}
		adapter = new CustomInfoAdapter(act, resf, datsf, 0);
		info.setAdapter(adapter);
		db.close();
		
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
			db = new SolarDb(act.getApplicationContext());
			db.open();
			db.updatetemp(4, res[5], "null", res[3]);
			db.updateLogTemp(res[3], res[5]);
			db.close();
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
			}
			isGet = true;
		} catch (Exception e) {
			Log.e("Tag", "ERROR: "+e.toString());
			isGet = false;
		}
	}
	
}
