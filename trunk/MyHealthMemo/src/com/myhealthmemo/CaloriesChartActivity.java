package com.myhealthmemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class CaloriesChartActivity extends FragmentActivity {

	HomePagerAdapter HomePagerAdapter;
	static ViewPager mViewPager;
	int i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calories_chart);
		HomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager4);
		mViewPager.setAdapter(HomePagerAdapter);
		mViewPager.setCurrentItem(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calories_chart, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case android.R.id.home:
			 finish();
		}
		return super.onOptionsItemSelected(item);
		
	}
	

	
	public static void minusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a-1);
	}
	
	public static void plusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a+1);
	}
	
	public class HomePagerAdapter extends FragmentPagerAdapter {
		
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
			
		public HomePagerAdapter (FragmentManager fm) {
				super(fm);
		}
			
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummyCaloriesChartFragment();
			Bundle args = new Bundle();
			DateTime oldPlus = old.plusDays(position);
			Date oldPlusDate = oldPlus.toDate();
			SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
			String a = formatter.format(oldPlusDate);
			args.putString(DummyMainFragment.ARG_SECTION_NUMBER, a);
			fragment.setArguments(args);
			return fragment;
		}
			
		@Override
		public int getCount() {
			DateTime now = new DateTime();
			i = Days.daysBetween(old, now).getDays();
			return i+1;
		}
	}

}
