package com.myhealthmemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.myhealthmemo.MainFragment.HomePagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class DietDiaryFragment extends Fragment {

	DietPagerAdapter DietPagerAdapter;
	static ViewPager mViewPager;
	int i;
	
	public DietDiaryFragment(){
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.diet_diary_fragment, container, false);
		DietPagerAdapter = new DietPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager2);
		mViewPager.setAdapter(DietPagerAdapter);
		mViewPager.setCurrentItem(i);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.diet_diary, menu);
	}
	
	public static void minusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a-1);
	}
	
	public static void plusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a+1);
	}
	
	public class DietPagerAdapter extends FragmentPagerAdapter {
		
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
			
		public DietPagerAdapter (FragmentManager fm) {
				super(fm);
		}
			
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummyDietFragment();
			Bundle args = new Bundle();
			DateTime oldPlus = old.plusDays(position);
			Date oldPlusDate = oldPlus.toDate();
			SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
			String a = formatter.format(oldPlusDate);
			args.putString(DummyDietFragment.ARG_SECTION_NUMBER, a);
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
