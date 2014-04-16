package com.fiec.ssapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{
	
	//**TABLA PLANETA
		public static final String TABLEPLANET = "table_planet";
		public static final String IDPLANET = "idplanet";
		public static final String IDDETALLE = "iddetalle";
	//**
		public static final String TABLEMOON = "table_moon";
		public static final String IDLUNA = "idluna";
		public static final String NAME = "name";
		
		public static final String TABLEDETALLE = "table_detalle";
		public static final String TEMPMAX = "temp_max";
		public static final String TEMP_MIN = "temp_min";
		public static final String TEMP_MED = "temp_med";
		public static final String ICECOVER = "ice_cover";
		public static final String SURFACE = "surface";
		public static final String MASS = "masa_kg";
		public static final String DIAMETER = "diameter_km";
		public static final String MEAN_DEN = "mean_density";
		public static final String SCAP_VEL = "Escape_velocity_ms";
		public static final String AVDIS = "Average_distance_from_Sun";
		public static final String ROTPER = "Rotation_period_length_of_day_in_Earth_days_";
		public static final String OBLIQUITI = "Obliquity_tilt_of_axis_degrees";
		public static final String ORBIT = "Orbit_inclination_degrees";
		public static final String ORBIT_ECC = "Orbit_eccentricity_deviation_from_circular";
	
	
	public static final String DBNAME = "solar.db";
	public static final Integer DBVERSION = 1;
	
	public static final String DB_CREATE = "create table "+TABLEPLANET+
			"("+IDPLANET+" integer primary key autoincrement, "+
			NAME+" text, "+IDDETALLE+" integer not null, foreign key("+IDDETALLE+") "
					+ "references "+TABLEDETALLE+"("+IDDETALLE+"))";
	
	public static final String DB_CREATE2 = "create table "+TABLEMOON+"("+IDPLANET+
			" integer, "+IDLUNA+" integer primary key autoincrement not null, "+NAME+" text, "+IDDETALLE+
			" integer not null, foreign key("+IDDETALLE+") "+"references "+TABLEDETALLE+
			"("+IDDETALLE+"), foreign key("+IDPLANET+") references "+TABLEPLANET+"("+
			IDPLANET+"))";
	
	public static final String DB_CREATE3 = "create table "+TABLEDETALLE+"("+IDDETALLE+
			" integer primary key autoincrement, "+TEMPMAX+" text, "+TEMP_MED+" text, "+TEMP_MIN+" text, "+
			ICECOVER+" text, "+SURFACE+" text, "+MASS+" text, "+DIAMETER+" text, "+
			MEAN_DEN+" text, "+SCAP_VEL+" text, "+AVDIS+" text, "+ROTPER+" text, "+
			OBLIQUITI+" text, "+ORBIT+" text, "+ORBIT_ECC+" text)";

	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE3);
		db.execSQL(DB_CREATE2);
		db.execSQL(DB_CREATE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("gmaTag", "Actualizando db version "+oldVersion+" to "+
				newVersion+", se borraran los anteriores datos.");
		db.execSQL("DROP TABLE IF EXISTS"+ TABLEPLANET);
		onCreate(db);		
	}

}
