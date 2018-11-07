package com.madrassa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Single;

import com.madrassa.AppRepository; 
import com.madrassa.response.CourseResponse;
import com.madrassa.response.BooleanResponse;
import com.madrassa.util.Constants;
import com.madrassa.MadrassaApplication;


public class CourseViewModel{

	private AppRepository repo;
	// public MutableLiveData<CourseResponse> courseResponse = 
	// 	new MutableLiveData<CourseResponse>();
	// public MutableLiveData<BooleanResponse> joinCourseResponse = 
	// 	new MutableLiveData<BooleanResponse>();
	// public MutableLiveData<BooleanResponse> leaveCourseResponse =
	// 	new MutableLiveData<BooleanResponse>();
	Single<CourseResponse> courseResponse;
	Single<BooleanResponse> joinCourseResponse;
	Single<BooleanResponse> leaveCourseResponse;

	public CourseViewModel(Context context){
		repo = AppRepository.getInstance(context);
	}

	public Single<CourseResponse> getCourse(int id){
		return repo.getCourse(id)
			.subscribeOn(Schedulers.io());

		// .observeOn(AndroidSchedulers.mainThread())
		// .subscribe(courseResponse -> {
		// 	// Log.i(Constants.TAG, courseResponse.toString());
		// 	this.courseResponse.postValue(courseResponse);
		// }, throwable -> {
		// 	Log.i(Constants.TAG, "Error occurred");
		// });
	}

	public Single<BooleanResponse> joinCourse(int courseId){
		return repo.joinCourse(courseId)
			.subscribeOn(Schedulers.io());

		// .observeOn(AndroidSchedulers.mainThread())
		// .subscribe(booleanResponse ->{
		// 	this.joinCourseResponse.postValue(booleanResponse);
		// }, throwable ->{
		// 	Log.i(Constants.TAG, "Error occurred");
		// });
	}

	public Single<BooleanResponse> leaveCourse(int courseId){
		return repo.leaveCourse(courseId)
			.subscribeOn(Schedulers.io());

		// .observeOn(AndroidSchedulers.mainThread())
		// .subscribe(booleanResponse ->{
		// 	this.leaveCourseResponse.postValue(booleanResponse);
		// }, throwable ->{
		// 	Log.i(Constants.TAG, "Error occurred");
		// });
	}
}