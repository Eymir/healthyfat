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
		dpd.getDatePicker().setMaxDate(new Date().getTime());
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
		Date date2 = old.toDate();
		dpd.getDatePicker().setMinDate(date2.getTime());
		return dpd;
	}
}
