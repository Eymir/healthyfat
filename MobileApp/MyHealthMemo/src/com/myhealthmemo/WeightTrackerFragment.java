package com.myhealthmemo;

import java.util.Locale;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class WeightTrackerFragment extends Fragment implements ActionBar.TabListener {
	
	MyPagerAdapter mMyPagerAdapter;
	ViewPager mViewPager;
	
	public WeightTrackerFragment(){
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.weight_tracker_fragment, container, false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mMyPagerAdapter = new MyPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.frame_container2);
		mViewPager.setAdapter(mMyPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getActivity().getActionBar().setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mMyPagerAdapter.getCount(); i++) {
			getActivity().getActionBar().addTab(
					getActivity().getActionBar().newTab()
					.setText(getPageTitle(i))
					.setTabListener(this));
		}
		setHasOptionsMenu(true);
		return rootView;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.navigation_forward:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onTabReselected(Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab,
			android.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
		
	}
	@Override
	public void onTabUnselected(Tab tab,
			android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.weightfirsttab).toUpperCase(l);
		case 1:
			return getString(R.string.weightsecondtab).toUpperCase(l);
		case 2:
			return getString(R.string.weightthirdtab).toUpperCase(l);
		}
		return null;
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter (FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0: return WeightTrackerHomeFragment.newInstance("WeightTrackerHomeFragment, Instance 1");
			case 1: return WeightTrackerBMIFragment.newInstance("BMIFragment, Instance 2");
			case 2: return WeightHistoryFragment.newInstance("WeightHistoryFragment, Instance 3");
			default: return WeightTrackerHomeFragment.newInstance("WeightTrackerHomeFragment, Default");
			}
		}
		
		@Override
		public int getCount() {
			return 3;
		}
	}
	
}

