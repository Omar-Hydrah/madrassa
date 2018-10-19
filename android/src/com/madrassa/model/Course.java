package com.madrassa.model;

public class Course{
	private int id;
	private int teacherId;
	private String teacher;
	private String title;
	private String description;
	private int year;
	private Student[] students;

	public Course(String title, String description){
		// super(null, null, null, title, description);
		this.title = title;
		this.description = description;
	}

	public Course(int id, int teacherId, String teacher, 
		String title, String description)
	{
		this.id          = id;
		this.teacherId   = teacherId;
		this.teacher = teacher;
		this.title       = title;
		this.description = description;
	}

	public Course(int id, int teacherId, String teacher,
		String title, String description, Student[] students, int year)
	{
		this(id, teacherId, teacher, title, description);
		this.students = students;
		this.year = year;
	}


	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; }
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
	public String getTeacherName(){return this.teacher;}
	public void setTeacherName(String name){this.teacher = name;}
	public Student[] getStudents(){return this.students;}
	public void setStudents(Student[] students){this.students = students;}

	public String toString(){
		return "{" + 
			"id: "    + id + ", " +
			"teacherName: " + teacher + ", " +
			"title: "       + title + ", " +
			"description: " + description +
			"}";
	}
}