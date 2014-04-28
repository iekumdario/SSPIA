package com.fiec.sspia;

import com.fiec.ssapp.R;
import com.fiec.sspia.db.SolarDb;
import com.fiec.sspia.mclass.MainClass;
import com.fiec.sspia.mclass.SSClass;
import com.fiec.sspia.mclass.SetttingsClass;
import com.fiec.sspia.mclass.SplashClass;
import com.fiec.sspia.system.IRemoteService;
import com.fiec.sspia.util.MenuSettings;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.ProgressBar;

public class SolarActivity extends MainClass{
	private String _isChk = "false";
	private SolarDb db;
	private SSClass clase;
	public IRemoteService serv;
	private static int _POS = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solar);		
	}
	
	@Override
	protected void onResume() {
		super.onResume();		
		new MenuSettings(this).show();
		if(firstini() == true){
			getActionBar().hide();
			Fragment splash = new SplashClass1(this);
			FragmentManager fragmentManager = getSupportFragmentManager();
		    fragmentManager.beginTransaction().replace(R.id.content_frame, splash).commit();
		}
		else{		
			clase = new SSClass(this);
			SSClass.drawerToggle.syncState();
			clase.selectItem(_POS);
			super.start();
		}		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//unbindService(connect);
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
        SSClass.drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.action_settings: new SetttingsClass(this).isNoty(_isChk); break;
    	case R.id.about: new SetttingsClass(this).isAbout();
    	}
        if (SSClass.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);		
	}

	private boolean firstini() {
		SolarDb db = new SolarDb(this);
		db.open();
		String aux = db.getPlanet();
		if (aux == null) {
			db.close();
			return true;
		}
		db.close();
		return false;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		this.finish();
	}
	
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	//this.finish();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	clase = null;
    }
}

class SplashClass1 extends Fragment{
	
	private ProgressBar progress;
	private View relative;
	private Activity context;
	
	public SplashClass1(Activity context) {
		this.context = context;
	}	  	
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    relative = inflater.inflate(R.layout.loadingactivity, null);
	    progress = (ProgressBar)relative.findViewById(R.id.loading_progress);
	    new SplashClass(context, progress).execute();
	    return relative;
	}
}