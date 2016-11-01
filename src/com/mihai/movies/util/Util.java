package com.mihai.movies.util;

import com.mihai.movies.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Util {
	public static String getPreferenceSort(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getString(context.getString(R.string.pref_sort_key),
				context.getString(R.string.pref_sort_default));
	}
}
