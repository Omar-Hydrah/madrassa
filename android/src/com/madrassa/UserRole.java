package com.madrassa;

public enum UserRole{
	ADMIN("admin"),
	TEACHER("teacher"),
	STUDENT("student");

	private String role;

	UserRole(String role){
		this.role = role;
	}
}