package com.myhealthmemo;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;


public class UserProfileFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

	Button mBtn;
	ImageButton mBrowse;
	ImageButton mTakePhoto;
	ImageButton mRemove;
	Intent mIntent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.user_profile);
		PreferenceManager.setDefaultValues(this.getActivity(), R.xml.user_profile, false);
		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++){
			initSummary(getPreferenceScreen().getPreference(i));
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		//Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key){
		updatePrefSummary(findPreference(key));
	}
	
	private void initSummary(Preference p) {
		if (p instanceof PreferenceCategory) {
			PreferenceCategory pCat = (PreferenceCategory) p;
			for(int i = 0; i < pCat.getPreferenceCount(); i++) {
				initSummary(pCat.getPreference(i));
			}
		} else {
			updatePrefSummary(p);
		}
	}
	
	private void updatePrefSummary(Preference p) {
		if (p instanceof ListPreference) {
			ListPreference listPref = (ListPreference) p;
			p.setSummary(listPref.getValue());
		}
		if (p instanceof EditTextPreference) {
			EditTextPreference editTextPref = (EditTextPreference) p;
			p.setSummary(editTextPref.getText());
		}

	}
	
	
}
