package com.fiec.sspia.buff;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;

import com.fiec.sspia.db.Planets;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.util.CustomInfoAdapter;
import com.fiec.sspia.util.JSONParser;

public class PlanetSource extends AsyncTask<Void,Void,Boolean>{
	
	//public static final String _ACTUALURL = "http://marsweather.ingenology.com/v1/latest/";
	public static final String _URLKEY = "maasurlkey";
	private String URL;
	
	protected boolean isGet = false; 
	private String[] res;
	
	private static final String _TAG = "gmaTag";
	private FragmentActivity act;
	private JSONParser parser;
	private JSONObject json = null;
	
	private CustomInfoAdapter adapter;
	
	private SolarDb db;
	private Planets planet;
	
	//private Planets planet;
	private String[] tags;
	
	public PlanetSource(FragmentActivity act, String[] dats, Planets planet) {
		parser = new JSONParser();
		this.tags = dats;
		this.act = act;
		this.db = planet.getDb();
		this.planet = planet;
		this.execute();
	}
	
	public PlanetSource(FragmentActivity act, ListView info, Planets planet, String[] tags){
		this.act = act;
		//this.planet = planet;
		this.tags = tags;	
		
		int i=0,j=0, nulos=0;
		
		if(planet == null)
			Log.w(Tag._TAG, "planet null ");
		if(planet.getinf() == null) Log.w(Tag._TAG, "info null ");
		res = planet.getinf();		
		db = planet.getDb();
		
		db.updatetemp(planet.getPid(), res[0], res[1], res[2]);
		
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
				datsf[i]=tags[j];
				j++;
				i++;
				}
			}
		adapter = new CustomInfoAdapter(act, resf, datsf, 0);
		info.setAdapter(adapter);
	}
		
	@Override
	protected void onPreExecute() {
		URL = PreferenceManager.getDefaultSharedPreferences(act).getString(_URLKEY, null);
		Log.w(Tag._TAG, "URL = "+URL);
		res = new String[this.tags.length];
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
			//db = new SolarDb(act.getApplicationContext());
			//db.open();
			Log.w(Tag._TAG, "res5 = "+res[5]+" res3 = "+res[3]);
			db.updatetemp(4, res[5], "null", res[3]);
			db.updateLogTemp(res[3], res[5]);
			this.planet.setInfoMars(res[5], res[3]);
			//db.close();
		}
	}
	
	private boolean getFromCuriosity(){
		try{
			json = parser.makeHttpRequest(URL, "GET");
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
				res[i] = array.getString(tags[i]);
			}
			isGet = true;
		} catch (Exception e) {
			Log.e("Tag", "ERROR: "+e.toString());
			isGet = false;
		}
	}
	
}
