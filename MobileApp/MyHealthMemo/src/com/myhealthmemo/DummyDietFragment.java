package com.myhealthmemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import com.myhealthmemo.R;
import com.myhealthmemo.adapter.ExpandableListAdapter;
import com.myhealthmemo.adapter.DateUtils;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class DummyDietFragment extends Fragment implements OnClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";
	private TextView mTxtView;
	private Button Btn1, Btn2;
	Date date, date2;
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> Meals;
    HashMap<String, List<String>>MealsCollections;
    Intent mIntent;
    
  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.dummy_diet_fragment, container, false);
		
		setHasOptionsMenu(true);
		 // get the expandablelistview
        expListView = (ExpandableListView) rootView.findViewById(R.id.expand);

        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), Meals, MealsCollections);
        
        // setting list adapter
        expListView.setAdapter(listAdapter);
        
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
 
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // Listview Group expanded listener
         expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
 
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        Meals.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });
         
         // Listview Group collapsed listener
         expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
  
             @Override
             public void onGroupCollapse(int groupPosition) {
                 Toast.makeText(getActivity(),
                         Meals.get(groupPosition) + " Collapsed",
                         Toast.LENGTH_SHORT).show();
  
             }
         }); 
         // Listview on child click listener
         expListView.setOnChildClickListener(new OnChildClickListener() {
  
             @Override
             public boolean onChildClick(ExpandableListView parent, View v,
                     int groupPosition, int childPosition, long id) {
                 // TODO Auto-generated method stub
                 Toast.makeText(
                         getActivity(),
                         Meals.get(groupPosition)
                                 + " : "
                                 + MealsCollections.get(
                                         Meals.get(groupPosition)).get(
                                         childPosition), Toast.LENGTH_SHORT)
                         .show();
                 return false;
             }
         });
		mTxtView = (TextView) rootView.findViewById(R.id.dateArgument2);
		mTxtView.setText(getArguments().getString(ARG_SECTION_NUMBER));
		Btn1 = (Button) rootView.findViewById(R.id.left_arrow2);
		Btn1.setOnClickListener(this);
		Btn2 = (Button) rootView.findViewById(R.id.right_arrow2);
		Btn2.setOnClickListener(this);
		mTxtView.setOnClickListener(this);
		SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");
		String a = getArguments().getString(ARG_SECTION_NUMBER);
		try {
			date = formatter.parse(a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DateUtils.isToday(date)){
			Btn2.setVisibility(View.INVISIBLE);
		}
		DateTime old = new DateTime(1996, 1, 1, 0, 0, 0);
		Date date2 = old.toDate();
		if (DateUtils.isSameDay(date, date2)){
			Btn1.setVisibility(View.INVISIBLE);
		}
		return rootView;
	}
	
		 /*
	     * Preparing the list data
	     */
	    private void prepareListData() {
	        Meals = new ArrayList<String>();
	        MealsCollections = new HashMap<String, List<String>>();
	 
	        // Adding child data
	        Meals.add("BreakFast");
	        Meals.add("Lunch");
	        Meals.add("Snacks");
	        Meals.add("Dinner");
	        Meals.add("Supper");
	 
	        // Adding child data
	        List<String> BreakFast= new ArrayList<String>();
	        BreakFast.add("Bread");
	        BreakFast.add("Milk");
	       
	 
	        List<String> Lunch = new ArrayList<String>();
	        Lunch.add("chicken rice");
	    
	 
	        List<String> Snacks = new ArrayList<String>();
	        Snacks.add("Biscuit");
	      
	        
	        List<String> Dinner = new ArrayList<String>();
	        Dinner.add("Mixed Rice");
	        
	        List<String> Supper = new ArrayList<String>();
	        Supper.add("<Empty>");
	 
	        MealsCollections.put(Meals.get(0), BreakFast); // Header, Child data
	        MealsCollections.put(Meals.get(1), Lunch);
	        MealsCollections.put(Meals.get(2), Snacks);
	        MealsCollections.put(Meals.get(3), Dinner);
	        MealsCollections.put(Meals.get(4), Supper);
	    }
	     	
	 
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.dummy_diet, menu);
	}
	
	public void onClick(View v){
		switch(v.getId()){
		case R.id.left_arrow2:
			MainFragment.minusOne();
			break;
		case R.id.right_arrow2:
			MainFragment.plusOne();
			break;
		case R.id.dateArgument2:

			break;
		default:
			break;
		}
	}
	//transition to menu list on action bar
	boolean canEditItem = false;
	   
	    public  boolean onOptionsItemSelected(MenuItem item){
	    	 //calling the navigation_add function
	    
	    	switch(item.getItemId()){
	    	case R.id.add:
	    		mIntent = new Intent (getActivity().getApplicationContext(), DietMenuListActivity.class);
	    		getActivity().startActivity(mIntent);
	    	break;
	    	case R.id.edit:
	    		{
	    			if(item.getItemId() == R.id.edit){
	    				  //( 1 ) add a new item 
	    				getActivity().invalidateOptionsMenu();
	    			}
	    			
	    			return super.onOptionsItemSelected(item);
	    		}
	    	default:
	    		break;
	    	}
			return true;
	    }
	  @Override  
	 public void onPrepareOptionsMenu(Menu menu) {
	    	
		  MenuInflater inflater = getActivity().getMenuInflater();
	    if(canEditItem){
	    	 inflater.inflate(R.menu.dummy_diet_2, menu);
	    	 MenuItem mi = menu.getItem(0);
             mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
             canEditItem = false;
	    	}
	    	else{
	    		inflater.inflate(R.menu.dummy_diet, menu) ;
	    		 canEditItem = true;
	    		
	    	}
	    	super.onPrepareOptionsMenu(menu); 
	    }

}
