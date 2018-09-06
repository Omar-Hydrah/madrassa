package com.madrassa.data;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

public class PreferenceHandler{

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private Context context;

	public PreferenceHandler(){
		context = Context.getApplicationContext();
		prefs   = PreferenceManager.getDefaultSharedPreferences(context);
		editor  = prefs.edit(); 
	}

	// Writes a java.util.Map to shared preferences
	public void writeValues(Map<String, String> values){
		for(String key : values.keySet()){
			editor.putString(key, values.get(key));
		}

		editor.commit();
	}

	public void writeValue(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}

	public String getValue(String key){
		return prefs.get(key);
	}
}
