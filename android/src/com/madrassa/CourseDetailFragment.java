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
import android.util.Log;

import java.util.List;
import java.util.Arrays;

import com.madrassa.AppRepository;
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
	private Course     course;
	private List<User> students; 
	private User appUser;
	private TextView courseTitle;
	private TextView courseDescription;
	private StudentAdapter studentAdapter;
	private Button courseButton;
	private boolean userJoinedCourse;
	private AppRepository repo;

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
		courseButton = (Button) view.findViewById(R.id.button_course);
		courseDescription = 
			(TextView)view.findViewById(R.id.course_description);

		courseButton.setOnClickListener(v ->{
			handleClick();
		});

		repo    = AppRepository.getInstance(getContext());
		appUser = repo.getUser();
		if(!appUser.getRole().equals("student")){
			displayCourseButton(false);
			Log.i(Constants.TAG, appUser.getRole());
		}

		courseVM = ViewModelProviders.of(this)
			.get(CourseViewModel.class);

		courseVM.courseResponse.observe(this, courseResponse -> {
			List<User> students = Arrays.asList(courseResponse.getStudents());
			Course course = courseResponse.getCourse();
			courseTitle.setText(course.getTitle());
			courseDescription.setText(course.getDescription());

			this.course = course;

			for (int i = 0; i < students.size(); i++ ) {
				if(students.get(i).getId() == appUser.getId()){
					this.userJoinedCourse = true;
					// displayCourseButton(false);
					displayLeaveCourseButton();
				}
			}

			if(studentAdapter == null){
				studentAdapter = new StudentAdapter(students);
				recyclerView.setAdapter(studentAdapter);

			}else{
				studentAdapter.notifyDataSetChanged();
			}
		});

		courseVM.joinCourseResponse.observe(this, joinCourseResponse ->{
			String message  = joinCourseResponse.getMessage();
			boolean success = joinCourseResponse.isSuccess();	
			Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
		});

		courseVM.leaveCourseResponse.observe(this, leaveCourseResponse ->{
			String message  = leaveCourseResponse.getMessage();
			boolean success = leaveCourseResponse.isSuccess();
			Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
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
		/*Toast.makeText(getContext(),"Registering in course" ,
			Toast.LENGTH_SHORT).show();*/

		if(this.course == null){
			return;
		}

		int courseId = course.getId();
		if(userJoinedCourse){
			courseVM.leaveCourse(courseId);
		}else{
			courseVM.joinCourse(courseId);
		}
	}

	public void handleJoinCourse(View view){
		if(this.course == null){
			return;
		}

		int courseId = course.getId();
		if(userJoinedCourse){
			courseVM.leaveCourse(courseId);
		}else{
			courseVM.joinCourse(courseId);
		}
	}

	public void displayCourseButton(boolean display){
		if(display){
			courseButton.setVisibility(View.VISIBLE);
		}else{
			courseButton.setVisibility(View.INVISIBLE);
		}
	}

	public void displayLeaveCourseButton(){
		displayCourseButton(true);
		courseButton.setText(R.string.leave_course);
	}

}