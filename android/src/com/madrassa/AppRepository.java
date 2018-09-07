package com.madrassa;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.madrassa.model.Course;
import com.madrassa.model.Student;
import com.madrassa.data.PreferenceHandler;


public class AppRepository{

	private static AppRepository instance;
	private PreferenceHandler prefHandler;
	private Context context;

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
		
	}

	private AppRepository(Context context){
		this.context = context;
	}

	// Returns the ["x-auth-header"] stored in shared preferences.
	public String getAuthHeader(){
		return prefHandler.getAuthHeader();
	}
}