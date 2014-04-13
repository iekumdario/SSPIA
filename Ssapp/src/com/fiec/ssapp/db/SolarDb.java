package com.fiec.ssapp.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SolarDb {

		private SQLiteDatabase db;
		private DBHelper dbhelper;
		/*private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_USER,
				DBHelper.COLUMN_SECURITY,DBHelper.COLUMN_FULLNAME,DBHelper.COLUMN_EMAIL,
				DBHelper.COLUMN_DATE,DBHelper.COLUMN_STRING}; */
		
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
		
		public boolean create(){
			db.execSQL("INSERT INTO "
			+DBHelper.TABLEPLANET+ "("+AB_IMG_PASS+","+AB_IMG_Q+","+AB_IMG_W+","+AB_IMG_E
			+","+AB_IMG_R+")"+" VALUES ("+img_skills[_CONT]+","+img_skills[_CONT+1]
			+","+img_skills[_CONT+2]+","+img_skills[_CONT+3]+","+img_skills[_CONT+4]+")");
		}
}
