package com.myhealthmemo;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myhealthmemo.adapter.NavDrawerListAdapter;
import com.myhealthmemo.model.NavDrawerItem;

public class NavDrawerActivity extends FragmentActivity {

	protected SharedPreferences mPrefs;
	protected Intent mIntent;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	//nav drawer title and used to store app title
	private CharSequence mDrawerTitle,mTitle;

	//slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PreferenceManager.setDefaultValues(NavDrawerActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		/*
		 * If "firstrun" is true, navigate to PreLogin Screen
		 * commit the prefs to take note the "firstrun" is now false
		 */
		
		if (mPrefs.getBoolean("firstrun", true)) {
			
			mIntent = new Intent (this, PreActivity.class);
			startActivity(mIntent);
		}
		mTitle = mDrawerTitle = getTitle();
		
		//load slide menu items
		navMenuTitles = getResources()
				.getStringArray(R.array.nav_drawer_items);
		
		//nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		//adding nav drawer items to array
		//Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		//Diet Diary
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		//Activity Diary
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		//Weight Tracker
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		//My Healthy Plate
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		//Guides/Tips
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		//Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		
		//Recycle the typed array
		navMenuIcons.recycle();
		
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		
		//setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(), 
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		
		//enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, //nav drawer open - description for accessibility
				R.string.app_name //nav drawer close - description for accessibility
				){
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				//calling onPreparedOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		
	}
	
	/**
	 * Slide menu item click listener
	 **/
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		//Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		//if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	/**
	 *  Displaying fragment view for selected nav drawer list item
	 **/
	private void displayView(int position) {
		//update the main content by replacing fragments
		Fragment fragment = null; 
		switch (position) {
		case 0:
			fragment = new MainFragment();
			break;
		case 1:
			fragment = new DietDiaryFragment();
			break;
		case 2:
			fragment = new ActivityDiaryFragment();
			break;
		case 3:
			fragment = new WeightTrackerFragment();
			break;
		case 4:
			fragment = new HealthyPlateFragment();
			break;
		case 5:
			fragment = new GuideTipsFragment();
			break;
		case 6:
			fragment = new SettingsFragment();
			break;
		default:
			break;
		}
		
		if (fragment != null) {
			
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
			getActionBar().removeAllTabs();
			getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			//update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList); 
			
			
		} else {
			//error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during 
	 * onPostCreate() and onConfigurationChanged()...
	 */
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//Sync the toggle state after onRestoreInstanceState has occured.
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		//Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		
	}

}
