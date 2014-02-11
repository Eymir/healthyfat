package com.myhealthmemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.myhealthmemo.adapter.DBAdapter;
import com.myhealthmemo.adapter.SearchAdapter;
import com.myhealthmemo.model.SearchItem;


public class DietSearchActivity extends Activity {
	 
	
	private EditText searchText;
	private Button searchbtn;
	private String[] listitem;
	private String[] listname;
	private ListView mListView;
	private DBAdapter db;
	private String path;
	private String dd;
	private String names;
	private ImageView pp;
	ArrayList imageList = new ArrayList();
	private String mealMenuList;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
	   	super.onCreate(savedInstanceState);
	   	setContentView(R.layout.dummy_diet_fragment_search);
	    searchText = (EditText) findViewById(R.id.searchEdit);
        searchbtn = (Button)findViewById(R.id.btnSearch);
        SearchAdapter sa = new SearchAdapter(this, generateData());
        mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(sa);
        mListView.setOnItemClickListener(new ListViewClick());
       
      //get bundle
  	  Bundle b = this.getIntent().getExtras();
  	   mealMenuList=  b.getString("mealmenu");

        //call database here
        db = new DBAdapter(this);
        db.open();
        
        
     }
	

	int Counter=0;
	private ArrayList<SearchItem> generateData(){
		final ArrayList<SearchItem> searchItem = new ArrayList<SearchItem> ();
		
		 searchbtn.setOnClickListener(new OnClickListener(){
	        	@Override 
	        	public void onClick(View v){
	        		Cursor cursor = db.searchFood((v!= null ? v.toString() : "@@@@"));
	        		if(cursor.moveToFirst()){	
	        			do{
	        				
	        				//path = c.getString(c.getColumnIndex("image"));
	        				//byte [] encodeByte=Base64.decode(path,Base64.DEFAULT);
	        				//Bitmap myBitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);			
	        				dd = cursor.getString(cursor.getColumnIndex("food_Name"));
	        				//searchItem.add(new SearchItem(myBitmap, dd));
	        			
	        		
	        			}while(cursor.moveToNext());
	        	            
	        		}
	        	}
	        	
		 });
		return searchItem;
		
	}
	
	
	private class ListViewClick implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			displayView(position);
		}
		
	}
	
	
	private void displayView(int position) {
		switch (position) {
		case 0:
			final Intent mIntent = new Intent(this.getApplicationContext(), DummyDietServingSizeActivity.class);
			Bundle extras = mIntent.getExtras();
			extras.putString("Food_Name", dd);
			extras.putString("mealmenu", mealMenuList);
			//extras.putString("Image" , myBitmap);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}