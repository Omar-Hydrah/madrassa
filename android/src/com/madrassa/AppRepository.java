package com.madrassa;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.madrassa.model.Course;
import com.madrassa.model.Student;
import com.madrassa.data.PreferenceHandler;
import com.madrassa.data.AuthRequest;
import com.madrassa.data.MadrassaRequest;
import com.madrassa.response.AuthResponse;
import com.madrassa.response.CourseListResponse;
import com.madrassa.response.CourseResponse;
import com.madrassa.util.Constants;

import io.reactivex.Single;
import io.reactivex.Observable;

import java.util.List;
import java.util.Map;

public class AppRepository{

	private static AppRepository instance;
	private PreferenceHandler prefHandler;
	private Context context;
	private AuthRequest authRequest;
	// For authenticated requests.  
	// It should be initialized, after a successful login. 
	private MadrassaRequest madrassaRequest; 
	public static final String TAG = Constants.LOG_TAG;


	// Preferred way of constructing AppRepository.
	public static AppRepository getInstance(Context context){
		if(instance == null){
			instance = new AppRepository(context);			
		}

		return instance;
	}

	private AppRepository(Context context){
		this.context = context;
		prefHandler     = new PreferenceHandler();
		authRequest     = new AuthRequest();
		// getAuthHeader() == null -> before login
		// madrassaRequest = new MadrassaRequest(getAuthHeader());
	}

	public String getUserName(){
		return prefHandler.getUserName();
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

	public void saveLoginPreferences(Map<String, String> loginPreferences){
		// Log.i("madrassa", loginPreferences.toString());
		prefHandler.putStringMap(loginPreferences);
		// Initializing Authenticated users' request client, to insure the 
		// existence of the authentication header from shared preferences
		madrassaRequest = new MadrassaRequest(getAuthHeader());
	}

	public Single<AuthResponse> login(Map<String, String> credentials){
		return authRequest.login(credentials);
	}

	public void logout(){
		prefHandler.logout();
	}

	public Single<CourseListResponse> getCourseList(){
		return madrassaRequest.getCourseList();
	}

	public Single<CourseResponse> getCourse(int id){
		return madrassaRequest.getCourse(id);
	}
}