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

		repo = AppRepository.getInstance(getApplicationContext());

		// Show courses.
		/*courses = new ArrayList<Course>(); 
		courses.add(new Course(1, 1, "Omar", "Physics", 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
		courses.add(new Course(2, 2, "Ahmad", "Chemistry", 
			"Welcome to the world of Chemistry"));
		courses.add(new Course(3, 2, "Ahmad", "Astronomy", 
			"Inspecting the universe around us"));*/

		courseAdapter = new CourseAdapter(courses);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		// recyclerView.setAdapter(courseAdapter);

		final Observer<CourseListResponse> listObserver = 
			new Observer<CourseListResponse>()
		{
			@Override
			public void onChanged(@NonNull CourseListResponse listResponse){
				courses = listResponse.getCourses();

				if(courseAdapter == null){
					courseAdapter = new CourseAdapter(courses);
					recyclerView.setAdapter(courseAdapter);
				}else{
					courseAdapter.notifyDataSetChanged();
				}
			}
		};

		courseListViewModel = ViewModelProviders.of(this)
			.get(CourseListViewModel.class);
		courseListViewModel.courseListResponse.observe(this, listObserver);
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