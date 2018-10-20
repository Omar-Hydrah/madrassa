package com.madrassa;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
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

		courseTitle       = (TextView) findViewById(R.id.course_title);
		courseDescription = (TextView) findViewById(R.id.course_description);
		recyclerView      = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		courseViewModel = ViewModelProviders.of(this)
			.get(CourseViewModel.class);

		courseViewModel.courseResponse.observe(this, courseResponse ->{
			// Log.i(Constants.TAG, courseResponse.toString());
			Course course = courseResponse.getCourse();
			Log.i(Constants.TAG, courseResponse.toString());

			setTitle(course.getTitle());
			courseTitle.setText(course.getTitle());
			courseDescription.setText(course.getDescription());

			if(studentAdapter == null){

				List<User> students = Arrays.asList(
					courseResponse.getStudents());

				studentAdapter = new StudentAdapter(students);

				recyclerView.setAdapter(studentAdapter);
			}else{
				studentAdapter.notifyDataSetChanged();
			}
		});

		int courseId = getIntent().getIntExtra(Constants.COURSE_ID, 0);

		if(savedInstanceState == null){
			Log.i(Constants.TAG, "New Activity");
			courseViewModel.getCourse(courseId);
		}else{
			Log.i(Constants.TAG, "Restored Activity");
			Log.i(Constants.TAG, "course id: " + courseId);
		}

	}

	public void handleClick(View view){
		Toast.makeText(this, "Registering course", Toast.LENGTH_SHORT).show();
	}

}