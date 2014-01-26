package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

import com.myhealthmemo.adapter.DateUtils;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DummyDietFragment extends Fragment implements OnClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView mTxtView;
	private Button Btn1, Btn2;
	Date date, date2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dummy_diet_fragment, container, false);
		mTxtView = (TextView) rootView.findViewById(R.id.dateArgument2);
		mTxtView.setText(getArguments().getString(ARG_SECTION_NUMBER));
		Btn1 = (Button) rootView.findViewById(R.id.left_arrow2);
		Btn1.setOnClickListener(this);
		Btn2 = (Button) rootView.findViewById(R.id.right_arrow2);
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
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.dummy_diet, menu);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.left_arrow2:
			MainFragment.minusOne();
			break;
		case R.id.right_arrow2:
			MainFragment.plusOne();
			break;
		case R.id.dateArgument2:

			break;
		default:
			break;
		}
	}

}
