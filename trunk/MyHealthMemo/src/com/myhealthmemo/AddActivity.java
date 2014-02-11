package com.myhealthmemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class AddActivity extends Activity {

	RelativeLayout thirdLayer;
	Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		thirdLayer = (RelativeLayout) findViewById(R.id.thirdlayer);
		thirdLayer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				mIntent = new Intent(AddActivity.this, Activity_List_of_Activities.class);
				startActivity(mIntent);
			}
		});
		setupActionBar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
    
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
    	case android.R.id.home:
    		finish();
    		break;
    	case R.id.accept:
    		break;
    	}
		return super.onOptionsItemSelected(item);
    }

}
