package com.myhealthmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class HealthyPlateBreakfastFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.healthy_plate_breakfast_fragment, container, false);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.healthy_plate_breakfast, menu);
	}
	
	public static HealthyPlateBreakfastFragment newInstance(Bundle args) {

		HealthyPlateBreakfastFragment f = new HealthyPlateBreakfastFragment();
		f.setArguments(args);
		return f;
    }

}
