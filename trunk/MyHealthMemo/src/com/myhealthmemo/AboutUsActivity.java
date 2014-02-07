package com.myhealthmemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUsActivity extends Activity {
	private TextView mtexttext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		mtexttext = (TextView) findViewById(R.id.texttext);
		mtexttext.setText("At MyHealthMemo, our mission is to help people to lose or gain weight and to reach a healthier lifestyle." +
				" " + 
				"Our application includes the weight tracker, diet tracker as well as exercise tracker." +
				"We also provides guides/tips for users who do not understand how to lose weight and it¡¯s totally 100% free of charge.");
		setupActionBar();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
		return true;
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
