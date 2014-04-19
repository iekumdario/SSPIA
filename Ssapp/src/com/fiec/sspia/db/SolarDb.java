package com.fiec.sspia.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SolarDb {

	private SQLiteDatabase db;
	private DBHelper dbhelper;

	private String[] allColumns_planet = { DBHelper.IDPLANET, DBHelper.NAME,
			DBHelper.IDDETALLE };
	private String[] allColumns_detail = { DBHelper.TEMPMAX, DBHelper.TEMP_MED,
			DBHelper.TEMP_MIN, DBHelper.ICECOVER, DBHelper.SURFACE,
			DBHelper.MASS, DBHelper.DIAMETER, DBHelper.MEAN_DEN,
			DBHelper.SCAP_VEL, DBHelper.AVDIS, DBHelper.ROTPER,
			DBHelper.OBLIQUITI, DBHelper.ORBIT, DBHelper.ORBIT_ECC };
	private String[] allColumns_satdetail = { DBHelper.TEMPMAX, DBHelper.TEMP_MED,
			DBHelper.TEMP_MIN, DBHelper.ICECOVER, DBHelper.SURFACE};

	public SolarDb(Context context) {
		dbhelper = new DBHelper(context);
	}

	public void open() {
		try {
			db = dbhelper.getWritableDatabase();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void close() {
		dbhelper.close();
	}

	public boolean updatetemp(int id, String max, String med, String min) {
		Log.w("gmaTag", "id:" + id + " max:" + max + " med:" + med + " min:"
				+ min);
		double medi = 0;
		if (med.compareTo("null") == 0 || med.compareTo("0.0") == 0) {
			medi = (Double.parseDouble(max) + Double.parseDouble(min)) / 2;
			db.execSQL("update " + DBHelper.TABLEDETALLE + " set "
					+ DBHelper.TEMPMAX + "='" + max + "', " + DBHelper.TEMP_MED
					+ "='" + medi + "'," + DBHelper.TEMP_MIN + "='" + min
					+ "' where " + DBHelper.IDDETALLE + "=" + id + ";");
		}
		return true;
	}
	
	public String getPlanet(){
		Cursor cursor = db.query(DBHelper.TABLEPLANET, allColumns_planet, null, null, null, null, null);
		if(cursor.getCount()==0){			
			return null;
		}
		cursor.move(1);
		return cursor.getString(1);		
	}
	
	public String[] getSatDetails(int id){
		String[] aux = new String[allColumns_satdetail.length];
		Cursor cursor = db.query(DBHelper.TABLEDETALLE, allColumns_satdetail, null, null, null, null,null);
	
		cursor.move(id);
		for(int k=0; k<allColumns_satdetail.length; k++){			
			aux[k] = cursor.getString(k);
			Log.e("gmaTag", "detail = "+aux[k]);
		}
		cursor.close();
		return aux;
}
		
	public String[] getDetails(int id){
			String[] aux = new String[14];
			Cursor cursor = db.query(DBHelper.TABLEDETALLE, allColumns_detail, null, null, null, null,null);
		
			cursor.move(id);
			for(int k=0; k<allColumns_detail.length; k++){
				aux[k] = cursor.getString(k);
			}
			cursor.close();
			return aux;
	}

	public int getIdbyPname(String planet) {
		int id;
		Cursor cursor = db.query(DBHelper.TABLEPLANET, allColumns_planet, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if (cursor.getString(1).compareTo(planet) == 0) {
				id = cursor.getInt(0);
				cursor.close();
				return id;
			}
			cursor.moveToNext();
		}
		cursor.close();
		return 0;
	}

	public boolean createPlanets(String[] dats) {
		for (int i = 0; i < dats.length; i++)
			db.execSQL("insert into " + DBHelper.TABLEPLANET + "("
					+ DBHelper.NAME + "," + DBHelper.IDDETALLE + ") values ('"
					+ dats[i] + "'," + (i + 1) + ")");
		return true;
	}

	public boolean create(String[] dats) {
		db.execSQL("INSERT INTO " + DBHelper.TABLEDETALLE + "("
				+ DBHelper.TEMPMAX + "," + DBHelper.TEMP_MED + ","
				+ DBHelper.TEMP_MIN + "," + DBHelper.ICECOVER + ","
				+ DBHelper.SURFACE + "," + DBHelper.MASS + ","
				+ DBHelper.DIAMETER + "," + DBHelper.MEAN_DEN + ","
				+ DBHelper.SCAP_VEL + "," + DBHelper.AVDIS + ","
				+ DBHelper.ROTPER + "," + DBHelper.OBLIQUITI + ","
				+ DBHelper.ORBIT + "," + DBHelper.ORBIT_ECC + ")"
				+ " VALUES ('" + dats[0] + "','" + dats[1] + "','" + dats[2]
				+ "','" + dats[3] + "','" + dats[4] + "','" + dats[5] + "','"
				+ dats[6] + "','" + dats[7] + "','" + dats[8] + "','" + dats[9]
				+ "','" + dats[10] + "','" + dats[11] + "','" + dats[12]
				+ "','" + dats[13] + "')");
		return true;
	}

	// agrega datos a tabla "table_moon"
	public boolean createSatellites(String[] satName, int[] satPlanet) {
		for (int i = 0; i < satPlanet.length; i++) {
			db.execSQL("INSERT INTO " + DBHelper.TABLEMOON + "("
					+ DBHelper.NAME + "," + DBHelper.IDDETALLE + ","
					+ DBHelper.IDPLANET + ")" + " VALUES ('" + satName[i]
					+ "','" + (i + 8) + "','" + satPlanet[i] + "')");
		}
		return true;
	}

	// obtiene id de lunas de planeta segun id de planeta, compatible con getdetails
	public int[] getSatellitesByPlanetId(int PlanetId) {
		Log.e("gmaTag", "id1 = "+PlanetId);
		Cursor cursor = db.query(DBHelper.TABLEMOON,
				new String[] { DBHelper.IDLUNA }, DBHelper.IDPLANET + "="
						+ PlanetId, null, null, null, null);
		int[] satellites = new int[cursor.getCount()];
		Log.e("gmaTag", "leng1 = "+cursor.getCount());
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++){
			satellites[i] = cursor.getInt(0);
			cursor.moveToNext();
		}
		cursor.close();
		return satellites;
	}
	
	public int getRealSatBySatellitesId(int satId){
		Log.e("gmaTag", "id = "+satId);
		Cursor cursor = db.query(DBHelper.TABLEMOON,
				new String[] { DBHelper.IDDETALLE }, DBHelper.IDLUNA + "="
						+ satId, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
}
