package com.madrassa.data;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Map;

import com.madrassa.MadrassaApplication;
import com.madrassa.model.User;

public class PreferenceHandler{

	private SharedPreferences prefs;
	private SharedPreferences.Editor editor;
	private Context context;

	public PreferenceHandler(){
		context = MadrassaApplication.getContext();
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

	public void putString(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key){
		return prefs.getString(key, null);
	}

	// Gets the stored ["x-auth-header"] from preferences (jsonwebtoken)
	public String getAuthHeader(){
		return prefs.getString(User.TOKEN_KEY, null);
	}

}
