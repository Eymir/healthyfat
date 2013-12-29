package com.healthyfat;

import com.healthyfat.R;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView; //a
import android.content.Intent; //a

import com.facebook.*; //a
import com.facebook.model.*; //a

public class MainActivity extends FragmentActivity {
	
	private MainFragment mainFragment;
	private static final String TAG = "MainFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager()
            .beginTransaction()
            .add(android.R.id.content, mainFragment)
            .commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager()
            .findFragmentById(android.R.id.content);
        }
        
        
        //start facebook login
        //Session.openActiveSession(this, true, new Session.StatusCallback() {
        	
        	//callback when session changes state
        	//@Override
            //public void call(Session session, SessionState state, Exception exception) {
        		//if (session.isOpened()) {
        			// make request to the /me API
        			//Request.newMeRequest(session, new Request.GraphUserCallback() {
        	        	  
        	        	// callback after Graph API response with user object
        	              //@Override
        	              //public void onCompleted(GraphUser user, Response response) {
        	                //if (user != null) {
        	                  //TextView welcome = (TextView) findViewById(R.id.welcome);
        	                  //welcome.setText("Hello " + user.getName() + "!");
        	                //}
        	              //}
        	        //}).executeAsync();
        		//}
            //}
        //});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
   // @Override
    //public void onActivityResult(int requestCode, int resultCode, Intent data) { //a
     // super.onActivityResult(requestCode, resultCode, data);
      //Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
   // }
    
}
