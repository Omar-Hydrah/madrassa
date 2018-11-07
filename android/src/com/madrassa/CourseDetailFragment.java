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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.CompositeDisposable;

import java.util.List;
import java.util.Arrays;

import com.madrassa.AppRepository;
import com.madrassa.util.Constants;
import com.madrassa.viewmodel.CourseViewModel;
import com.madrassa.response.CourseResponse;
import com.madrassa.response.BooleanResponse;
import com.madrassa.model.Course;
import com.madrassa.model.User;
import com.madrassa.model.Student;
import com.madrassa.adapter.StudentAdapter;

public class CourseDetailFragment extends Fragment {

	private AppRepository repo;
	private CourseViewModel courseVM; 
	private Disposable courseDisposable;
	private CourseResponse courseResponse;
	private Course     course; // that's displayed in this activity
	private List<User> students; 
	private User appUser;
	private boolean userJoinedCourse;
	private RecyclerView recyclerView;
	private StudentAdapter studentAdapter;
	private TextView courseTitle;
	private TextView courseDescription;
	private Button courseButton;

	public CourseDetailFragment(){

	}

	@Override
	public void onResume(){
		super.onResume();

		Bundle args = getArguments();
		if(args != null){
			int courseId = args.getInt(Constants.COURSE_ID, 0);
			courseDisposable = courseVM.getCourse(courseId)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(this::setCourse);
		}
	}

	@Override
	public void onPause(){
		super.onPause();
		courseDisposable.dispose();
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
			// Log.i(Constants.TAG, appUser.getRole());
		}
		courseVM = new CourseViewModel(getActivity());

		return view;
	}


	public void handleClick(){
		if(this.course == null){
			return;
		}

		int courseId = course.getId();
		if(userJoinedCourse){
			courseVM.leaveCourse(courseId)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(this::displayBooleanResponse);
		}else{
			courseVM.joinCourse(courseId)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(this::displayBooleanResponse);
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

	private void displayBooleanResponse(BooleanResponse response){
		String message  = response.getMessage();
		boolean success = response.isSuccess();
		Toast.makeText(getContext(), message , Toast.LENGTH_SHORT).show();
	}

	// Handles the observable subscribe operation
	private void setCourse(CourseResponse courseResponse){

		List<User> students = Arrays.asList(courseResponse.getStudents());
		Course course = courseResponse.getCourse();
		courseTitle.setText(course.getTitle());
		courseDescription.setText(course.getDescription());

		this.course = course;

		if(studentAdapter == null){
			studentAdapter = new StudentAdapter(students);
			recyclerView.setAdapter(studentAdapter);

		}else{
			studentAdapter.notifyDataSetChanged();
		}


		for (int i = 0; i < students.size(); i++ ) {
			if(students.get(i).getId() == appUser.getId()){
				this.userJoinedCourse = true;
				// displayCourseButton(false);
				displayLeaveCourseButton();
			}
		}
	}

}