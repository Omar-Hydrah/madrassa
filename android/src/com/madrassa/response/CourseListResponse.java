package com.madrassa.response;

import com.madrassa.model.Course;
import java.util.List;

public class CourseListResponse{
	private String message;
	private boolean success;
	private List<Course> courses;

	public CourseListResponse(){

	}

	public CourseListResponse(String message, boolean success){
		this.message = message;
		this.success = success;
	}

	public CourseListResponse(String message, boolean success, List<Course> courses){
		this.message = message;
		this.success = success;
		this.courses = courses;
	}

	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; }
	public boolean isSuccess() { return this.success; }
	public void setSuccess(boolean success) { this.success = success; }
	public List<Course> getCourses() { return this.courses; }
	public void setCourses(List<Course> courses) { this.courses = courses; }
}