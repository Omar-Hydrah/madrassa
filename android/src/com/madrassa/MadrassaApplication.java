package com.madrassa;

import android.app.Application;
import android.content.Context;

public class MadrassaApplication extends Application{

	private static Context context;

	@Override
	public void onCreate(){
		super.onCreate();
		MadrassaApplication.context = getApplicationContext();

	}

	public static Context getContext(){
		return MadrassaApplication.context;
	}
}