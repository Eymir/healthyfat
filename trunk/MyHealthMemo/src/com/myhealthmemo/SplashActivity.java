package com.myhealthmemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.ProgressBar;

import com.myhealthmemo.adapter.DBAdapter;

public class SplashActivity extends Activity {

	protected ActionBar mActionBar;
	protected Intent mIntent;
	private static int mTimeOut;
	protected boolean mActive;
	protected ProgressBar mProgressBar;
	protected SharedPreferences mPrefs;
	private DBAdapter datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		hideActionBar();
		runTimer();
		PreferenceManager.setDefaultValues(SplashActivity.this, R.xml.user_profile, false);
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	/*
	 * To hide the action bar from the user
	 */
	protected void hideActionBar(){
		mActionBar = getActionBar();
		mActionBar.hide();
	}
	
	/*
	 * To create a runTimer,
	 * have the mProgressBar update itself until mTimeOut runs out
	 * finally, when the whole process end, set up the mIntent
	 * and bring the user to the MainActivity
	 */
	protected void runTimer(){
		mTimeOut = 2000;
		mProgressBar = (ProgressBar) findViewById(R.id.init_Bar);
		final Thread timerThread = new Thread() {
			@Override
	        public void run() {
				mActive = true;
	            try {
	            	int waited = 0;
	            	while(mActive && (waited < mTimeOut)) {
	            		sleep(200);
	            		if(mActive) {
	            			waited += 200;
	            			updateProgress(waited);
	            		}
	            	}
	            } catch(InterruptedException e) {
	            	// do nothing
	            } finally {
	            	/*
	        		 * If "firstrun" is true, navigate to PreLogin Screen
	        		 * commit the prefs to take note the "firstrun" is now false
	        		 */
	        		
	        		if (mPrefs.getBoolean("firstrun", true)) {
	        			datasource = new DBAdapter(SplashActivity.this);
	        			boolean dbExist = checkDatabase() ;
	        			if(!dbExist)
	        				try{
	        					String destPath =  "/data/data/" + getPackageName() +
	        							 "/databases/";
	        					File f = new File(destPath);
	        					if (!f.exists()) {	
	        						f.mkdirs();	
	        						f.createNewFile();
	        						CopyDB(getBaseContext().getAssets().open("myhealthmemo.db"),
	        								new FileOutputStream(destPath + "/myhealthmemo.db"));
	        					}
	        					String destPath2 = "data/data/" + getPackageName();
	        					File f2 = new File(destPath2);
	        					if (!f2.exists()) {
	        						f2.mkdirs();
	        						f2.createNewFile();
	        						CopyDB(getBaseContext().getAssets().open("MyHealthMemo_Images"),
	        								new FileOutputStream(destPath2 + "/MyHealthMemo_Images"));
	        					}
	        							 
	        				}catch (FileNotFoundException e){
	        					e.printStackTrace();
	        				}
	        				catch (IOException e) {
	        					e.printStackTrace();
	        				}finally {
	        		            datasource.closeDB();
	        		        }
	        			mIntent = new Intent (SplashActivity.this, PreActivity.class);
	        			startActivity(mIntent);
	        		} else {
	        			mIntent = new Intent (SplashActivity.this, NavDrawerActivity.class);
	        			startActivity(mIntent);
	        		}
	            }
	        }
	    };
	    timerThread.start();
	}
	
	protected void updateProgress(final int timePassed) {
		if(null != mProgressBar) {
			// Ignore rounding error here
			final int progress = mProgressBar.getMax() * timePassed / mTimeOut;
			mProgressBar.setProgress(progress);
		}
	}
	
	public void CopyDB(InputStream inputStream, 
			 OutputStream outputStream) throws IOException {
			 //---copy 1K bytes at a time---
			 byte[] buffer = new byte[1024];
			 int length;
			 while ((length = inputStream.read(buffer)) > 0) {
			 outputStream.write(buffer, 0, length);
			 }
			 outputStream.flush();
			 inputStream.close();
			 outputStream.close();
	
	}	
	
	private boolean checkDatabase() {
		try {
	        final String destPath =  "/data/data/" + getPackageName() +
					 "/databases/";
	        final File file = new File(destPath);
	        if (file.exists())
	            return true;
	        else
	            return false;
	    } catch (SQLiteException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
