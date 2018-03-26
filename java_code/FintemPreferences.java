package com.amilutinovic.inat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by milutinac on 30.10.16..
 */
public class FintemPreferences {

	public static final String INAT_PREFERENCES = "inat.preferences";
	public static final String LAST_MAP_USED = "last_map";
	public Context context;
	private SharedPreferences preferences;

	public FintemPreferences(Context context) throws NullPointerException {
		this.context = context;
		this.preferences = context.getSharedPreferences(INAT_PREFERENCES, Context.MODE_PRIVATE);
	}/*

	public void setLastMapUsed(String mapName){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(LAST_MAP_USED, mapName);
		editor.apply();
	}*/

	public void setLastMapUsed(String mapName){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(LAST_MAP_USED, mapName);
		editor.apply();
	}

	public String getLastMapUsed() throws NullPointerException {
		return this.preferences.getString(LAST_MAP_USED, null);
	}
}
