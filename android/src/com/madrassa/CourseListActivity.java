package com.madrassa;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import com.madrassa.AppRepository;
import com.madrassa.adapter.CourseAdapter;
import com.madrassa.adapter.CourseItemClickListener;
import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.viewmodel.CourseListViewModel;
import com.madrassa.response.CourseListResponse;
import com.madrassa.util.Constants;

public class CourseListActivity extends AppCompatActivity 
	implements CourseItemClickListener
{

	private RecyclerView recyclerView;
	private CourseListViewModel courseListVM;
	private CourseAdapter courseAdapter;
	private AppRepository repo;
	private List<Course> courses;
	private boolean isFragmentAvailable;

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


		ViewGroup fragmentContainer = 
			(ViewGroup) findViewById(R.id.course_detail_fragment_container);
		isFragmentAvailable = (fragmentContainer != null);
		/*if(savedInstanceState == null){
			// courseListVM.getCourseList();
			Log.i(Constants.TAG, "new activity");
		}else{
			Log.i(Constants.TAG, "restored activity");
		}
		*/
	}

	@Override
	public void handleCourseItemClick(int courseId){
		if(isFragmentAvailable){
			Bundle extras = new Bundle();
			extras.putInt(Constants.COURSE_ID, courseId);

			
			CourseDetailFragment fragment = new CourseDetailFragment();
			fragment.setArguments(extras);

			FragmentManager manager = getSupportFragmentManager();
			manager.beginTransaction()
				.replace(R.id.course_detail_fragment_container, 
					fragment, Constants.FRAGMENT_COURSE_DETAIL)
				.addToBackStack(null)
				.commit();

		}else{
			Intent courseDetailIntent = 
				new Intent(this, CourseDetailActivity.class);

			courseDetailIntent.putExtra(Constants.COURSE_ID, courseId);

			startActivity(courseDetailIntent);
		}
	}

}