package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.myhealthmemo.common.DateUtils;
import com.myhealthmemo.piechart.PieChart;

public class DummyHPFragment extends Fragment implements OnClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView mTxtView, rda_No;
	private Button Btn1, Btn2;
	Date date, date2;
	PieChart pie;
	protected SharedPreferences mPrefs;
	private FragmentTabHost mTabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dummy_hpfragment, container, false);
		mTxtView = (TextView) rootView.findViewById(R.id.dateArgument);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(getActivity(), R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		mTxtView.setText(getArguments().getString(ARG_SECTION_NUMBER));
		Btn1 = (Button) rootView.findViewById(R.id.left_arrow);
		Btn1.setOnClickListener(this);
		Btn2 = (Button) rootView.findViewById(R.id.right_arrow);
		Btn2.setOnClickListener(this);
		mTxtView.setOnClickListener(this);
		SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
		String a = getArguments().getString(ARG_SECTION_NUMBER);
		try {
			date = formatter.parse(a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DateUtils.isToday(date)){
			Btn2.setVisibility(View.INVISIBLE);
		}
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
		Date date2 = old.toDate();
		if (DateUtils.isSameDay(date, date2)){
			Btn1.setVisibility(View.INVISIBLE);
		}
		mTabHost = (FragmentTabHost) rootView.findViewById(R.id.fragmentTab);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_tabhost);
		Bundle arg1 = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("TabHostTextView1").setIndicator(""),
			HealthyPlateDailyFragment.class, arg1);

		Bundle arg2 = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("TabHostTextView2").setIndicator(""),
			HealthyPlateBreakfastFragment.class, arg2);
		
		Bundle arg3 = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("TabHostTextView3").setIndicator(""),
			HealthyPlateLunchFragment.class, arg3);
		
		Bundle arg4 = new Bundle();
		mTabHost.addTab(mTabHost.newTabSpec("TabHostTextView4").setIndicator(""),
			HealthyPlateDinnerFragment.class, arg4);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.weight, menu);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.left_arrow:
			MainFragment.minusOne();
			break;
		case R.id.right_arrow:
			MainFragment.plusOne();
			break;
		case R.id.dateArgument:

			break;
		default:
			break;
		}
	}

}
