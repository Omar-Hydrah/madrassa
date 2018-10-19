package com.madrassa.response;

import com.madrassa.model.Student;
import com.madrassa.model.Course;

public class CourseResponse{

	String message;
	boolean success;
	Course course;
	Student[] students;

	public CourseResponse(){

	}

	public CourseResponse(String message, boolean success){
		this.message = message;
		this.success = success;
	}

	public CourseResponse(String message, boolean success, Course course, 
		Student[] students)
	{
		this.message  = message;
		this.success  = success;
		this.course   = course;
		this.students = students;
	}


	public String getMessage(){return this.message;}
	public void setMessage(String message){this.message = message;}
	public boolean getSuccess(){return this.success;}
	public void setSuccess(boolean success){this.success = success;}
	public Course getCourse(){return this.course;}
	public void setCourse(Course course){this.course = course;}
	public Student[] getStudents(){return this.students;}
	public void setStudents(Student[] students){this.students = students;}

	public String toString(){
		StringBuilder studentsJson = new StringBuilder();

		if(this.students != null){
			for(int i = 0; i < this.students.length; i++){
				studentsJson.append(this.students[i].toString()); 
				studentsJson.append(",");
			}
		}
		return "{" + 
			"message: " + this.message + ", " +
			"success: " + this.success + ", " +
			"course: "  + "'" + this.course.toString() + "'" + "," +
			"students: " + studentsJson + 
		"}";
	}
}