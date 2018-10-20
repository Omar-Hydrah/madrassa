package com.madrassa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.util.List;

import com.madrassa.util.Constants;
import com.madrassa.model.Course;
import com.madrassa.HomeActivity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
	private List<Course> courses;
	private Context context;

	public CourseAdapter(List<Course> courses){
		this.courses = courses;
	}

	public CourseAdapter(Context context, List<Course> courses){
		this.courses = courses;
		this.context = context;
	}

	@Override
	public CourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, 
		int viewType)
	{
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.course_row, parent, false);

		return new ViewHolder(view);	
	}

	@Override
	public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position){
		Course course = courses.get(position);

		// Displaying course title, and an excerpt from the description.
		holder.courseTitle
			.setText(course.getTitle() + " by " + course.getTeacherName());
		holder.courseDescription
			.setText(course.getShortDescription());	

		holder.courseTitle.setOnClickListener(view ->{
			openCourseDetailActivity(course.getId());
		});

		holder.courseDescription.setOnClickListener(view -> {
			openCourseDetailActivity(course.getId());
		});
	}

	@Override
	public int getItemCount(){
		return courses.size();
	}

	private void openCourseDetailActivity(int courseId){
		Intent courseDetailIntent = new Intent(this.context, 
			CourseDetailActivity.class);
		courseDetailIntent.putExtra(Constants.COURSE_ID, courseId);

		this.context.startActivity(courseDetailIntent);
	}

	public class ViewHolder extends RecyclerView.ViewHolder{
		public TextView courseTitle;
		public TextView courseDescription;
		public ViewHolder(View item){
			super(item);
			courseTitle       = 
				(TextView) item.findViewById(R.id.course_title);
			courseDescription = 
				(TextView) item.findViewById(R.id.course_description);

		}
	}
}