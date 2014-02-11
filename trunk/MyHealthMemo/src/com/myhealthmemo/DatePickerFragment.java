package com.myhealthmemo;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
	static final int mDialog = 1;
	protected int mYear, mDay, mMonth;
	protected Calendar mCalendar;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		mCalendar = Calendar.getInstance();
		mYear = mCalendar.get(Calendar.YEAR);
		mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		mMonth = mCalendar.get(Calendar.MONTH);
		DatePickerDialog dpd = new DatePickerDialog(getActivity(), (PreActivity)getActivity(), mYear, mMonth, mDay);
		DateTime currentDate = new DateTime();
		int a = currentDate.getYear()-6;
		int c = currentDate.getDayOfMonth();
		int d = currentDate.getMonthOfYear();
		DateTime maxDate = new DateTime(a,d,c,0,0,0);
		Date maxDateJava = maxDate.toDate();
		dpd.getDatePicker().setMaxDate(maxDateJava.getTime());
		int b = currentDate.getYear()-18;
		DateTime minDate = new DateTime(b,1,1,0,0,0);
		Date minDateJava = minDate.toDate();
		dpd.getDatePicker().setMinDate(minDateJava.getTime());
		return dpd;
	}
}
