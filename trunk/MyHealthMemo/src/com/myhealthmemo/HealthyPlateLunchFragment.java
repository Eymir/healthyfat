package com.myhealthmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class HealthyPlateLunchFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.healthy_plate_lunch_fragment, container, false);
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.healthy_plate_lunch, menu);
	}

	public static HealthyPlateLunchFragment newInstance(Bundle args) {

		HealthyPlateLunchFragment f = new HealthyPlateLunchFragment();
		f.setArguments(args);
		return f;
    }

}
