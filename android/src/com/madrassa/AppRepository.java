package com.madrassa;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.madrassa.model.Course;
import com.madrassa.model.Student;
import com.madrassa.data.PreferenceHandler;
import com.madrassa.data.AuthRequest;
import com.madrassa.response.AuthResponse;

import io.reactivex.Single;
import io.reactivex.Observable;


import java.util.List;
import java.util.Map;

public class AppRepository{

	private static AppRepository instance;
	private PreferenceHandler prefHandler;
	private Context context;
	private AuthRequest authRequest;

	public static AppRepository getInstance(){
		if(instance == null){
			instance = new AppRepository();
		}

		return instance;
	}

	public static AppRepository getInstance(Context context){
		if(instance == null){
			instance = new AppRepository(context);
		}

		return instance;
	}

	private AppRepository(){
		if(authRequest == null){
			authRequest = new AuthRequest();
		}
	}

	private AppRepository(Context context){
		this.context = context;
		if(authRequest == null){
			authRequest  = new AuthRequest();
		}
	}

	// Returns the ["x-auth-header"] stored in shared preferences.
	public String getAuthHeader(){
		return prefHandler.getAuthHeader();
	}

	// Save a string to shared preferences.
	public void putPrefString(String key, String value){
		prefHandler.putString(key, value);
	}

	// Retrieve a string from shared preferences
	public String getPrefString(String key){
		return prefHandler.getString(key);
	}

	public Single<AuthResponse> login(Map<String, String> credentials){
		return authRequest.login(credentials);
	}

	/*public List<Course> getCourseList(){
		
	}*/
}