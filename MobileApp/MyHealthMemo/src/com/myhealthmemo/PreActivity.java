package com.myhealthmemo;

import java.text.DateFormatSymbols;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class PreActivity extends FragmentActivity implements OnDateSetListener {

	private EditText mUn;
	private EditText mDOB;
	private RadioGroup mGender;
	private String mString;
	private ImageButton mAdd;
	private Intent mIntent;
	private Button mBtn;
	private static final int mReq_ID = 0;
	private static final int mCamReq_ID = 1;
	private ImageButton mBrowse;
	private ImageButton mTakePhoto;
	private ImageView mPic;
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor mPrefsEdit;
	private RadioButton mRD;
	private String filename;
	private Uri mCapturedImageURI;
	private Bitmap photo;
	private boolean a;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre);
		mUn = (EditText) findViewById(R.id.username_Edit);
		mGender = (RadioGroup) findViewById(R.id.radio_Gender);
		mDOB = (EditText) findViewById(R.id.dob_Edit);
		mPic = (ImageView) findViewById(R.id.def_pic);
		//Use the SharedPreferences from our own created xml preferences
		PreferenceManager.setDefaultValues(PreActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		mPrefsEdit = mPrefs.edit();
		mDOB.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
		      public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					DialogFragment newFragment = new DatePickerFragment();
				    newFragment.show(getSupportFragmentManager(), "datePicker");
				} 
		      }
		});
		
		mAdd = (ImageButton) findViewById(R.id.add);
		mAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v){
				final Dialog mDialog = new Dialog(PreActivity.this);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.add_dialog);
				mBtn = (Button) mDialog.findViewById(R.id.done_button);
				mBrowse =  (ImageButton) mDialog.findViewById(R.id.browse);
				mTakePhoto = (ImageButton) mDialog.findViewById(R.id.take_photo);
				mBtn.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
					}
				});
				mBrowse.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						mIntent = new Intent();
						mIntent.setAction(Intent.ACTION_GET_CONTENT);
						mIntent.addCategory(Intent.CATEGORY_OPENABLE);
						mIntent.setType("image/*");
						startActivityForResult(mIntent, mReq_ID);
					}			
				});
				mTakePhoto.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						ContentValues values = new ContentValues();
						filename = "photo.jpg";
						values.put(MediaStore.Images.Media.TITLE, filename);
						mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
						
						mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
						mIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
						startActivityForResult(mIntent, mCamReq_ID);
					}
				});
				mDialog.show();
			}
		});
	}	
	
	@SuppressLint("SimpleDateFormat")
	public String getMonthForInt(int m) {
	    String month = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (m >= 0 && m <= 11 ) {
	        month = months[m];
	    }
	    return month;
	}
	
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
			a = isEmpty(mUn.getText().toString());
			if (a==true){
				showAlert();
			}
			else if (a!=true){
				a = isEmpty(mDOB.getText().toString());
				if (a==true){
					showAlert();
				}else{
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
					mPrefsEdit.putBoolean("gender", mRD.isChecked());
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
	
	// method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case mReq_ID:
			if (resultCode == RESULT_OK){
				Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                //Bitmap photo = BitmapFactory.decodeFile(filePath);
                Options options = null;
                photo = BitmapFactory.decodeFile(filePath,options);
                mPic.setImageBitmap(photo);
                mPrefsEdit.putString("profilePic", filePath);
			}
			break;
		case mCamReq_ID:
			if (resultCode == RESULT_OK){
				String[] projection = {MediaStore.Images.Media.DATA}; 
				Cursor cursor = this.getContentResolver().query(mCapturedImageURI, projection, null, null, null); 
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
                cursor.moveToFirst(); 
                String capturedImageFilePath = cursor.getString(column_index_data);
                Log.d("photos*******"," in camera take int  "+capturedImageFilePath);
                
                Options options = null;
				photo = BitmapFactory.decodeFile(capturedImageFilePath, options);
                
                if(data != null) {
                		mPic.setImageBitmap(photo);
                        mPrefsEdit.putString("profilePic",capturedImageFilePath);
                }
			}
			break;
		default:
			break;
		}
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
	
}
