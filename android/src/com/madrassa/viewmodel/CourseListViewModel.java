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
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

import com.madrassa.AppRepository;
import com.madrassa.MadrassaApplication;
import com.madrassa.model.Course;
import com.madrassa.response.CourseListResponse;
import com.madrassa.response.CourseResponse;

import java.util.List;

public class CourseListViewModel{
	public static final String TAG = "madrassa";

	private AppRepository repo;
	public Single<CourseListResponse> courseListResponse;


	public CourseListViewModel(Context context){
		repo = AppRepository.getInstance(context);
		// Log.i(TAG, "Initializing CourseListVM");
	}


	public Single<CourseListResponse> getCourseList(){

		return repo.getCourseList()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread());
	}
}