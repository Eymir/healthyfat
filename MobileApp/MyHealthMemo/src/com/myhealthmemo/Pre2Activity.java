package com.myhealthmemo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Pre2Activity extends Activity implements NumberPicker.OnValueChangeListener, OnItemSelectedListener, 
	OnFocusChangeListener, OnClickListener, OnCheckedChangeListener {

	private SharedPreferences mPrefs;
	private Intent mIntent;
	private EditText mhEdit,mwEdit,mcEdit,mrnEdit;
	private Spinner mSpinner;
	private AutoCompleteTextView mAuto;
	private Button mBtn;
	private String w;
	private String[] mPriSch,mSecSch,nums;
	private RadioGroup mGroup;
	private RadioButton mRB;
	private SharedPreferences.Editor mPrefsEdit;
	private double daily_calories_need,bmr,wp,hp,ap;
	private int whole,decimal,number;
	private boolean isNull;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre2);
		// Show the Up button in the action bar.
		setupActionBar();
		init();	
		setPriAdapter();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_level_arrays, R.layout.dropdown);
		adapter.setDropDownViewResource(R.layout.dropdown);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(Pre2Activity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		hasPreference();
		setListener();
	}
	
	private void init(){
		mhEdit = (EditText) findViewById(R.id.height_Edit);
		mwEdit = (EditText) findViewById(R.id.weight_Edit);
		mAuto = (AutoCompleteTextView) findViewById(R.id.school_Edit);
		mGroup = (RadioGroup) findViewById(R.id.radio_Education);
		mcEdit = (EditText) findViewById(R.id.class_Edit);
		mrnEdit = (EditText) findViewById(R.id.reg_no_Edit);
		mSpinner = (Spinner) findViewById(R.id.activity_level_Edit);
		mPriSch = getResources()
				.getStringArray(R.array.primary_school_arrays);
		mSecSch = getResources()
				.getStringArray(R.array.secondary_school_arrays);
	}
	
	private void setListener(){
		mhEdit.setOnFocusChangeListener(this);
		mhEdit.setOnClickListener(this);
		mwEdit.setOnFocusChangeListener(this);
		mwEdit.setOnClickListener(this);
		mGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_timer2, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			mPrefsEdit.putString("height", mhEdit.getText().toString());
			mPrefsEdit.putString("weight", mwEdit.getText().toString());	
			mPrefsEdit.putString("bmi", calculateBMI(mhEdit.getText().toString(),mwEdit.getText().toString()));
			mPrefsEdit.putString("daily_calories_need", calculateDCN(mhEdit.getText().toString(),mwEdit.getText().toString()));
			switch (mGroup.getCheckedRadioButtonId()) {
			case R.id.radio_primary:
				mRB = (RadioButton) findViewById(R.id.radio_primary);
				break;
			case R.id.radio_secondary:
				mRB = (RadioButton) findViewById(R.id.radio_secondary);
				break;
			default:
				break;
			} 
			mPrefsEdit.putString("education", mRB.getText().toString());
			mPrefsEdit.putString("school", mAuto.getText().toString());
			mPrefsEdit.putString("class", mcEdit.getText().toString());
			mPrefsEdit.putString("reg_no", mrnEdit.getText().toString());
			mPrefsEdit.putString("activity_level", mSpinner.getSelectedItem().toString());
			mPrefsEdit.commit();
			finish();
			break;
		case R.id.accept:
			isNull = isEmpty(mhEdit.getText().toString());
			
			if(isNull){
				showAlert();
			} else {
				isNull = isEmpty(mwEdit.getText().toString());
				if (isNull){
					showAlert();
				} else {
					isNull = isEmpty(mAuto.getText().toString());
					if(isNull){
						showAlert();
					} else {
						isNull = isEmpty(mcEdit.getText().toString());
						if (isNull){
							showAlert();
						} else {
							isNull = isEmpty(mrnEdit.getText().toString());
							if (isNull){
								showAlert();
							} else {
								mPrefsEdit.putString("height", mhEdit.getText().toString());
								mPrefsEdit.putString("weight", mwEdit.getText().toString());	
								mPrefsEdit.putString("bmi", calculateBMI(mhEdit.getText().toString(),mwEdit.getText().toString()));
								mPrefsEdit.putString("daily_calories_need", calculateDCN(mhEdit.getText().toString(),mwEdit.getText().toString()));
								switch (mGroup.getCheckedRadioButtonId()) {
								case R.id.radio_primary:
									mRB = (RadioButton) findViewById(R.id.radio_primary);
									break;
								case R.id.radio_secondary:
									mRB = (RadioButton) findViewById(R.id.radio_secondary);
									break;
								default:
									break;
								} 
								mPrefsEdit.putString("education", mRB.getText().toString());
								mPrefsEdit.putString("school", mAuto.getText().toString());
								mPrefsEdit.putString("class", mcEdit.getText().toString());
								mPrefsEdit.putString("reg_no", mrnEdit.getText().toString());
								mPrefsEdit.putString("activity_level", mSpinner.getSelectedItem().toString());
							
								mIntent = new Intent (this, NavDrawerActivity.class);
								startActivity(mIntent);
								
								mPrefsEdit.putBoolean("firstrun", false);
								mPrefsEdit.commit();
								
							}
						}
					}
				}
			}
			break;
			
		}
		return true;
	}
	
	private void hasPreference(){
		isNull = isEmpty(mPrefs.getString("height", ""));
		if(!isNull){
			mhEdit.setText(mPrefs.getString("height",""));
		}
		isNull = isEmpty(mPrefs.getString("weight", ""));
		if(!isNull){
			mwEdit.setText(mPrefs.getString("weight", ""));
		}
		isNull = isEmpty(mPrefs.getString("school", ""));
		if(!isNull){
			mAuto.setText(mPrefs.getString("school", ""));
		}
		isNull = isEmpty(mPrefs.getString("class", ""));
		if(!isNull){
			mcEdit.setText(mPrefs.getString("class",""));
		}
		isNull = isEmpty(mPrefs.getString("reg_no", ""));
		if(!isNull){
			mrnEdit.setText(mPrefs.getString("reg_no",""));
		}
		isNull = isEmpty(mPrefs.getString("activity_level", ""));
		if(!isNull){
			if (mPrefs.getString("activity_level", "").equals("little or no exercise")){
				mSpinner.setSelection(0);
			}
			else if (mPrefs.getString("activity_level", "").equals("light exercise/sports")){
				mSpinner.setSelection(1);
			}
			else if (mPrefs.getString("activity_level", "").equals("moderate exercise/sports")){
				mSpinner.setSelection(2);
			}
			else if (mPrefs.getString("activity_level", "").equals("hard exercise/sports")){
				mSpinner.setSelection(3);
			}
			else if (mPrefs.getString("activity_level", "").equals("very hard exercise/sports")){
				mSpinner.setSelection(4);
			}
		}
	}
	
	@Override
    public void onFocusChange(View v, boolean hasFocus) {
		switch(v.getId()){
		case R.id.height_Edit:
			if(hasFocus){
				showHeightDialog();
			} 
			break;
		case R.id.weight_Edit:
			if(hasFocus){
				showWeightDialog();
			} 
			break;
		default:
			break;
		}
		
		
    }
	
	@Override
    public void onClick(View v) {
    	switch (v.getId()){
    	case R.id.height_Edit:
    		showHeightDialog();
    		break;
    	case R.id.weight_Edit:
    		showWeightDialog();
    		break;
    	}
	}
	
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(group.getId()){
		case R.id.radio_Education:
			switch (checkedId) {
    		case R.id.radio_primary:
    			setPriAdapter();
    			break;
    		case R.id.radio_secondary:
    			setSecAdapter();
    			break;
    		default:
    			break;
    		}
			break;
    	default:
    		break;
		}
    }
	
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		Log.i("value is", ""+newVal);
	}
	
	private void showHeightDialog() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set Height");
		mDialog.setContentView(R.layout.number_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		mNumPk.setMaxValue(200);
		mNumPk.setMinValue(50);
		if(!isEmpty(mhEdit.getText().toString())){
			number = Integer.parseInt(mhEdit.getText().toString());
			mNumPk.setValue(number);
		}
		mNumPk.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mhEdit.setText(String.valueOf(mNumPk.getValue()));
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	private void showWeightDialog() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set Weight");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		mNumPk.setMaxValue(100);
		mNumPk.setMinValue(20);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
		if(!isEmpty(mwEdit.getText().toString())){
			w = mwEdit.getText().toString();
			nums =  w.split("\\.");
			whole = Integer.parseInt(nums[0]);
			decimal = Integer.parseInt(nums[1]);
			mNumPk.setValue(whole);
			mNumPk2.setValue(decimal);
		}
		mNumPk.setOnValueChangedListener(this);
		mNumPk2.setOnValueChangedListener(this);
		mBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				mwEdit.setText(String.valueOf(mNumPk.getValue()) + "." + String.valueOf(mNumPk2.getValue()));
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		parent.getItemAtPosition(pos);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	public boolean isEmpty(String a){
		if (a.matches("")){
			return true;
		} else {
			return false;
		}
	}
	
	public void showAlert(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		//set title
		alertDialogBuilder.setTitle("Attention!");
		//set dialog message
		alertDialogBuilder
		.setMessage("Please fill up all required fields before proceeding...")
		.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				/*
				 * if this button is clicked, just close
				 * the dialog box and do nothing
				 */
				dialog.cancel();
				
			}
		});
		//create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		//show it
		alertDialog.show();
	}
	
	public void setPriAdapter(){
		mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mPriSch));
	}
	
	public void setSecAdapter(){
		mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mSecSch));
	}
	
	public String calculateBMI(String height, String weight){
		BigDecimal kg_weight = new BigDecimal(weight);
		BigDecimal cm_height = new BigDecimal(height);
		BigDecimal hundred = new BigDecimal("100");
		BigDecimal m_height = cm_height.divide(hundred,1,RoundingMode.HALF_UP);
		BigDecimal m_height_sq = m_height.multiply(m_height);
		BigDecimal bmi = kg_weight.divide(m_height_sq,1,RoundingMode.HALF_UP);
		return bmi.toString();
	}
	
	public int calculateAge(String dob){
		Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
	    Date d = null;
	    try {
	    	d = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
	    		.parse(mPrefs.getString("dob", ""));
	        
	    } catch (ParseException e) {
	    	
	    }
	    cal2.setTime(d);
        int year2 = cal2.get(Calendar.YEAR);
        int month2 = cal2.get(Calendar.MONTH) + 1;
        int day2 = cal2.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int current_age = year - year2;
		if (month2 > month){
			current_age = current_age-1;
		}
		else if (month2 == month){
			if (day < day2){
				current_age = current_age-1;
			}
		}
		return current_age;
	}
	
	public String calculateDCN(String height, String weight){
		double kg_weight = Double.parseDouble(weight);
		double cm_height = Double.parseDouble(height);
		int current_age = calculateAge(mPrefs.getString("dob", ""));

		if(mPrefs.getString("gender","").equals("Male")){
			wp = 10 * kg_weight;
			hp = 6.25 * cm_height;
			ap = 5 * current_age;
			bmr = wp + hp - ap + 5;
		}
		else if (mPrefs.getString("gender","").equals("Female")){
			wp = 10 * kg_weight;
			hp = 6.25 * cm_height;
			ap = 5 * current_age;
			bmr = wp + hp - ap - 161;
		}
		
		if (mSpinner.getSelectedItem().toString().equals("little or no exercise")){
			daily_calories_need = bmr * 1.2;
		}
		else if (mSpinner.getSelectedItem().toString().equals("light exercise/sports")){
			daily_calories_need = bmr * 1.375;
		}
		else if (mSpinner.getSelectedItem().toString().equals("moderate exercise/sports")){
			daily_calories_need = bmr * 1.55;
		}
		else if (mSpinner.getSelectedItem().toString().equals("hard exercise/sports")){
			daily_calories_need = bmr * 1.725;
		}
		else{
			daily_calories_need = bmr * 1.9;
		}
		int int_dcn = (int) Math.round(daily_calories_need);
		return String.valueOf(int_dcn);
	}


}
