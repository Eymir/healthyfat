package com.healthyfat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PreLoginActivity extends Activity implements OnClickListener {

	Button reg_btn;
	Button lgn_btn;
	Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_login);
		btnClickHandler();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_login, menu);
		return true;
	}

	protected void btnClickHandler() {
		reg_btn = (Button) findViewById(R.id.reg_btn);
		lgn_btn = (Button) findViewById(R.id.lgn_btn);
		reg_btn.setOnClickListener(this);
		lgn_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		/*
		 * IF the user click on register button, call intent to move to RegistrationActivity
		 * start RegistrationActivity
		 * Else if the user click on login now, call intent to move to LoginActivity
		 * start LoginActivity
		 */
			case R.id.reg_btn:
				i = new Intent(this, RegistrationActivity.class);
				startActivity(i);
			break;
			case R.id.lgn_btn:
				i = new Intent(this, LoginActivity.class);
				startActivity(i);
			break;
		
		}
	}

}