package com.fiec.sspia.mclass;

import com.fiec.ssapp.R;
import com.fiec.sspia.util.FillMenuAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SSClass implements OnItemClickListener{
	
	private FragmentActivity act;
	private DrawerLayout maindrawer;
	private ListView drawerList;
	public static ActionBarDrawerToggle drawerToggle;
	
	private FillMenuAdapter adapter;
	private String[] planets;
	private int[] img;
	private int position;
	

	public SSClass(Activity activity) {
		this.act = (FragmentActivity) activity;
		        
        activity.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getActionBar().setDisplayShowTitleEnabled(true);
        
        this.planets = activity.getResources().getStringArray(R.array.theplanets);
        this.img = activity.getResources().getIntArray(R.array.theimages);
        this.adapter = new FillMenuAdapter(activity, planets, img);
        this.maindrawer = (DrawerLayout)activity.findViewById(R.id.drawer_layout);        
		drawerList = (ListView) activity.findViewById(R.id.left_drawer);
		drawerList.setAdapter(adapter.getItemAdapter());
		drawerList.setOnItemClickListener(this);
		
		drawerToggle = new ActionBarDrawerToggle(
                activity,               
                maindrawer,R.drawable.ic_drawer,         
                R.string.drawer_open, 
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View view) {
            	act.getActionBar().setTitle(planets[position]);
                act.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                act.getActionBar().setTitle(R.string.drawer_open);
                act.invalidateOptionsMenu();
            }
        };
       maindrawer.setDrawerListener(drawerToggle);
        
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectItem(position);
		this.position = position;
	}
	
	public void selectItem(int position) {
		MainFragment fragment = new MainFragment();
	    fragment.setact(act);
	    fragment.setStrings(planets[position], img[position]);
	    fragment.setPosition(position);
	    FragmentManager fragmentManager = act.getSupportFragmentManager();
	    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
	   
	    drawerList.setItemChecked(position, true);
	    maindrawer.closeDrawer(drawerList);		
	}
}
