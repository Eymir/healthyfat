package com.myhealthmemo;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

	protected ActionBar mActionBar;
	protected Intent mIntent;
	private static int mTimeOut;
	protected boolean mActive;
	protected ProgressBar mProgressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		hideActionBar();
		runTimer();
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
	            	mIntent = new Intent(SplashActivity.this, NavDrawerActivity.class);
	            	startActivity(mIntent);
	            	finish();
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

}
