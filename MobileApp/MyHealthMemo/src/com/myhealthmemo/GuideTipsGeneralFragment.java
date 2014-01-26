package com.myhealthmemo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GuideTipsGeneralFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.guide_tips_general_fragment, container, false);
		TextView tv = (TextView) rootView.findViewById(R.id.tvFragFirst3);
		tv.setText(getArguments().getString("msg"));
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.guide_tips_general, menu);
	}
	
	public static GuideTipsGeneralFragment newInstance(String text) {

		GuideTipsGeneralFragment f = new GuideTipsGeneralFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
