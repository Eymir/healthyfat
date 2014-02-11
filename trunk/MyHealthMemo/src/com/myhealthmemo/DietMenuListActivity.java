package com.myhealthmemo;

import java.util.ArrayList;


import com.myhealthmemo.adapter.SettingMealAdapter;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myhealthmemo.adapter.SettingMealAdapter;
import com.myhealthmemo.model.MealsItem;

public class DietMenuListActivity extends Activity{
    
   //Define Array Values to show in the ListView meal menu
	private String[] listitem;
	private TypedArray listicon;
	private TypedArray listicon2;
	private ListView lv;
	
    
	   @Override
	   public void onCreate(Bundle savedInstanceState){
	   	super.onCreate(savedInstanceState);
	   	setContentView(R.layout.dummy_diet_fragment_list_menu);
	   	//Define and set new adapter

	   	SettingMealAdapter sa = new SettingMealAdapter(this, generateData());  
	   	lv = (ListView) findViewById(R.id.meal_list);
	  	lv.setAdapter(sa);

	    lv.setOnItemClickListener(new ListViewClick());
	   	setupActionBar();	
	   }
	   
	   	private ArrayList<MealsItem> generateData(){
	   		listitem = getResources().getStringArray(R.array.mealitem);
	        listicon = getResources().obtainTypedArray(R.array.mealicon);
	        listicon2 = getResources().obtainTypedArray(R.array.mealicon2);
	   		ArrayList<MealsItem> mi = new ArrayList<MealsItem>();
	   		mi.add(new MealsItem(listicon.getResourceId(0,-1),listicon2.getResourceId(0, -1), listitem[0]));
	   		mi.add(new MealsItem(listicon.getResourceId(1,-1),listicon2.getResourceId(1, -1), listitem[1]));
	   		mi.add(new MealsItem(listicon.getResourceId(2,-1),listicon2.getResourceId(2, -1), listitem[2]));
	   		mi.add(new MealsItem(listicon.getResourceId(3,-1),listicon2.getResourceId(3, -1), listitem[3]));
	   		mi.add(new MealsItem(listicon.getResourceId(4,-1),listicon2.getResourceId(4, -1), listitem[4]));
	   		listicon.recycle();
	   		listicon2.recycle();
	   		return mi;
	   	}

	   
	    public boolean onOptionsItemSelected(MenuItem item){
	    	switch(item.getItemId()){
	    	case android.R.id.home:
	    		
	    		finish();
	    		return true;
	    	}
			return super.onOptionsItemSelected(item);
	    }
	    //set up the @link.android.actionbar
	    private void setupActionBar() {
	    	getActionBar().setDisplayHomeAsUpEnabled(true);
	    }

	 public class ListViewClick implements ListView.OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
				displayView(position);
			}
			
		}
	    private void displayView(int position){
	    	Intent mIntent =null;
	    	switch(position){
	    	case 0:
	    		mIntent = new Intent(DietMenuListActivity.this, DietSearchActivity.class);
	    		mIntent.putExtra("mealmenu", listitem[position]);
	    		startActivity(mIntent);
	    		break;
	    	case 1:
	    		mIntent = new Intent(DietMenuListActivity.this, DietSearchActivity.class);
	    		mIntent.putExtra("mealmenu", listitem[position]);
	    		startActivity(mIntent);
	    		break;
	    	case 2:
	    		mIntent = new Intent(DietMenuListActivity.this, DietSearchActivity.class);
	    		mIntent.putExtra("mealmenu", listitem[position]);
	    		startActivity(mIntent);
    		break;
    		
	    	case 3:
	    		mIntent = new Intent(DietMenuListActivity.this, DietSearchActivity.class);
	    		mIntent.putExtra("mealmenu", listitem[position]);
	    		startActivity(mIntent);
	    	break;
	    	
	    	case 4:
	    		mIntent = new Intent(DietMenuListActivity.this, DietSearchActivity.class);
	    		mIntent.putExtra("mealmenu", listitem[position]);
	    		startActivity(mIntent);
	    	break;
	    	
	    	default:
		    break;
	    }
	    	

   }
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.settings, menu);
			return true;
   }
}
