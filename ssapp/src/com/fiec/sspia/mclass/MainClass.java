package com.fiec.sspia.mclass;

import java.util.Calendar;

import com.fiec.ssapp.R;
import com.fiec.sspia.buff.PlanetSource;
import com.fiec.sspia.buff.Tag;
import com.fiec.sspia.db.DbAuxClass;
import com.fiec.sspia.db.Planets;
import com.fiec.sspia.db.Satellites;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.system.IRemoteService;
import com.fiec.sspia.system.SspiaService;
import com.fiec.sspia.util.FillMenuAdapter;
import com.fiec.sspia.util.MenuSettings;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainClass extends FragmentActivity implements OnItemClickListener{
	protected String _isChk = "false";
	protected SolarDb db;
	/*public IRemoteService serv;
	
	protected final static long _INTERVAL = AlarmManager.INTERVAL_HOUR*6;	
	protected Intent intent;
	protected Intent in;
	protected AlarmManager service;
	protected PendingIntent pintent;
	protected Calendar cal;*/
	
	
	
	/***
	 * desde aqui nuevo codigo para agregar..
	 * *********
	 */
	protected int content = R.layout.activity_solar;
	protected boolean hasmenu = true;
	
	protected SharedPreferences preferences;
	
	//CONFIGURACION
	protected static String _FIRSTINIKEY = "fini";
	protected static String _UPDATEKEY = "logup";	
	protected boolean _ISFIRST;
	protected boolean _ISUP;
	protected Editor setsharedpreferences;
	//
	
	protected Fragment splash;
	protected DbAuxClass dbaux;
	protected Planets[] planets;
	protected Satellites[] satellites;
	protected String[] tags;
	
	private FillMenuAdapter adapter;
	private DrawerLayout maindrawer;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	
	protected MainFragment fragment;
	protected FragmentManager fragmentManager;
	
	protected int _POS = 0;
	protected int _CURRENT_POS = 0;
	
	protected Bundle in;
	
	@Override
	protected void onCreate(Bundle in) {
		super.onCreate(in);
		setContentView(content);
		Log.i(Tag._TAG, "create");
		this.in = in;
		if(in != null){
			//fragment = (MainFragment) getSupportFragmentManager().getFragment(in, "fragment");
			_POS = in.getInt("poskey");
			_CURRENT_POS = in.getInt("currposkey");
			db = new SolarDb(this);
		}
		else{
			PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
			this.preferences = PreferenceManager.getDefaultSharedPreferences(this);	
			db = new SolarDb(this);
			db.open();
			
			this._ISFIRST = this.preferences.getBoolean(_FIRSTINIKEY, true);	
			
			//SI SE INICIA POR PRIMERA VEZ - NUEVA VERSION
			if(_ISFIRST == true){
				setsharedpreferences = preferences.edit();
				setsharedpreferences.putBoolean(_FIRSTINIKEY, false).commit();
				
				
				//VERIFICAR SI NO EXISTE UNA VERSION ANTERIOR
				String aux = db.getPlanet();
				if (aux == null) {
					//CREAR LA BASE DE DATOS
					
					getActionBar().hide();
					splash = new SplashClass1(this, db);
					FragmentManager fragmentManager = getSupportFragmentManager();
				    fragmentManager.beginTransaction().replace(R.id.content_frame, splash)
				    	.addToBackStack(null).commit();
				}
				
				//SI EXISTE, BORRAR TABLA DE LOGS Y AJUSTAR.
				else{
					Log.i(Tag._TAG, "Ya existe db...");
				}
			
			}
			
			
			//SI NO ES LA PRIMERA VEZ
			else{
			}
			db.close();
		}
	}
	
	@Override
	protected void onStart() {		
		super.onStart();	
		Log.i(Tag._TAG, "start");
		auxOnStart();
		
		//CARGAR BASE DE DATOS
		db.open();
		tags = getResources().getStringArray(R.array.fields);
		this.dbaux = new DbAuxClass(this, this.db);
		this.dbaux.setPlanets();
		this.dbaux.setSatellites();
		this.planets = this.dbaux.getPlanets();
		this.satellites = this.dbaux.getSatelites();
		this.adapter = new FillMenuAdapter(this, planets);
		drawerList.setAdapter(adapter.getItemAdapter());
		drawerList.setOnItemClickListener(this);
				
		maindrawer.setDrawerListener(drawerToggle);
		drawerToggle.syncState();
		selectItem(_POS);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//REVISAR EL SERVICIO EN ESTE PUNTO
		Log.i(Tag._TAG, "resume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(Tag._TAG, "Pause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.i(Tag._TAG, "Stop");
	}
	
	/*@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		finish();
	}*/
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//finish();
		Log.w(Tag._TAG, "Destroy");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle out) {
		out.putInt("poskey", this._POS);
		out.putInt("currposkey", this._CURRENT_POS);
		fragmentManager.beginTransaction().remove(fragment).commit();
		super.onSaveInstanceState(out);
	}
	
	private void auxOnStart(){
		//MOSTRAR EL MENU
		if(this.hasmenu == true){
			new MenuSettings(this).show();
		}
		
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
		              | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
			
		this.maindrawer = (DrawerLayout)findViewById(R.id.drawer_layout);        
		drawerList = (ListView) findViewById(R.id.left_drawer);
		drawerToggle = new ActionBarDrawerToggle(
		            this,               
		            maindrawer,R.drawable.ic_drawer,        
		            R.string.drawer_open, 
		            R.string.drawer_close
		) {
					public void onDrawerClosed(View view) {
		            	getActionBar().setTitle(planets[_POS].getName());
		                invalidateOptionsMenu();
		            }

		            public void onDrawerOpened(View drawerView) {
		                getActionBar().setTitle(R.string.drawer_open);
		                invalidateOptionsMenu();
		            }
		  };
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.solar, menu);

        return true;
    }
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	  	switch(item.getItemId()){
	  	case R.id.action_settings: new SetttingsClass(this).isNoty(_isChk); break;
	   	case R.id.about: new SetttingsClass(this).isAbout();
	   	}
	    if (drawerToggle.onOptionsItemSelected(item)) { 
	       return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		this._POS = position;
		if(position != this._CURRENT_POS){
			this._CURRENT_POS = position;
			selectItem(position);
		}
		else maindrawer.closeDrawer(drawerList);
	}
	
	public void selectItem(int position) {
		fragment = new MainFragment();
	    fragment.setact(this);
	    fragment.setPlanet(planets[position], tags);
	    fragment.setPosition(position);
	    fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	   
	    drawerList.setItemChecked(position, true);
	    maindrawer.closeDrawer(drawerList);		
	}
	
	
	/***
	 * hasta aquí!
	 */
	/*public void start(){
		if(getIsAct().equals("off")){
			runAlarm();
			db.updateLogIsact("on");
			db.close();
		}		
		else db.close();
	}
	
	private String getIsAct(){
		db = new SolarDb(this);
		db.open();
		return db.getIsAct();
	}
	
	public void start2(Activity act){		
		act.getActionBar().show();
		if(getIsAct2(act).equals("off")){
			runAlarm2(act);
			db.updateLogIsact("on");
			db.close();
		}		
		else db.close();
		//checkUpdate(act);
	}
	
	private String getIsAct2(Activity act){
		db = new SolarDb(act);
		db.open();
		return db.getIsAct();
	}
	
	public boolean checkUpdate(Activity act){
		Log.i(Tag._TAG, "UPDATE!");
		db = new SolarDb(act);
		db.open();
		
		String aux = db.getUpdate();
		if(aux == null){
			String currentAct = db.getIsAct();
			String currentCheck = db.getIsCheck();
			db.dropTable(3);
			db.createTableLog();
			String[] aux2 = db.getMarsTemp(db.getIdbyPname("Mars"));
			db.createLog(currentAct, currentCheck, aux2[0], aux2[1],-1);
		}		
		int[] updates = act.getResources().getIntArray(R.array.updates);
		if(!db.getUpdate().equals("-2")){
			for(int i=0; i<updates.length; i++){
				if(updates[i] == 1)
					db.updateLogUpdate(i);
			}
			if(!(aux=db.getUpdate()).equals("-1")){
				int[] inits = act.getResources().getIntArray(R.array.planetscode);
				int[] inits2 = act.getResources().getIntArray(R.array.satcode);
				switch(Integer.parseInt(aux)){
					case 0: break;
					case 1:
						String[] dats;
						db.dropTable(1);
						db.createTableDetalle();
						for (int i = 0; i < inits.length; i++) {
							dats = act.getResources().getStringArray(inits[i]);
							db.create(dats);
						}
						for (int i = 0; i < inits2.length; i++) {
							dats = act.getResources().getStringArray(inits2[i]);
							db.create(dats);
						}
					break;
					
					case 2: break;
					case 3: break;
				}
				db.updateLogUpdate(-2);
				db.close();
				String[] mdats = act.getResources().getStringArray(R.array.fromcuriositi);
				new PlanetSource((FragmentActivity) act, mdats);
			}		
		}
		
		return true;
	}
	
	public void runAlarm(){
		intent = new Intent(this, SspiaService.class);
		bindService(intent, connect, Context.BIND_AUTO_CREATE);
		service = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		in = new Intent(this, SspiaService.class);
		pintent = PendingIntent.getService(this, 0, in, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		cal = Calendar.getInstance();
		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				_INTERVAL, pintent);
	}
	
	public void runAlarm2(Activity act){
		intent = new Intent(act, SspiaService.class);
		act.bindService(intent, connect, Context.BIND_AUTO_CREATE);
		service = (AlarmManager)act.getSystemService(Context.ALARM_SERVICE);
		in = new Intent(act, SspiaService.class);
		pintent = PendingIntent.getService(act, 0, in, 
				PendingIntent.FLAG_UPDATE_CURRENT);
		cal = Calendar.getInstance();
		service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
				_INTERVAL, pintent);
	}
	
	private ServiceConnection connect = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			serv = IRemoteService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serv = null;
		}
	};*/	
}

class SplashClass1 extends Fragment{
	
	private ProgressBar progress;
	private View relative;
	private Activity context;
	private SolarDb db;
	private SplashClass splash;

	public SplashClass1(Activity context, SolarDb db) {
		this.context = context;
		this.db = db;
	}	  	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    relative = inflater.inflate(R.layout.loadingactivity, null);
	    progress = (ProgressBar)relative.findViewById(R.id.loading_progress);
	    //new SplashClass(context, progress, db).execute();
	    splash = new SplashClass(context, progress, db);
	    return relative;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//new DBMaker(context, db, progress).initialize();
		splash.initialize();
		Log.i(Tag._TAG, "en espera...");
		//splash.execute();
		//Thread.sleep(5000);
		Log.i(Tag._TAG, "sale de espera..");
	}
	
}
