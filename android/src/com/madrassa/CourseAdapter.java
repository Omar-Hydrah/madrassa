package com.madrassa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import java.util.List;

import com.madrassa.model.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
	private List<Course> courses;

	public CourseAdapter(List<Course> courses){
		this.courses = courses;
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

	}

	@Override
	public int getItemCount(){
		return courses.size();
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