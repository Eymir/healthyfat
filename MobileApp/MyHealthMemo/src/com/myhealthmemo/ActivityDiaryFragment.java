package com.myhealthmemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.myhealthmemo.R;
import org.joda.time.DateTime;
import org.joda.time.Days;

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

public class ActivityDiaryFragment extends Fragment {

	ActivityPagerAdapter ActivityPagerAdapter;
	static ViewPager mViewPager;
	int i;
	
	public ActivityDiaryFragment(){
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.activity_diary_fragment, container, false);
		ActivityPagerAdapter = new ActivityPagerAdapter(getChildFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager3);
		mViewPager.setAdapter(ActivityPagerAdapter);
		mViewPager.setCurrentItem(i);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_diary, menu);
	}
	
	public static void minusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a-1);
	}
	
	public static void plusOne(){
		int a = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(a+1);
	}
	
	public class ActivityPagerAdapter extends FragmentPagerAdapter {
		
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
			
		public ActivityPagerAdapter (FragmentManager fm) {
				super(fm);
		}
			
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new DummyActivityFragment();
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
