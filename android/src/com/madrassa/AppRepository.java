package com.madrassa;

import android.arch.lifecycle.LiveData;

import com.madrassa.model.Course;
import com.madrassa.model.Student;


public class AppRepository{
	private static AppRepository instance;


	public static AppRepository getInstance(){
		if(instance == null){
			instance = new AppRepository();
		}

		return instance;
	}

	private AppRepositry(){

	}
}