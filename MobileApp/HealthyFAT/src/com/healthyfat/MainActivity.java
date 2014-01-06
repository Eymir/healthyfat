package com.healthyfat;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {

	SharedPreferences prefs = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prefs = getSharedPreferences("com.healthyfat", MODE_PRIVATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		 * If "firstrun" is true, navigate to PreLogin Screen
		 * commit the prefs to take note the "firstrun" is now false
		 */
		
		if (prefs.getBoolean("firstrun", true)) {
			
			Intent i = new Intent (this, PreLoginActivity.class);
			startActivity(i);
			
			prefs.edit().putBoolean("firstrun", false).commit();
		}
	}

}
