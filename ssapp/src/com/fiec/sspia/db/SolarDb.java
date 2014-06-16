package com.fiec.sspia.db;

import com.fiec.sspia.buff.Tag;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class SolarDb implements Parcelable{

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
	private String[] mars_columns2 = {DBHelper.TEMP_MIN, DBHelper.TEMPMAX};
	private String[] mars_columns = {DBHelper.LOG_TEMPMIN, DBHelper.LOG_TEMPMAX};
	private String[] check_column = {DBHelper.LOG_TEMPMIN};

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
		try{
			Cursor cursor = db.query(DBHelper.TABLEPLANET, allColumns_planet, null, null, null, null, null);
			if(cursor.getCount()==0){			
				return null;
			}
			cursor.move(1);
			return cursor.getString(1);
			}
		catch(Exception ex){
			return null;
		}
	}
	
	public String[] getSatDetails(int id){
		String[] aux = new String[allColumns_satdetail.length];
		Cursor cursor = db.query(DBHelper.TABLEDETALLE, allColumns_satdetail, null, null, null, null,null);
	
		cursor.move(id);
		for(int k=0; k<allColumns_satdetail.length; k++){			
			aux[k] = cursor.getString(k);
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
	
	public String[] getMarsTemp(int id){
		Log.w("gmaTag", "idmars = "+id);
		String[] aux = new String[2];
		Cursor cursor = db.query(DBHelper.TABLEDETALLE, mars_columns2,
				null, null, null, null,null);		
		cursor.move(id);
		Log.w("gmaTag", "marsdet = "+cursor.getCount());
		for(int k=0; k<mars_columns2.length; k++){
			aux[k] = cursor.getString(k);
		}
		cursor.close();
		return aux;
	}
	
	public String[] getUserTemp(){
		String[] aux = new String[2];
		Cursor cursor = db.query(DBHelper.TABLECHECK, mars_columns,
				null, null, null, null,null);
		cursor.moveToFirst();
		for(int k=0; k<mars_columns.length; k++){
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
	
	public boolean createTableDetalle(){
		String DB_CREATE3 = "create table "+DBHelper.TABLEDETALLE+"("
				+DBHelper.IDDETALLE+" integer primary key autoincrement, "
				+DBHelper.TEMPMAX+" text, "
				+DBHelper.TEMP_MED+" text, "
				+DBHelper.TEMP_MIN+" text, "+
				DBHelper.ICECOVER+" text, "
				+DBHelper.SURFACE+" text, "
				+DBHelper.MASS+" text, "
				+DBHelper.DIAMETER+" text, "+
				DBHelper.MEAN_DEN+" text, "
				+DBHelper.SCAP_VEL+" text, "
				+DBHelper.AVDIS+" text, "
				+DBHelper.ROTPER+" text, "+
				DBHelper.OBLIQUITI+" text, "
				+DBHelper.ORBIT+" text, "
				+DBHelper.ORBIT_ECC+" text)";
		db.execSQL(DB_CREATE3);
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
	
	public boolean dropTable(int _TABLE){
		try{
			switch(_TABLE){
				case 0: //DROP PLANETS
						db.execSQL("DROP TABLE IF EXISTS "+DBHelper.TABLEPLANET); break;
				case 1: //DROP DETALLE
						db.execSQL("DROP TABLE IF EXISTS "+DBHelper.TABLEDETALLE); break;						
				case 2: //DROP MOON
						db.execSQL("DROP TABLE IF EXISTS "+DBHelper.TABLEMOON); break;
				case 3: //DROP CHECK
						db.execSQL("DROP TABLE IF EXISTS "+DBHelper.TABLECHECK); break;
			}
			return true;
		}		
		catch(SQLException ex){Log.e(Tag._TAG, ex.toString()); return false;}		
	}
	
	public boolean updateDetail(String[] dats){
		db.execSQL("UPDATE " + DBHelper.TABLEDETALLE + " SET " 
				+ DBHelper.TEMPMAX + "='"+dats[0]+"'," 
				+ DBHelper.TEMP_MED +"='"+dats[1]+"',"
				+ DBHelper.TEMP_MIN + "='"+dats[2]+"'," 
				+ DBHelper.ICECOVER + "='"+dats[3]+"',"
				+ DBHelper.SURFACE + "='"+dats[4]+"',"
				+ DBHelper.MASS + "='"+dats[5]+"',"
				+ DBHelper.DIAMETER + "='"+dats[6]+"',"
				+ DBHelper.MEAN_DEN + "='"+dats[7]+"',"
				+ DBHelper.SCAP_VEL + "='"+dats[8]+"',"
				+ DBHelper.AVDIS + "='"+dats[9]+"',"
				+ DBHelper.ROTPER + "='"+dats[10]+"',"
				+ DBHelper.OBLIQUITI + "='"+dats[11]+"',"
				+ DBHelper.ORBIT + "='"+dats[12]+"',"
				+ DBHelper.ORBIT_ECC +"='"+dats[13]+"';");
		return true;
	}
	
	public boolean createTableLog(){
		try{
			String DB_CREATE4 = "create table "+DBHelper.TABLECHECK+"("
					+DBHelper.ISACT+" text not null, "
					+DBHelper.ISCHECK+" text not null, "
					+DBHelper.LOG_TEMPMIN+" text not null, "
					+DBHelper.LOG_TEMPMAX+" text not null, "
					+DBHelper.LOG_TABLEUPDATE+" integer not null)";
			db.execSQL(DB_CREATE4);
			return true;
		}
		catch(Exception ex){
			Log.e(Tag._TAG, ex.toString());
			return false;
		}
	}
	
	public boolean createLog(String arg1, String arg2, String arg3, 
			String arg4, int arg5){
		db.execSQL("INSERT INTO "+DBHelper.TABLECHECK+ "("+DBHelper.ISACT+","
				+DBHelper.ISCHECK+","+DBHelper.LOG_TEMPMIN+","+DBHelper.LOG_TEMPMAX+","
				+DBHelper.LOG_TABLEUPDATE+")"+
				" VALUES ('"+arg1+"','"+arg2+"','"+arg3+"','"+arg4+"','"+arg5+"')");
		return true;
	}
	
	public boolean createLog_2(String arg1, String arg2, String arg3, 
			String arg4){
		db.execSQL("INSERT INTO "+DBHelper.TABLECHECK+ "("+DBHelper.ISACT+","
				+DBHelper.ISCHECK+","+DBHelper.LOG_TEMPMIN+","+DBHelper.LOG_TEMPMAX+")"+
				" VALUES ('"+arg1+"','"+arg2+"','"+arg3+"','"+arg4+"')");
		return true;
	}
	public boolean updateLogIscheck(String arg2) {
		db.execSQL("update " + DBHelper.TABLECHECK + " set "
					+ DBHelper.ISCHECK + "='" + arg2 + "';");
		return true;
	}
	public boolean updateLogIsact(String arg1) {
		db.execSQL("update " + DBHelper.TABLECHECK + " set "
					+ DBHelper.ISACT + "='" + arg1 + "';");
		return true;
	}

	public boolean updateLogTemp(String arg1, String arg2) {
		db.execSQL("update " + DBHelper.TABLECHECK + " set "
					+ DBHelper.LOG_TEMPMIN + "='" + arg1 + "',"
					+ DBHelper.LOG_TEMPMAX + "='" + arg2+"';");
		return true;
	}
	public boolean updateLogUpdate(int arg1) {
		db.execSQL("update " + DBHelper.TABLECHECK + " set "
					+ DBHelper.LOG_TABLEUPDATE + "='" + arg1 + "';");
		return true;
	}	
	
	public String getIsAct(){
		Cursor cursor = db.query(DBHelper.TABLECHECK,
				new String[] { DBHelper.ISACT }, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(0);
	}
	
	public String getIsCheck(){
		Cursor cursor = db.query(DBHelper.TABLECHECK,
				new String[] { DBHelper.ISCHECK }, null, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(0);
	}
	
	public String getUpdate(){
		try{
			Cursor cursor = db.query(DBHelper.TABLECHECK,
					new String[] { DBHelper.LOG_TABLEUPDATE }, null, null, null, null, null);
			if(cursor.getCount()==0){			
				return null;
			}
			cursor.moveToFirst();
			return String.valueOf(cursor.getInt(0));
		}
		catch(Exception ex){
			Log.e(Tag._TAG, ex.toString());
			return null;
		}
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
		Cursor cursor = db.query(DBHelper.TABLEMOON,
				new String[] { DBHelper.IDLUNA }, DBHelper.IDPLANET + "="
						+ PlanetId, null, null, null, null);
		int[] satellites = new int[cursor.getCount()];
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getCount(); i++){
			satellites[i] = cursor.getInt(0);
			cursor.moveToNext();
		}
		cursor.close();
		return satellites;
	}
	
	public int getSatDetailId(int satId){
		Cursor cursor = db.query(DBHelper.TABLEMOON,
				new String[] { DBHelper.IDDETALLE }, DBHelper.IDLUNA + "="
						+ satId, null, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	
	/***
	 * Nuevo codigo de aqui
	 */
	
	public int getSatellitesIdByName(String satname){
		Cursor cursor = db.query(DBHelper.TABLEMOON,
				new String[] { DBHelper.IDLUNA }, DBHelper.NAME + "='"
						+ satname+"'", null, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	/***
	 * Hasta aqui
	 */
}
