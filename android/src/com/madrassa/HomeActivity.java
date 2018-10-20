package com.madrassa;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;

import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.viewmodel.CourseListViewModel;
import com.madrassa.response.CourseListResponse;
import com.madrassa.AppRepository;
import com.madrassa.adapter.CourseAdapter;

public class HomeActivity extends AppCompatActivity{

	private List<Course> courses;
	private RecyclerView recyclerView;
	private RecyclerView.Adapter courseAdapter;
	private CourseListViewModel courseListViewModel;
	private AppRepository repo;
	public static final String TAG = "madrassa";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		repo = AppRepository.getInstance(getApplicationContext());

		courseListViewModel = ViewModelProviders.of(this)
			.get(CourseListViewModel.class);

		courseListViewModel.courseListResponse.observe(this, 
			courseListResponse ->
		{
			courses = courseListResponse.getCourses();
			if(courseAdapter == null){
				courseAdapter = new CourseAdapter(this, courses);
				recyclerView.setAdapter(courseAdapter);
			}else{
				courseAdapter.notifyDataSetChanged();
			}
		});

	}

	
	// Allow joining courses.

	// Create a new course for teachers.
	// Prevent teachers from joining a course.
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		switch(id){
			case R.id.action_logout:

				repo.logout();
				Toast.makeText(this, "Logged out" , Toast.LENGTH_SHORT).show();

				Intent loginIntent = new Intent(this, LoginActivity.class);
				loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
					Intent.FLAG_ACTIVITY_CLEAR_TASK);

				startActivity(loginIntent);
				break;
		}
	
		return true;
	}
}