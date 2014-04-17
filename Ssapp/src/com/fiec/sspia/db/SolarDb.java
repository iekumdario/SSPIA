package com.fiec.sspia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SolarDb {

		private SQLiteDatabase db;
		private DBHelper dbhelper;
		
		private String[] allColumns_planet = {DBHelper.IDPLANET, DBHelper.NAME,
				DBHelper.IDDETALLE};
		private String[] allColumns_detail = {DBHelper.TEMP_MIN, 
				 DBHelper.TEMP_MED,	DBHelper.TEMPMAX, DBHelper.ICECOVER, DBHelper.SURFACE, DBHelper.MASS,
				DBHelper.DIAMETER, DBHelper.MEAN_DEN, DBHelper.SCAP_VEL, DBHelper.AVDIS,
				DBHelper.ROTPER, DBHelper.OBLIQUITI, DBHelper.ORBIT, DBHelper.ORBIT_ECC};
		
		public SolarDb(Context context){
			dbhelper = new DBHelper(context);
		}
		
		public void open(){
			try{
				db = dbhelper.getWritableDatabase();
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		
		public void close(){
			dbhelper.close();
		}
		
		public boolean update(int id, String max, String min, String med){
			double medi = 0;
			Log.w("gmaTag", "med = "+med);
			if(med.compareTo("null")==0)
				//Log.w("gmaTag", "mas = "+max+" min = " +min+ "xD = "+(Double.parseDouble(max)+Double.parseDouble(min)/2));
				medi = (Double.parseDouble(max)+Double.parseDouble(min))/2;
				db.execSQL("update "+DBHelper.TABLEDETALLE+" set "+DBHelper.TEMP_MIN+"='"+
					min+"', "+DBHelper.TEMP_MED+"='"+medi+"',"+DBHelper.TEMPMAX+"='"+max+"' where "+DBHelper.IDDETALLE+"="+(id+1));
			//}
			
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
		
		public int getIdbyPname(String planet){		
			int id;
			Cursor cursor = db.query(DBHelper.TABLEPLANET, allColumns_planet,
					null, null, null, null, null);
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				if(cursor.getString(1).compareTo(planet)==0){
					id = cursor.getInt(0);
					cursor.close();
					return id;
				}				
				cursor.moveToNext();		
			}
			cursor.close();
			return 0;
		}
		
		public String[] getDetails(int id){
			int i = 1, j = 0;
			String[] aux = new String[14];
			Cursor cursor = db.query(DBHelper.TABLEDETALLE, allColumns_detail, null, null, null, null,null);
			//Cursor cursor = db.query(DBHelper.TABLEDETALLE, allColumns_detail, DBHelper.IDDETALLE+"="+id, null, null, null,null);
			cursor.move(id);
			for(int k=0; k<14; k++){
				aux[k] = cursor.getString(k);
				//Log.w("gmaTag", "aux = "+aux[k]);
			}
			/*while(!cursor.isAfterLast()){
				aux[j] = cursor.getString(i);
				Log.w("gmaTag", "aux = "+aux[j]);
				j++; i++;
				cursor.moveToNext();
			}*/
			cursor.close();
			return aux;
		}
		
		public boolean createPlanets(String[] dats){
			for(int i=0;i<dats.length;i++)
			db.execSQL("insert into "+DBHelper.TABLEPLANET+"("+DBHelper.NAME+","+DBHelper.IDDETALLE+") values ('"+dats[i]+"',"+(i+1)+")");
			return true;
		}
		
		public boolean create(String[] dats){		
			db.execSQL("INSERT INTO "
			+DBHelper.TABLEDETALLE+ "("+DBHelper.TEMPMAX+","+DBHelper.TEMP_MED+","+
			DBHelper.TEMP_MIN+","+DBHelper.ICECOVER+","+DBHelper.SURFACE+","+DBHelper.MASS+
			","+DBHelper.DIAMETER+","+DBHelper.MEAN_DEN+","+DBHelper.SCAP_VEL+","+DBHelper.AVDIS+
			","+DBHelper.ROTPER+","+DBHelper.OBLIQUITI+","+DBHelper.ORBIT+","+DBHelper.ORBIT_ECC+")"+
			" VALUES ('"+dats[0]+"','"+dats[1]+"','"+dats[2]+"','"+dats[3]+"','"+dats[4]+
			"','"+dats[5]+"','"+dats[6]+"','"+dats[7]+"','"+dats[8]+"','"+dats[9]+"','"+dats[10]+
			"','"+dats[11]+"','"+dats[12]+"','"+dats[13]+"')");
			return true;
		}
		
}
