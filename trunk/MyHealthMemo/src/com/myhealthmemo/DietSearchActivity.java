package com.myhealthmemo;

import java.util.ArrayList;

import com.myhealthmemo.adapter.DBAdapter;
import com.myhealthmemo.adapter.SettingsAdapter;
import com.myhealthmemo.model.SettingsItem;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

public class DietSearchActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {
	 
	
	private SearchView searchView;
	private String[] listitem;
	private TypedArray listicon;
	private ListView mListView;
	private DBAdapter db;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
	   	super.onCreate(savedInstanceState);
	   	setContentView(R.layout.dummy_diet_fragment_search);
	    searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        SettingsAdapter sa = new SettingsAdapter(this, generateData());
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(sa);
        mListView.setOnItemClickListener(new ListViewClick());

        //call database here
        db = new DBAdapter(this);
        db.open();
        
        
     }
	
	private ArrayList<SettingsItem> generateData(){
		return null;
		// call from database
	}
	
	private class ListViewClick implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			displayView(position);
		}
}
	
	private void displayView(int position) {
		Intent mIntent = null;
		switch (position) {
		case 0:
			mIntent = new Intent(this.getApplicationContext(), DummyDietServingSizeActivity.class);
			startActivity(mIntent);
			break;

		}
	}
	 @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        if (db  != null) {
	            db.closeDB();
	        }
	    }	 

	@Override
	public boolean onClose() {
		showResults("");
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		showResults(newText + "*");
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		showResults(query + "*");
		return false;
	}
	
	//Searching
	private void showResults(String query) {
		Cursor cursor = db.searchFood((query != null ? query.toString() : "@@@@"));
		if (cursor == null) {
            //
        } 
		else{
			
		}
		
	
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}