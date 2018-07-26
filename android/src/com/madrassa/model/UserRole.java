package com.madrassa.model;

public enum UserRole{
	ADMIN("admin"),
	TEACHER("teacher"),
	STUDENT("student");

	public String role;

	UserRole(String role){
		this.role = role;
	}

	@Override
	public String toString(){
		return this.role;
	}
}