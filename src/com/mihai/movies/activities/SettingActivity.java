package com.mihai.movies.activities;

import com.mihai.movies.R;

import android.app.Activity;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		getFragmentManager().beginTransaction().replace(R.id.container_setting,new SettingFragment()).commit();
	}
	
	 private static Preference.OnPreferenceChangeListener bindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
	        @Override
	        public boolean onPreferenceChange(Preference preference, Object newValue) {
	            String stringValue = newValue.toString();

	            if(preference instanceof ListPreference){
	                ListPreference listPreference = (ListPreference)preference;
	                int index = listPreference.findIndexOfValue(stringValue);

	                preference.setSummary(
	                        index >=0 ? listPreference.getEntries()[index]:null
	                );
	            }else {
	                preference.setSummary(stringValue);
	            }
	            
	            return true;
	        }
	    };
	
	private static void bindPreferenceSummaryToValue(Preference preference) {
      
        preference.setOnPreferenceChangeListener(bindPreferenceSummaryToValueListener);

        bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

	public static class SettingFragment extends PreferenceFragment{
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
			bindPreferenceSummaryToValue(findPreference(getActivity().getString(R.string.pref_sort_key)));
		}
	}
}
