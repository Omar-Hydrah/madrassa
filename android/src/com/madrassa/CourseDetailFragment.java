package com.madrassa;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;
import java.util.Arrays;

import com.madrassa.util.Constants;
import com.madrassa.viewmodel.CourseViewModel;
import com.madrassa.response.CourseResponse;
import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.model.Student;
import com.madrassa.adapter.StudentAdapter;

public class CourseDetailFragment extends Fragment {

	private RecyclerView recyclerView;
	private CourseViewModel courseVM; 
	private CourseResponse courseResponse;
	private TextView courseTitle;
	private TextView courseDescription;
	private StudentAdapter studentAdapter;
	private Button courseButton;

	public CourseDetailFragment(){

	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_course_detail, container,
			false);
		
		recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		courseTitle  = (TextView) view.findViewById(R.id.course_title);
		courseDescription = 
			(TextView)view.findViewById(R.id.course_description);
		courseButton = (Button) view.findViewById(R.id.button_course);

		courseButton.setOnClickListener(v ->{
			handleClick();
		});

		courseVM = ViewModelProviders.of(this)
			.get(CourseViewModel.class);

		courseVM.courseResponse.observe(this, courseResponse -> {
			Course course = courseResponse.getCourse();
			courseTitle.setText(course.getTitle());
			courseDescription.setText(course.getDescription());

			if(studentAdapter == null){
				List<User> students = 
					Arrays.asList(courseResponse.getStudents());

				studentAdapter = new StudentAdapter(students);
				recyclerView.setAdapter(studentAdapter);

			}else{
				studentAdapter.notifyDataSetChanged();
			}
		});

		Bundle args = getArguments();

		if(args != null){

			int courseId = args.getInt(Constants.COURSE_ID, 0);

			if(savedInstanceState == null){
				courseVM.getCourse(courseId);
			}
		}
		return view;
	}


	public void handleClick(){
		Toast.makeText(getContext(),"Registering in course" ,
			Toast.LENGTH_SHORT).show();
	}
}