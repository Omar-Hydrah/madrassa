package com.madrassa;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Arrays;

import com.madrassa.util.Constants;
import com.madrassa.viewmodel.CourseViewModel;
import com.madrassa.response.CourseResponse;
import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.model.Student;
import com.madrassa.adapter.StudentAdapter;


public class CourseDetailActivity extends AppCompatActivity{

	private CourseViewModel courseViewModel;
	private CourseResponse  courseResponse;
	private TextView courseTitle;
	private TextView courseDescription;
	private StudentAdapter studentAdapter;
	private RecyclerView recyclerView;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_detail);

		CourseDetailFragment fragment = new CourseDetailFragment();
		fragment.setArguments(getIntent().getExtras());

		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction()
			.replace(R.id.course_detail_fragment_container, 
				fragment, Constants.FRAGMENT_COURSE_DETAIL)
			// .addToBackStack(null)
			.commit();
	}

	public void handleClick(View view){
		Toast.makeText(this, "Registering course", Toast.LENGTH_SHORT).show();
	}

}