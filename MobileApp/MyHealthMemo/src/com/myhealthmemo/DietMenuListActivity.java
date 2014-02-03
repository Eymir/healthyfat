package com.myhealthmemo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.myhealthmemo.adapter.SettingMealAdapter;
import com.myhealthmemo.model.MealsItem;

public class DietMenuListActivity extends ListActivity{
    
   //Define Array Values to show in the ListView meal menu
	private String[] listitem;
	private TypedArray listicon;
	private TypedArray listicon2;
	 ListView lv = getListView();
	
    
	   @Override
	   public void onCreate(Bundle savedInstanceState){
	   	super.onCreate(savedInstanceState);
	   	//Define and set new adapter
	   	SettingMealAdapter sa = new SettingMealAdapter(this, generateData());
	   	lv.setAdapter(sa);
	   	setupActionBar();	
	  
	    lv.setOnItemClickListener(new OnItemClickListener() {
		    @Override
		   public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    //ListView Clicked item index
		    	displayView(position);	
		    	
		    }
		    });
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
	    private void displayView(int position){
	    	Intent mIntent =null;
	    	switch(position){
	    	case 0:
	    		mIntent = new Intent(this.getApplicationContext(), DietSearchActivity.class);
	    		mIntent.putExtra("BreakFast", listitem[position]);
	    		break;
	    	case 1:
	    		
	    		break;
	    	case 2:
	    	
    		break;
    		
	    	case 3:
	    
	    	break;
	    	
	    	case 4:
	    	
	    	break;
	    	
	    	case 5:
	    	
	    	break;
	    }

   }
}
