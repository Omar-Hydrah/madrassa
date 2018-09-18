package com.madrassa.model;

public class Course{
	private int courseId;
	private int teacherId;
	private String teacherName;
	private String title;
	private String description;
	private Student[] students;

	public Course(String title, String description){
		// super(null, null, null, title, description);
		this.title = title;
		this.description = description;
	}

	public Course(int courseId, int teacherId, String teacherName, 
		String title, String description)
	{
		this.courseId    = courseId;
		this.teacherId   = teacherId;
		this.teacherName = teacherName;
		this.title       = title;
		this.description = description;
	}

	public Course(int courseId, int teacherId, String teacherName,
		String title, String description, Student[] students)
	{
		this(courseId, teacherId, teacherName, title, description);
		this.students = students;
	}


	public int getCourseId() { return this.courseId; }
	public void setCourseId(int courseId) { this.courseId = courseId; }
	public int getTeacherId() { return this.teacherId; }
	public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
	public String getTitle() { return this.title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return this.description; }
	// Return a short description for presentation in UI
	public String getShortDescription()
		{return this.getShortDescription(100);}
	public String getShortDescription(int length)
		{
			if(this.description.length() < length){
				return this.description;
			}
			// Dots to indicate more text.
			return this.description.substring(0, length).trim() + "...";
		}
	public void setDescription(String description) 
		{ this.description = description; }
	public String getTeacherName(){return this.teacherName;}
	public void setTeacherName(String name){this.teacherName = name;}
	public Student[] getStudents(){return this.students;}
	public void setStudents(Student[] students){this.students = students;}

	public String toString(){
		return "{" + 
			"courseId: "    + courseId + ", " +
			"teacherName: " + teacherName + ", " +
			"title: "       + title + ", " +
			"description: " + description +
			"}";
	}
}