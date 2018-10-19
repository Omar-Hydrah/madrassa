package com.madrassa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.arch.lifecycle.ViewModelProviders;


import com.madrassa.util.Constants;
import com.madrassa.viewmodel.CourseViewModel;
import com.madrassa.response.CourseResponse;


public class CourseDetailActivity extends AppCompatActivity{

	private CourseViewModel courseViewModel;
	private CourseResponse  courseResponse;
	private final String COURSE_IS_LOADED = "COURSE_IS_LOADED";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);

		courseViewModel = ViewModelProviders.of(this)
			.get(CourseViewModel.class);

		courseViewModel.courseResponse.observe(this, courseResponse ->{
			Log.i(Constants.TAG, courseResponse.toString());
		});

		int courseId = getIntent().getIntExtra(Constants.COURSE_ID, 0);
		Log.i(Constants.TAG, "course id: "+ courseId);

		if(savedInstanceState == null){
			Log.i(Constants.TAG, "New Activity");

			courseViewModel.getCourse(courseId);
		}else{
			Log.i(Constants.TAG, "Restored Activity");
			Log.i(Constants.TAG, "course id: " + courseId);
		}

	}

	/*@Override
	public void onSaveInstanceState(Bundle state){
		state.putBoolean(COURSE_IS_LOADED, true);
	}*/
}