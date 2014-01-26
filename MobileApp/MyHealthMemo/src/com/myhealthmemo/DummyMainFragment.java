package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import com.myhealthmemo.adapter.DateUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class DummyMainFragment extends Fragment implements OnClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView mTxtView;
	private Button Btn1, Btn2;
	Date date, date2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dummy_fragment, container, false);
		mTxtView = (TextView) rootView.findViewById(R.id.dateArgument);
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
