package com.myhealthmemo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.AdapterView.OnItemSelectedListener;

public class DummyDietServingSizeActivity  extends Activity implements NumberPicker.OnValueChangeListener, OnItemSelectedListener {
	
	private Button mBtn;
	private EditText ssEdit;
	private String[] listitem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_diet_serving_size);
		ssEdit = (EditText) findViewById(R.id.boxnumber);
		ListView lv = (ListView) findViewById(R.id.ingredient_list);
		setupActionBar();
		
	ssEdit.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		      public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					show2();
				} 
		      }
		});			
		
	}

	public void show2() {
		final Dialog mDialog = new Dialog(DummyDietServingSizeActivity.this);
		mDialog.setTitle("Set Serving Size");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		mNumPk.setMaxValue(9);
		mNumPk.setMinValue(0);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
		mNumPk.setOnValueChangedListener(this);
		mNumPk2.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ssEdit.setText(String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue()));
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
