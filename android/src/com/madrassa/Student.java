package com.madrassa;

public class Student extends User{

	public Student(String username, String password){
		super(username, password, UserRole.STUDENT);
	}
}