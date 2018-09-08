package com.madrassa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.madrassa.AppRepository;
import com.madrassa.model.Course;

import java.util.List;

public class CourseListViewModel extends ViewModel{
	private AppRepository repo;
	public MutableLiveData<List<Course>> courses = new MutableLiveData<>();


	public void getCourseList(){

	}
}