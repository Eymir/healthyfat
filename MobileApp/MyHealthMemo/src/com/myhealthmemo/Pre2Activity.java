package com.myhealthmemo;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.support.v4.app.NavUtils;

public class Pre2Activity extends Activity implements NumberPicker.OnValueChangeListener, OnItemSelectedListener {

	protected SharedPreferences mPrefs;
	protected Intent mIntent;
	private EditText mhEdit,mwEdit,mcEdit,mrnEdit;
	private Spinner mSpinner;
	private AutoCompleteTextView mAuto;
	private Button mBtn;
	private String[] mPriSch;
	private String[] mSecSch;
	private RadioGroup mGroup;
	private RadioButton mRB;
	private SharedPreferences.Editor mPrefsEdit;
	private String mSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre2);
		// Show the Up button in the action bar.
		setupActionBar();
		mhEdit = (EditText) findViewById(R.id.height_Edit);
		mwEdit = (EditText) findViewById(R.id.weight_Edit);
		mAuto = (AutoCompleteTextView) findViewById(R.id.school_Edit);
		mPriSch = getResources()
				.getStringArray(R.array.primary_school_arrays);
		mSecSch = getResources()
				.getStringArray(R.array.secondary_school_arrays);
		mGroup = (RadioGroup) findViewById(R.id.radio_Education);
		mcEdit = (EditText) findViewById(R.id.class_Edit);
		mrnEdit = (EditText) findViewById(R.id.reg_no_Edit);
		mSpinner = (Spinner) findViewById(R.id.activity_level_Edit);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activity_level_arrays, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(Pre2Activity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		mhEdit.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		      public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					show();
				} 
		      }
		});
		mwEdit.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		      public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					show2();
				} 
		      }
		});
		
		switch (mGroup.getCheckedRadioButtonId()) {
		case R.id.radio_primary:
			mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mPriSch));
			break;
		case R.id.radio_secondary:
			mAuto.setAdapter(new ArrayAdapter<String>(this,R.layout.auto_list_details,mSecSch));
			break;
		default:
			break;
		}

		mPrefs = getSharedPreferences("com.myhealthmemo", MODE_PRIVATE);
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
			NavUtils.navigateUpFromSameTask(this);
			break;
		case R.id.accept:
			mPrefsEdit.putString("height", mhEdit.getText().toString());
			mPrefsEdit.putString("weight", mwEdit.getText().toString());
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
			mPrefsEdit.putBoolean("education", mRB.isChecked());
			mPrefsEdit.putString("school", mAuto.getText().toString());
			mPrefsEdit.putString("class", mcEdit.getText().toString());
			mPrefsEdit.putString("reg_no", mrnEdit.getText().toString());
			mIntent = new Intent (this, NavDrawerActivity.class);
			startActivity(mIntent);
			mPrefs.edit().putBoolean("firstrun", false).commit();
			break;
			
		}
		return true;
	}
	
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		Log.i("value is", ""+newVal);
	}
	
	public void show() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set Height");
		mDialog.setContentView(R.layout.number_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		mNumPk.setMaxValue(200);
		mNumPk.setMinValue(0);
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
	
	public void show2() {
		final Dialog mDialog = new Dialog(Pre2Activity.this);
		mDialog.setTitle("Set W eight");
		mDialog.setContentView(R.layout.decimal_picker_dialog);
		mBtn = (Button) mDialog.findViewById(R.id.done_button);
		final NumberPicker mNumPk = (NumberPicker) mDialog.findViewById(R.id.number_Picker);
		final NumberPicker mNumPk2 = (NumberPicker) mDialog.findViewById(R.id.number_Picker2);
		mNumPk.setMaxValue(100);
		mNumPk.setMinValue(0);
		mNumPk2.setMaxValue(9);
		mNumPk2.setMinValue(0);
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

}
