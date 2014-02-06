package com.myhealthmemo;

import java.util.ArrayList;

import com.myhealthmemo.adapter.SettingsAdapter;
import com.myhealthmemo.model.SettingsItem;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class SettingsFragment extends Fragment {
	
	private String[] listitem;
	private TypedArray listicon;
	private ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
		SettingsAdapter sa = new SettingsAdapter(getActivity().getBaseContext(), generateData());
		lv = (ListView) rootView.findViewById(R.id.settings_Layout);
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new ListViewClick());
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.settings, menu);
	}
	
	private ArrayList<SettingsItem> generateData(){
        
        listitem = getResources().getStringArray(R.array.stg_list_items);
        listicon = getResources().obtainTypedArray(R.array.stg_list_icons);
        ArrayList<SettingsItem> si = new ArrayList<SettingsItem>();
        si.add(new SettingsItem(listicon.getResourceId(0,-1), listitem[0]));
        si.add(new SettingsItem(listicon.getResourceId(1,-1), listitem[1]));
        si.add(new SettingsItem(listicon.getResourceId(2,-1), listitem[2]));
        listicon.recycle();
        return si;
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
			mIntent = new Intent(getActivity().getApplicationContext(), UserProfileActivity.class);
			startActivity(mIntent);
			break;
		case 1:
			
			break;
		case 2:
			mIntent = new Intent(getActivity().getApplicationContext(), AboutUsActivity.class);
			startActivity(mIntent);
			break;
		default:
			break;
		}
	}
	
}
