package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.myhealthmemo.adapter.ActivityArrayAdapter;
import com.myhealthmemo.adapter.DateUtils;
import com.myhealthmemo.model.ActivityItem;

public class DummyActivityFragment extends Fragment implements OnClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView mTxtView;
	private Button Btn1, Btn2;
	Date date, date2;
	private ListView lv;
	private boolean flag = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dummy_activity_fragment, container, false);
		setHasOptionsMenu(true);
		mTxtView = (TextView) rootView.findViewById(R.id.dateArgument3);
		mTxtView.setText(getArguments().getString(ARG_SECTION_NUMBER));
		Btn1 = (Button) rootView.findViewById(R.id.left_arrow3);
		Btn1.setOnClickListener(this);
		Btn2 = (Button) rootView.findViewById(R.id.right_arrow3);
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
		inflater.inflate(R.menu.dummy_activity, menu);
	}
	
	@Override
	public  boolean onOptionsItemSelected(MenuItem item){
   	 //calling the navigation_add function
		Intent mIntent = null;
		switch(item.getItemId()){
		case R.id.add:
			mIntent = new Intent(getActivity().getBaseContext(), AddActivity.class);
			startActivity(mIntent);
			break;
		case R.id.edit:
			getActivity().invalidateOptionsMenu();
		case R.id.delete:
			break;
		case R.id.accept:
			getActivity().invalidateOptionsMenu();
			break;
		default:
	   		break;
   		}
		return super.onOptionsItemSelected(item);
   	}
	
	 @Override  
	 public void onPrepareOptionsMenu(Menu menu) {
	    	
		 MenuInflater inflater = getActivity().getMenuInflater();
		 if(!flag){
			 menu.clear();
			 inflater.inflate(R.menu.dummy_activity_2, menu);
			 flag=true;
		 } else {
			 menu.clear();
			 inflater.inflate(R.menu.dummy_activity, menu);
			 flag=false;
		 }
		 
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
