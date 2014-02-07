package com.myhealthmemo;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreActivity extends FragmentActivity implements OnDateSetListener, 
	OnClickListener, OnFocusChangeListener {

	private EditText mUn,mDOB;
	private RadioGroup mGender;
	private String mString,filename,path;
	private ImageButton mAdd,mBrowse,mTakePhoto,mRemove;
	private Intent mIntent;
	private Button mBtn;
	private static final int mReq_ID = 0;
	private static final int mCamReq_ID = 1;
	private ImageView mPic;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private RadioButton mRD;
	private Uri mCapturedImageURI;
	private Bitmap photo;
	private boolean isNull;
	private Drawable profile_pic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre);
		init();
		setImageToDefault();
		photo = ((BitmapDrawable)profile_pic).getBitmap();
		path = convertPF(photo);
		mPic.setImageBitmap(photo);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(PreActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		hasPreference();
		setListener();
	}	
	
	//A method to initialize each of the variable with the corresponding layout in xml
	private void init(){
		mUn = (EditText) findViewById(R.id.username_Edit);
		mGender = (RadioGroup) findViewById(R.id.radio_Gender);
		mDOB = (EditText) findViewById(R.id.dob_Edit);
		mPic = (ImageView) findViewById(R.id.def_pic);
		mAdd = (ImageButton) findViewById(R.id.add);
	}
	
	/*
	 * A method to convert the default profile picture in the drawable folder into encoded Base64 String
	 * It first convert the drawable into bitmap
	 * then convert it to byte array
	 * and then finally, encode it with Base 64
	 */
	private String convertPF(Bitmap a){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		a.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] bitmapdata = stream.toByteArray();
		String apath = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
		return apath;
	}
	
	//A method to assign listener to corresponding layout variable
	private void setListener(){
		mDOB.setOnFocusChangeListener(this);
		mDOB.setOnClickListener(this);
		mAdd.setOnClickListener(this);
	}
	
	@SuppressLint("SimpleDateFormat")
	/*
	 * It converts the month integer to "MMM" format 
	 * "MMM" = Jan, Feb etc.
	 */
	public String getMonthForInt(int m) {
		String monthName="";
		 
		 if(m>=0 && m<12)
		 try
		 {
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.MONTH, m);
		 
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
		 simpleDateFormat.setCalendar(calendar);
		 monthName = simpleDateFormat.format(calendar.getTime());
		 }
		 catch (Exception e)
		 {
		 if(e!=null)
		 e.printStackTrace();
		 }
		 return monthName;
	}
	
	/*
	 * A method that is invoke after DatePickerFragment Dialog was dismissed.
	 * It first invoke getMonthForInt method to convert the month to "MMM" format
	 * then set the mDOB layout variable to set its text to display the date
	 *
	 */
	@Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
		mString = getMonthForInt(month);
        mDOB.setText(day + " " + mString + " " +year);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_timer, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.navigation_forward:
			/*
			 * First, the two outer loop will check if user has entered the username and dob
			 * if not, a alert will be displayed telling the user to enter all variable
			 * if username and dob is entered, proceed to run the internal method
			 * which is to assign the String to the preference key
			 * and commit the changes
			 */
			isNull = isEmpty(mUn.getText().toString());
			if (isNull){
				showAlert();
			}
			else if (!isNull){
				isNull = isEmpty(mDOB.getText().toString());
				if (isNull){
					showAlert();
				}else{
					mPrefsEdit.putString("profilePic",path);
					mPrefsEdit.putString("userName", mUn.getText().toString());
					mPrefsEdit.putString("dob", mDOB.getText().toString());
					switch (mGender.getCheckedRadioButtonId()) {
					case R.id.radio_male:
						mRD = (RadioButton) findViewById(R.id.radio_male);
						break;
					case R.id.radio_female:
						mRD = (RadioButton) findViewById(R.id.radio_female);
						break;
					default:
						break;
					} 
					
					mPrefsEdit.putString("gender", mRD.getText().toString());
					mPrefsEdit.commit();
					mIntent = new Intent(this, Pre2Activity.class);
					startActivity(mIntent);
				}
			}
			break;
		default:
			break;
		}
		return true;
	}
    
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    	switch (v.getId()){
    	case R.id.dob_Edit:
    		//if the user has set the focus on dob, display the DatePickerFragment
    		if(hasFocus){
    			DialogFragment newFragment = new DatePickerFragment();
    			newFragment.show(getSupportFragmentManager(), "datePicker");
    		} 
    		break;
    	default:
    		break;
    	}
    }
    
    /*
     * A method to determine if there is any preference value for username and dob
     * This is to handle the case where the user click on the back button at Pre2Activity
     * it works like a savedinstance.
     */
    private void hasPreference(){
    	isNull = isEmpty(mPrefs.getString("userName", ""));
    	if(!isNull){
    		mUn.setText(mPrefs.getString("userName", ""));
    	}
    	isNull = isEmpty(mPrefs.getString("dob", ""));
    	if(!isNull){
    		mDOB.setText(mPrefs.getString("dob", ""));
    	}
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()){
    	case R.id.add:
    		/*Display a dialog which includes 4 buttons:
    		 * browse button
    		 * take photo button
    		 * remove button
    		 * done button
    		 */
    		final Dialog mDialog = new Dialog(PreActivity.this);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setContentView(R.layout.add_dialog);
			mBtn = (Button) mDialog.findViewById(R.id.done_button);
			mBrowse =  (ImageButton) mDialog.findViewById(R.id.browse);
			mTakePhoto = (ImageButton) mDialog.findViewById(R.id.take_photo);
			mRemove = (ImageButton) mDialog.findViewById(R.id.remove);
			
			Object tag = mPic.getTag();
			int re = R.drawable.default_profile_pic;
			if( tag != null && ((Integer)tag).intValue() == re) {
				mRemove.setVisibility(View.GONE);
			}

			//If user clicked on done button, dismiss the Dialog
			mBtn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
				}
			});
			/*If user clicked on browse, define the action and 
			 * startActivityForResult with the key mReg_ID
			 */
			mBrowse.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					mIntent = new Intent();
					mIntent.setAction(Intent.ACTION_GET_CONTENT);
					mIntent.addCategory(Intent.CATEGORY_OPENABLE);
					mIntent.setType("image/*");
					startActivityForResult(mIntent, mReq_ID);
					mDialog.dismiss();
				}			
			});
			/*If user clicked on take photo, define the action and 
			 * startActivityForResult with the key mCamReq_ID
			 */
			mTakePhoto.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					ContentValues values = new ContentValues();			
					mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(mIntent, mCamReq_ID);
					mDialog.dismiss();
				}
			});
			
			mRemove.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					setImageToDefault();
					mPic.setTag(R.drawable.default_profile_pic);
					mDialog.dismiss();
				}
			});
			mDialog.show();
    		break;
    	case R.id.dob_Edit:
    		//When clicked on dob, display DatePickerFragment
    		DialogFragment newFragment = new DatePickerFragment();
			newFragment.show(getSupportFragmentManager(), "datePicker");
    		break;
    	}
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		/*it checked for the key that is received
		 * if received mReq_ID from the browse button action
		 */
		case mReq_ID:
			if (resultCode == RESULT_OK){
				Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                cursor.moveToFirst();
                String yu = cursor.getString(columnIndex);
                cursor.close();
                
                //Bitmap photo = BitmapFactory.decodeFile(filePath);
                Options options = null;
                photo = BitmapFactory.decodeFile(yu,options);
                mPic.setImageBitmap(photo);
                mPic.setTag(1);
                path = convertPF(photo);
                
			}
			break;
		//if received mCamReq_ID from the take photo button action
		case mCamReq_ID:
			if (resultCode == RESULT_OK){
				Bundle extras = data.getExtras();
				photo = (Bitmap) extras.get("data");
                mPic.setImageBitmap(photo);
                mPic.setTag(1);
                path = convertPF(photo);
			}
			break;
		default:
			break;
		}
	}
	
	//A method to check if a String is empty
	public boolean isEmpty(String a){
		if (a.matches("")){
			return true;
		} else {
			return false;
		}
	}
	
	//An method to display alert dialog
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
	
	public void setImageToDefault(){
		profile_pic = getResources().getDrawable(R.drawable.default_profile_pic);
		mPic.setImageDrawable(profile_pic);
		mPic.setTag(R.drawable.default_profile_pic);
	}
}
