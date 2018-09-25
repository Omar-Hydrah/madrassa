package com.madrassa.data;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Map;

import com.madrassa.MadrassaApplication;
import com.madrassa.model.User;

import android.util.Log;

public class PreferenceHandler{

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;
	private Context context;

	public PreferenceHandler(){
		context = MadrassaApplication.getContext();
		sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(context);
		editor  = sharedPreferences.edit(); 
	}


	// Writes a java.util.Map to shared preferences
	public void putStringMap(Map<String, String> values){
		// Log.i("madrassa", values.toString());
		for(String key : values.keySet()){
			// Log.i("madrassa", "prefHandler: " + key + "- " +values.get(key));
			editor.putString(key, values.get(key));
		}

		editor.commit();
	}

	public void putString(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key){
		return sharedPreferences.getString(key, null);
	}

	// Gets the stored ["x-auth-header"] from preferences (jsonwebtoken)
	public String getAuthHeader(){
		return sharedPreferences.getString(User.TOKEN_KEY, null);
	}

	public String getUserName(){
		return sharedPreferences.getString(User.USERNAME_KEY, null);
	}

}
