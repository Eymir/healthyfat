package com.myhealthmemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("SimpleDateFormat")
public class MainFragment extends Fragment {

	HomePagerAdapter HomePagerAdapter;
	static ViewPager mViewPager;
	int i;
	
	public MainFragment(){
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.home_fragment, container, false);
		HomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(HomePagerAdapter);
		mViewPager.setCurrentItem(i);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight, menu);
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
			Fragment fragment = new DummyMainFragment();
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
