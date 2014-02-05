package com.myhealthmemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HealthyPlateDailyFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.healthy_plate_daily_fragment, container, false);
		TextView tv = (TextView) rootView.findViewById(R.id.tvFragFirst2);
		tv.setText(getArguments().getString("msg"));
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.healthy_plate_daily, menu);
	}
	
	public static HealthyPlateDailyFragment newInstance(String text) {

		HealthyPlateDailyFragment f = new HealthyPlateDailyFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
