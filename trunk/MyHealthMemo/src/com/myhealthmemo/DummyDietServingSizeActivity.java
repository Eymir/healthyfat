package com.myhealthmemo;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.myhealthmemo.adapter.DBAdapter;

public class DummyDietServingSizeActivity  extends Activity implements NumberPicker.OnValueChangeListener, OnItemSelectedListener {
	
	private Button mBtn;
	private EditText ssEdit;
	private String[] listitem;
	private TextView foodname, calorie, fats, carbohydrates, protein, dietary_fibre, sodium, cholestrol;
	private DBAdapter db;
	private ImageView foodimage;
	private String foodNameIntent, mealMenuList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_diet_serving_size);
		ssEdit = (EditText) findViewById(R.id.boxnumber);
		init();
		 //call database here
        db = new DBAdapter(this);
        db.open();
		setupActionBar();
		
		//get bundle
		  Bundle b = this.getIntent().getExtras();
		  foodNameIntent=  b.getString("Food_Name");
		  mealMenuList = b.getString("mealmenu");
	
		  
       //Set Food Name 
		  foodname.setText(foodNameIntent);
		//set serving size text
		ssEdit.setText(R.id.serving_size_text);
		  
	   // retrieve from database
       Cursor c = db.getFood(foodNameIntent);
       calorie.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("calories"))));
       fats.setText(String.valueOf(c.getDouble(c.getColumnIndex("fats"))));
       carbohydrates.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("carbohydrates"))));
       protein.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("protein"))));
       dietary_fibre.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("dietary_Fibre"))));
       sodium.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("sodium"))));
       cholestrol.setText(String.valueOf(c.getDouble(c.getColumnIndexOrThrow("cholestrol"))));
       
	ssEdit.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		      public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					show2();
				} 
		      }
		});			
		setupActionBar();	
		
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
	
	//A method to initialize all layout to corresponding variable
		private void init() {
			// TODO Auto-generated method stub
			foodname = (TextView) findViewById(R.id.food_item);
			foodimage = (ImageView)findViewById(R.id.food_image);
			calorie = (TextView) findViewById(R.id.calories_percent);
			fats = (TextView) findViewById(R.id.fats_percent);
			carbohydrates = (TextView) findViewById(R.id.carbohydrates_percent);
			protein = (TextView) findViewById(R.id.protein_percent);
			dietary_fibre = (TextView) findViewById(R.id.dietary_fibre_percent);
			sodium = (TextView) findViewById(R.id.sodium_percent);
			cholestrol = (TextView) findViewById(R.id.cholestrol_percent);
		}
}
