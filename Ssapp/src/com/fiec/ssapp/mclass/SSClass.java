package com.fiec.ssapp.mclass;

import com.fiec.ssapp.R;
import com.fiec.ssapp.util.FillMenuAdapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SSClass implements OnItemClickListener{
	
	private Activity act;
	private DrawerLayout maindrawer;
	private ListView drawerList;
	public static ActionBarDrawerToggle drawerToggle;
	
	private FillMenuAdapter adapter;
	private String[] planets;
	private int[] img;

	public SSClass(Activity activity) {
		this.act = activity;
		
		activity.getActionBar().setDisplayHomeAsUpEnabled(true);
		//activity.getActionBar().setHomeButtonEnabled(true);
        activity.getActionBar().setTitle(R.string.title_app);
        
        this.planets = activity.getResources().getStringArray(R.array.theplanets);
        this.img = activity.getResources().getIntArray(R.array.theimages);
        
        this.adapter = new FillMenuAdapter(activity, planets, img);
        this.maindrawer = (DrawerLayout)activity.findViewById(R.id.drawer_layout);
        this.maindrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);        
		drawerList = (ListView) activity.findViewById(R.id.left_drawer);
		drawerList.setAdapter(adapter.getItemAdapter());
		drawerList.setOnItemClickListener(this);
		
		drawerToggle = new ActionBarDrawerToggle(
                activity,               
                maindrawer,R.array.mercury,         
                /*R.drawable.ic_drawer,*/
                R.string.drawer_open, 
                R.string.drawer_close
                ) {
            public void onDrawerClosed(View view) {
                //act.getActionBar().setTitle("");
                act.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //act.getActionBar().setTitle(R.string.action_menu);
                act.invalidateOptionsMenu();
            }
        };
       maindrawer.setDrawerListener(drawerToggle);
       selectItem(0); 
        
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectItem(position);
	}
	
	private void selectItem(int position) {		 
	     //Bundle args = new Bundle();
	     //args.putCharSequence(BaseMenuClass.ARG_TXT, "Inicio");
	     MainFragment fragment = new MainFragment();
	     fragment.setact(act);
	     fragment.setStrings(planets[position], img[position]);
	     fragment.setPosition(position);
	     //((BaseMenuClass) fragment).setAdapter(adapter.getItemAdapter());
	     //fragment.setArguments(args);

	     FragmentManager fragmentManager = act.getFragmentManager();
	     fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	     drawerList.setItemChecked(position, true);
	     //setTitle("Inicio");
	     maindrawer.closeDrawer(drawerList);		
	}

}
