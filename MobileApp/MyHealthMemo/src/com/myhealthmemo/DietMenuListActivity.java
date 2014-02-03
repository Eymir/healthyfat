package com.myhealthmemo;

import java.util.ArrayList;

import com.myhealthmemo.adapter.MealsArrayAdapter;
import com.myhealthmemo.model.MealsItem;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.content.Intent;
import android.content.res.TypedArray;
import android.widget.Toast;

public class DietMenuListActivity extends ListActivity{
    
   //Define Array Values to show in the ListView meal menu
	private TypedArray listicon;
	 ListView lv = getListView();
	
   static final String[] mealslist= new String[]{"BreakFast", "Lunch", "Snacks", "Dinner", "Supper"};
    
	   @Override
	   public void onCreate(Bundle savedInstanceState){
	   	super.onCreate(savedInstanceState);
	   	//Define and set new adapter
	   	setListAdapter(new MealsArrayAdapter(this, mealslist));
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
	   		ArrayList<MealsItem> mi = new ArrayList<MealsItem>();
	   		mi.add(new MealsItem(listicon.getResourceId(0, -1), mealslist[0]));
	   		mi.add(new MealsItem(listicon.getResourceId(1, -1), mealslist[1]));
	   		mi.add(new MealsItem(listicon.getResourceId(2, -1), mealslist[2]));
	   		mi.add(new MealsItem(listicon.getResourceId(3, -1), mealslist[3]));
	   		mi.add(new MealsItem(listicon.getResourceId(4, -1), mealslist[4]));
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
	    		mIntent.putExtra("BreakFast", mealslist[0]);
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
