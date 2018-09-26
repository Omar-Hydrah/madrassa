package com.madrassa.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

import com.madrassa.AppRepository;
import com.madrassa.MadrassaApplication;
import com.madrassa.model.Course;
import com.madrassa.response.CourseListResponse;
import com.madrassa.response.CourseResponse;

import java.util.List;

public class CourseListViewModel extends AndroidViewModel{
	public static final String TAG = "madrassa";
	private AppRepository repo;
	// public MutableLiveData<List<Course>> courses = new MutableLiveData<>();
	public MutableLiveData<CourseListResponse> courseListResponse =
		new MutableLiveData<CourseListResponse>();


	public CourseListViewModel(@NonNull Application app){
		super(app);
		repo = AppRepository.getInstance(MadrassaApplication.getContext());
	}


	public void getCourseList(){

		repo.getCourseList()
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(listResponse ->{
			courseListResponse.postValue(listResponse);
		}, throwable ->{
			Log.i(TAG, "Error occurred");
		});
	}
}