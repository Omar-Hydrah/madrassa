package com.madrassa;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import java.util.List;

import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.viewmodel.CourseListViewModel;
import com.madrassa.response.CourseListResponse;
import com.madrassa.AppRepository;
import com.madrassa.adapter.CourseAdapter;
import com.madrassa.util.Constants;

public class CourseListActivity extends AppCompatActivity{

	private RecyclerView recyclerView;
	private CourseListViewModel courseListVM;
	private CourseAdapter courseAdapter;
	private AppRepository repo;
	private List<Course> courses;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_list);

		courseListVM = ViewModelProviders.of(this)
			.get(CourseListViewModel.class);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		repo = AppRepository.getInstance(getApplicationContext());
				
		courseListVM.courseListResponse.observe(this, courseListResponse ->{
			courses = courseListResponse.getCourses();
			if(courseAdapter == null){
				courseAdapter = new CourseAdapter(this, courses);
				recyclerView.setAdapter(courseAdapter);
			}else{
				courseAdapter.notifyDataSetChanged();
			}
		});

		if(savedInstanceState == null){
			courseListVM.getCourseList();
		}
	}
}